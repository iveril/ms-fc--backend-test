package com.scmspain.application.services;

/**
 *
 */
public class UrlExtractor {

    private static final String WHITE_SPACE = " ";
    private static final String HTTP_PROTOCOL = "http://";
    private static final String HTTPS_PROTOCOL = "https://";

    /**
     *
     * @param text
     * @return
     */
    public String extract(final String text) {
        String cleanText = extract(text, HTTP_PROTOCOL);
        return extract(cleanText, HTTPS_PROTOCOL);
    }

    private String extract(final String text, final String protocol) {
        if (text.contains(protocol)) {
            String newText = extractUrl(text, protocol);
            return extract(newText, protocol);
        }
        return text;
    }

    private String extractUrl(String text, String protocol) {
        int urlStart = text.indexOf(protocol);
        int urlEnd = text.indexOf(WHITE_SPACE, urlStart);
        if (urlEnd == -1) {
            return text.substring(0, urlStart);
        }
        return text.substring(0, urlStart) + text.substring(urlEnd + 1);
    }

}
