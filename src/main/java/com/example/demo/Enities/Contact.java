package com.example.demo.Enities;

import java.util.ArrayList;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Contact {

    @Id
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    @Column (length=1000)
    private String about;
    // private String contacts;
    private String address ;
    private boolean favorite;

    
    private String picture;
    private String webSiteLink;
    private String linkedInLink;
   // private List<String> socailLink = new ArrayList<>();
     @ManyToOne
     @JsonIgnore
    private User user;

     @OneToMany(mappedBy = "contact",cascade = CascadeType.ALL,fetch = FetchType.EAGER,orphanRemoval = true)
    private List<SocialLink> links =new ArrayList<>();

    
}
