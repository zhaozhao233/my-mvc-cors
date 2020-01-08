package com.controller;

import com.entity.ResponseVO;
import com.entity.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cj
 * @date 2019/12/23
 */

@Controller
@CrossOrigin(
        value = {"http://127.0.0.1:8848","http://172.16.11.11:8848"},
        methods = {RequestMethod.DELETE,RequestMethod.GET,RequestMethod.POST},
        allowCredentials = "false")
public class HomeController {


    @RequestMapping("/")
    public String home(){
        return "index";
    }


    /**
     * 设置允许的源域的时候,要设置全
     * 比如http要加上
     * 域名要正确.在案例中,不要用localhost,因为传递过来的请求头的origin值是:http://127.0.0.1
     *
     *
     * 在方法上添加CrossOrigin,也可以在类上添加这个注解(表示此类中的所有方法都可以跨域)
     *
     * 如果同时存在,结果是这样的:
     * 1.如果CrossOrigin某个属性值是可以允许多个值的,那么就是类上的设置合并方法上的设置
     * 2.如果CrossOrigin某个属性值不能设置多个值,那么就会用方法上的内容覆盖掉类上的设置
     *
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    @CrossOrigin(value = {"http://127.0.0.1:8848"},
            methods = {RequestMethod.DELETE,RequestMethod.GET},
    allowCredentials = "true")
//    @CrossOrigin
    public List<Student> listStudent(){
        List<Student> list  = new ArrayList<Student>();
        list.add(new Student(1,"abc"));
        list.add(new Student(2, "def"));
        list.add(new Student(3, "fgh"));
        return list;
    }


    @RequestMapping("/list2")
    @CrossOrigin
    public void requestNetEase(HttpServletResponse response) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://3g.163.com/touch/reconstruct/article/list/BA8E6OEOwangning/0-5.html";
        ResponseEntity<String> entity = restTemplate.getForEntity(url, String.class);
        String result = entity.getBody();
        String newsType = "BA8E6OEOwangning";
        int newsTypeIndex = result.indexOf(newsType);

        String jsonResult = result.substring(newsTypeIndex + newsType.length() + 2,
                result.length() - 2);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().print(jsonResult);

    }

    @RequestMapping("/addStu")
    @ResponseBody
    @CrossOrigin(allowCredentials = "true")
    public ResponseVO addStudent(HttpSession session,Integer id,String name) {
        Object s = session.getAttribute("s");
        String xx = "要添加的学生ID为："+id+"名字为："+name;
        return new ResponseVO("200","添加新的学生",xx);
    }

    @RequestMapping("/listStu")
    @ResponseBody
    @CrossOrigin(allowCredentials = "true")
    public ResponseVO getListStudent(HttpSession session) {
        session.setAttribute("s","直接打最难的");
        return new ResponseVO("200","ok","牛逼嚯");
    }

}
