package com.edi.tagsBackend.service;

import com.edi.tagsBackend.models.Link;
import com.edi.tagsBackend.models.LinkDTO;
import com.edi.tagsBackend.models.Tag;
import com.edi.tagsBackend.repository.LinkRepository;
import com.edi.tagsBackend.repository.TagRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

@Service
public class TagServiceImpl implements TagService {

    private final String[] commonStopWords = {"a", "about", "above", "after", "again", "against", "all", "am", "an", "and", "any",
            "are", "aren't", "as", "at", "be", "because", "been", "before", "being", "below",
            "between", "both", "but", "by", "can't", "cannot", "could", "couldn't", "did",
            "didn't", "do", "does", "doesn't", "doing", "don't", "down", "during", "each", "few",
            "for", "from", "further", "had", "hadn't", "has", "hasn't", "have", "haven't", "having",
            "he", "he'd", "he'll", "he's", "her", "here", "here's", "hers", "herself", "him",
            "himself", "his", "how", "how's", "i", "i'd", "i'll", "i'm", "i've", "if", "in", "into",
            "is", "isn't", "it", "it's", "its", "itself", "let's", "me", "more", "most", "mustn't",
            "my", "myself", "no", "nor", "not", "of", "off", "on", "once", "only", "or", "other",
            "ought", "our", "ours", "ourselves", "out", "over", "own", "same", "shan't", "she",
            "she'd", "she'll", "she's", "should", "shouldn't", "so", "some", "such", "than", "that",
            "that's", "the", "their", "theirs", "them", "themselves", "then", "there", "there's",
            "these", "they", "they'd", "they'll", "they're", "they've", "this", "those", "through",
            "to", "too", "under", "until", "up", "very", "was", "wasn't", "we", "we'd", "we'll",
            "we're", "we've", "were", "weren't", "what", "what's", "when", "when's", "where",
            "where's", "which", "while", "who", "who's", "whom", "why", "why's", "with", "won't",
            "would", "wouldn't", "you", "you'd", "you'll", "you're", "you've", "your", "yours",
            "yourself", "yourselves", ""};

    @Autowired
    private LinkRepository linkRepository;
    @Autowired
    private TagRepository tagRepository;

    @Override
    public HashMap<String, Integer> getTags(LinkDTO link) throws IOException {
        Document doc = Jsoup.connect(link.getValue()).get();
        String text = doc.body().text();

        List<String> wordsFromTextLowerCase = getWordsFromString(text).stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());

        List<String> commonStopWordsList = Arrays.asList(commonStopWords);
        wordsFromTextLowerCase.removeAll(commonStopWordsList);

        HashMap<String, Integer> map = new HashMap<>();
        for (String w : wordsFromTextLowerCase)
            map.put(w, map.getOrDefault(w, 0) + 1);

        HashMap<String, Integer> sorted = map
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));

        return sorted;
    }

    @Override
    public Long addTag(String tag, LinkDTO linkDTO) {
        Link link = linkRepository.findByValue(linkDTO.getValue());
        if (link == null) {
            return null;
        }

        Tag savedTag = tagRepository.save(new Tag(tag, link.getId()));
        Set<Tag> tags = link.getTags();
        tags.add(savedTag);
        link.setTags(tags);
        linkRepository.save(link);

        return savedTag.getId();
    }


    private List<String> getWordsFromString(String s) {

        String[] words = s.split("\\s+");
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].replaceAll("[^\\w]", "");
        }
        return Arrays.asList(words);
    }


}
