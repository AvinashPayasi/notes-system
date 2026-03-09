package com.securenotes.cli;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PageInfoDTO {
    private int size;
    private int number;
    @JsonProperty("totalElements")
    private int totalElements;
    private int totalPages;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getTotalElement() {
        return totalElements;
    }

    public void setTotalElement(int totalElement) {
        this.totalElements = totalElement;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
