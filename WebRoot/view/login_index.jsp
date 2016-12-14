<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
    <base href="<%=basePath%>">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="description" content="ChaoLib">
    <meta name="keywords" content="图书馆,系统">
    <meta name="robots" content="none">
    <title>欢迎 - ${siteName}</title>
    <link rel="stylesheet" type="text/css" href="./view/assets/css/layui.css">
    <link rel="stylesheet" type="text/css" href="./view/assets/global.css">
    <link rel="shortcut icon" href="./view/assets/favicon.ico">
</head>
<body>
    <div class="layui-header">
        <div class="layui-main">
            <a class="logo" href="">${siteName}</a>
            <ul class="layui-nav">
                <li class="layui-nav-item layui-this"><a href="./login">登录</a></li>
            </ul>
        </div>
    </div>
    <div class="layui-main">
        <div class="content fluid">
            <h2 class="page-title">登录</h2>
            <form id="passport" class="layui-form layui-form-pane login">
                <input type="hidden" name="isRegister" value="0">
                <div class="layui-form-item">
                    <label class="layui-form-label">帐号</label>
                    <div class="layui-input-inline">
                        <input class="layui-input" type="text" name="uAccount" autocomplete="off" minlength="5" maxlength="30" lay-verify="required">
                    </div>
                </div>
                <div class="layui-form-item uname">
                    <label class="layui-form-label">昵称</label>
                    <div class="layui-input-inline">
                        <input class="layui-input" type="text" name="uName" placeholder="小明 [1-30 位]" autocomplete="off" minlength="1" maxlength="30">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">密码</label>
                    <div class="layui-input-inline">
                        <input class="layui-input" type="password" name="uPwd" autocomplete="off" minlength="6" maxlength="20" lay-verify="required">
                    </div>
                </div>
                <div class="layui-form-item">
                    <button class="layui-btn" lay-submit>确定</button>
                    <a class="register">没帐号？</a>
                </div>
            </form>
        </div>
    </div>
    <div class="footer">&copy; 2017 ChaoLib.</div>
    <script src="./view/assets/layui.js"></script>
    <script>
        layui.config({
            base: './view/assets/',
            version: '0.0.1'
        }).use('global');
    </script>
</body>
</html>