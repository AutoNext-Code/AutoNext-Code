package com.autonext.code.autonext_server.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;

import org.springframework.core.io.UrlResource;

import org.springframework.http.*;

import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;

import java.nio.file.Path;

import java.nio.file.Paths;

@RestController
@RequestMapping("/images")

public class ImageController {

  @Value("${app.upload-dir}")
  private String uploadDir;

  @GetMapping("/{filename:.+}")

  public ResponseEntity<Resource> getImage(@PathVariable String filename) {

    try {

      Path file = Paths.get(uploadDir).resolve(filename);

      Resource resource = new UrlResource(file.toUri());

      if (!resource.exists() || !resource.isReadable()) {

        return ResponseEntity.notFound().build();

      }

      return ResponseEntity.ok()

          .contentType(new MediaType("image", "webp"))

          .body(resource);

    } catch (MalformedURLException e) {

      return ResponseEntity.badRequest().build();

    }

  }

}