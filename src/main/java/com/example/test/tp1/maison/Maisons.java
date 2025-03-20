package com.example.test.tp1.maison;

import java.util.List;

import jakarta.persistence.CascadeType;

import com.example.test.tp1.user.Users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Maisons {
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    private Long id;
    private String nom;
    private double prix;
    @Column(columnDefinition = "Text")
    private String description;
    @ManyToOne
    @JoinColumn(name = "users_id",nullable = false)
    private Users users;
    @OneToMany(mappedBy = "maison", cascade = CascadeType.ALL)
    private List<MaisonPhoto> photos;

    

}
