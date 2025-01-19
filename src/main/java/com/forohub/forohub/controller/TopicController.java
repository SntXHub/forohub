package com.forohub.forohub.controller;

import com.forohub.forohub.dto.TopicRequest;
import com.forohub.forohub.dto.TopicResponse;
import com.forohub.forohub.model.Topic;
import com.forohub.forohub.model.User;
import com.forohub.forohub.service.TopicService;
import com.forohub.forohub.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/topics")
public class TopicController {

    private final TopicService topicService;
    private final UserRepository userRepository;

    public TopicController(TopicService topicService, UserRepository userRepository) {
        this.topicService = topicService;
        this.userRepository = userRepository;
    }

    // 1. Obtener todos los tópicos (paginación)
    @GetMapping
    public Page<TopicResponse> getAllTopics(Pageable pageable) {
        return topicService.getAllTopics(pageable);
    }

    // 2. Crear un nuevo tópico
    @PostMapping
    public ResponseEntity<TopicResponse> createTopic(@RequestBody @Valid TopicRequest topicRequest, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + username));

        Topic topic = new Topic();
        topic.setTitle(topicRequest.getTitle());
        topic.setContent(topicRequest.getContent());
        topic.setCourse(topicRequest.getCourse());
        topic.setStatus(topicRequest.getStatus());
        topic.setAuthor(user);

        Topic savedTopic = topicService.createTopic(topic);

        return ResponseEntity.status(HttpStatus.CREATED).body(new TopicResponse(savedTopic));
    }

    // 3. Obtener un tópico por ID
    @GetMapping("/{id}")
    public ResponseEntity<TopicResponse> getTopicById(@PathVariable Long id) {
        Topic topic = topicService.getTopicById(id)
                .orElseThrow(() -> new RuntimeException("Tópico no encontrado"));
        return ResponseEntity.ok(new TopicResponse(topic));
    }

    // 4. Actualizar un tópico
    @PutMapping("/{id}")
    public ResponseEntity<TopicResponse> updateTopic(@PathVariable Long id, @RequestBody @Valid TopicRequest topicRequest) {
        Topic updatedTopic = topicService.updateTopic(id, topicRequest);
        return ResponseEntity.ok(new TopicResponse(updatedTopic));
    }

    // 5. Eliminar un tópico
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id) {
        topicService.deleteTopic(id);
        return ResponseEntity.noContent().build();
    }
}


