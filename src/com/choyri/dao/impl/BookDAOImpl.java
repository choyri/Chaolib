package com.choyri.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.choyri.dao.IBookDAO;
import com.choyri.entity.Book;
import com.choyri.util.DBConnection;
import com.choyri.util.PageBean;

/**
 * 图书数据数据访问对象 实现类
 */
public class BookDAOImpl implements IBookDAO {
    private DBConnection dbc = null;

    @Override
    public boolean Insert(Book b) {
        String sql = "INSERT INTO `book` VALUES (null, ?, ?, ?, ?, ?, ?, ?)";
        int res = 0;
        dbc = new DBConnection();
        try (PreparedStatement stmt = dbc.getConnection().prepareStatement(sql)) {
            stmt.setString(1, b.getIsbn());
            stmt.setString(2, b.getTitle());
            stmt.setString(3, b.getAuthor());
            stmt.setInt(4, b.getType());
            stmt.setInt(5, b.getAmount());
            stmt.setString(6, b.getPrice());
            stmt.setString(7, b.getIntro());
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
        String sql = "DELETE FROM `book` WHERE `id`=?;";
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
    public boolean Update(Book b) {
        String sql = "UPDATE `book` SET `isbn`=?, `title`=?, `author`=?, `type`=?, `amount`=?, `price`=?, `intro`=? WHERE `id`=?;";
        int res = 0;
        dbc = new DBConnection();
        try (PreparedStatement stmt = dbc.getConnection().prepareStatement(sql)) {
            stmt.setString(1, b.getIsbn());
            stmt.setString(2, b.getTitle());
            stmt.setString(3, b.getAuthor());
            stmt.setInt(4, b.getType());
            stmt.setInt(5, b.getAmount());
            stmt.setString(6, b.getPrice());
            stmt.setString(7, b.getIntro());
            stmt.setInt(8, b.getId());
            res = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbc.close();
        }
        return res == 1 ? true : false;
    }

    @Override
    public Book findByID(int id) {
        String sql = "SELECT * FROM `book` WHERE `id`=?;";
        Book b = null;
        dbc = new DBConnection();
        try (PreparedStatement stmt = dbc.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                b = new Book();
                b.setId(rs.getInt("id"));
                b.setIsbn(rs.getString("isbn"));
                b.setTitle(rs.getString("title"));
                b.setAuthor(rs.getString("author"));
                b.setType(rs.getInt("type"));
                b.setAmount(rs.getInt("amount"));
                b.setPrice(rs.getString("price"));
                b.setIntro(rs.getString("intro"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbc.close();
        }
        return b;
    }

    @Override
    public Book findByISBN(String isbn) {
        String sql = "SELECT * FROM `book` WHERE `isbn`=?;";
        Book b = null;
        dbc = new DBConnection();
        try (PreparedStatement stmt = dbc.getConnection().prepareStatement(sql)) {
            stmt.setString(1, isbn);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                b = new Book();
                b.setId(rs.getInt("id"));
                b.setIsbn(rs.getString("isbn"));
                b.setTitle(rs.getString("title"));
                b.setAuthor(rs.getString("author"));
                b.setType(rs.getInt("type"));
                b.setAmount(rs.getInt("amount"));
                b.setPrice(rs.getString("price"));
                b.setIntro(rs.getString("intro"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbc.close();
        }
        return b;
    }

    public List<Book> findByType(int bid) {
        String sql = "SELECT * FROM `book` WHERE `type`=?;";
        List<Book> list = new ArrayList<>();
        dbc = new DBConnection();
        try (PreparedStatement stmt = dbc.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, bid);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Book b = new Book();
                b.setId(rs.getInt("id"));
                b.setIsbn(rs.getString("isbn"));
                b.setTitle(rs.getString("title"));
                b.setAuthor(rs.getString("author"));
                b.setType(rs.getInt("type"));
                b.setAmount(rs.getInt("amount"));
                b.setPrice(rs.getString("price"));
                b.setIntro(rs.getString("intro"));
                list.add(b);
            }
            rs.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbc.close();
        }
        return list;
    }

    public List<Book> findLike(String key) {
        String sql = "SELECT * FROM `book` WHERE CONCAT(`title`, `author`) LIKE ?;";
        List<Book> list = new ArrayList<>();
        dbc = new DBConnection();
        try (PreparedStatement stmt = dbc.getConnection().prepareStatement(sql)) {
            stmt.setString(1, "%" + key + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Book b = new Book();
                b.setId(rs.getInt("id"));
                b.setIsbn(rs.getString("isbn"));
                b.setTitle(rs.getString("title"));
                b.setAuthor(rs.getString("author"));
                b.setType(rs.getInt("type"));
                b.setAmount(rs.getInt("amount"));
                b.setPrice(rs.getString("price"));
                b.setIntro(rs.getString("intro"));
                list.add(b);
            }
            rs.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbc.close();
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Book> findAll(PageBean<Book>... pb) {
        String sql = "SELECT * FROM `book` ORDER BY `id` DESC";
        List<Book> list = new ArrayList<>();
        dbc = new DBConnection();
        boolean flag = false;

        // 如果有传入参数
        if (pb != null && pb.length == 1) {
            int count = 0;

            // 获取记录数
            try (ResultSet tmp = dbc.getConnection().prepareStatement("SELECT COUNT(*) FROM `book`;").executeQuery()) {
                if (tmp.next()) {
                    count = tmp.getInt(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            pb[0].setTotalRecord(count);
            if (pb[0].getCurrentPage() > pb[0].getTotalPage()) {
                pb[0].setCurrentPage(pb[0].getTotalPage());
            }

            sql += " LIMIT ?,?";
            flag = true;
        }
        
        sql += ";";
        try (PreparedStatement stmt = dbc.getConnection().prepareStatement(sql)) {
            // 如果是按页查询
            if (flag) {
                // 第几条记录开始
                stmt.setInt(1, (pb[0].getCurrentPage() - 1) * pb[0].getPerPage());
                
                // 每次几条记录
                stmt.setInt(2, pb[0].getPerPage());
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Book b = new Book();
                b.setId(rs.getInt("id"));
                b.setIsbn(rs.getString("isbn"));
                b.setTitle(rs.getString("title"));
                b.setAuthor(rs.getString("author"));
                b.setType(rs.getInt("type"));
                b.setAmount(rs.getInt("amount"));
                b.setPrice(rs.getString("price"));
                b.setIntro(rs.getString("intro"));
                list.add(b);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbc.close();
        }
        return list;
    }
}