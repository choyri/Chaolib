package com.choyri.dao;

import java.util.List;

import com.choyri.entity.BookType;

/**
 * 图书类别数据访问对象 接口
 */
public interface IBookTypeDAO {
    public boolean Insert(BookType bt);

    public boolean Update(BookType bt);

    public void Delete(int id);

    public BookType findByID(int id);

    public BookType findByName(String name);

    public List<BookType> findAll();
}