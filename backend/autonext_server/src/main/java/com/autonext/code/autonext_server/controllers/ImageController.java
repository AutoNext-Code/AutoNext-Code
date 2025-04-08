package com.autonext.code.autonext_server.controllers;

import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;

import org.springframework.core.io.UrlResource;

import org.springframework.http.*;

import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;

import java.nio.file.Path;

import java.nio.file.Paths;

@Profile("prod")
@RestController
@RequestMapping("/images")

public class ImageController {

  private final String UPLOAD_DIR = "/home/ubuntu/uploads";

  @GetMapping("/{filename:.+}")

  public ResponseEntity<Resource> getImage(@PathVariable String filename) {

    try {

      Path file = Paths.get(UPLOAD_DIR).resolve(filename);

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