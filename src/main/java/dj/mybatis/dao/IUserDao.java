package dj.mybatis.dao;

import dj.mybatis.config.DataSource;
import dj.mybatis.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by dj on 2020/2/22.
 */
@Component
@Mapper
public interface IUserDao {
     List<User> findAll() ;

//     @DataSource("secondary")
     @DataSource
     List<Map<String, Object>> getName();
}
