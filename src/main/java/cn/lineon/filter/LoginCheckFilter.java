package cn.lineon.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author 刘国庆
 * @version 1.0.0
 * @ClassName LoginCheckFilter.java
 * @Description 检查用户是否登录
 * @createTime 2022年04月26日 22:54:00
 */
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    private static final  AntPathMatcher PATH_MATCHER=new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        log.info("拦截到请求：{}",request.getRequestURI());
        //1.获取本次请求URI
        String requestURI = request.getRequestURI();
        //2.定义不需要处理的请求路径
        String urls[]=new String[]{
                "/login/**",
                "/index.html"
        };
        //3.判断请求是否需要处理
        boolean check = check(urls, requestURI);
        //4.如果不需要处理直接放行
        if (check){
            filterChain.doFilter(request,response);
            return;
        }
        //5.判断登录状态，如果已登录直接放行
        if(request.getSession().getAttribute("user")!=null){
            filterChain.doFilter(request,response);
            return;
        }
        //6.如果未登录，重定向到登录界面
        response.sendRedirect("/login/index.html");
        return;
    }

    /**
     * 路径匹配，判断本次请求需不需要处理
     * @param urls
     * @param requestURI
     * @return
     */
    public boolean check(String[] urls,String requestURI){
        for (String url : urls) {
            boolean match=PATH_MATCHER.match(url,requestURI);
            if (match){
                return true;
            }
        }
        return false;
    }
}

