package com.stackabuse.facebookapi.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackabuse.facebookapi.controller.ToJsonSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/")
public class MainController {
    @Autowired
    ObjectMapper objectMapper;
    private Facebook facebook;
    private ConnectionRepository connectionRepository;

    public MainController(Facebook facebook, ConnectionRepository connectionRepository) {
        this.facebook = facebook;
        this.connectionRepository = connectionRepository;
    }

    @RequestMapping(value = "name", method = RequestMethod.GET)
    public String name(Model model) {

        if(connectionRepository.findPrimaryConnection(Facebook.class) == null) {
            return "redirect:/connect/facebook";
        }

        User userProfile = facebook.userOperations().getUserProfile();
        model.addAttribute("userProfile", userProfile);
        PagedList<Post> userFeed = facebook.feedOperations().getFeed();
        model.addAttribute("userFeed", userFeed);

        String resultAsString;
        try {
            resultAsString = new ToJsonSerializer("sendData", userProfile, objectMapper).serialize();
        } catch (IOException io) {
            resultAsString = io.getMessage();
        }
        return resultAsString;
    }

    @RequestMapping(value = "feed", method = RequestMethod.GET)
    public String feed(Model model) {

        if(connectionRepository.findPrimaryConnection(Facebook.class) == null) {
            return "redirect:/connect/facebook";
        }

        User userProfile = facebook.userOperations().getUserProfile();
        model.addAttribute("userProfile", userProfile);
        PagedList<Post> userFeed = facebook.feedOperations().getFeed();
        model.addAttribute("userFeed", userFeed);
        return "feed";
    }

    @RequestMapping(value = "friends", method = RequestMethod.GET)
    public String friends(Model model) {

        if(connectionRepository.findPrimaryConnection(Facebook.class) == null) {
            return "redirect:/connect/facebook";
        }

        User userProfile = facebook.userOperations().getUserProfile();
        model.addAttribute("userProfile", userProfile);
        List<User> friends = facebook.friendOperations().getFriendProfiles();
        model.addAttribute("friends", friends);

        return "friends";
    }
}
