package com.choyri.servlet;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.choyri.entity.Book;
import com.choyri.entity.Comment;
import com.choyri.util.DAOFactory;
import com.choyri.util.HumanTime;

/**
 * 图书详情
 */
@WebServlet("/book/*")
public class BookServlet extends BaseServlet {

    public Object index(HttpServletRequest request, HttpServletResponse response) {

        // 获取 ISBN 参数
        String[] arg = request.getRequestURI().substring(request.getContextPath().length() + 1).split("/");
        String isbn = "";
        if (arg.length == 2) {
            isbn = arg[1];
        } else {
            // 如果没有 ISBN 返回首页
            return "/";
        }

        Book b = DAOFactory.getBookDAOInstance().findByISBN(isbn);
        if (b == null) {
            // 如果没有相应的书 返回首页
            return "/";
        }

        // 设置特殊属性 图书类别名称
        b.setTypeForJSP(DAOFactory.getBookTypeDAOInstance().findByID(b.getType()).getName());

        // 书评
        List<Comment> c = DAOFactory.getCommentDAOInstance().findBy("bid", b.getId());
        for (Comment tmp : c) {
            // 设置特殊属性 用户昵称
            tmp.setUnameForJSP(DAOFactory.getUserDAOInstance().findByID(tmp.getUid()).getUname());
            
            // 人性化时间
            tmp.setTime(HumanTime.get(tmp.getTime()));
        }

        request.setAttribute("bookTypeList", DAOFactory.getBookTypeDAOInstance().findAll());
        request.setAttribute("book", b);
        request.setAttribute("commentList", c);

        return request.getRequestDispatcher("/view/book_index.jsp");
    }
}