package com.dzc.Wenda.interceptor;

import com.dzc.Wenda.model.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class LoginRequiredInterceptor implements HandlerInterceptor {

    @Autowired
    private  HostHolder hostHolder;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        if (hostHolder.getUser() == null) {
            //当用户没有登陆的时候，即HostHolder对象中没有用户数据时，重定向到登陆页面，并且把要访问的页面URL记录下来
            response.sendRedirect("/reglogin?next=" + request.getRequestURI());
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {

    }
}
