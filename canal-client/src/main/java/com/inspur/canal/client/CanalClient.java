package com.inspur.canal.client;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.*;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class CanalClient {
    private Queue<String> sqlQueue = new ConcurrentLinkedQueue<>();

    @Resource
    private DataSource dataSource;

    public void run() {
        CanalConnector connector = CanalConnectors.newSingleConnector(new
                InetSocketAddress("192.168.1.1", 9099), "example", "", "");
        int batchSize = 1000;
        try {
            connector.connect();
            connector.subscribe(".*\\..*");
            connector.rollback();

            try {
                while (true) {
                    //尝试从master那边拉去数据batchSize条记录，有多少取多少
                    Message message = connector.getWithoutAck(batchSize);
                    long id = message.getId();
                    int size = message.getEntries().size();
                    if (id == -1 && size == 0) {
                        Thread.sleep(1000);
                    } else {
                        dataHandle(message.getEntries());
                    }
                    connector.ack(id);
                    //当队列里面堆积的sql大于一定数值的时候就模拟执行
                    if (sqlQueue.size() >= 1) {
                        executeQueueSql();
                    }
                }
            } catch (InterruptedException | InvalidProtocolBufferException e) {
                e.printStackTrace();
            }
        } finally {
            connector.disconnect();
        }
    }

    /**
     * 模拟执行队列里面的sql语句
     */
    private void executeQueueSql() {
        int size = sqlQueue.size();
        for (int i = 0; i < size; i++) {
            String poll = sqlQueue.poll();
            System.out.println("[sql]----> " + poll);
            this.execute(poll.toString());
        }
    }

    private void execute(String sql) {
        Connection con = null;
        try {
            if (sql==null){
                 con = dataSource.getConnection();
                QueryRunner queryRunner = new QueryRunner();
                int row = queryRunner.execute(con, sql);
                System.out.println("update: "+ row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(con);
        }
    }

    private void dataHandle(List<CanalEntry.Entry> entrys) throws InvalidProtocolBufferException {
        for (CanalEntry.Entry entry : entrys) {
            if (CanalEntry.EntryType.ROWDATA == entry.getEntryType()) {
                CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
                CanalEntry.EventType eventType = rowChange.getEventType();
                if (eventType == CanalEntry.EventType.DELETE) {
                    saveDeleteSql(entry);
                } else if (eventType == CanalEntry.EventType.UPDATE) {
                    saveUpdateSql(entry);
                } else if (eventType == CanalEntry.EventType.INSERT) {
                    saveInsertSql(entry);
                }
            }
        }
    }

    private void saveInsertSql(CanalEntry.Entry entry) throws InvalidProtocolBufferException {
        try {
            CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            List<CanalEntry.RowData> rowDatasList = rowChange.getRowDatasList();
            for (CanalEntry.RowData rowData : rowDatasList) {
                List<CanalEntry.Column> list = rowData.getAfterColumnsList();
                StringBuffer sql = new StringBuffer("Insert into" + entry.getHeader().getTableName() + "(");
                for (int i = 0; i < list.size(); i++) {
                    sql.append(list.get(i).getName());
                    if (i != list.size() - 1) {
                        sql.append(",");
                    }
                    sql.append(") VALUES (");
                }
                for (int i = 0; i < list.size(); i++) {
                    sql.append("'" + list.get(i).getValue() + "'");
                    if (i != list.size() - 1) {
                        sql.append(",");
                    }
                }
                sql.append(")");
                sqlQueue.add(sql.toString());
            }
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    private void saveUpdateSql(CanalEntry.Entry entry) {
        try {
            CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            List<CanalEntry.RowData> rowDatasList = rowChange.getRowDatasList();
            for (CanalEntry.RowData rowData : rowDatasList) {
                List<CanalEntry.Column> list = rowData.getAfterColumnsList();
                StringBuffer sql = new StringBuffer("update " + entry.getHeader().getTableName() + "set");
                for (int i = 0; i < list.size(); i++) {
                    sql.append(" " + list.get(i).getName() + "='" + list.get(i).getValue() + "'");
                    if (i != list.size() - 1) {
                        sql.append(",");
                    }
                    sql.append("where");
                    List<CanalEntry.Column> beforeList = rowData.getBeforeColumnsList();
                    for (CanalEntry.Column column : beforeList) {
                        if (column.getIsKey()) {
                            sql.append(column.getName() + "=" + column.getValue());
                            break;
                        }
                    }
                    sqlQueue.add(sql.toString());
                }
            }
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    private void saveDeleteSql(CanalEntry.Entry entry) {
        try {
            CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            List<CanalEntry.RowData> rowDatasList = rowChange.getRowDatasList();
            for (CanalEntry.RowData rowData : rowDatasList) {
                List<CanalEntry.Column> list = rowData.getBeforeColumnsList();
                StringBuffer sql = new StringBuffer("Delete from" + entry.getHeader().getTableName() + "where");
                for (CanalEntry.Column column : list) {
                    if (column.getIsKey()) {
                        sql.append(column.getName() + "=" + column.getValue());
                        break;
                    }
                }
                sqlQueue.add(sql.toString());
            }
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }
}
