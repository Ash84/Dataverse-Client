package com.researchspace.dataverse.entities;

/**
 * Metadata languages allowed.
 */
public enum MetadataLanguage {

    /**
     * English tag.
     */
    ENGLISH("en"),
    /**
     * French tag.
     */
    FRENCH("fr");

    /**
     * Tag value.
     */
    String languageTag;

    /**
     * Constructor.
     * @param tag
     */
    MetadataLanguage(final String tag) {
        languageTag = tag;
    }

    /**
     * Get the value of the tag.
     * @return string value
     */
    public String getTag() {
        return languageTag;
    }

}
