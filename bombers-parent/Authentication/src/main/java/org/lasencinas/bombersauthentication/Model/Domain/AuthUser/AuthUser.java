package org.lasencinas.bombersauthentication.Model.Domain.AuthUser;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.lasencinas.bombersauthentication.Model.Domain.Dni.Dni;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "AUTHENTICATION_USER")
public class AuthUser {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", unique = true)
    private Long id;

    @OneToOne(mappedBy = "authUser", fetch = FetchType.LAZY)
    @JoinColumn(name = "dni")
    private Dni dni;


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

    public AuthUser(Dni dni, String password) {
        this.password = password;
        this.dni = dni;
    }

}
