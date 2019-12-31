package org.lasencinas.bombersauthentication.Repository;

import org.lasencinas.bombersauthentication.Model.Domain.Dni.Dni;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DniRepository extends JpaRepository<Dni, String> {
}
