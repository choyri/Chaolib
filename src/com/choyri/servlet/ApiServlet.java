package com.choyri.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.choyri.entity.Book;
import com.choyri.entity.BookType;
import com.choyri.entity.User;
import com.choyri.service.BookService;
import com.choyri.service.BookTypeService;
import com.choyri.service.CommentService;
import com.choyri.service.RecordService;
import com.choyri.service.UserService;
import com.choyri.util.Config;

/**
 * API 提供前端交互服务
 */
@MultipartConfig
@WebServlet("/api")
public class ApiServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 禁止 GET 请求
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.write("黑人问号脸");
        out.close();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-store");
        response.setDateHeader("Expires", 0);

        PrintWriter out = response.getWriter();

        String res = "{\"res\":\"error\", \"msg\":\"黑人问号脸\"}";
        String type = (String) request.getParameter("type");
        boolean isAdmin = false;
        boolean isBoss = false;

        User login = (User) request.getSession().getAttribute("login");

        // 取得用户类型
        if (login != null) {
            if (login.getType() != 0) {
                isAdmin = true;
                if (login.getType() == 2) {
                    isBoss = true;
                }
            }
        }

        System.out.println("ApiServlet --- Start Switch --- " + type);

        switch (type) {

        // 通行操作 登录/注册
        case "passport":
            String tmpIsRegister = request.getParameter("isRegister");
            if (tmpIsRegister == null || (!tmpIsRegister.equals("0") && !tmpIsRegister.equals("1"))) {
                res = "{\"res\":\"error\", \"msg\":\"isRegister 参数错误。\"}";
                break;
            }
            Boolean isRegister = tmpIsRegister.equals("1") ? true : false;

            String passportAccount = request.getParameter("uAccount");
            if (passportAccount == null || passportAccount.equals("")) {
                res = "{\"res\":\"error\", \"msg\":\"帐号不能为空。\"}";
                break;
            }
            if (passportAccount.length() < 5 || passportAccount.length() > 30) {
                res = "{\"res\":\"error\", \"msg\":\"帐号长度须在 6-30 位之内。\"}";
                break;
            }

            String passportName = request.getParameter("uName");
            if (isRegister) {
                if (passportName == null || passportName.equals("")) {
                    res = "{\"res\":\"error\", \"msg\":\"昵称不能为空。\"}";
                    break;
                }
                if (passportName.length() > 30) {
                    res = "{\"res\":\"error\", \"msg\":\"昵称长度须在 30 位之内。\"}";
                    break;
                }
            }

            String passportPwd = request.getParameter("uPwd");
            if (passportPwd == null || passportPwd.equals("")) {
                res = "{\"res\":\"error\", \"msg\":\"密码不能为空。\"}";
                break;
            }
            if (passportPwd.length() < 6 || passportPwd.length() > 20) {
                res = "{\"res\":\"error\", \"msg\":\"密码长度须在 6-20 位之内。\"}";
                break;
            }

            Object passportRes = null;
            if (isRegister) {
                passportRes = UserService.insert(new User(passportAccount, passportName, passportPwd, 0));
            } else {
                passportRes = UserService.check(passportAccount, passportPwd);
            }
            if (passportRes instanceof Boolean) {
                if ((boolean) passportRes) {
                    res = "{\"res\":\"success\", \"msg\":\"\"}";
                } else {
                    res = "{\"res\":\"error\", \"msg\":\"发生未知错误，操作失败。\"}";
                }
            } else if (passportRes instanceof User) {
                request.getSession().setAttribute("login", passportRes);
                res = "{\"res\":\"success\", \"msg\":\"\"}";
            } else {
                res = "{\"res\":\"error\", \"msg\":\"" + passportRes + "\"}";
            }

            break;

        // 借阅
        case "borrow":
            String tmpBorrowId = request.getParameter("id");
            if (tmpBorrowId != null && !tmpBorrowId.equals("")) {
                int borrowId = 0;
                try {
                    borrowId = Integer.parseInt(tmpBorrowId);
                } catch (NumberFormatException e) {
                    res = "{\"res\":\"error\", \"msg\":\"图书 ID 必须为数字。\"}";
                    break;
                }
                Object borrowHas = BookService.has(borrowId);
                if (borrowHas instanceof Book) {
                    Object borrowInsert = RecordService.insert(borrowId, login.getId());
                    if (borrowInsert instanceof Boolean) {
                        if ((boolean) borrowInsert) {
                            res = "{\"res\":\"success\", \"msg\":\"\"}";
                        } else {
                            res = "{\"res\":\"error\", \"msg\":\"发生未知错误，借阅失败。\"}";
                        }
                    } else {
                        res = "{\"res\":\"error\", \"msg\":\"" + borrowInsert + "\"}";
                    }
                } else {
                    res = "{\"res\":\"error\", \"msg\":\"在书库里找不到这本书，请检查图书 ID 是否正确。\"}";
                }
            }

            break;

        // 发表书评
        case "addComment":
            String addCommentContent = (String) request.getParameter("content");
            String tmpAddCommentBid = request.getParameter("bid");
            if (addCommentContent != null && !addCommentContent.equals("")) {
                int addCommentBid = 0;
                try {
                    addCommentBid = Integer.parseInt(tmpAddCommentBid);
                } catch (NumberFormatException e) {
                    res = "{\"res\":\"error\", \"msg\":\"bid 必须为数字。\"}";
                    break;
                }
                Object addCommentHas = BookService.has(addCommentBid);
                if (addCommentHas instanceof Book) {
                    if (CommentService.insert(addCommentBid, login.getId(), addCommentContent)) {
                        res = "{\"res\":\"success\", \"msg\":\"\"}";
                    } else {
                        res = "{\"res\":\"error\", \"msg\":\"发生未知错误，发表失败。\"}";
                    }
                } else {
                    res = "{\"res\":\"error\", \"msg\":\"在书库里找不到这本书，请检查 bid 是否正确。\"}";
                }
            }

            break;

        // 删除书评
        case "delComment":
            if (!isAdmin) {
                break;
            }

            String tmpDelCommentId = request.getParameter("id");
            if (tmpDelCommentId != null && !tmpDelCommentId.equals("")) {
                int delCommentId = 0;
                try {
                    delCommentId = Integer.parseInt(tmpDelCommentId);
                } catch (NumberFormatException e) {
                    res = "{\"res\":\"error\", \"msg\":\"书评 ID 必须为数字。\"}";
                    break;
                }
                Object delCommentDel = CommentService.del(delCommentId);
                if (delCommentDel instanceof Boolean) {
                    res = "{\"res\":\"success\", \"msg\":\"\"}";
                } else {
                    res = "{\"res\":\"error\", \"msg\":\"" + delCommentDel + "\"}";
                }
            }

            break;

        // 用户设置
        case "userSet":
            String userSetName = request.getParameter("uName");
            String userSetPwd = request.getParameter("uPwd");

            if (userSetName != null && !userSetName.equals("") && userSetName.length() > 30) {
                res = "{\"res\":\"error\", \"msg\":\"用户昵称长度须在 30 位之内。\"}";
                break;
            }
            if (userSetPwd != null && !userSetPwd.equals("") && (userSetPwd.length() < 6 || userSetPwd.length() > 20)) {
                res = "{\"res\":\"error\", \"msg\":\"用户密码长度须在 6-20 位之内。\"}";
                break;
            }
            Object userSetRes = UserService.update(login.getId(), new User("", userSetName, userSetPwd, -1));
            if (userSetRes instanceof Boolean) {
                if ((boolean) userSetRes) {
                    res = "{\"res\":\"success\", \"msg\":\"\"}";
                    if (!userSetName.equals("")) {
                        login.setUname(userSetName);
                        request.getSession().setAttribute("login", login);
                    }
                } else {
                    res = "{\"res\":\"error\", \"msg\":\"发生未知错误，操作失败。\"}";
                }
            } else {
                res = "{\"res\":\"error\", \"msg\":\"" + userSetRes + "\"}";
            }

            break;

        // 上传图书封面
        case "bookPic":
            if (!isAdmin) {
                break;
            }

            Part bookPicP = request.getPart("pic");

            if (bookPicP.getContentType().split("/")[1].indexOf("jpeg") < 0) {
                res = "{\"res\":\"error\", \"msg\":\"图片格式仅限 jpg。\"}";
            }

            String picName = UUID.randomUUID().toString();
            bookPicP.write(getServletContext().getRealPath(Config.get("picPath")) + "/" + picName + ".jpg");

            res = "{\"res\":\"success\", \"msg\":\"" + picName + "\"}";

            break;

        // 图书操作
        case "bookHandle":
            if (!isAdmin) {
                break;
            }

            String tmpIsEditBook = request.getParameter("isEdit");
            if (tmpIsEditBook == null || (!tmpIsEditBook.equals("0") && !tmpIsEditBook.equals("1"))) {
                res = "{\"res\":\"error\", \"msg\":\"isEdit 参数错误。\"}";
                break;
            }
            Boolean isEditBook = tmpIsEditBook.equals("1") ? true : false;

            String tmpBookHandleId = request.getParameter("bid");
            int bookHandleId = 0;
            if (isEditBook) {
                if (tmpBookHandleId == null || tmpBookHandleId.equals("")) {
                    res = "{\"res\":\"error\", \"msg\":\"编辑状态下 bid 不能为空。\"}";
                    break;
                }
                try {
                    bookHandleId = Integer.parseInt(tmpBookHandleId);
                } catch (NumberFormatException e) {
                    res = "{\"res\":\"error\", \"msg\":\"bid 必须为数字。\"}";
                    break;
                }
            }

            String bookHandleTitle = request.getParameter("bTitle");
            if (bookHandleTitle == null || bookHandleTitle.equals("")) {
                res = "{\"res\":\"error\", \"msg\":\"图书名称不能为空。\"}";
                break;
            }

            String bookHandleAuthor = request.getParameter("bAuthor");
            if (bookHandleAuthor == null || bookHandleAuthor.equals("")) {
                res = "{\"res\":\"error\", \"msg\":\"作者不能为空。\"}";
                break;
            }

            String bookHandleIsbn = request.getParameter("bIsbn");
            if (bookHandleIsbn == null || bookHandleIsbn.equals("")) {
                res = "{\"res\":\"error\", \"msg\":\"ISBN 不能为空。\"}";
                break;
            }
            if (!isEditBook) {
                if (BookService.has(bookHandleIsbn)) {
                    res = "{\"res\":\"error\", \"msg\":\"ISBN 重复。\"}";
                    break;
                }
            }

            String tmpBookHandleType = request.getParameter("bType");
            if (tmpBookHandleType == null || tmpBookHandleType.equals("")) {
                res = "{\"res\":\"error\", \"msg\":\"类型不能为空。\"}";
                break;
            }
            int bookHandleType = 0;
            try {
                bookHandleType = Integer.parseInt(tmpBookHandleType);
            } catch (NumberFormatException e) {
                res = "{\"res\":\"error\", \"msg\":\"类型 ID 必须为数字。\"}";
                break;
            }
            if (!BookTypeService.has(bookHandleType)) {
                res = "{\"res\":\"error\", \"msg\":\"无此类别。\"}";
                break;
            }

            String tmpBookHandleAmount = request.getParameter("bAmount");
            if (tmpBookHandleAmount == null || tmpBookHandleAmount.equals("")) {
                res = "{\"res\":\"error\", \"msg\":\"数量不能为空。\"}";
                break;
            }
            int bookHandleAmount = 0;
            try {
                bookHandleAmount = Integer.parseInt(tmpBookHandleAmount);
            } catch (NumberFormatException e) {
                res = "{\"res\":\"error\", \"msg\":\"数量必须为数字。\"}";
                break;
            }

            String bookHandlePrice = request.getParameter("bPrice");
            if (bookHandlePrice == null || bookHandlePrice.equals("")) {
                res = "{\"res\":\"error\", \"msg\":\"定价不能为空。\"}";
                break;
            }

            String bookHandleIntro = request.getParameter("bIntro");
            if (bookHandleIntro == null || bookHandleIntro.equals("")) {
                res = "{\"res\":\"error\", \"msg\":\"简介不能为空。\"}";
                break;
            }

            String bookHandlePicRes = request.getParameter("bPicRes");
            if (!isEditBook) {
                if (bookHandlePicRes == null || bookHandlePicRes.equals("")) {
                    res = "{\"res\":\"error\", \"msg\":\"封面不能为空。\"}";
                    break;
                }
            }

            String prefixPicPath = getServletContext().getRealPath(Config.get("picPath")) + "/";

            if (isEditBook) {
                Object bookHandleChange = BookService.isChangeISBN(bookHandleId, bookHandleIsbn);
                if (bookHandleChange instanceof String) {
                    BookService.renamePic(prefixPicPath + (String) bookHandleChange, prefixPicPath + bookHandleIsbn);
                }
            }

            // 编辑状态下 bPicRes 有两种情况 不为空的话表明上传了新封面 否则表明没修改封面
            if (!bookHandlePicRes.equals("")) {
                BookService.renamePic(prefixPicPath + bookHandlePicRes, prefixPicPath + bookHandleIsbn);
            }

            Book bookHandleBook = new Book(bookHandleIsbn, bookHandleTitle, bookHandleAuthor, bookHandleType,
                    bookHandleAmount, bookHandlePrice, bookHandleIntro);
            Object bookHandleRes = null;
            if (isEditBook) {
                bookHandleRes = BookService.update(bookHandleId, bookHandleBook);
            } else {
                bookHandleRes = BookService.insert(bookHandleBook);
            }
            if (bookHandleRes instanceof Boolean) {
                if ((boolean) bookHandleRes) {
                    res = "{\"res\":\"success\", \"msg\":\"\"}";
                } else {
                    res = "{\"res\":\"error\", \"msg\":\"发生未知错误，操作失败。\"}";
                }
            } else {
                res = "{\"res\":\"error\", \"msg\":\"" + bookHandleRes + "\"}";
            }

            break;

        // 归还图书
        case "returnBook":
            if (!isAdmin) {
                break;
            }

            String tmpReturnBookId = request.getParameter("id");
            if (tmpReturnBookId != null && !tmpReturnBookId.equals("")) {
                int returnBookId = 0;
                try {
                    returnBookId = Integer.parseInt(tmpReturnBookId);
                } catch (NumberFormatException e) {
                    res = "{\"res\":\"error\", \"msg\":\"图书类别 ID 必须为数字。\"}";
                    break;
                }
                Object returnBook = RecordService.back(returnBookId);
                if (returnBook instanceof Boolean) {
                    res = "{\"res\":\"success\", \"msg\":\"\"}";
                } else {
                    res = "{\"res\":\"error\", \"msg\":\"" + returnBook + "\"}";
                }
            }

            break;

        // 删除图书
        case "delBook":
            if (!isAdmin) {
                break;
            }

            String tmpDelBookId = request.getParameter("id");
            if (tmpDelBookId != null && !tmpDelBookId.equals("")) {
                int delBookId = 0;
                try {
                    delBookId = Integer.parseInt(tmpDelBookId);
                } catch (NumberFormatException e) {
                    res = "{\"res\":\"error\", \"msg\":\"图书 ID 必须为数字。\"}";
                    break;
                }
                Object delBookDel = BookService.del(delBookId);
                if (delBookDel instanceof Boolean) {
                    res = "{\"res\":\"success\", \"msg\":\"\"}";
                } else {
                    res = "{\"res\":\"error\", \"msg\":\"" + delBookDel + "\"}";
                }
            }

            break;

        // 增加图书类别
        case "addType":
            if (!isAdmin) {
                break;
            }

            String addTypeName = request.getParameter("name");
            if (addTypeName != null && !addTypeName.equals("")) {
                Object addTypeUpdate = BookTypeService.insert(new BookType(addTypeName));
                if (addTypeUpdate instanceof Boolean) {
                    if ((boolean) addTypeUpdate) {
                        res = "{\"res\":\"success\", \"msg\":\"\"}";
                    } else {
                        res = "{\"res\":\"error\", \"msg\":\"发生未知错误，新增失败。\"}";
                    }
                } else {
                    res = "{\"res\":\"error\", \"msg\":\"" + addTypeUpdate + "\"}";
                }
            }

            break;

        // 编辑图书类别
        case "editType":
            if (!isAdmin) {
                break;
            }

            String tmpEditTypeId = request.getParameter("id");
            String editTypeName = request.getParameter("name");
            if (tmpEditTypeId != null && !tmpEditTypeId.equals("") && editTypeName != null && !editTypeName.equals("")) {
                int editTypeId = 0;
                try {
                    editTypeId = Integer.parseInt(tmpEditTypeId);
                } catch (NumberFormatException e) {
                    res = "{\"res\":\"error\", \"msg\":\"图书类别 ID 必须为数字。\"}";
                    break;
                }
                Object editTypeUpdate = BookTypeService.update(editTypeId, editTypeName);
                if (editTypeUpdate instanceof Boolean) {
                    if ((boolean) editTypeUpdate) {
                        res = "{\"res\":\"success\", \"msg\":\"\"}";
                    } else {
                        res = "{\"res\":\"error\", \"msg\":\"发生未知错误，修改失败。\"}";
                    }
                } else {
                    res = "{\"res\":\"error\", \"msg\":\"" + editTypeUpdate + "\"}";
                }
            }

            break;

        // 删除图书类别
        case "delType":
            if (!isAdmin) {
                break;
            }

            String tmpDelTypeId = request.getParameter("id");
            if (tmpDelTypeId != null && !tmpDelTypeId.equals("")) {
                int delTypeId = 0;
                try {
                    delTypeId = Integer.parseInt(tmpDelTypeId);
                } catch (NumberFormatException e) {
                    res = "{\"res\":\"error\", \"msg\":\"图书类别 ID 必须为数字。\"}";
                    break;
                }
                Object delTypeDel = BookTypeService.del(delTypeId);
                if (delTypeDel instanceof Boolean) {
                    res = "{\"res\":\"success\", \"msg\":\"\"}";
                } else {
                    res = "{\"res\":\"error\", \"msg\":\"" + delTypeDel + "\"}";
                }
            }

            break;

        // 用户操作
        case "userHandle":
            if (!isBoss) {
                break;
            }

            String tmpIsEditUser = request.getParameter("isEdit");
            if (tmpIsEditUser == null || (!tmpIsEditUser.equals("0") && !tmpIsEditUser.equals("1"))) {
                res = "{\"res\":\"error\", \"msg\":\"isEdit 参数错误。\"}";
                break;
            }
            Boolean isEditUser = tmpIsEditUser.equals("1") ? true : false;

            String tmpUserHandleId = request.getParameter("uid");
            int userHandleId = 0;
            if (isEditUser) {
                if (tmpUserHandleId == null || tmpUserHandleId.equals("")) {
                    res = "{\"res\":\"error\", \"msg\":\"编辑状态下 uid 不能为空。\"}";
                    break;
                }
                try {
                    userHandleId = Integer.parseInt(tmpUserHandleId);
                } catch (NumberFormatException e) {
                    res = "{\"res\":\"error\", \"msg\":\"uid 必须为数字。\"}";
                    break;
                }
            }

            String userHandleAccount = request.getParameter("uAccount");
            if (userHandleAccount == null || userHandleAccount.equals("")) {
                res = "{\"res\":\"error\", \"msg\":\"用户帐号不能为空。\"}";
                break;
            }
            if (userHandleAccount.length() < 5 || userHandleAccount.length() > 30) {
                res = "{\"res\":\"error\", \"msg\":\"用户帐号长度须在 6-30 位之内。\"}";
                break;
            }

            String userHandleName = request.getParameter("uName");
            if (userHandleName == null || userHandleName.equals("")) {
                res = "{\"res\":\"error\", \"msg\":\"用户昵称不能为空。\"}";
                break;
            }
            if (userHandleName.length() > 30) {
                res = "{\"res\":\"error\", \"msg\":\"用户昵称长度须在 30 位之内。\"}";
                break;
            }

            String userHandlePwd = request.getParameter("uPwd");
            if (!isEditUser) {
                if (userHandlePwd == null || userHandlePwd.equals("")) {
                    res = "{\"res\":\"error\", \"msg\":\"用户密码不能为空。\"}";
                    break;
                }
                if (userHandlePwd.length() < 6 || userHandlePwd.length() > 20) {
                    res = "{\"res\":\"error\", \"msg\":\"用户密码长度须在 6-20 位之内。\"}";
                    break;
                }
            }

            String tmpUserHandleType = request.getParameter("uType");
            if (tmpUserHandleType == null || tmpUserHandleType.equals("")) {
                res = "{\"res\":\"error\", \"msg\":\"用户类型 ID 不能为空。\"}";
                break;
            }
            int userHandleType = 0;
            try {
                userHandleType = Integer.parseInt(tmpUserHandleType);
            } catch (NumberFormatException e) {
                res = "{\"res\":\"error\", \"msg\":\"用户类型 ID 必须为数字。\"}";
                break;
            }
            if (userHandleType < 0 || userHandleType > 2) {
                res = "{\"res\":\"error\", \"msg\":\"用户类型 ID 不在范围内。\"}";
                break;
            }

            User userHandleUser = new User(userHandleAccount, userHandleName, userHandlePwd, userHandleType);
            Object userHandleRes = null;
            if (isEditUser) {
                userHandleRes = UserService.update(userHandleId, userHandleUser);
            } else {
                userHandleRes = UserService.insert(userHandleUser);
            }
            if (userHandleRes instanceof Boolean) {
                if ((boolean) userHandleRes) {
                    res = "{\"res\":\"success\", \"msg\":\"\"}";
                } else {
                    res = "{\"res\":\"error\", \"msg\":\"发生未知错误，操作失败。\"}";
                }
            } else if (userHandleRes instanceof User) {
                if (isEditUser) {
                    request.getSession().setAttribute("login", userHandleRes);
                }
                res = "{\"res\":\"success\", \"msg\":\"\"}";
            } else {
                res = "{\"res\":\"error\", \"msg\":\"" + userHandleRes + "\"}";
            }

            break;

        default:
            break;
        }

        out.println(res);
        out.flush();
        out.close();
    }
}