package com.scmspain.application.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Extract URLs from a String for some protocols.
 */
public class UrlExtractor {

    private static final String WHITE_SPACE = " ";
    private static final String HTTP_PROTOCOL = "http://";
    private static final String HTTPS_PROTOCOL = "https://";

    private String text;
    private List<String> urls = new ArrayList<>();

    public UrlExtractor(final String text) {
        this.text = text;
        this.extractUrls();
    }

    public String getText() {
        return text;
    }

    public List<String> getUrls() {
        return urls;
    }

    private void extractUrls() {
        extractUrls(HTTP_PROTOCOL);
        extractUrls(HTTPS_PROTOCOL);
    }

    private void extractUrls(final String protocol) {
        Optional<String> url = extractUrl(protocol);
        if (url.isPresent()) {
            text = text.replace(url.get(), getEscapeIndex(urls.size()));
            urls.add(url.get());
            extractUrl(protocol);
        }
    }

    private Optional<String> extractUrl(String protocol) {
        int urlStart = text.indexOf(protocol);
        if (urlStart != -1) {
            int urlEnd = text.indexOf(WHITE_SPACE, urlStart);
            if (urlEnd != -1) {
                return Optional.of(text.substring(urlStart, urlEnd));
            } else {
                return Optional.of(text.substring(urlStart));
            }
        }
        return Optional.empty();
    }

    public static String rebuild(String text, List<String> urls) {
        for (int i = 0; i < urls.size(); i++) {
            String target = getEscapeIndex(i);
            while (text.contains(target)) {
                text = text.replace(target, urls.get(i));
            }
        }
        return text;
    }

    private static String getEscapeIndex(int index) {
        return "{" + index + "}";
    }

}
