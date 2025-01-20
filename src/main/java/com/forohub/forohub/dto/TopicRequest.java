//Proyecto ForoHub Alura ONE
package com.forohub.forohub.dto;

import jakarta.validation.constraints.NotBlank;

public class TopicRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotBlank
    private String course;

    @NotBlank
    private String status;

    // Getters y Setters
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

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
