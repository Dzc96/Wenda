package com.dzc.Wenda.dao;

import com.dzc.Wenda.model.LoginTicket;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LoginTicketDao {
    int addTicket(LoginTicket loginTicket);

    int deleteTicket(int id);

    LoginTicket selectByTicket(String ticket);

    LoginTicket selectByUserId(int userId);

    void updateStatus(@Param("ticket") String ticket, @Param("status") int status);

}
