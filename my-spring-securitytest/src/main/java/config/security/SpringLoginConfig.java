package config.security;

import com.security.MyAuthenticationFailHandler;
import com.security.MyAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringLoginConfig extends WebSecurityConfigurerAdapter {

    /**
     * 类似于xml中的AuthenticationManager的配置
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        System.out.println("configure Authentication???????????.................................");
        // @formatter:off
        auth.inMemoryAuthentication()
                .withUser("user")
                .password(passwordEncoder().encode("123"))
                .authorities("ROLE_USER")
                .and()
                .withUser("admin")
                .password(passwordEncoder().encode("456"))
                .authorities("ROLE_ADMIN");
        // @formatter:on
    }

    /**
     * 这块的配置类似于xml中的http块的配置
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .formLogin()
                .loginPage("/loginview")    // 这个设置是打开登录页面的url
                .usernameParameter("uname") // 默认是username
                .passwordParameter("password")  // 默认是password
                .loginProcessingUrl("/dologin") // 配置的是登录的处理url，就是自定义表单的action的值
                .defaultSuccessUrl("/loginsuccess")
                .failureUrl("/loginfail")
                .successHandler(new MyAuthenticationSuccessHandler())
                .failureHandler(new MyAuthenticationFailHandler())
                .and()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/admin").authenticated();

//        http.formLogin()
//                .and()
//                .authorizeRequests()
//                .antMatchers("/admin").authenticated()
//                .antMatchers("/").anonymous();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
