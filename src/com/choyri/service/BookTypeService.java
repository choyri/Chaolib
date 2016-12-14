package com.choyri.service;

import com.choyri.entity.BookType;
import com.choyri.util.DAOFactory;

public class BookTypeService {

    /**
     * 根据 ID 判断是否存在该图书类别
     * 
     * @param id
     *            ID
     * @return 如果存在 返回真 否则返回假
     */
    public static boolean has(int id) {
        if (DAOFactory.getBookTypeDAOInstance().findByID(id) != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 添加类别
     * 
     * @param bt
     *            BookType 实例
     * @return 成功返回真 否则返回 String 或者逻辑假
     */
    public static Object insert(BookType bt) {
        if (DAOFactory.getBookTypeDAOInstance().findByName(bt.getName()) != null) {
            return "已有该名称的类别。";
        }
        if (DAOFactory.getBookTypeDAOInstance().Insert(bt)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除类别
     * 
     * @param id
     *            类别 ID
     * @return 成功返回真 否则返回 String
     */
    public static Object del(int id) {
        if (DAOFactory.getBookTypeDAOInstance().findByID(id) == null) {
            return "没有这个类别。";
        } else {
            DAOFactory.getBookTypeDAOInstance().Delete(id);
            return true;
        }
    }

    /**
     * 更新类别名称
     * 
     * @param id
     *            类别 ID
     * @param name
     *            新名称
     * @return 成功返回真 否则返回 String 或者逻辑假
     */
    public static Object update(int id, String name) {
        BookType bt = DAOFactory.getBookTypeDAOInstance().findByID(id);
        if (bt == null) {
            return "没有这个类别";
        }
        if (DAOFactory.getBookTypeDAOInstance().findByName(name) != null) {
            return "已有该名称的类别。";
        }
        if (bt.getName().equals(name)) {
            return "名称不能跟之前一样。";
        }
        bt.setName(name);
        if (DAOFactory.getBookTypeDAOInstance().Update(bt)) {
            return true;
        } else {
            return false;
        }
    }
}