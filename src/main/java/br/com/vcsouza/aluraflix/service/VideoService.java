package br.com.vcsouza.aluraflix.service;

import br.com.vcsouza.aluraflix.dto.VideoDto;
import br.com.vcsouza.aluraflix.dto.VideoResponseDto;
import br.com.vcsouza.aluraflix.exception.videoNotFoundException;
import br.com.vcsouza.aluraflix.model.Categoria;
import br.com.vcsouza.aluraflix.model.Video;
import br.com.vcsouza.aluraflix.repository.CategoriaRepository;
import br.com.vcsouza.aluraflix.repository.VideoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VideoService {

    @Autowired
    private VideoRepository repository;

    @Autowired
    private CategoriaRepository categoriaRepository;


    public Page<Video> findall(Pageable paginacao) {
        return repository.findAll(paginacao);
    }

    public VideoDto findById(Long id) {

        Video video = repository.getReferenceById(id);
        if (video == null) {
            throw new videoNotFoundException("Vídeo do id:" + id + " não encontrado!!!");
        }
        return new VideoDto(video);
    }


    public VideoResponseDto saveVideo(VideoDto dto) {
        Categoria categoria = categoriaRepository.getReferenceById(dto.getCategoria_id());

        Video video = new Video(dto, categoria);
        repository.save(video);

        return new VideoResponseDto(video);
    }

    public VideoDto updateVideo(Long id, VideoDto dto) {
        Video video = repository.getReferenceById(id);
        video.update(dto);
        repository.save(video);
        dto.setId(video.getId());
        return dto;
    }

    public void deleteVideo(Long id) {
        repository.deleteById(id);
    }

    public Page<Video> searchVideoForTitulo(String search, Pageable paginacao) {
        return repository.searchVideo(search, paginacao);
    }

    public List<VideoDto> freeVideo() {
        List<Video> videoList = repository.freeVideos();
        if(videoList == null){
            throw new EntityNotFoundException();
        }

        return videoList.stream().map(VideoDto::new).collect(Collectors.toList());
    }
}
