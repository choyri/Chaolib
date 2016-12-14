package com.choyri.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.choyri.util.DAOFactory;

/**
 * 搜索
 */
@WebServlet("/search")
public class SearchServlet extends BaseServlet {

    public Object index(HttpServletRequest request, HttpServletResponse response) {

        String so = request.getParameter("so");
        if (so == null || so.equals("")) {
            // 没有没有数据 返回首页
            return "/";
        }

        request.setAttribute("so", so);
        request.setAttribute("bookTypeList", DAOFactory.getBookTypeDAOInstance().findAll());
        request.setAttribute("bookList", DAOFactory.getBookDAOInstance().findLike(so));

        return request.getRequestDispatcher("/view/search_index.jsp");
    }
}