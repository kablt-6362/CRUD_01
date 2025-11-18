package com.example.praccrud1.tododto;

public class TodoDto {
    private Long id;
    private String title;
    private String content;
    private Boolean completed;

    public TodoDto(Long id, String title, String content, Boolean completed) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.completed = completed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
