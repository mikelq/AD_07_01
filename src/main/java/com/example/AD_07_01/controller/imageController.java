package com.example.AD_07_01.controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/uploads")
public class imageController {

    private final Logger logger = LoggerFactory.getLogger(imageController.class);
    private final Path uploadDir = Paths.get("uploads").toAbsolutePath().normalize(); // Ruta absoluta

    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            // Resuelve la ruta del archivo
            Path file = uploadDir.resolve(filename).normalize();
            Resource resource = new UrlResource(file.toUri());

            // Verifica si el archivo existe y es legible
            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                logger.warn("Archivo no encontrado o no legible: {}", file);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error al cargar la imagen: {}", e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }
}
