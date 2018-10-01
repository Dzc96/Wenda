package com.dzc.Wenda.service;

import com.dzc.Wenda.dao.LoginTicketDao;
import com.dzc.Wenda.dao.UserDao;
import com.dzc.Wenda.model.LoginTicket;
import com.dzc.Wenda.model.User;
import com.dzc.Wenda.utils.WendaUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private LoginTicketDao loginTicketDao;

    public User selectByName(String name) {
        return userDao.selectByName(name);
    }


    public User getUser(int id) {
        return userDao.selectById(id);
    }


    //注册功能
    public Map<String, Object> register(String username, String password) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isBlank(username)) {
            map.put("msg", "用户名不能为空");
            return map;
        }
        if (StringUtils.isBlank(password)) {
            map.put("msg", "密码不能为空");
            return map;
        }

        User user = userDao.selectByName(username);
        if (user != null) {
            map.put("msg", "用户名已经被注册");
            return map;
        }

        user = new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString());
        String head = String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000));
        user.setHeadUrl(head);
        user.setPassword(WendaUtil.MD5(password+user.getSalt()));
        userDao.addUser(user);

        //用户注册完毕，把该用户的信息写到Cookie的ticket里面
        String ticket = addLoginTicket(user.getId());
         map.put("ticket", ticket);
        return map;
    }



    private String addLoginTicket(int userId) {
        LoginTicket ticket = new LoginTicket();
        ticket.setUserId(userId);
        Date date = new Date();
        date.setTime(date.getTime() + 1000*3600*24);
        ticket.setExpired(date);
        ticket.setStatus(0);
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
        loginTicketDao.addTicket(ticket);
        return ticket.getTicket();
    }




    public Map<String, Object> login(String username, String password) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isBlank(username)) {
            map.put("msg", "用户名不能为空");
            return map;
        }

        if (StringUtils.isBlank(password)) {
            map.put("msg", "密码不能为空");
            return map;
        }

        User user = userDao.selectByName(username);

        if (user == null) {
            map.put("msg", "用户名不存在");
            return map;
        }

        if (!WendaUtil.MD5(password+user.getSalt()).equals(user.getPassword())) {
            map.put("msg", "密码不正确");
            return map;
        }

        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);
        return map;
    }


    public void logout(String ticket) {
        loginTicketDao.updateStatus(ticket, 1);
    }







}
