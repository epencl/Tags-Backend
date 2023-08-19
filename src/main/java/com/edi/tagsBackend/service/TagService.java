package com.edi.tagsBackend.service;

import com.edi.tagsBackend.models.LinkDTO;

import java.io.IOException;
import java.util.HashMap;

public interface TagService {

    HashMap<String, Integer> getTags(LinkDTO link) throws IOException;

    Long addTag(String tag, LinkDTO linkDTO);
}
