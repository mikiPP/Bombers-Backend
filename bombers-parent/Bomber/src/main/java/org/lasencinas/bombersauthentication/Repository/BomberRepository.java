package org.lasencinas.bombersauthentication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BomberRepository extends JpaRepository<org.lasencinas.bombersauthentication.Model.Domain.AuthUser.Bomber, Long> {

    Optional<org.lasencinas.bombersauthentication.Model.Domain.AuthUser.Bomber> findByEmail(String email);


}