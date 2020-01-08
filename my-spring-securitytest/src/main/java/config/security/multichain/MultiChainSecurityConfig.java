package config.security.multichain;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MultiChainSecurityConfig extends WebSecurityConfigurerAdapter {

    public static class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            System.out.println("BrowserSecurityConfig 身份验证管理器生成器..........");
            auth.inMemoryAuthentication()
                    .withUser("user")
                    .password(passwordEncoder().encode("123"))
                    .authorities("xxx");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
//            下面这样配置，记得调整login的地址，因为默认是/login，但这样会没有过滤链可以处理地址，会出现404错误

//            这条安全链去掉了csrf
            http.csrf().disable()
                    .antMatcher("/foo/**")
                    .formLogin().loginPage("/foo/login")
                    .and()
                    .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/fo0/admin").authenticated();
        }

        @Bean
        private PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }


    public static class ApiSecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.inMemoryAuthentication()
                    .withUser("user")
                    .password(passwordEncoder().encode("123"))
                    .authorities("xxx");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/bar/**")
                    .formLogin().loginPage("/bar/login")
                    .and()
                    .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/bar/admin").authenticated();
        }

        @Bean
        private PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }
}
