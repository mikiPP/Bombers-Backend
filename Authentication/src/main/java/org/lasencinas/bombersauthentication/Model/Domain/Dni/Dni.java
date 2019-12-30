package org.lasencinas.bombersauthentication.Model.Domain.Dni;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.lasencinas.bombersauthentication.Model.Domain.AuthUser.AuthUser;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "dni", uniqueConstraints = @UniqueConstraint(columnNames = {"dni", "AUTH_USER"}))
public class Dni implements java.io.Serializable {


    @Id
    @Column(name = "dni")
    private String dni;


    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "AUTH_USER", unique = true)
    private AuthUser authUser;


    public Dni(String dni) {
        this.dni = dni;
    }
}