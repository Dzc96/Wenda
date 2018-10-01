package com.dzc.Wenda.dao;

import com.dzc.Wenda.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {

    int addUser(User user);

    User selectById(int id);

    User selectByName(String name);

    void updatePassWord(User user);

    void deleteById(int id);

}
