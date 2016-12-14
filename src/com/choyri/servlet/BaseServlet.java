package com.choyri.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.choyri.util.Config;
import com.choyri.util.WebUtil;

/**
 * 核心 Servlet
 * 
 * 所有 Servlet [除 ApiServlet 外] 的父类
 */
public class BaseServlet extends HttpServlet {

    /**
     * 记录上一次的访问地址 用于 登录/注册 后返回原地址
     */
    public String redirect;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // 保存下一步的动作
        Object res = null;

        String uri = request.getRequestURI();
        String path = request.getContextPath();
        if (uri.equals(path)) {
            uri += "/";
        }

        redirect = uri.substring(path.length());

        String methodName = request.getParameter("m");
        if (methodName == null || methodName.equals("")) {
            // 如果不传入方法 默认执行 index
            methodName = "index";
        }

        System.out.println("URI: " + uri);
        System.out.println("Path: " + path);
        System.out.println("methodName: " + methodName);

        try {
            Class<? extends BaseServlet> clazz = this.getClass();

            Method method = null;
            try {
                method = clazz.getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            } catch (NoSuchMethodException e) {
                // 如果找不到指定的方法 则执行 index
                methodName = "index";
            }
            method = clazz.getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);

            res = method.invoke(this, request, response);

            request.setAttribute("siteName", Config.get("siteName"));
            request.setAttribute("redirect", URLEncoder.encode(redirect, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
            res = "/";
        }

        WebUtil.goTo(request, response, res);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}