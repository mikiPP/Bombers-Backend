package org.lasencinas.bombersauthentication.Model.Domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "AUTHENTICATION_USER")
public class Bomber {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", unique = true)
    private Long id;

    @Column(name= "Dni")
    private String dni;


    @Column(name = "EMAIL", unique = true)
    @NotNull
    @Email
    private String email;

    @NotNull
    @Column(name = "PASSWORD")
    private String password;

//
//    @Column(name = "NAME")
//    private String name;


    // Constructor


//    public AuthUser(String email, String password) {
//        this.password = password;
//        this.email = email;
//    }

    public Bomber(String dni, String password) {
        this.password = password;
        this.dni = dni;
    }

}
