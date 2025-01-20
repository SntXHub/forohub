//Proyecto ForoHub Alura ONE
package com.forohub.forohub.repository;

import com.forohub.forohub.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {
}
