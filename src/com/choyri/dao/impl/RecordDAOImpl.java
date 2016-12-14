package com.choyri.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.choyri.dao.IRecordDAO;
import com.choyri.entity.Record;
import com.choyri.util.DBConnection;

/**
 * 借阅记录数据访问对象 实现类
 */
public class RecordDAOImpl implements IRecordDAO {
    private DBConnection dbc = null;

    @Override
    public boolean Insert(Record r) {
        String sql = "INSERT INTO `record`(`bid`, `uid`) VALUES (?, ?)";
        int res = 0;
        dbc = new DBConnection();
        try (PreparedStatement stmt = dbc.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, r.getBid());
            stmt.setInt(2, r.getUid());
            res = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbc.close();
        }
        return res > 0 ? true : false;
    }

    @Override
    public boolean Update(Record r) {
        String sql = "UPDATE `record` SET `isreturn`=? WHERE `id`=?;";
        int res = 0;
        dbc = new DBConnection();
        try (PreparedStatement stmt = dbc.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, r.getIsreturn());
            stmt.setInt(2, r.getId());
            res = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbc.close();
        }
        return res == 1 ? true : false;
    }

    @Override
    public Record findByID(int id) {
        String sql = "SELECT * FROM `record` WHERE `id`=?;";
        Record r = null;
        dbc = new DBConnection();
        try (PreparedStatement stmt = dbc.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                r = new Record();
                r.setId(rs.getInt("id"));
                r.setBid(rs.getInt("bid"));
                r.setUid(rs.getInt("uid"));
                r.setTime(rs.getString("time"));
                r.setIsreturn(rs.getInt("isreturn"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbc.close();
        }
        return r;
    }

    @Override
    public List<Record> findBy(String[] type, Integer[] key) {
        String sql = "SELECT * FROM `record` WHERE";
        for (String t : type) {
            sql += " `" + t + "`=? and";
        }
        sql = sql.substring(0, sql.length() - 4) + " ORDER BY `id` DESC;";
        List<Record> list = new ArrayList<>();
        dbc = new DBConnection();
        try (PreparedStatement stmt = dbc.getConnection().prepareStatement(sql)) {
            for (int i = 0; i < key.length; i++) {
                stmt.setInt(i + 1, key[i]);
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Record r = new Record();
                r.setId(rs.getInt("id"));
                r.setBid(rs.getInt("bid"));
                r.setUid(rs.getInt("uid"));
                r.setTime(rs.getString("time"));
                r.setIsreturn(rs.getInt("isreturn"));
                list.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbc.close();
        }
        return list;
    }

    @Override
    public List<Record> findAll() {
        String sql = "SELECT * FROM `record` ORDER BY `id` DESC;";
        List<Record> list = new ArrayList<>();
        dbc = new DBConnection();
        try (ResultSet rs = dbc.getConnection().prepareStatement(sql).executeQuery()) {
            while (rs.next()) {
                Record r = new Record();
                r.setId(rs.getInt("id"));
                r.setBid(rs.getInt("bid"));
                r.setUid(rs.getInt("uid"));
                r.setTime(rs.getString("time"));
                r.setIsreturn(rs.getInt("isreturn"));
                list.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbc.close();
        }
        return list;
    }
}