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
public interface PrimaryUserMapper {
    @DataSource
    List<Map<String, Object>> findAll();
}
