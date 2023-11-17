<template>
  <div>
    <el-button type="text" @click="insert">新增Banner</el-button>
    <el-card>
      <el-table
                v-loading="listLoading"
                :data="List"
                element-loading-text="数据加载中"
                border
                fit
                highlight-current-row
                row-class-name="myClassList">
        <el-table-column type="index"/>
        <el-table-column label="标题" prop="title" align="center"/>
        <el-table-column label="图片"  prop="imageUrl" align="center">
          <template slot-scope="scope">
            <img :src="scope.row.imageUrl" :alt="scope.row.imageUrl">
          </template>
        </el-table-column>
        <el-table-column label="链接地址" prop="linkUrl" align="center"/>
        <el-table-column prop="gmtCreate" label="创建时间" align="center" :formatter="fmtTime" />
        <el-table-column prop="gmtModified" label="发布时间" align="center" :formatter="fmtTime"/>
        <el-table-column label="操作" width="150">
          <template slot-scope="scope">
          <el-button size="small" type="primary" @click="remove(scope.row.id)">删除</el-button>
          <el-button size="small" type="primary" @click="handlerEdit(scope.row)">编辑</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <el-dialog :visible.sync="dialogVideoFormVisible" title="Banner" @close="fetchData">
      <el-form ref="bannerForm" :model="banner" label-width="120px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="banner.title"></el-input>
        </el-form-item>
        <el-form-item label="图片地址" prop="imageUrl">
          <el-input v-model="banner.imageUrl"></el-input>
        </el-form-item>
        <el-form-item label="链接地址" prop="linkUrl">
          <el-input v-model="banner.linkUrl"></el-input>
        </el-form-item>
        <el-form-item label="课时排序" prop="sort">
          <el-input-number v-model="banner.sort" :min="0" controls-position="right"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
      <el-button @click="dialogVideoFormVisible = false">取 消</el-button>
      <el-button type="primary" @click="saveOrUpdateVideo">确 定</el-button>
      </div>
    </el-dialog>
    <el-pagination
      layout="total, prev, pager, next, jumper"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center;"
      @current-change="fetchData"
    />
  </div>
</template>
<script>
import {getPage } from "@/api/edu/banner";
import {save} from "../../../api/edu/banner";
export default {
  name: 'list',
  data(){
    return{
      listLoading:true,
      List:[],
      banner:{
        id:'',
        title:'',
        imageUrl:'',
        linkUrl:'',
        sort: 0,
        gmtCreate:'',
        gmtModified:''
      },
      dialogVideoFormVisible:false,
      total:0,
      page:1,
      limit:10,
    }
  },
  created() {
    this.fetchData()
  },
  methods:{
    fetchData(){
      this.listLoading=true
      getPage(this.page,this.limit).then(res=>{
       this.List = res.data.items
        this.total = res.data.total
        this.listLoading =false
      })
    },
    fmtTime(row, column, value){
      return value.substring(0,10)
    },
    handlerEdit(data){
      this.dialogVideoFormVisible=true
      this.banner=data
    },
    saveOrUpdateVideo(){
       save(this.banner).then(res=>{
          this.dialogVideoFormVisible= false
       })
      this.fetchData()
    },
    insert(){
      this.dialogVideoFormVisible= true
      Object.keys(this.banner).forEach((key) => (this.banner[key] = ''))
    }
  }
}

</script>
