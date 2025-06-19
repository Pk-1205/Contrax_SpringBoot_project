package com.example.demo.repsitiories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.Enities.Contact;
import com.example.demo.Enities.User;

@Repository
public interface ContactRepo extends JpaRepository<Contact, String> {

    Page<Contact> findByUser(User user,Pageable pageable);

    List<Contact> findByEmail(String email);

     @Query("SELECT c FROM Contact c WHERE c.user.id = :userId")
     List<Contact> findByUserId(@Param("userId") String userId);


     Page<Contact> findByUserAndNameContaining( User user,String nameKeyword, Pageable pageable);
     Page<Contact> findByUserAndEmailContaining( User user,String emailKeyword, Pageable pageable);
     Page<Contact> findByUserAndPhoneNumberContaining( User user,String phoneNUmberKeyword, Pageable pageable);

     Optional<Contact> findByIdAndUser(String id, User user);

      // Recommended optimized method
   //  @Query("SELECT DISTINCT c FROM Contact c LEFT JOIN FETCH c.links WHERE c.user.userid = :userId")
    //  List<Contact> findAllContactsWithLinksByUserId(@Param("userId") String userId);
}
