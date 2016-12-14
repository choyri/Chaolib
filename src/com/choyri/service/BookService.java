package com.choyri.service;

import java.io.File;

import com.choyri.entity.Book;
import com.choyri.util.DAOFactory;

public class BookService {

    /**
     * 根据图书 ID 判断是否存在该图书
     * 
     * @param bid
     *            图书 ID
     * @return 如果存在 返回图书实例 否则返回逻辑假
     */
    public static Object has(int bid) {
        Book tmp = DAOFactory.getBookDAOInstance().findByID(bid);
        if (tmp != null) {
            return tmp;
        } else {
            return false;
        }
    }

    /**
     * 根据 ISBN 判断是否存在该图书
     * 
     * @param isbn
     *            ISBN
     * @return 如果存在 返回真 否则返回假
     */
    public static boolean has(String isbn) {
        if (DAOFactory.getBookDAOInstance().findByISBN(isbn) != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 添加图书
     * 
     * @param book
     *            Book 实例
     * @return 成功返回真 否则返回 String 或者逻辑假
     */
    public static Object insert(Book book) {
        if (DAOFactory.getBookDAOInstance().findByISBN(book.getIsbn()) != null) {
            return "书库里已有该 ISBN 的图书。";
        }
        if (DAOFactory.getBookDAOInstance().Insert(book)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除图书
     * 
     * @param bid
     *            图书 ID
     * @return 成功返回真 否则返回 String
     */
    public static Object del(int bid) {
        if (DAOFactory.getBookDAOInstance().findByID(bid) == null) {
            return "没有这本书。";
        } else {
            DAOFactory.getBookDAOInstance().Delete(bid);
            return true;
        }
    }

    /**
     * 更新图书
     * 
     * @param bid
     *            图书 ID
     * @param book
     *            Book 实例
     * @return 成功返回真 否则返回 String 或者逻辑假
     */
    public static Object update(int bid, Book book) {
        if (DAOFactory.getBookDAOInstance().findByID(bid) == null) {
            return "没有这本书。";
        }
        book.setId(bid);
        if (DAOFactory.getBookDAOInstance().Update(book)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 查询是否修改 ISBN
     * 
     * @param bid
     *            图书 ID
     * @param isbn
     *            ISBN
     * @return 修改了的话 返回原来的 ISBN 否则返回逻辑假
     */
    public static Object isChangeISBN(int bid, String isbn) {
        String oldISBN = DAOFactory.getBookDAOInstance().findByID(bid).getIsbn();
        if (oldISBN.equals(isbn)) {
            return false;
        } else {
            return oldISBN;
        }
    }

    /**
     * 根据图书 ID 返回 ISBN
     * 
     * @param bid
     *            图书 ID
     * @return ISBN
     */
    public static String getISBN(int bid) {
        return DAOFactory.getBookDAOInstance().findByID(bid).getIsbn();
    }

    /**
     * 重命名图书封面
     * 
     * @param oldName
     *            原名字
     * @param newName
     *            新名字
     * @return 成功返回真 否则返回假
     */
    public static boolean renamePic(String oldName, String newName) {
        File oldFile = new File(oldName + ".jpg");
        File newFile = new File(newName + ".jpg");
        if (oldFile.exists()) {
            if (newFile.exists()) {
                newFile.delete();
            }
            if (oldFile.renameTo(newFile)) {
                return true;
            }
        }
        return false;
    }
}