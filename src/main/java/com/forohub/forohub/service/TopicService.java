package com.forohub.forohub.service;

import com.forohub.forohub.model.Topic;
import com.forohub.forohub.repository.TopicRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return topicRepository.findAll();
    }

    public Topic getTopicById(Long id) {
        return topicRepository.findById(id).orElseThrow(() -> new RuntimeException("Tópico no encontrado"));
    }

    public Topic updateTopic(Long id, Topic topicDetails) {
        Topic topic = getTopicById(id);
        topic.setTitulo(topicDetails.getTitulo());
        topic.setMensaje(topicDetails.getMensaje());
        topic.setStatus(topicDetails.getStatus());
        topic.setAutor(topicDetails.getAutor());
        topic.setCurso(topicDetails.getCurso());
        return topicRepository.save(topic);
    }

    public void deleteTopic(Long id) {
        topicRepository.deleteById(id);
    }
}
