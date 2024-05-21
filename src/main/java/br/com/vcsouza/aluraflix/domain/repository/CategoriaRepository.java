package br.com.vcsouza.aluraflix.domain.repository;

import br.com.vcsouza.aluraflix.domain.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
