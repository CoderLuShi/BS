package cn.lineon.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 刘国庆
 * @version 1.0.0
 * @ClassName ErrorController.java
 * @Description 错误页面跳转
 * @createTime 2022年04月27日 00:02:00
 */
@Slf4j
@RestController
public class ErrorContorller {
    @RequestMapping("/404")
    public void error404(HttpServletResponse response) throws IOException {
        log.warn("404跳转");
        response.sendRedirect("/error/404.html");
    }

    @RequestMapping("/500")
    public void error500(HttpServletResponse response) throws IOException {
        log.warn("500跳转");
        response.sendRedirect("/error/500.html");
    }

}
