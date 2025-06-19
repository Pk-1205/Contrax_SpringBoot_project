package com.example.demo.services;

import java.util.List;

import org.springframework.data.domain.Page;

import org.springframework.stereotype.Service;

import com.example.demo.Enities.Contact;
import com.example.demo.Enities.User;

@Service
public interface ContactService {


    Contact save(Contact contact);

    Contact update(Contact contact);

    List<Contact> getAll();

    Contact getById(String id);

    void delete(String id);
    


    Page<Contact> searchByName(String nameKeyword, int size, int page, String sortBy, String order,User user);
    Page<Contact> searchByEmail(String emailKeyword ,int size, int page, String sortBy, String order,User user);
    Page<Contact> searchByPhoneNumber(String phoneNUmberKeyword ,int size, int page, String sortBy, String order,User user);
    

    List<Contact> getByUserId(String userId);

    Page<Contact> getByUser(User user,int page, int size ,String sortField, String sortDirection);

    Contact getByIdAndUser(String contactId, User user);
    Contact update(Contact contact, User user);


}
