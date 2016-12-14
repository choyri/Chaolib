package com.choyri.servlet;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.choyri.entity.Book;
import com.choyri.entity.Comment;
import com.choyri.entity.Record;
import com.choyri.entity.User;
import com.choyri.util.DAOFactory;
import com.choyri.util.HumanTime;

/**
 * 后台
 */
@WebServlet("/admin")
public class AdminServlet extends BaseServlet {

    @SuppressWarnings("unchecked")
    public Object index(HttpServletRequest request, HttpServletResponse response) {

        User login = (User) request.getSession().getAttribute("login");

        // 未登录
        if (login == null) {
            return "/";
        }

        // 图书
        List<Book> b = DAOFactory.getBookDAOInstance().findAll();
        for (Book tmp : b) {
            // 设置特殊属性 图书类别名称
            tmp.setTypeForJSP(DAOFactory.getBookTypeDAOInstance().findByID(tmp.getType()).getName());
        }

        // 借阅
        List<Record> r = DAOFactory.getRecordDAOInstance().findAll();
        for (Record tmp : r) {
            // 设置特殊属性 图书标题
            tmp.setBtitleForJSP(DAOFactory.getBookDAOInstance().findByID(tmp.getBid()).getTitle());

            // 设置特殊属性 用户昵称
            tmp.setUnameForJSP(DAOFactory.getUserDAOInstance().findByID(tmp.getUid()).getUname());

            // 人性化时间
            tmp.setTime(HumanTime.get(tmp.getTime()));
        }

        // 书评
        List<Comment> c = DAOFactory.getCommentDAOInstance().findAll();
        for (Comment tmp : c) {
            // 设置特殊属性 图书标题
            tmp.setBtitleForJSP(DAOFactory.getBookDAOInstance().findByID(tmp.getBid()).getTitle());

            // 设置特殊属性 用户昵称
            tmp.setUnameForJSP(DAOFactory.getUserDAOInstance().findByID(tmp.getUid()).getUname());

            // 人性化时间
            tmp.setTime(HumanTime.get(tmp.getTime()));
        }

        request.setAttribute("bookList", b);
        request.setAttribute("bookTypeList", DAOFactory.getBookTypeDAOInstance().findAll());
        request.setAttribute("recordList", r);
        request.setAttribute("commentList", c);
        request.setAttribute("userList", DAOFactory.getUserDAOInstance().findAll());

        return request.getRequestDispatcher("/view/admin_index.jsp");
    }
}