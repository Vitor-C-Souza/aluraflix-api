package br.com.vcsouza.aluraflix.repository;

import br.com.vcsouza.aluraflix.model.Categoria;
import br.com.vcsouza.aluraflix.model.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    @Query("SELECT v FROM Video v WHERE v.categoria = :categoria")
    Page<Video> encontrarTodosVideosPorCategoria(Categoria categoria, Pageable paginacao);

    @Query("SELECT v FROM Video v WHERE v.titulo LIKE :search%")
    Page<Video> searchVideo(String search, Pageable paginacao);
}
