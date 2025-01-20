//Proyecto ForoHub Alura ONE
package com.forohub.forohub.controller;

import com.forohub.forohub.dto.TopicRequest;
import com.forohub.forohub.dto.TopicResponse;
import com.forohub.forohub.service.TopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @Operation(summary = "Obtener todos los tópicos", description = "Devuelve una lista paginada de tópicos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de tópicos devuelta exitosamente"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public Page<TopicResponse> getAllTopics(Pageable pageable) {
        return topicService.getAllTopics(pageable);
    }

    @Operation(summary = "Crear un nuevo tópico", description = "Permite a los usuarios autenticados crear un nuevo tópico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tópico creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @PostMapping
    public ResponseEntity<TopicResponse> createTopic(@RequestBody @Valid TopicRequest topicRequest, Authentication authentication) {
        TopicResponse createdTopic = topicService.createTopic(topicRequest, authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTopic);
    }

    @Operation(summary = "Obtener un tópico por ID", description = "Devuelve los detalles de un tópico específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tópico encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Tópico no encontrado"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TopicResponse> getTopicById(@PathVariable Long id) {
        TopicResponse topic = topicService.getTopicById(id)
                .orElseThrow(() -> new RuntimeException("Tópico no encontrado"));
        return ResponseEntity.ok(topic);
    }

    @Operation(summary = "Actualizar un tópico", description = "Permite a los administradores actualizar un tópico existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tópico actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Tópico no encontrado"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<TopicResponse> updateTopic(@PathVariable Long id, @RequestBody @Valid TopicRequest topicRequest) {
        TopicResponse updatedTopic = topicService.updateTopic(id, topicRequest);
        return ResponseEntity.ok(updatedTopic);
    }

    @Operation(summary = "Eliminar un tópico", description = "Permite a los administradores eliminar un tópico existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tópico eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Tópico no encontrado"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id) {
        topicService.deleteTopic(id);
        return ResponseEntity.noContent().build();
    }
}





