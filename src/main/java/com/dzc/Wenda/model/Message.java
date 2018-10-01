package com.dzc.Wenda.model;

import java.util.Date;

public class Message {
    private int id;
    private int fromId;
    private int toId;
    private String content;
    private Date createDate;
    private int hasRead; //0为没读，1为已经阅读
    private String conversationId;


    public Message() {
    }


    public Message(int id, int fromId, int toId, String content, Date createdDate, int hasRead, String conversationId) {
        this.id = id;
        this.fromId = fromId;
        this.toId = toId;
        this.content = content;
        this.createDate = createdDate;
        this.hasRead = hasRead;
        this.conversationId = conversationId;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public int getToId() {
        return toId;
    }

    public void setToId(int toId) {
        this.toId = toId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedDate() {
        return createDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createDate = createdDate;
    }

    public int getHasRead() {
        return hasRead;
    }

    public void setHasRead(int hasRead) {
        this.hasRead = hasRead;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }


    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", fromId=" + fromId +
                ", toId=" + toId +
                ", content='" + content + '\'' +
                ", createdDate=" + createDate +
                ", hasRead=" + hasRead +
                ", conversationId='" + conversationId + '\'' +
                '}';
    }
}
