package com.dzc.Wenda;


import com.dzc.Wenda.dao.LoginTicketDao;
import com.dzc.Wenda.model.LoginTicket;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginTicketDaoTests {

    /**
     *  int addTicket(LoginTicket loginTicket);
     *
     *     int deleteTicket(int id);
     *
     *     LoginTicket selectByTicket(String ticket);
     *
     *     LoginTicket selectByUserId(int userId);
     *
     *     void updateStatus(String ticket, int status);
     */


    @Autowired
    private LoginTicketDao loginTicketDao;


    @Test
    public void testUpdateStatus() {
        loginTicketDao.updateStatus("motherfucker", 1);
    }



    @Test
    public void testselectByUserId() {
        LoginTicket loginTicket = loginTicketDao.selectByUserId(10000);
        System.out.println(loginTicket);
    }

    @Test
    public void testselectByTicket(){
        LoginTicket loginTicket = loginTicketDao.selectByTicket("motherfucker");
        System.out.println(loginTicket);
    }



    @Test
    public void testAddTicket() {
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(10000);
        loginTicket.setExpired(new Date());
        loginTicket.setStatus(0);
        loginTicket.setTicket("motherfucker");
        int count = loginTicketDao.addTicket(loginTicket);
        System.out.println("影响行数：" + count);
    }


    //deleteTicket

    @Test
    public void testDeleteTicket(){
        loginTicketDao.deleteTicket(1);

    }
}
