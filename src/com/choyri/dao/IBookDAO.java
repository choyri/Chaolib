package com.choyri.dao;

import java.util.List;

import com.choyri.entity.Book;
import com.choyri.util.PageBean;

/**
 * 图书数据访问对象 接口
 */
public interface IBookDAO {
    public boolean Insert(Book b);

    public void Delete(int id);

    public boolean Update(Book b);

    public Book findByID(int id);

    public Book findByISBN(String isbn);

    public List<Book> findByType(int bid);

    public List<Book> findLike(String key);

    @SuppressWarnings("unchecked")
    public List<Book> findAll(PageBean<Book>... pb);
}