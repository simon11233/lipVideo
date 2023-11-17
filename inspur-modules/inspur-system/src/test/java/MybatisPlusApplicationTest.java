import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.inspur.system.InspurDemoSystemApplication;
import com.inspur.system.domain.User;
import com.inspur.system.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {InspurDemoSystemApplication.class})
public class MybatisPlusApplicationTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void test(){
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }
    @Test
    public void insert(){
        User user = new User();
        user.setUsername("simon");
        user.setPassword("1515641515456");
        user.setNickName("test");
        int insert = userMapper.insert(user);
        System.out.println(insert);
    }
    @Test
    public void update(){
        User user = new User();
        user.setId("1698982359442952194");
        user.setUsername("kenny");
        user.setNickName("kenny");
        userMapper.updateById(user);
    }
    @Test
    public void testOptimisticLocker(){
        User user = userMapper.selectById("1698982359442952194");
        user.setUsername("test2");
        userMapper.updateById(user);
    }

    @Test
    public void selectByIds(){
        List<User> users = userMapper.selectBatchIds(Arrays.asList("1", "2"));
        users.forEach(System.out::println);
    }

    @Test
    public void testmapper(){
        QueryWrapper<User> eq = new QueryWrapper<User>().eq("username", "admin").eq("nick_name", "admin");
        List<User> users = userMapper.selectList(eq);
        users.forEach(System.out::println);
    }

}
