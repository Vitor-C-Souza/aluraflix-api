package br.com.vcsouza.aluraflix.domain.repository;

import br.com.vcsouza.aluraflix.domain.model.Categoria;
import br.com.vcsouza.aluraflix.domain.model.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    @Query("SELECT v FROM Video v WHERE v.categoria = :categoria")
    Page<Video> encontrarTodosVideosPorCategoria(Categoria categoria, Pageable paginacao);

    @Query("SELECT v FROM Video v WHERE v.titulo LIKE :search%")
    Page<Video> searchVideo(String search, Pageable paginacao);

    @Query(value = "SELECT * FROM videos_tb LIMIT 5", nativeQuery = true)
    List<Video> freeVideos();
}
