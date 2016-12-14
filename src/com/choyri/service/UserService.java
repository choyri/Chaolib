package com.choyri.service;

import com.choyri.entity.User;
import com.choyri.util.DAOFactory;

public class UserService {

    /**
     * 判断帐号密码是否正确
     * 
     * @param account
     *            帐号
     * @param pwd
     *            密码
     * @return 成功返回用户实例 否则返回逻辑假
     */
    public static Object check(String account, String pwd) {
        User tmp = DAOFactory.getUserDAOInstance().findByAccount(account);
        if (tmp != null && pwd.equals(tmp.getPwd())) {
            return tmp;
        } else {
            return "帐号或密码错误。";
        }
    }

    /**
     * 添加用户
     * 
     * @param user
     *            User 实例
     * @return 成功返回用户实例 否则返回 String 或者逻辑假
     */
    public static Object insert(User user) {
        if (DAOFactory.getUserDAOInstance().findByAccount(user.getAccount()) != null) {
            return "帐号已有其他人使用。";
        }
        if (DAOFactory.getUserDAOInstance().Insert(user)) {
            return DAOFactory.getUserDAOInstance().findByAccount(user.getAccount());
        } else {
            return false;
        }
    }

    /**
     * 更新用户
     * 
     * @param uid
     *            用户 ID
     * @param user
     *            User 实例
     * @return 成功返回用户实例 否则返回 String 或者逻辑假
     */
    public static Object update(int uid, User user) {
        User currUser = DAOFactory.getUserDAOInstance().findByID(uid);
        if (currUser == null) {
            return "没有这个用户。";
        }

        if (user.getAccount().equals("")) {
            user.setAccount(currUser.getAccount());
        } else {
            User tmp = DAOFactory.getUserDAOInstance().findByAccount(user.getAccount());
            if (tmp != null && tmp.getId() != uid) {
                return "帐号已有其他人使用。";
            }
        }

        if (user.getUname().equals("")) {
            user.setUname(currUser.getUname());
        }
        if (user.getPwd().equals("")) {
            user.setPwd(currUser.getPwd());
        }
        if (user.getType() < 0) {
            user.setType(currUser.getType());
        }

        user.setId(uid);

        if (DAOFactory.getUserDAOInstance().Update(user)) {
            return user;
        } else {
            return false;
        }
    }
}