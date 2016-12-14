package com.choyri.dao;

import java.util.List;

import com.choyri.entity.Comment;

/**
 * 书评数据访问对象 接口
 */
public interface ICommentDAO {
    public boolean Insert(Comment c);

    public void Delete(int id);

    public Comment findByID(int id);

    public List<Comment> findBy(String type, int key);

    public List<Comment> findAll();
}