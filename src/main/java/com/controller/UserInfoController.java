package com.controller;

import com.dao.UserInfoDao;
import com.dto.ResponseDTO;
import com.entity.UserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin(
//        "http://172.16.11.11:8848"
        value = {"http://127.0.0.1:8848"},
        methods = {
                RequestMethod.OPTIONS,
                RequestMethod.GET,
                RequestMethod.POST,
                RequestMethod.DELETE,
                RequestMethod.PUT
        }
)
@Api(value = "这是操作用户信息的控制器")
public class UserInfoController {
    @Autowired
    private UserInfoDao dao;

    @ApiOperation(value = "查询所有用户信息")
    @GetMapping("/listUserInfo")
    @ResponseBody
    public ResponseDTO listUserInfo(@RequestParam(defaultValue = "1") Integer pageNum) {
        List<UserInfo> list = dao.listUserInfo(1, 99);
        return new ResponseDTO("200","查询所有用户",list);
    }

    @PostMapping("/insertUserInfo")
    @ResponseBody
    public ResponseDTO insertUserInfo(UserInfo userInfo) {
        int i = dao.insertUserInfo(userInfo);
        System.out.println(userInfo);
        return new ResponseDTO("200","添加一个用户","操作结果"+i+"要添加的用户名字为"+userInfo.getSysUsername());
    }

    @DeleteMapping("/deleteUserInfo/{id}")
    @ResponseBody
    public ResponseDTO deleteUserInfo(@PathVariable Integer id) {
        int i = dao.deleteUserInfo(id);
        return new ResponseDTO("200","删除一个用户",i);
    }

    @PutMapping("/updateUserInfo/{id}")
    @ResponseBody
    public ResponseDTO updateUserInfo(@PathVariable Integer id,@RequestBody UserInfo userInfo) {
        System.out.println(userInfo);
        int i = dao.updateUserInfo(userInfo);
        return new ResponseDTO("200","修改用户信息",i);
    }

}
