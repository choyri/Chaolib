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
 * 个人主页
 */
@WebServlet("/user")
public class UserServlet extends BaseServlet {

    public Object index(HttpServletRequest request, HttpServletResponse response) {

        User login = (User) request.getSession().getAttribute("login");

        // 未登录
        if (login == null) {
            return "/";
        }

        // 借阅
        List<Record> r = DAOFactory.getRecordDAOInstance().findBy(new String[] { "uid" }, new Integer[] { login.getId() });
        for (Record tmp : r) {
            Book b = DAOFactory.getBookDAOInstance().findByID(tmp.getBid());

            // 设置特殊属性 图书标题
            tmp.setBtitleForJSP(b.getTitle());

            // 设置特殊属性 ISBN
            tmp.setIsbnForJSP(b.getIsbn());

            // 人性化时间
            tmp.setTime(HumanTime.get(tmp.getTime()));
        }

        // 书评
        List<Comment> c = DAOFactory.getCommentDAOInstance().findBy("uid", login.getId());
        for (Comment tmp : c) {
            Book b = DAOFactory.getBookDAOInstance().findByID(tmp.getBid());

            // 设置特殊属性 图书标题
            tmp.setBtitleForJSP(b.getTitle());

            // 设置特殊属性 ISBN
            tmp.setIsbnForJSP(b.getIsbn());

            // 人性化时间
            tmp.setTime(HumanTime.get(tmp.getTime()));
        }

        request.setAttribute("recordList", r);
        request.setAttribute("commentList", c);

        return request.getRequestDispatcher("/view/user_index.jsp");
    }
}