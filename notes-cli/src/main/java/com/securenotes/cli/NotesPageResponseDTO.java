package com.securenotes.cli;

import java.util.List;

public class NotesPageResponseDTO {
    private List<NotesResponseDTO> content;
    private PageInfoDTO page;

    public List<NotesResponseDTO> getContent() {
        return content;
    }

    public void setContent(List<NotesResponseDTO> content) {
        this.content = content;
    }

    public PageInfoDTO getPage() {
        return page;
    }

    public void setPage(PageInfoDTO page) {
        this.page = page;
    }
}
