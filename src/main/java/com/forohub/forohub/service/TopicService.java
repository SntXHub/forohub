package com.forohub.forohub.service;

import com.forohub.forohub.dto.TopicRequest;
import com.forohub.forohub.dto.TopicResponse;
import com.forohub.forohub.model.Topic;
import com.forohub.forohub.model.User;
import com.forohub.forohub.repository.TopicRepository;
import com.forohub.forohub.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TopicService {

    private final TopicRepository topicRepository;
    private final UserRepository userRepository;

    public TopicService(TopicRepository topicRepository, UserRepository userRepository) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
    }

    public Page<TopicResponse> getAllTopics(Pageable pageable) {
        return topicRepository.findAll(pageable).map(TopicResponse::new);
    }

    public TopicResponse createTopic(TopicRequest topicRequest, String username) {
        // Buscar el usuario por su username
        User author = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + username));

        Topic topic = new Topic();
        topic.setTitle(topicRequest.getTitle());
        topic.setContent(topicRequest.getContent());
        topic.setCourse(topicRequest.getCourse());

        // Conversión de String a Topic.Status
        topic.setStatus(Topic.Status.valueOf(topicRequest.getStatus().toUpperCase()));

        topic.setCreatedAt(java.time.LocalDateTime.now());
        topic.setAuthor(author);

        Topic savedTopic = topicRepository.save(topic);
        return new TopicResponse(savedTopic);
    }

    public Optional<TopicResponse> getTopicById(Long id) {
        return topicRepository.findById(id).map(TopicResponse::new);
    }

    public TopicResponse updateTopic(Long id, TopicRequest topicRequest) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tópico no encontrado"));

        topic.setTitle(topicRequest.getTitle());
        topic.setContent(topicRequest.getContent());
        topic.setCourse(topicRequest.getCourse());

        // Conversión de String a Topic.Status
        topic.setStatus(Topic.Status.valueOf(topicRequest.getStatus().toUpperCase()));

        Topic updatedTopic = topicRepository.save(topic);
        return new TopicResponse(updatedTopic);
    }

    public void deleteTopic(Long id) {
        topicRepository.deleteById(id);
    }
}




