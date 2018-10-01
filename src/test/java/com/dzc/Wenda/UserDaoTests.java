package com.dzc.Wenda;

import com.dzc.Wenda.dao.UserDao;
import com.dzc.Wenda.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTests {
    /**
     *  int addUser(User user);
     *
     *     User selectById(int id);
     *
     *     User selectByName(String name);
     *
     *     void updatePassWord(User user);
     *
     *     void deleteById(int id);
     */




    @Autowired
    private UserDao userDao;


    @Test
    public void testSelectById() {
        User user = userDao.selectById(10010);
        System.out.println(user);
    }


    @Test
    public void testAddUser() {
        User user = new User();
        user.setName("dzc111");
        user.setPassword("123");
        user.setSalt("123");
        user.setHeadUrl("https://www.baidu.com/img/bd_logo1.png");
        int count = userDao.addUser(user);
        System.out.println("影响行数：" + count);
    }


    @Test
    public void testSelectByName() {
        User user = userDao.selectByName("dzc111");
        System.out.println(user);
    }



    @Test
    public void testUpdatePassWord() {
        User user = userDao.selectById(10010);
        user.setPassword("newpsd");
        userDao.updatePassWord(user);

    }



    @Test
    public void testDeleteById() {
        userDao.deleteById(10010);
    }


}
