package com.choyri.dao;

import java.util.List;

import com.choyri.entity.Record;

/**
 * 借阅记录数据访问对象 接口
 */
public interface IRecordDAO {
    public boolean Insert(Record r);

    public boolean Update(Record r);

    public Record findByID(int id);

    public List<Record> findBy(String[] type, Integer[] key);

    public List<Record> findAll();
}