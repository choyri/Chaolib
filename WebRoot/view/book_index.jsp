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
    <title>${book.title} - ${siteName}</title>
    <link rel="stylesheet" type="text/css" href="./view/assets/css/layui.css">
    <link rel="stylesheet" type="text/css" href="./view/assets/global.css">
    <link rel="shortcut icon" href="./view/assets/favicon.ico">
</head>
<body>
    <div class="layui-header">
        <div class="layui-main">
            <a class="logo" href="">${siteName}</a>
            <ul class="layui-nav">
                <c:choose>
                    <c:when test="${login != null}">
                        <li class="layui-nav-item layui-this"><a href="./user">${login.uname}</a></li>
                        <c:if test="${login.type != 0}">
                        <li class="layui-nav-item"><a href="./admin">管理</a></li>
                        </c:if>
                        <li class="layui-nav-item"><a href="./login?m=out&url=${redirect}">注销</a></li>
                    </c:when>
                    <c:otherwise>
                        <li class="layui-nav-item layui-this"><a href="./login?${redirect}">登录</a></li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
    <div class="layui-main">
        <ul class="layui-tree">
            <li><h2>类别</h2></li>
            <c:forEach var="tmp" items="${bookTypeList}">
            <li><a href="./btype/${tmp.id}"><cite>${tmp.name}</cite></a></li>
            </c:forEach>
        </ul>
        <div class="content">
            <div class="book-detail">
                <div class="wrap">
                    <div class="cover">
                        <img src="./pic/${book.isbn}.jpg" alt="${book.title}">
                    </div>
                    <div class="info">
                        <h2 class="title">${book.title}</h2>
                        <p class="author">${book.author}</p>
                        <p>类型：<span>${book.typeForJSP}</span></p>
                        <p>余量：<span id="amount">${book.amount}</span></p>
                        <p>定价：<span>${book.price}</span></p>
                        <c:choose>
                            <c:when test="${login != null}">
                                <button class="layui-btn layui-btn-normal lib-btn" data-type="borrow" data-id="${book.id}">借阅</button>
                            </c:when>
                            <c:otherwise>
                                <button class="layui-btn layui-btn-disabled">借阅</button>
                                <p style="color:#c2c2c2;">你还没登录</p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <div class="intro">
                    <p>${book.intro}</p>
                </div>
            </div>
            <fieldset class="layui-elem-field">
                <legend>书评</legend>
                <div class="layui-field-box">
                    <div class="comment">
                    <c:choose>
                        <c:when test="${fn:length(commentList) > 0}">
                            <c:forEach var="tmp" varStatus="vs" items="${commentList}">
                            <div class="comment-list">
                                <span>${tmp.unameForJSP}</span><span>${tmp.time}</span><span class="floor">${vs.count}<sup>#</sup></span>
                                <p>${tmp.content}</p>
                            </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <p class="nothing">暂无</p>
                        </c:otherwise>
                    </c:choose>
                    </div>
                    <hr>
                    <form id="comment" class="layui-form layui-form-pane">
                        <input type="hidden" name="bid" value="${book.id}">
                        <div class="layui-form-item layui-form-text">
                            <label class="layui-form-label">写点想法</label>
                            <div class="layui-input-block">
                                <textarea class="layui-textarea" name="content" placeholder="少年辛苦终事成，莫向光明惰寸功。" minlength="5" maxlength="200" required></textarea>
                            </div>
                        </div>
                        <c:choose>
                            <c:when test="${login != null}">
                                <button class="layui-btn">立即提交</button>
                            </c:when>
                            <c:otherwise>
                                <button class="layui-btn layui-btn-disabled" disabled>立即提交</button>
                                <span style="margin-left:10px;color:#c2c2c2;">你还没登录</span>
                            </c:otherwise>
                        </c:choose>
                    </form>
                </div>
            </fieldset>
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