package br.com.vcsouza.aluraflix.service;

import br.com.vcsouza.aluraflix.dto.VideoDto;
import br.com.vcsouza.aluraflix.exception.videoNotFoundExceptio;
import br.com.vcsouza.aluraflix.model.Video;
import br.com.vcsouza.aluraflix.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VideoService {

    @Autowired
    VideoRepository repository;


    public Page<Video> findall(Pageable paginacao) {

        return repository.findAll(paginacao);
    }

    public VideoDto findById(Long id) {
        Optional<Video> optional = repository.findById(id);

        return new VideoDto(optional.orElseThrow(videoNotFoundExceptio::new));
    }


    public VideoDto saveVideo(VideoDto dto) {
        Video video = new Video(dto);
        repository.save(video);

        dto.setId(video.getId());
        return dto;
    }

    public void updateVideo(Long id, VideoDto dto) {
        Video video = repository.getReferenceById(id);
        video.update(dto);
        repository.save(video);
    }

    public void deleteVideo(Long id) {
        repository.deleteById(id);
    }
}
