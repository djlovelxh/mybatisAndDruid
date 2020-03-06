package dj.mybatis.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * Created by dj on 2020/2/23.
 */
@Aspect
@Component
public class DynamicDataSourceAspect {
    @Before("@annotation(dataSource)")//拦截我们的注解
    public void changeDataSource(JoinPoint point, DataSource dataSource) {
        String value = dataSource.value();
        if (value.equals("primary")) {
            DataSourceType.setDataBaseType(DataSourceType.DataBaseType.primary);
        } else if (value.equals("secondary")) {
            DataSourceType.setDataBaseType(DataSourceType.DataBaseType.secondary);
        } else {
            DataSourceType.setDataBaseType(DataSourceType.DataBaseType.primary);//默认使用主数据库
        }
    }

    @After("@annotation(dataSource)") //清除数据源的配置
    public void restoreDataSource(JoinPoint point, DataSource dataSource) {
        DataSourceType.clearDataBaseType();
    }
}