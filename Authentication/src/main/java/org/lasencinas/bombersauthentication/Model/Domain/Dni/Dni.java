package org.lasencinas.bombersauthentication.Model.Domain.Dni;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.lasencinas.bombersauthentication.Model.Domain.AuthUser.AuthUser;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "dni", uniqueConstraints = @UniqueConstraint(columnNames = {"dni", "USER"}))
public class Dni implements java.io.Serializable {


    @Id
    @Column(name = "dni")
    private String dni;


    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "USER", unique = true)
    private AuthUser user;


    public Dni(String dni) {
        this.dni = dni;
    }
}