package cn.lineon.service;

import cn.lineon.common.R;
import cn.lineon.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author 刘国庆
 * @version 1.0.0
 * @ClassName UserService.java
 * @Description TODO
 * @createTime 2022年04月26日 23:02:00
 */
public interface UserService extends IService<User> {
    /**
     * 登录验证
     * @param request
     * @param user
     * @return
     */
    R<String> login(HttpServletRequest request, User user);

    /**
     * 获取验证码
     * @param request
     * @param user
     * @return
     */
    R<String> verificationCode(HttpServletRequest request, User user);

    R<String> register(HttpServletRequest request,User user);
}
