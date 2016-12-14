package com.choyri.util;

import com.choyri.dao.*;
import com.choyri.dao.impl.*;

/**
 * DAO 工厂
 */
public class DAOFactory {
    public static IBookDAO getBookDAOInstance() {
        return new BookDAOImpl();
    }

    public static IBookTypeDAO getBookTypeDAOInstance() {
        return new BookTypeDAOImpl();
    }

    public static ICommentDAO getCommentDAOInstance() {
        return new CommentDAOImpl();
    }

    public static IRecordDAO getRecordDAOInstance() {
        return new RecordDAOImpl();
    }

    public static IUserDAO getUserDAOInstance() {
        return new UserDAOImpl();
    }
}