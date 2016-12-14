package com.choyri.servlet;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.choyri.entity.Book;
import com.choyri.util.DAOFactory;
import com.choyri.util.PageBean;

/**
 * 主页
 */
@WebServlet("/main/*")
public class MainServlet extends BaseServlet {

    @SuppressWarnings("unchecked")
    public Object index(HttpServletRequest request, HttpServletResponse response) {

        // 分页
        PageBean<Book> pb = new PageBean<Book>();

        // 获取页码
        String[] arg = request.getRequestURI().substring(request.getContextPath().length() + 1).split("/");
        int id = 1;
        if (arg.length == 2) {
            try {
                id = Integer.parseInt(arg[1]);
            } catch (NumberFormatException e) {
                // 页码不是整数的话 返回首页
                return "/";
            }
        }

        // 当前页
        pb.setCurrentPage(id);

        // 每页记录数
        pb.setPerPage(5);

        List<Book> b = DAOFactory.getBookDAOInstance().findAll(pb);
        for (Book tmp : b) {
            // 设置特殊属性 图书类别名称
            tmp.setTypeForJSP(DAOFactory.getBookTypeDAOInstance().findByID(tmp.getType()).getName());
        }
        pb.setPageData(b);

        // 如果当前不是第一页 则加上一个页数标题
        String title = "";
        int page = pb.getCurrentPage();
        if (page != 0 && page != 1) {
            title += " - 第 " + page + " 页";
        }

        request.setAttribute("title", title);
        request.setAttribute("bookTypeList", DAOFactory.getBookTypeDAOInstance().findAll());
        request.setAttribute("pageBean", pb);

        return request.getRequestDispatcher("/view/main_index.jsp");
    }
}