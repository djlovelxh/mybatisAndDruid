package dj.mybatis.dao;

import dj.mybatis.config.DataSource;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by dj on 2020/2/23.
 */
@Component
@Mapper
public interface SecondaryUserMapper {
    //指定数据源为:secondary
    @DataSource("secondary")
    List<Map<String, Object>> findAll();
}
