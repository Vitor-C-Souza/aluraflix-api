package br.com.vcsouza.aluraflix.repository;

import br.com.vcsouza.aluraflix.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
}