package com.edi.tagsBackend.controller;

import com.edi.tagsBackend.models.ErrorMessage;
import com.edi.tagsBackend.models.LinkDTO;
import com.edi.tagsBackend.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TagController {

    @Autowired
    TagService tagService;

    @RequestMapping(value = "/tags/{tag}", method = RequestMethod.POST)
    public ResponseEntity<?> addTag(@PathVariable String tag, @RequestBody LinkDTO link) throws Exception {
        Long tagId = tagService.addTag(tag, link);
        if (tagId == null)
            return ResponseEntity.badRequest().body(new ErrorMessage("Given link does not exists."));
        return ResponseEntity.ok("Tag id: " + tagService.addTag(tag, link));
    }
}
