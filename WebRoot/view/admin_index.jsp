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
    <title>仪表盘 - ${siteName}</title>
    <link rel="stylesheet" type="text/css" href="./view/assets/css/layui.css">
    <link rel="stylesheet" type="text/css" href="./view/assets/global.css">
    <link rel="shortcut icon" href="./view/assets/favicon.ico">
</head>
<body>
    <div class="layui-header">
        <div class="layui-main">
            <a class="logo" href="">${siteName}</a>
            <ul class="layui-nav">
                <li class="layui-nav-item"><a href="./user">${login.uname}</a></li>
                <li class="layui-nav-item layui-this"><a href="./admin">管理</a></li>
                <li class="layui-nav-item"><a href="./login?m=out&url=${redirect}">注销</a></li>
            </ul>
        </div>
    </div>
    <div class="layui-main">
        <div class="content fluid">
            <div class="layui-tab layui-tab-brief">
                <ul class="layui-tab-title">
                    <li class="layui-this">图书</li>
                    <c:if test="${login.type == 2}"><li>用户</li></c:if>
                </ul>
                <div class="layui-tab-content">
                    <div class="layui-tab-item layui-show">
                        <div class="layui-tab">
                            <ul class="layui-tab-title">
                                <li class="layui-this">在架</li>
                                <li>类别</li>
                                <li>借阅</li>
                                <li>书评</li>
                                <li id="bookHandle">添加</li>
                            </ul>
                            <div class="layui-tab-content">
                                <div class="layui-tab-item layui-show">
                                <c:choose>
                                    <c:when test="${fn:length(bookList) > 0}">
                                    <table class="layui-table book" lay-skin="nob">
                                        <thead>
                                            <tr>
                                                <th>#</th>
                                                <th>封面</th>
                                                <th>详情</th>
                                                <th>余量</th>
                                                <th>操作</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="tmp" varStatus="vs" items="${bookList}">
                                            <tr data-id="${tmp.id}">
                                                <td>${vs.count}</td>
                                                <td><img src="./pic/${tmp.isbn}.jpg" alt="${tmp.title}"></td>
                                                <td>
                                                    <p class="td-isbn">${tmp.isbn}</p>
                                                    <p class="td-title">${tmp.title}</p>
                                                    <p class="td-author">${tmp.author}</p>
                                                    <p>${tmp.typeForJSP}</p>
                                                    <input class="td-type" type="hidden" value="${tmp.type}">
                                                    <input class="td-price" type="hidden" value="${tmp.price}">
                                                    <input class="td-intro" type="hidden" value="${tmp.intro}">
                                                </td>
                                                <td class="td-amount">${tmp.amount}</td>
                                                <td>
                                                    <button class="layui-btn layui-btn-small layui-btn-danger lib-btn" data-type="editBook"><i class="layui-icon">&#xe642;</i></button>
                                                    <button class="layui-btn layui-btn-small layui-btn-primary lib-btn" data-type="delBook"><i class="layui-icon">&#xe640;</i></button>
                                                </td>
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
                                    <c:when test="${fn:length(bookTypeList) > 0}">
                                    <table class="layui-table" lay-skin="nob">
                                        <thead>
                                            <tr>
                                                <th>#</th>
                                                <th>名称</th>
                                                <th>操作</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="tmp" varStatus="vs" items="${bookTypeList}">
                                            <tr data-id="${tmp.id}">
                                                <td>${vs.count}</td>
                                                <td>${tmp.name}</td>
                                                <td>
                                                    <button class="layui-btn layui-btn-small layui-btn-danger lib-btn" data-type="editType"><i class="layui-icon">&#xe642;</i></button>
                                                    <button class="layui-btn layui-btn-small layui-btn-primary lib-btn" data-type="delType"><i class="layui-icon">&#xe640;</i></button>
                                                </td>
                                            </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                    </c:when>
                                    <c:otherwise>
                                    <p class="nothing">暂无</p>
                                    </c:otherwise>
                                </c:choose>
                                    <button class="layui-btn lib-btn" data-type="addType">新增</button>
                                </div>
                                <div class="layui-tab-item">
                                <c:choose>
                                    <c:when test="${fn:length(recordList) > 0}">
                                    <table class="layui-table" lay-skin="nob">
                                        <thead>
                                            <tr>
                                                <th>#</th>
                                                <th>图书</th>
                                                <th>用户</th>
                                                <th>时间</th>
                                                <th>归还</th>
                                                <th>操作</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="tmp" varStatus="vs" items="${recordList}">
                                            <tr data-id="${tmp.id}">
                                                <td>${vs.count}</td>
                                                <td>${tmp.btitleForJSP}</td>
                                                <td>${tmp.unameForJSP}</td>
                                                <td>${tmp.time}</td>
                                                <c:choose>
                                                <c:when test="${tmp.isreturn == 0}">
                                                <td><span class="color-orange">否</span></td>
                                                <td><button class="layui-btn layui-btn-small layui-btn-danger lib-btn" data-type="returnBook"><i class="layui-icon">&#xe605;</i></button></td>
                                                </c:when>
                                                <c:otherwise>
                                                <td><span class="color-blue">是</span></td>
                                                <td>-</td>
                                                </c:otherwise>
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
                                                <th>用户</th>
                                                <th>时间</th>
                                                <th>内容</th>
                                                <th>操作</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="tmp" varStatus="vs" items="${commentList}">
                                            <tr data-id="${tmp.id}">
                                                <td>${vs.count}</td>
                                                <td>${tmp.btitleForJSP}</td>
                                                <td>${tmp.unameForJSP}</td>
                                                <td>${tmp.time}</td>
                                                <td class="fix-td-width">${tmp.content}</td>
                                                <td><button class="layui-btn layui-btn-small layui-btn-primary lib-btn" data-type="delComment"><i class="layui-icon">&#xe640;</i></button></td>
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
                                    <div class="layui-form-item">
                                        <label class="layui-form-label">封面</label>
                                        <div class="layui-input-inline">
                                            <input class="layui-upload-file" type="file" name="pic">
                                        </div>
                                        <div class="layui-form-mid color-blue" style="display: none;">已上传</div>
                                    </div>
                                    <form id="book" class="layui-form">
                                        <input type="hidden" name="isEdit" value="0">
                                        <input type="hidden" name="bid">
                                        <input type="hidden" name="bPicRes">
                                        <div class="layui-form-item">
                                            <label class="layui-form-label">名称</label>
                                            <div class="layui-input-block">
                                                <input class="layui-input" type="text" name="bTitle" placeholder="最好的我们" autocomplete="off" lay-verify="required">
                                            </div>
                                        </div>
                                        <div class="layui-form-item">
                                            <label class="layui-form-label">作者</label>
                                            <div class="layui-input-block">
                                                <input class="layui-input" type="text" name="bAuthor" placeholder="八月长安" autocomplete="off" lay-verify="required">
                                            </div>
                                        </div>
                                        <div class="layui-form-item">
                                            <label class="layui-form-label">ISBN</label>
                                            <div class="layui-input-block">
                                                <input class="layui-input" type="text" name="bIsbn" placeholder="9787540462642" autocomplete="off" lay-verify="required">
                                            </div>
                                        </div>
                                        <div class="layui-form-item">
                                            <label class="layui-form-label">类别</label>
                                            <div class="layui-input-block">
                                                <select name="bType" lay-verify="required">
                                                    <option value=""></option>
                                                    <c:forEach var="tmp" items="${bookTypeList}">
                                                    <option value="${tmp.id}">${tmp.name}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="layui-form-item">
                                            <label class="layui-form-label">数量</label>
                                            <div class="layui-input-block">
                                                <input class="layui-input" type="text" name="bAmount" placeholder="10" autocomplete="off" lay-verify="required">
                                            </div>
                                        </div>
                                        <div class="layui-form-item">
                                            <label class="layui-form-label">定价</label>
                                            <div class="layui-input-block">
                                                <input class="layui-input" type="text" name="bPrice" placeholder="55.00" autocomplete="off" lay-verify="required">
                                            </div>
                                        </div>
                                        <div class="layui-form-item layui-form-text">
                                            <label class="layui-form-label">简介</label>
                                            <div class="layui-input-block">
                                                <textarea class="layui-textarea" name="bIntro" placeholder="你总是说青春从不曾永远，而那时候的我们，就是最好的我们。" minlength="10" maxlength="500" lay-verify="required"></textarea>
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
                    <c:if test="${login.type == 2}">
                    <div class="layui-tab-item">
                        <div class="layui-tab">
                            <ul class="layui-tab-title">
                                <li class="layui-this">概览</li>
                                <li id="userHandle">添加</li>
                            </ul>
                            <div class="layui-tab-content">
                                <div class="layui-tab-item layui-show">
                                <c:choose>
                                    <c:when test="${fn:length(userList) > 0}">
                                    <table class="layui-table" lay-skin="nob">
                                        <thead>
                                            <tr>
                                                <th>#</th>
                                                <th>帐号</th>
                                                <th>昵称</th>
                                                <th>类型</th>
                                                <th>操作</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="tmp" varStatus="vs" items="${userList}">
                                            <tr data-id="${tmp.id}">
                                                <td>${vs.count}</td>
                                                <td>${tmp.account}</td>
                                                <td>${tmp.uname}</td>
                                                <td data-id="${tmp.type}">
                                                <c:choose>
                                                    <c:when test="${tmp.type == 2}"><span class="color-orange">超级管理员</span></c:when>
                                                    <c:when test="${tmp.type == 1}"><span class="color-blue">图书管理员</span></c:when>
                                                    <c:otherwise>用户</c:otherwise>
                                                </c:choose>
                                                <input class="td-uid" type="hidden" value="${tmp.id}">
                                                </td>
                                                <td><button class="layui-btn layui-btn-small layui-btn-danger lib-btn" data-type="editUser"><i class="layui-icon">&#xe642;</i></button></td>
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
                                    <form id="user" class="layui-form">
                                        <input type="hidden" name="isEdit" value="0">
                                        <input type="hidden" name="uid">
                                        <div class="layui-form-item">
                                            <label class="layui-form-label">帐号</label>
                                            <div class="layui-input-block">
                                                <input class="layui-input" type="text" name="uAccount" placeholder="xiaoming [5-30 位]" autocomplete="off" minlength="5" maxlength="30" lay-verify="required">
                                            </div>
                                        </div>
                                        <div class="layui-form-item">
                                            <label class="layui-form-label">昵称</label>
                                            <div class="layui-input-block">
                                                <input class="layui-input" type="text" name="uName" placeholder="小明 [1-30 位]" autocomplete="off" minlength="1" maxlength="30" lay-verify="required">
                                            </div>
                                        </div>
                                        <div class="layui-form-item">
                                            <label class="layui-form-label">密码</label>
                                            <div class="layui-input-block">
                                                <input class="layui-input" type="password" name="uPwd" placeholder="6-20 位" autocomplete="off" minlength="6" maxlength="20" lay-verify="required">
                                            </div>
                                        </div>
                                        <div class="layui-form-item">
                                            <label class="layui-form-label">类型</label>
                                            <div class="layui-input-block radioUType">
                                                <input type="radio" name="uType" value="0" title="用户" checked>
                                                <input type="radio" name="uType" value="1" title="图书管理员">
                                                <input type="radio" name="uType" value="2" title="超级管理员">
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
                    </c:if>
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