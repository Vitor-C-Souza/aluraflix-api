package br.com.vcsouza.aluraflix.repository;

import br.com.vcsouza.aluraflix.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRespository extends JpaRepository<Login, Long> {
    UserDetails findByUsuario(String username);
}
