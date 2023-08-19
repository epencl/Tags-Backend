package com.edi.tagsBackend.service;

import com.edi.tagsBackend.models.Link;
import com.edi.tagsBackend.models.LinkDTO;
import com.edi.tagsBackend.models.User;
import com.edi.tagsBackend.repository.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

@Service
public class LinkServiceImpl implements LinkService {

    @Autowired
    UserService userService;

    @Autowired
    TagService tagService;

    @Autowired
    LinkRepository linkRepository;

    @Override
    public HashMap<String, Integer> addLink(LinkDTO link, String email) throws IOException {
        if (linkRepository.findByValue(link.getValue()) != null) {
            return null;
        }
        User user = userService.findByEmail(email);
        Set<Link> links = user.getLinks();
        Link newLink = new Link(link.getValue(), user.getId());
        linkRepository.save(newLink);
        links.add(newLink);
        user.setLinks(links);
        userService.save(user);

        return tagService.getTags(link);
    }

    @Override
    public Set<Link> findByUser(String email) {
        User user = userService.findByEmail(email);
        return user.getLinks();
    }
}
