package com.choyri.dao;

import java.util.List;

import com.choyri.entity.User;

/**
 * 用户数据访问对象 接口
 */
public interface IUserDAO {
    public boolean Insert(User u);

    public boolean Update(User u);

    public User findByID(int id);

    public User findByAccount(String account);

    public List<User> findAll();
}