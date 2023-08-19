package com.edi.tagsBackend.service;

import com.edi.tagsBackend.models.Link;
import com.edi.tagsBackend.models.LinkDTO;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

public interface LinkService {

    HashMap<String, Integer> addLink(LinkDTO link, String email) throws IOException;

    Set<Link> findByUser(String email);

}
