package com.example.test.tp1.maison;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MaisonDTO {
    private double prix;
    private String nom;
    private String description;
    private int quantite;
    private List<MultipartFile> images;
}
