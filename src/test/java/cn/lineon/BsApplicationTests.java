package cn.lineon;

import cn.lineon.pojo.User;
import cn.lineon.service.UserService;
import cn.lineon.utils.SendEmail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.text.MessageFormat;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

@SpringBootTest
class BsApplicationTests {
    @Autowired
    private UserService service;
    @Autowired
    private SendEmail sendEmail;
    @Test
    void contextLoads() {
        //插入用户
        User user = new User();
        user.setName("张晨曦");
        user.setUsername("xixi");
        user.setPassword(DigestUtils.md5DigestAsHex("336699".getBytes()));
        boolean save = service.save(user);
        System.out.println(save);
    }
    @Test
    public void cdoe(){
        String template="<!DOCTYPE html>\n" +
                "<html xmlns:th=\"http://www.thymeleaf.org\">\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"description\" content=\"email code\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "</head>\n" +
                "<!--邮箱验证码模板-->\n" +
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
                "\n";
        System.out.println(String.format("%04d",new Random().nextInt(9999)));
        User user = new User();
        user.setEmail("2801941655@qq.com");
        sendEmail.setParameter(user,"最终测试", MessageFormat.format(template,"6666")).start();
    }
}
