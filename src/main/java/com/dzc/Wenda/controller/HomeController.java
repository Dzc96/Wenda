package com.dzc.Wenda.controller;


import com.dzc.Wenda.model.*;
import com.dzc.Wenda.service.CommentService;
import com.dzc.Wenda.service.FollowService;
import com.dzc.Wenda.service.QuestionService;
import com.dzc.Wenda.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    QuestionService questionService;

    @Autowired
    UserService userService;

    @Autowired
    FollowService followService;

    @Autowired
    CommentService commentService;

    @Autowired
    HostHolder hostHolder;

    /**
    * @Description: 显示每个Question的同时，还需要加上用户等一些额外信息，封装成一条ViewObject
    * @Param:
    * @return: 返回一个List,放满了ViewObject
    * @Author: dzc
    * @Date: 2018/9/22
    */
    private List<ViewObject> getQuestion(int userId, int offset, int limit) {

        List<Question> questionList = questionService.getLatestQuestions(userId, offset, limit);
        List<ViewObject> objectsList = new ArrayList<>();
        for (Question question : questionList) {
            ViewObject viewObject = new ViewObject();
            viewObject.set("question", question);
            viewObject.set("user", userService.getUser(question.getUserId()));
            objectsList.add(viewObject);
        }
        return objectsList;

    }



    @RequestMapping(value = {"/index", "/"})
    public String home(Model model) {
        //userId等于0，意味着查询所有问题
        model.addAttribute("vos", getQuestion(0, 0, 10));
       // model.addObject("vos", getQuestion(0, 0, 10));
        return "index";
    }


    /**
     * 根据用户的ID找到对应用户是最新是个问题
     * @param model
     * @param userId
     * @return
     */
    @RequestMapping(path = {"/user/{userId}"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String userIndex(Model model, @PathVariable("userId") int userId) {
        model.addAttribute("vos", getQuestions(userId, 0, 10));

        User user = userService.getUser(userId);
        ViewObject vo = new ViewObject();
        vo.set("user", user);
        vo.set("commentCount", commentService.getUserCommentCount(userId));
        vo.set("followerCount", followService.getFollowerCount(EntityType.ENTITY_USER, userId));
        vo.set("followeeCount", followService.getFolloweeCount(userId, EntityType.ENTITY_USER));
        if (hostHolder.getUser() != null) {
            vo.set("followed", followService.isFollower(hostHolder.getUser().getId(), EntityType.ENTITY_USER, userId));
        } else {
            vo.set("followed", false);
        }
        model.addAttribute("profileUser", vo);
        return "profile";
    }




    @RequestMapping("/fuck")
    @ResponseBody
    public String fuck() {
        return "dsssddd";
    }

    private List<ViewObject> getQuestions(int userId, int offset, int limit) {
        List<Question> questionList = questionService.getLatestQuestions(userId, offset, limit);
        List<ViewObject> vos = new ArrayList<>();
        for (Question question : questionList) {
            ViewObject vo = new ViewObject();
            vo.set("question", question);
            vo.set("followCount", followService.getFollowerCount(EntityType.ENTITY_QUESTION, question.getId()));
            vo.set("user", userService.getUser(question.getUserId()));
            vos.add(vo);
        }
        return vos;
    }








}
