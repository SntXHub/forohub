//Proyecto ForoHub Alura ONE
package com.forohub.forohub.dto;

import com.forohub.forohub.model.Topic;
import java.time.LocalDateTime;

public class TopicResponse {

    private Long id;
    private String title;
    private String content;
    private String course;
    private String status; // Cambiará el enum a String
    private LocalDateTime createdAt;
    private String authorUsername;

    // Constructor
    public TopicResponse(Topic topic) {
        this.id = topic.getId();
        this.title = topic.getTitle();
        this.content = topic.getContent();
        this.course = topic.getCourse();

        // Convertir el enum Topic.Status a String
        this.status = topic.getStatus().name();

        this.createdAt = topic.getCreatedAt();
        this.authorUsername = topic.getAuthor().getUsername();
    }

    // Getters y Setters
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }
}

