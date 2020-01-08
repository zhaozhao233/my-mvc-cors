package com.security;

import com.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 可以确保自己的过滤器只执行一次
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        1.读取传递过来的token 此时按要求token是放在请求头部
//        2.Authorization Bearer token Authorization
//        3.jWTUtil.parse -> usern
//        4.username -> Authentication(true)
//        5.SecurityContextHolder.getContext.setAuthentication
        String authorizationHeader = request.getHeader("Authorization");
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            String jwtToken = authorizationHeader.substring(7);
            JwtUtil jwtUtil = new JwtUtil();
            jwtUtil.parseJWT(jwtToken);
            try {
                // 如果解析报错，比如过期了，直接相当于放行

            }

        }
    }
}
