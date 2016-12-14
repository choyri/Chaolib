package com.choyri.service;

import com.choyri.entity.Comment;
import com.choyri.util.DAOFactory;

public class CommentService {

    /**
     * 发表书评
     * 
     * @param bid
     *            图书 ID
     * @param uid
     *            用户 ID
     * @param content
     *            内容
     * @return 成功返回真 否则返回假
     */
    public static boolean insert(int bid, int uid, String content) {
        if (DAOFactory.getCommentDAOInstance().Insert(new Comment(bid, uid, content))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除书评
     * 
     * @param id
     *            书评 ID
     * @return 成功返回真 否则返回 String
     */
    public static Object del(int id) {
        if (DAOFactory.getCommentDAOInstance().findByID(id) == null) {
            return "没有这条书评。";
        } else {
            DAOFactory.getCommentDAOInstance().Delete(id);
            return true;
        }
    }
}