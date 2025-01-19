package com.forohub.forohub.service;

import com.forohub.forohub.dto.TopicRequest;
import com.forohub.forohub.dto.TopicResponse;
import com.forohub.forohub.model.Topic;
import com.forohub.forohub.repository.TopicRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TopicService {

    private final TopicRepository topicRepository;

    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public Topic createTopic(Topic topic) {
        return topicRepository.save(topic);
    }

    public List<Topic> getAllTopics() {
        return topicRepository.findAll(); // Recupera todos los tópicos
    }

    public Optional<Topic> getTopicById(Long id) {
        return topicRepository.findById(id);
    }

    public Topic updateTopic(Long id, TopicRequest topicDetails) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tópico no encontrado"));

        topic.setTitle(topicDetails.getTitle());
        topic.setContent(topicDetails.getContent());
        topic.setCourse(topicDetails.getCourse());
        topic.setStatus(topicDetails.getStatus());

        return topicRepository.save(topic);
    }

    public void deleteTopic(Long id) {
        topicRepository.deleteById(id);
    }

    public Page<TopicResponse> getAllTopics(Pageable pageable) {
        return topicRepository.findAll(pageable)
                .map(TopicResponse::new); // Mapea cada Topic a un TopicResponse
    }
}

