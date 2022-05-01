package cn.lineon.service.impl;

import cn.lineon.common.R;
import cn.lineon.mapper.UserMapper;
import cn.lineon.pojo.User;
import cn.lineon.service.UserService;
import cn.lineon.utils.MD5Util;
import cn.lineon.utils.SendEmail;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.Random;

/**
 * @author 刘国庆
 * @version 1.0.0
 * @ClassName UserServiceImpl.java
 * @Description TODO
 * @createTime 2022年04月26日 23:03:00
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private SendEmail sendEmail;

    @Override
    public R<String> login(HttpServletRequest request, User user) {
        //将页面提交的用户密码password进行32位大写MD5加密处理
        user.setPassword(MD5Util.getMD5(user.getPassword(), true, 32));
        log.info("password:" + user.getPassword());
        //根据页面提交的用户名username进行数据库查询
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, user.getUsername());
        User userData = this.getOne(queryWrapper);
        //如果没有查询到则返回失败结果
        if (userData == null) {
            return R.error("登录失败！");
        }
        //进行密码比对，如果不一致则返回失败结果
        if (!user.getPassword().equals(userData.getPassword())) {
            return R.error("登录失败！");
        }
        //查看账号状态，如果为已禁用状态则返回账号已禁用结果
        if (userData.getStatus() == 0) {
            return R.error("用户已禁用！");
        }
        //登录成功，将用户username存入Session对象，并返回登录成功结果
        request.getSession().setAttribute("user", user.getUsername());
        return R.success("登录成功！");
    }

    /**
     * 生成并发送验证码
     * @param request
     * @param user
     * @return
     */
    @Override
    public R<String> verificationCode(HttpServletRequest request, User user) {
        //生成四位数验证码
        String code = String.format("%04d", new Random().nextInt(9999));
        //格式化模板
        String template = MessageFormat.format("<!DOCTYPE html>\n" +
                "<html xmlns:th=\"http://www.thymeleaf.org\">\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"description\" content=\"email code\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "</head>\n" +
                "<body>\n" +
                "<div style=\"background-color:#ECECEC; padding: 35px;\">\n" +
                "    <table cellpadding=\"0\" align=\"center\"\n" +
                "           style=\"width:100%;height: 100%; margin: 0px auto; text-align: left; position: relative; border-top-left-radius: 5px; border-top-right-radius: 5px; border-bottom-right-radius: 5px; border-bottom-left-radius: 5px; font-size: 14px; font-family:微软雅黑, 黑体; line-height: 1.5; box-shadow: rgb(153, 153, 153) 0px 0px 5px; border-collapse: collapse; background-position: initial initial; background-repeat: initial initial;background:#fff;\">\n" +
                "        <tbody>\n" +
                "        <tr>\n" +
                "            <th valign=\"middle\"\n" +
                "                style=\"height: 25px; line-height: 25px; padding: 15px 35px; border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: RGB(0,178,255); background-color: RGB(0,178,255); border-top-left-radius: 5px; border-top-right-radius: 5px; border-bottom-right-radius: 0px; border-bottom-left-radius: 0px;\">\n" +
                "                <font face=\"微软雅黑\" size=\"5\" style=\"color: rgb(255, 255, 255); \">用户注册</font>\n" +
                "            </th>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td style=\"word-break:break-all\">\n" +
                "                <div style=\"padding:25px 35px 40px; background-color:#fff;opacity:0.8;\">\n" +
                "\n" +
                "                    <h2 style=\"margin: 5px 0px; \">\n" +
                "                        <font color=\"#333333\" style=\"line-height: 20px; \">\n" +
                "                            <font style=\"line-height: 22px; \" size=\"4\">\n" +
                "                                尊敬的用户：</font>\n" +
                "                        </font>\n" +
                "                    </h2>\n" +
                "                    <!-- 中文 -->\n" +
                "                    <p>您好！感谢您的注册！，您的验证码为：<font color=\"#ff8c00\">『{0}』</font>，有效期5分钟，请尽快填写验证码完成验证！</p><br>\n" +
                "                    <!-- 英文 -->\n" +
                "                    <h2 style=\"margin: 5px 0px; \">\n" +
                "                        <font color=\"#333333\" style=\"line-height: 20px; \">\n" +
                "                            <font style=\"line-height: 22px; \" size=\"4\">\n" +
                "                                Dear user:</font>\n" +
                "                        </font>\n" +
                "                    </h2>\n" +
                "                    <p>Hello! Thank you for your registration!, Your verification code is: <font color=\"#ff8c00\">『{0}』</font>, valid for 5 minutes. Please fill in the verification code as soon as possible to complete the verification!</p>\n" +
                "                    <div style=\"width:100%;margin:0 auto;\">\n" +
                "                        <div style=\"padding:10px 10px 0;border-top:1px solid #ccc;color:#747474;margin-bottom:20px;line-height:1.3em;font-size:12px;\">\n" +
                "                            <p>蓝上小栈</p>\n" +
                "                            <p>联系我：QQ85605964</p>\n" +
                "                            <br>\n" +
                "                            <p>此为系统邮件，请勿回复<br>\n" +
                "                                Please do not reply to this system email\n" +
                "                            </p>\n" +
                "                            <!--<p>©***</p>-->\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        </tbody>\n" +
                "    </table>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>\n" +
                "\n", code);
        //发送邮件
        sendEmail.setParameter(user, "注册验证码", template).start();
        //发送成功后验证码存储到Session中
        request.getSession().setAttribute(user.getEmail(), code);
        //设置五分钟有效期
        request.getSession().setMaxInactiveInterval(5 * 60);
        return R.success("验证码发送成功！");
    }

    /**
     * 用户注册
     *
     * @param request
     * @param user
     * @return
     */
    @Override
    public R<String> register(HttpServletRequest request, User user) {
        //获取session中的验证码
        String code = (String) request.getSession().getAttribute(user.getEmail());
        //若取值为null表验证码已失效
        if (code == null) {
            return R.error("验证码已失效！");
        }
        //如果session中存储的验证码与用户提交的验证码不一致则表验证码错误
        if (!code.equals(request.getParameter("verificationCode"))) {
            return R.error("验证码错误！");
        }
        //将用户email作为用户名
        user.setUsername(user.getEmail());
        //将密码进行32位MD5加密(大写)
        user.setPassword(MD5Util.getMD5(user.getPassword(), true, 32));
        //生成sendKey，算法：雪花算法再进行16位MD5加密(大写)
        String sendkey = MD5Util.getMD5(IdWorker.getIdStr(), true, 16);
        user.setSendKey(sendkey);
        //持久化到数据库，成功返回成功反之则否
        boolean save = this.save(user);
        if (save) {
            return R.success("注册成功!");
        }
        return R.error("注册失败!");
    }
}
