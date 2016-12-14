package com.choyri.servlet;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.choyri.entity.User;

/**
 * 登录 / 注销
 */
@WebServlet("/login")
public class LoginServlet extends BaseServlet {

    public Object index(HttpServletRequest request, HttpServletResponse response) {

        User login = (User) request.getSession().getAttribute("login");

        // 已登录
        if (login != null) {
            return "/";
        }

        // 未登录
        return request.getRequestDispatcher("/view/login_index.jsp");
    }

    /**
     * 注销
     */
    public Object out(HttpServletRequest request, HttpServletResponse response) {

        request.getSession().invalidate();

        // 取 URL 参数
        String url = (String) request.getParameter("url");
        if (url != null && !url.equals("")) {
            // 解码 URL
            try {
                url = URLDecoder.decode(url, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                // 失败的话返回首页
                url = "/";
            }
        } else {
            // 没有 URL 参数的话返回首页
            url = "/";
        }

        return url;
    }
}