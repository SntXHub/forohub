package com.forohub.forohub.service;

import com.forohub.forohub.model.Topic;
import com.forohub.forohub.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    // Crear un nuevo tópico
    public Topic createTopic(Topic topic) {
        return topicRepository.save(topic);
    }

    // Obtener todos los tópicos
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    // Obtener un tópico por ID
    public Topic getTopicById(Long id) {
        return topicRepository.findById(id).orElseThrow(() -> new RuntimeException("Tópico no encontrado"));
    }

    // Actualizar un tópico
    public Topic updateTopic(Long id, Topic topicDetails) {
        Topic topic = getTopicById(id); // Verificar existencia

        topic.setTitle(topicDetails.getTitle());
        topic.setContent(topicDetails.getContent());
        return topicRepository.save(topic);
    }

    // Eliminar un tópico
    public void deleteTopic(Long id) {
        Topic topic = getTopicById(id); // Verificar existencia
        topicRepository.delete(topic);
    }
}
