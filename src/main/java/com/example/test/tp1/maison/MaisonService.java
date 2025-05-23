package com.example.test.tp1.maison;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.test.tp1.user.UserRepository;
import com.example.test.tp1.user.Users;

@Service
public class MaisonService {
@Autowired
private MaisonRepository repository;
@Autowired
private UserRepository userRepository;
@Autowired 
private PhotoRepository photoRepository;
// private Users users ;
// private Maisons maisons;
private final String images_Dir = "Images/";

public void add(int user_id,String nom,double prix ,int quantite,String desc,List<MultipartFile> images) throws IOException{
    Optional<Users> user = userRepository.findById(user_id);
    
        Maisons maison = new Maisons();
        maison.setPrix(prix);
        maison.setDescription(desc);
        maison.setQuantite(quantite);
        maison.setUsers(user.get());
        maison.setNom(nom);
        repository.save(maison);
        Users user_s= user.get();
        user_s.ajoutmaison(maison);
        userRepository.save(user_s);
        for(MultipartFile img : images){
           String filename=UUID.randomUUID() + "_" + img.getOriginalFilename();
           Path filePath = Paths.get(images_Dir,filename);
           Files.createDirectories(filePath.getParent());
           Files.write(filePath, img.getBytes());
           MaisonPhoto photo = new MaisonPhoto();
           photo.setFilename(filename);
           photo.setFilePath(filePath.toString());
           photo.setMaison(maison);
           photoRepository.save(photo);
           
        }
    
    
}

public void Modifierservice(Long id,String nom,double prix ,String desc,List<MultipartFile> images) throws IOException{
    Maisons maison=repository.findById(id).get();
    maison.setPrix(prix);
    maison.setNom(nom);
    maison.setDescription(desc);
    repository.save(maison);
    if(images != null){
           List<MaisonPhoto> anciennesPhotos = new ArrayList<>(maison.getPhotos());
        maison.getPhotos().clear();
        for (MaisonPhoto photo : anciennesPhotos) {
            // Supprimer le fichier physique
            Path filePath = Paths.get(photo.getFilePath());
            Files.deleteIfExists(filePath);
            // Supprimer l'entrée de la base de données
            photoRepository.delete(photo);
        }
      for(MultipartFile img : images){
        String filename=UUID.randomUUID() + "_" + img.getOriginalFilename();
        Path filePath = Paths.get(images_Dir,filename);
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, img.getBytes());
        MaisonPhoto photo = new MaisonPhoto();
        photo.setFilename(filename);
        photo.setFilePath(filePath.toString());
        photo.setMaison(maison);
        photoRepository.save(photo);
        
     }
    }
}

public ResponseEntity<?> AfficheSerice(int id){
    Users user = userRepository.findById(id).get();
    List<Maisons> maisons = user.getMaisons();
    List<MaisonReponse> reponse= maisons.stream().map(maison->{
        List<String> images = maison.getPhotos().stream().map(photo->"http://192.168.100.11:9000/Images/"+photo.getFilename()).collect(Collectors.toList());
        return new MaisonReponse(maison.getId(),maison.getNom(),maison.getPrix(),maison.getQuantite(),maison.getDescription(),maison.getUsers().getAltd(),maison.getUsers().getLongd(),images,maison.getUsers().getTel());
    }).collect(Collectors.toList());
    return ResponseEntity.ok().body(reponse);
}
public ResponseEntity<?> find(Long id){
    Maisons maisons = repository.findById(id).get();
    List<String> images = maisons.getPhotos().stream().map(photo->"http://192.168.100.11:9000/Images/"+photo.getFilename()).collect(Collectors.toList());
    MaisonReponse reponse = new MaisonReponse(maisons.getId(),maisons.getNom(),maisons.getPrix(),maisons.getQuantite(),maisons.getDescription(),maisons.getUsers().getAltd(),maisons.getUsers().getLongd(),images,maisons.getUsers().getTel());
    return ResponseEntity.ok().body(reponse);
}

public ResponseEntity<?> Allservice(){
    List<Maisons> maisons=repository.findAll();
    List<MaisonReponse> reponse= maisons.stream().map(maison->{
        List<String> images = maison.getPhotos().stream().map(photo->"http://192.168.100.11:9000/Images/"+photo.getFilename()).collect(Collectors.toList());
        return new MaisonReponse(maison.getId(),maison.getNom(),maison.getPrix(),maison.getQuantite(),maison.getDescription(),maison.getUsers().getAltd(),maison.getUsers().getLongd(),images,maison.getUsers().getTel());
    }).collect(Collectors.toList());
    return ResponseEntity.ok().body(reponse);
}

public void deleteservice(Long id){
      repository.deleteById(id);
}
public void active(Long id){
      Maisons maison=repository.findById(id).get();
      repository.save(maison);
}
}
