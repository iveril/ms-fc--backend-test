package com.scmspain.application.services;

/**
 * Remove URLs from a String for some protocols.
 */
class UrlRemover {

    private static final String WHITE_SPACE = " ";
    private static final String HTTP_PROTOCOL = "http://";
    private static final String HTTPS_PROTOCOL = "https://";

    /**
     * Given a text removes the URLs contained on it.
     *
     * @param text Text.
     * @return Text without URLs.
     */
    String removeUrls(final String text) {
        String cleanText = removeUrls(text, HTTP_PROTOCOL);
        return removeUrls(cleanText, HTTPS_PROTOCOL);
    }

    private String removeUrls(final String text, final String protocol) {
        if (text.contains(protocol)) {
            String newText = removeUrl(text, protocol);
            return removeUrls(newText, protocol);
        }
        return text;
    }

    private String removeUrl(String text, String protocol) {
        int urlStart = text.indexOf(protocol);
        int urlEnd = text.indexOf(WHITE_SPACE, urlStart);
        if (urlEnd == -1) {
            return text.substring(0, urlStart);
        }
        return text.substring(0, urlStart) + text.substring(urlEnd + 1);
    }

}
