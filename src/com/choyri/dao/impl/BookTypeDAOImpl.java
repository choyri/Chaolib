package com.choyri.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.choyri.dao.IBookTypeDAO;
import com.choyri.entity.BookType;
import com.choyri.util.DBConnection;

/**
 * 图书类别数据访问对象 实现类
 */
public class BookTypeDAOImpl implements IBookTypeDAO {
    private DBConnection dbc = null;

    @Override
    public boolean Insert(BookType bt) {
        String sql = "INSERT INTO `book_type` VALUES (null, ?)";
        int res = 0;
        dbc = new DBConnection();
        try (PreparedStatement stmt = dbc.getConnection().prepareStatement(sql)) {
            stmt.setString(1, bt.getName());
            res = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbc.close();
        }
        return res > 0 ? true : false;
    }

    @Override
    public void Delete(int id) {
        String sql = "DELETE FROM `book_type` WHERE `id`=?;";
        dbc = new DBConnection();
        try (PreparedStatement stmt = dbc.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbc.close();
        }
    }

    @Override
    public boolean Update(BookType bt) {
        String sql = "UPDATE `book_type` SET `name`=? WHERE `id`=?;";
        int res = 0;
        dbc = new DBConnection();
        try (PreparedStatement stmt = dbc.getConnection().prepareStatement(sql)) {
            stmt.setString(1, bt.getName());
            stmt.setInt(2, bt.getId());
            res = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbc.close();
        }
        return res == 1 ? true : false;
    }

    @Override
    public BookType findByID(int id) {
        String sql = "SELECT * FROM `book_type` WHERE `id`=?;";
        BookType bt = null;
        dbc = new DBConnection();
        try (PreparedStatement stmt = dbc.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                bt = new BookType();
                bt.setId(rs.getInt("id"));
                bt.setName(rs.getString("name"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbc.close();
        }
        return bt;
    }

    @Override
    public BookType findByName(String name) {
        String sql = "SELECT * FROM `book_type` WHERE `name`=?;";
        BookType bt = null;
        dbc = new DBConnection();
        try (PreparedStatement stmt = dbc.getConnection().prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                bt = new BookType();
                bt.setId(rs.getInt("id"));
                bt.setName(rs.getString("name"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbc.close();
        }
        return bt;
    }

    @Override
    public List<BookType> findAll() {
        String sql = "SELECT * FROM `book_type`;";
        List<BookType> list = new ArrayList<>();
        dbc = new DBConnection();
        try (ResultSet rs = dbc.getConnection().prepareStatement(sql).executeQuery()) {
            while (rs.next()) {
                BookType bt = new BookType();
                bt.setId(rs.getInt("id"));
                bt.setName(rs.getString("name"));
                list.add(bt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbc.close();
        }
        return list;
    }
}