package config.security.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vo.ResponseVO;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RestAuthSuccessHandler implements AuthenticationSuccessHandler {
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        System.out.println("登录成功的处理程序.............");
        ResponseVO responseVO = new ResponseVO();
        responseVO.setCode("200");
        responseVO.setMsg("login success----");
        responseVO.setData(authentication.getName());

        httpServletResponse.setContentType("application/json;charset=UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(httpServletResponse.getOutputStream(),responseVO);
    }
}
