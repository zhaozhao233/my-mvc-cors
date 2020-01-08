package config.security.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vo.ResponseVO;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//身份验证失败的处理程序
public class RestAuthFailHandler implements AuthenticationFailureHandler {
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        System.out.println("登录失败程序处理器");
        ResponseVO vo = new ResponseVO();
        vo.setCode("500");
        vo.setMsg("login fail-------");
        vo.setData("登录失败呀...");
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(httpServletResponse.getOutputStream(),vo);
    }
}
