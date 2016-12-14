package com.choyri.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.choyri.entity.BookType;
import com.choyri.util.DAOFactory;

/**
 * 图书类别
 */
@WebServlet("/btype/*")
public class BtypeServlet extends BaseServlet {

    public Object index(HttpServletRequest request, HttpServletResponse response) {

        // 获取类别 ID
        String[] arg = request.getRequestURI().substring(request.getContextPath().length() + 1).split("/");
        int bid = 0;
        if (arg.length == 2) {
            try {
                bid = Integer.parseInt(arg[1]);
            } catch (NumberFormatException e) {
                // ID 不是整数的话 返回首页
                return "/";
            }
        } else {
            // 如果没有 ID 返回首页
            return "/";
        }

        BookType bt = DAOFactory.getBookTypeDAOInstance().findByID(bid);

        // 如果没有该类别
        if (bt == null) {
            return "/";
        }

        request.setAttribute("currBookType", bt);
        request.setAttribute("bookTypeList", DAOFactory.getBookTypeDAOInstance().findAll());
        request.setAttribute("bookList", DAOFactory.getBookDAOInstance().findByType(bid));

        return request.getRequestDispatcher("/view/btype_index.jsp");
    }
}