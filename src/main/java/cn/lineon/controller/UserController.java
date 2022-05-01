package cn.lineon.controller;

import cn.lineon.common.R;
import cn.lineon.pojo.User;
import cn.lineon.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author 刘国庆
 * @version 1.0.0
 * @ClassName UserController.java
 * @Description LoginController
 * @createTime 2022年04月26日 22:46:00
 */
@Slf4j
@RestController
@RequestMapping("/login")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 重定向到登录页面
     *
     * @param response
     * @throws IOException
     */
    @GetMapping
    public void loginPage(HttpServletResponse response) throws IOException {
        response.sendRedirect("/login/index.html");
    }

    /**
     * 用户登录接口
     *
     * @param request
     * @param response
     * @param user
     * @return
     * @throws IOException
     */
    @PostMapping("/login")
    public R<String> login(HttpServletRequest request, HttpServletResponse response, User user) throws IOException {
        R<String> result = userService.login(request, user);
        return result;
    }

    /**
     * 用户退出接口
     * @param request
     * @return
     */
    @RequestMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        try {
            request.getSession().removeAttribute("user");
        } catch (Exception e) {
            return R.error("退出失败！");
        }
        return R.success("退出成功！");
    }

    /**
     * 获取验证码接口
     * @param request
     * @param user
     * @return
     */
    @PostMapping("verificationCode")
    public R<String> verificationCod(HttpServletRequest request, User user) {
        return userService.verificationCode(request, user);
    }

    /**
     * 注册接口
     * @param request
     * @param user
     * @return
     */
    @PostMapping("/register")
    public R<String> register(HttpServletRequest request, User user) {
        return userService.register(request, user);
    }

    /**
     * 删除单个用户
     * @param user
     * @return
     */
    @PostMapping("/deleteOne")
    public R<String> deleteOne(User user){
        boolean status = userService.removeById(user.getId());
        if (status){
            return R.success("删除成功！");
        }
        return R.error("删除失败！");
    }

    /**
     * 获取用户列表
     * @return
     */
    @RequestMapping("/getUserList")
    public R<List<User>> getUserList(){
        List<User> users = userService.list();
        for (User user : users) {
            System.out.println(user);
        }
       return R.success(users);
    }

}
