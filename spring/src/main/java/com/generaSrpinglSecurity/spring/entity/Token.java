package com.generaSrpinglSecurity.spring.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "token")
@Data
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private boolean loggedOut;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private User user;
}
