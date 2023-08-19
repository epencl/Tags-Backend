package com.edi.tagsBackend.controller;

import com.edi.tagsBackend.models.ErrorMessage;
import com.edi.tagsBackend.models.LinkDTO;
import com.edi.tagsBackend.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class LinkController {

    @Autowired
    LinkService linkService;

    @RequestMapping(value = "/links", method = RequestMethod.GET)
    public ResponseEntity<?> getLinks() throws Exception {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(linkService.findByUser(userDetails.getUsername()));
    }

    @RequestMapping(value = "/links", method = RequestMethod.POST)
    public ResponseEntity<?> addLink(@RequestBody LinkDTO link) throws Exception {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        HashMap<String, Integer> map = linkService.addLink(link, userDetails.getUsername());
        if (map == null)
            return ResponseEntity.badRequest().body(new ErrorMessage("Link already exists for current user."));
        return ResponseEntity.ok(map);
    }

}
