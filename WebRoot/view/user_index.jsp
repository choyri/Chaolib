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
    <title>${login.uname} 的个人主页 - ${siteName}</title>
    <link rel="stylesheet" type="text/css" href="./view/assets/css/layui.css">
    <link rel="stylesheet" type="text/css" href="./view/assets/global.css">
    <link rel="shortcut icon" href="./view/assets/favicon.ico">
</head>
<body>
    <div class="layui-header">
        <div class="layui-main">
            <a class="logo" href="">${siteName}</a>
            <ul class="layui-nav">
                <li class="layui-nav-item layui-this"><a href="./user">${login.uname}</a></li>
                <c:if test="${login.type != 0}">
                <li class="layui-nav-item"><a href="./admin">管理</a></li>
                </c:if>
                <li class="layui-nav-item"><a href="./login?m=out&url=${redirect}">注销</a></li>
            </ul>
        </div>
    </div>
    <div class="layui-main">
        <div class="content fluid">
            <blockquote class="layui-elem-quote layui-quote-nm"><span>人嘛，多读点书挺好的。</span></blockquote>
            <div class="layui-tab layui-tab-brief">
                <ul class="layui-tab-title">
                    <li class="layui-this">借阅</li>
                    <li>书评</li>
                    <li>设置</li>
                </ul>
                <div class="layui-tab-content">
                    <div class="layui-tab-item layui-show">
                    <c:choose>
                        <c:when test="${fn:length(recordList) > 0}">
                        <table class="layui-table book" lay-skin="nob">
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th>封面</th>
                                    <th>名称</th>
                                    <th>时间</th>
                                    <th>状态</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="tmp" varStatus="vs" items="${recordList}">
                                <tr data-id="${tmp.id}">
                                    <td>${vs.count}</td>
                                    <td><img src="./pic/${tmp.isbnForJSP}.jpg" alt="${tmp.btitleForJSP}"></td>
                                    <td><a class="book-title" href="./book/${tmp.isbnForJSP}">${tmp.btitleForJSP}</a></td>
                                    <td>${tmp.time}</td>
                                    <c:choose>
                                    <c:when test="${tmp.isreturn == 0}"><td><span class="color-orange">未还</span></td></c:when>
                                    <c:otherwise><td><span class="color-blue">已还</span></td></c:otherwise>
                                    </c:choose>
                                </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        </c:when>
                        <c:otherwise>
                        <p class="nothing">暂无</p>
                        </c:otherwise>
                    </c:choose>
                    </div>
                    <div class="layui-tab-item">
                    <c:choose>
                        <c:when test="${fn:length(commentList) > 0}">
                        <table class="layui-table" lay-skin="nob">
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th>图书</th>
                                    <th>时间</th>
                                    <th>内容</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="tmp" varStatus="vs" items="${commentList}">
                                <tr data-id="${tmp.id}">
                                    <td>${vs.count}</td>
                                    <td><a class="book-title" href="./book/${tmp.isbnForJSP}">${tmp.btitleForJSP}</a></td>
                                    <td>${tmp.time}</td>
                                    <td class="fix-td-width">${tmp.content}</td>
                                </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        </c:when>
                        <c:otherwise>
                        <p class="nothing">暂无</p>
                        </c:otherwise>
                    </c:choose>
                    </div>
                    <div class="layui-tab-item">
                        <form id="userSet" class="layui-form">
                            <div class="layui-form-item">
                                <label class="layui-form-label">帐号</label>
                                <div class="layui-input-inline">
                                    <input class="layui-input" type="text" value="${login.account}" readonly>
                                </div>
                                <div class="layui-form-mid layui-word-aux">不可修改</div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">昵称</label>
                                <div class="layui-input-block">
                                    <input class="layui-input" type="text" name="uName" placeholder="如不更改无需输入 [1-30 位]" autocomplete="off" minlength="1" maxlength="30">
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">密码</label>
                                <div class="layui-input-block">
                                    <input class="layui-input" type="password" name="uPwd" placeholder="如不更改无需输入 [6-20 位]" autocomplete="off" minlength="6" maxlength="20">
                                </div>
                            </div>
                            <div class="layui-form-item">
                              <div class="layui-input-block">
                                <button class="layui-btn" lay-submit>确定</button>
                                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                              </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
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