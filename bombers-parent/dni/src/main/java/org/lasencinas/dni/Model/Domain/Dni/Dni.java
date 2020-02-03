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

    @Column(name = "Bomber_ID")
    private Long bomberId;


    public Dni(String dni) {
        this.dni = dni;
    }

    public Dni(String dni,Long bomberId) {
        this.dni = dni;
        this.bomberId = bomberId;
    }
}