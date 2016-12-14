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
    <title>${siteName}${title}</title>
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
            <blockquote class="layui-elem-quote layui-quote-nm">
                <span>窗竹影摇书案上，野泉声入砚池中。</span>
                <form id="search" action="./search" method="post">
                    <input class="layui-input" type="text" name="so" placeholder="书名、作者，搜！" autocomplete="off">
                    <button class="layui-icon">&#xe615;</button>
                </form>
            </blockquote>
            <c:choose>
                <c:when test="${fn:length(pageBean.pageData) > 0}">
                    <c:forEach var="tmp" items="${pageBean.pageData}">
                        <div class="book-list">
                            <a class="pic" href="./book/${tmp.isbn}"><img src="./pic/${tmp.isbn}.jpg" alt="${tmp.title}"></a>
                            <div>
                                <h2 class="title"><a class="book-title" href="./book/${tmp.isbn}">${tmp.title}</a><span class="author"> / ${tmp.author}</span></h2>
                                <p class="type">${tmp.typeForJSP}</p>
                                <p class="intro">${tmp.intro}</p>
                            </div>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <p class="nothing">暂无</p>
                </c:otherwise>
            </c:choose>
            <div id="page"></div>
        </div>
    </div>
    <div class="footer">&copy; 2017 ChaoLib.</div>
    <script src="./view/assets/layui.js"></script>
    <script>
        layui.config({
            base: './view/assets/',
            version: '0.0.1'
        }).use('global');
        layui.use('laypage', function() {
            layui.laypage({
                cont: 'page',
                prev: '<em><</em>',
                next: '<em>></em>',
                pages: ${pageBean.totalPage},
                groups: 3,
                curr: function() {
                    var page = location.href.match(/main\/(\d+)/);
                    return page ? page[1] : 1;
                }(), 
                jump: function(obj, first) {
                    if (!first) {
                        location.href = '${pageContext.request.contextPath}/main/' + obj.curr;
                    }
                }
            });
        });
    </script>
</body>
</html>