package config.security.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity(debug = true)
public class RestApiSecurityConfig extends WebSecurityConfigurerAdapter {

//    @Override
//    public void init(WebSecurity web) throws Exception {
//        // 管理所有的安全过滤器
//        web.ignoring().antMatchers("/static/**");
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        System.out.println("RestApiSecurityConfig 身份验证管理器生成器................");
        auth.inMemoryAuthentication()
                .withUser("user")
                .password(passwordEncoder().encode("123"))
                .authorities("xxx");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("RestApiSecurityConfig 执行链执行................");
        http.cors().configurationSource(configurationSource())
                .and()
                    .csrf().disable()
                    .formLogin()
                        .usernameParameter("username")  // 提交form 的用户名
                        .passwordParameter("password")  // 提交form 的密码
                        .loginProcessingUrl("/dologin") //配置的是登录的处理url 就是自定义的表单action
                        .loginPage("/loginview")
                        .defaultSuccessUrl("/loginsuccess")
                        .successHandler(new RestAuthSuccessHandler()) //登录成功处理器
                        .failureUrl("/loginfail")
                        .failureHandler(new RestAuthFailHandler()) //登录失败处理器
                .and()
                    .exceptionHandling()
                        .authenticationEntryPoint(new RestAuthEntryPoint()) // 认证入口点
                .and()
                    .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/api/query").authenticated()
                    .antMatchers("/admin").authenticated();
    }

    // 跨域的配置
    private UrlBasedCorsConfigurationSource configurationSource(){
        CorsConfiguration config = new CorsConfiguration();
        // 允许携带 cookie
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://127.0.0.1:8848");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("HEAD");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",config);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}



















