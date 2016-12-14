package com.choyri.service;

import java.util.List;

import com.choyri.entity.Book;
import com.choyri.entity.Record;
import com.choyri.util.DAOFactory;

public class RecordService {

    /**
     * 借阅操作：判断是否借过；插入记录；图书数量自减
     * 
     * @param bid
     *            图书 ID
     * @param uid
     *            用户 ID
     * @return 成功返回真 否则返回 String 或者逻辑假
     */
    public static Object insert(int bid, int uid) {
        List<Record> recondList = DAOFactory.getRecordDAOInstance().findBy(new String[] { "bid", "uid" },
                new Integer[] { bid, uid });
        if (recondList.size() != 0) {
            for (Record r : recondList) {
                if (r.getIsreturn() != 1) {
                    return "你已借阅过该图书，并且还没归还！";
                }
            }
        }

        Book b = DAOFactory.getBookDAOInstance().findByID(bid);
        if (b.getAmount() == 1) {
            return "该图书剩余量为 1，不可外借！";
        }
        b.setAmount(b.getAmount() - 1);
        
        if (DAOFactory.getRecordDAOInstance().Insert(new Record(bid, uid))) {
            if (DAOFactory.getBookDAOInstance().Update(b)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 归还图书
     * 
     * @param id
     *            记录 ID
     * @return 成功返回真 否则返回 String 或者逻辑假
     */
    public static Object back(int id) {
        Record r = DAOFactory.getRecordDAOInstance().findByID(id);
        if (r == null) {
            return "没有这条记录。";
        }
        Book b = DAOFactory.getBookDAOInstance().findByID(r.getBid());
        if (b != null) {
            b.setAmount(b.getAmount() + 1);
            DAOFactory.getBookDAOInstance().Update(b);
        }
        r.setIsreturn(1);
        if (DAOFactory.getRecordDAOInstance().Update(r)) {
            return true;
        } else {
            return false;
        }
    }
}