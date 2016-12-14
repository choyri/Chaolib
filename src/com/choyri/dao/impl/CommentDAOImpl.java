package com.choyri.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.choyri.dao.ICommentDAO;
import com.choyri.entity.Comment;
import com.choyri.util.DBConnection;

/**
 * 书评数据访问对象 实现类
 */
public class CommentDAOImpl implements ICommentDAO {
    private DBConnection dbc = null;

    @Override
    public boolean Insert(Comment c) {
        String sql = "INSERT INTO `comment`(`bid`, `uid`, `content`) VALUES (?, ?, ?)";
        int res = 0;
        dbc = new DBConnection();
        try (PreparedStatement stmt = dbc.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, c.getBid());
            stmt.setInt(2, c.getUid());
            stmt.setString(3, c.getContent());
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
        String sql = "DELETE FROM `comment` WHERE `id`=?;";
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
    public Comment findByID(int id) {
        String sql = "SELECT * FROM `comment` WHERE `id`=?;";
        Comment c = null;
        dbc = new DBConnection();
        try (PreparedStatement stmt = dbc.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                c = new Comment();
                c.setId(rs.getInt("id"));
                c.setBid(rs.getInt("bid"));
                c.setUid(rs.getInt("uid"));
                c.setTime(rs.getString("time"));
                c.setContent(rs.getString("content"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbc.close();
        }
        return c;
    }

    @Override
    public List<Comment> findBy(String type, int key) {
        if (!type.equals("bid") && !type.equals("uid")) {
            type = "bid";
        }
        String desc = type.equals("bid") ? "" : " ORDER BY `id` DESC;";
        String sql = "SELECT * FROM `comment` WHERE `" + type + "`=?" + desc;
        List<Comment> list = new ArrayList<>();
        dbc = new DBConnection();
        try (PreparedStatement stmt = dbc.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, key);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Comment c = new Comment();
                c.setId(rs.getInt("id"));
                c.setBid(rs.getInt("bid"));
                c.setUid(rs.getInt("uid"));
                c.setTime(rs.getString("time"));
                c.setContent(rs.getString("content"));
                list.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbc.close();
        }
        return list;
    }

    @Override
    public List<Comment> findAll() {
        String sql = "SELECT * FROM `comment` ORDER BY `id` DESC;";
        List<Comment> list = new ArrayList<>();
        dbc = new DBConnection();
        try (ResultSet rs = dbc.getConnection().prepareStatement(sql).executeQuery()) {
            while (rs.next()) {
                Comment c = new Comment();
                c.setId(rs.getInt("id"));
                c.setBid(rs.getInt("bid"));
                c.setUid(rs.getInt("uid"));
                c.setTime(rs.getString("time"));
                c.setContent(rs.getString("content"));
                list.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbc.close();
        }
        return list;
    }
}