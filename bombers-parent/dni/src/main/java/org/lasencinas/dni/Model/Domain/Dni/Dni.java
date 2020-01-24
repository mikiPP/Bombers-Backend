package org.lasencinas.dni.Model.Domain.Dni;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @JoinColumn(name = "bomber", unique = true)
    private org.lasencinas.bombersauthentication.Model.Domain.AuthUser.Bomber bomber;


    public Dni(String dni) {
        this.dni = dni;
    }
}