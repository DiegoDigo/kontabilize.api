package br.com.kontabilize.kontabilizeapi.infra.repositories;

import br.com.kontabilize.kontabilizeapi.context.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);
}
