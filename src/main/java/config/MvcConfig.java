package config;

import com.alibaba.druid.pool.DruidDataSource;
//import com.controller.SwaggerConfiguration;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.*;

import javax.sql.DataSource;
import java.util.List;
import java.util.Properties;

/**
 * @author cj
 * @date 2019/11/25
 */

@Configuration
@EnableWebMvc // 什么鬼东西，加了测试有问题,（不加404），相当于<mvc:annotation-driven>，启用mvc的注解模式
@ComponentScan({"com.controller","com.service"})
@MapperScan({"com.dao"})
@PropertySource("classpath:db.properties")
//@Import(SwaggerConfiguration.class)
public class MvcConfig implements WebMvcConfigurer {

    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;

    // 数据元
    @Bean
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());

        PathMatchingResourcePatternResolver re = new PathMatchingResourcePatternResolver();
        Resource[] resources = re.getResources("classpath:mappers/*Mapper.xml");
        sqlSessionFactoryBean.setMapperLocations(resources);
        // ??
//        sqlSessionFactoryBean.setTypeAliasesPackage("com.entity");
        // 添加日志功能？
        sqlSessionFactoryBean.setConfiguration(configuration());
        // 添加分页功能？
        sqlSessionFactoryBean.setPlugins(pageInterceptor());
        return sqlSessionFactoryBean.getObject();
    }

    // 日志
    private org.apache.ibatis.session.Configuration configuration() {
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setLogImpl(StdOutImpl.class);
        configuration.setMapUnderscoreToCamelCase(true);
        return configuration;
    }

    // 分页
    private PageInterceptor pageInterceptor() {
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.put("supportMethodsArguments","true");
        properties.put("reasonable","true");
        pageInterceptor.setProperties(properties);
        return pageInterceptor;
    }

    // 视图资源解析器
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/views", ".jsp");
    }


    // 访问静态资源
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        ResourceHandlerRegistration registration
                = registry.addResourceHandler("/static/**");
        registration.addResourceLocations("classpath:/static/");

//         swagger
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

//    // 跨域的全局配置
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//       // registry.addMapping("/list"); //相当于在某个控制器方法上的requestMapping值为/list上面添加@CrossOrigin注解
//        /*registry.addMapping("/list")
//                .allowedOrigins("http://127.0.0.1:8080")
//                .allowedMethods("GET","POST","OPTIONS");
//*/
//        //地址是/**,表示所有的请求都配置了跨域
//       /* registry.addMapping("/**")
//                .allowedOrigins("http://127.0.0.1:8848")
//                .allowedMethods("GET","POST","OPTIONS");*/
//    }




}
