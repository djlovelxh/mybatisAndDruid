package dj.mybatis.service;

import com.alibaba.druid.sql.builder.SQLBuilderFactory;
import dj.mybatis.dao.IUserDao;
import dj.mybatis.dao.SecondaryUserMapper;
import dj.mybatis.entity.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by dj on 2020/2/23.
 */
public class UserService {
    //开启事务并测试
    @Transactional
    public static void main(String[] args) throws Exception {
    }
    public static void getData(){
        System.out.print("444444444");
    }
}
