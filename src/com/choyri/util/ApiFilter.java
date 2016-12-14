package com.choyri.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.choyri.entity.User;

/**
 * ApiServlet 过滤器
 * 如果没有登录 或者没有附上 type 参数 返回错误信息
 */
@WebFilter(urlPatterns = { "/api" })
public class ApiFilter implements Filter {

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
            throws IOException, ServletException {

        String type = (String) arg0.getParameter("type");
        User login = (User) ((HttpServletRequest) arg0).getSession().getAttribute("login");

        if (login != null && type != null && !type.equals("")) {
            arg2.doFilter(arg0, arg1);
        } else {
            // 特例 让通行操作通过
            if (type != null && type.equals("passport")) {
                arg2.doFilter(arg0, arg1);
            } else {
                arg1.setContentType("text/html;charset=UTF-8");
                PrintWriter out = arg1.getWriter();
                out.write("黑人问号脸");
                out.close();
            }
        }
    }

    @Override
    public void destroy() {
    }
}