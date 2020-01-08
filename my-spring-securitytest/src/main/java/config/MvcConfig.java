package config;

import com.alibaba.druid.pool.DruidDataSource;
import config.security.SpringLoginConfig;
import config.security.rest.RestApiSecurityConfig;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.web.servlet.config.annotation.*;

import javax.sql.DataSource;

@Configuration
@PropertySource({"classpath:db.properties"})
@EnableWebMvc
@ComponentScan({"com.service","com.dao","com.controller"})
@MapperScan({"com.dao"})
@Import(RestApiSecurityConfig.class)
public class MvcConfig implements WebMvcConfigurer {
    //在这里配置数据源
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;

    @Bean
    public DataSource getDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setPassword(password);
        dataSource.setUsername(username);
        dataSource.setUrl(url);
        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("classpath:mappers/*Mapper.xml");
        sqlSessionFactory.setMapperLocations(resources);
        // 配置数据源
        sqlSessionFactory.setDataSource(getDataSource());
        // 使用日志
        sqlSessionFactory.setConfiguration(getConfiguration());
        return sqlSessionFactory.getObject();
    }

    // 日志
    private org.apache.ibatis.session.Configuration getConfiguration() {
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setLogImpl(StdOutImpl.class);
        // 下划线变驼峰
        configuration.setMapUnderscoreToCamelCase(true);
        return configuration;
    }

    // 视图资源解析器
    public void configureViewResolvers(ViewResolverRegistry registry) {
        System.out.println("视图资源解析器启动................");
        registry.jsp("/WEB-INF/views/",".jsp");
    }

    // 静态资源
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 资源处理器
        ResourceHandlerRegistration registration = registry.addResourceHandler("/static/**/*");
        // 静态资源位置
        registration.addResourceLocations("classpath:/static/");
    }

}
