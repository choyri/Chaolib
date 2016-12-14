package com.choyri.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.choyri.dao.IUserDAO;
import com.choyri.entity.User;
import com.choyri.util.DBConnection;

/**
 * 用户数据访问对象 实现类
 */
public class UserDAOImpl implements IUserDAO {
    private DBConnection dbc = null;

    @Override
    public boolean Insert(User u) {
        String sql = "INSERT INTO `user`(`account`, `uname`, `pwd`, `type`) VALUES (?, ?, ?, ?)";
        int res = 0;
        dbc = new DBConnection();
        try (PreparedStatement stmt = dbc.getConnection().prepareStatement(sql)) {
            stmt.setString(1, u.getAccount());
            stmt.setString(2, u.getUname());
            stmt.setString(3, u.getPwd());
            stmt.setInt(4, u.getType());
            res = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbc.close();
        }
        return res > 0 ? true : false;
    }

    @Override
    public boolean Update(User u) {
        String sql = "UPDATE `user` SET `account`=?, `uname`=?, `pwd`=?, `type`=? WHERE `id`=?;";
        int res = 0;
        dbc = new DBConnection();
        try (PreparedStatement stmt = dbc.getConnection().prepareStatement(sql)) {
            stmt.setString(1, u.getAccount());
            stmt.setString(2, u.getUname());
            stmt.setString(3, u.getPwd());
            stmt.setInt(4, u.getType());
            stmt.setInt(5, u.getId());
            res = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbc.close();
        }
        return res == 1 ? true : false;
    }

    @Override
    public User findByID(int id) {
        String sql = "SELECT * FROM `user` WHERE `id`=?;";
        User u = null;
        dbc = new DBConnection();
        try (PreparedStatement stmt = dbc.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                u = new User();
                u.setId(rs.getInt("id"));
                u.setAccount(rs.getString("account"));
                u.setUname(rs.getString("uname"));
                u.setPwd(rs.getString("pwd"));
                u.setType(rs.getInt("type"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbc.close();
        }
        return u;
    }

    @Override
    public User findByAccount(String account) {
        String sql = "SELECT * FROM `user` WHERE `account`=?;";
        User u = null;
        dbc = new DBConnection();
        try (PreparedStatement stmt = dbc.getConnection().prepareStatement(sql)) {
            stmt.setString(1, account);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                u = new User();
                u.setId(rs.getInt("id"));
                u.setAccount(rs.getString("account"));
                u.setUname(rs.getString("uname"));
                u.setPwd(rs.getString("pwd"));
                u.setType(rs.getInt("type"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbc.close();
        }
        return u;
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM `user`;";
        List<User> list = new ArrayList<>();
        dbc = new DBConnection();
        try (ResultSet rs = dbc.getConnection().prepareStatement(sql).executeQuery()) {
            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setAccount(rs.getString("account"));
                u.setUname(rs.getString("uname"));
                u.setPwd(rs.getString("pwd"));
                u.setType(rs.getInt("type"));
                list.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbc.close();
        }
        return list;
    }
}