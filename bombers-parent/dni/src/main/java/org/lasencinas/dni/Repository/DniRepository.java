package org.lasencinas.dni.Repository;

import org.lasencinas.dni.Model.Domain.Dni.Dni;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DniRepository extends JpaRepository<Dni, String> {
}
