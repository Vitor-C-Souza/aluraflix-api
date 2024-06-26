package br.com.vcsouza.aluraflix.controller;

import br.com.vcsouza.aluraflix.domain.dto.VideoDto;
import br.com.vcsouza.aluraflix.domain.dto.VideoResponseDto;
import br.com.vcsouza.aluraflix.infra.exception.videoNotFoundException;
import br.com.vcsouza.aluraflix.infra.exception.videoNotSavedException;
import br.com.vcsouza.aluraflix.domain.model.Video;
import br.com.vcsouza.aluraflix.domain.service.VideoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/videos")
@SecurityRequirement(name = "bearer-key")
public class VideoController {

    @Autowired
    private VideoService service;

    @GetMapping
    public Page<VideoDto> listarVideos(@PageableDefault(size = 5) Pageable paginacao) {
        Page<Video> dto = service.findall(paginacao);
        if (dto == null) {
            throw new videoNotFoundException("Nenhum video encontrado!!!!");
        }
        return dto.map(VideoDto::new);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoDto> procurarVideo(@PathVariable Long id) {
        VideoDto dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<VideoResponseDto> saveVideo(@RequestBody @Valid VideoDto dto, UriComponentsBuilder uri) {

        var video = service.saveVideo(dto);
        if(video == null){
            throw new videoNotSavedException("Vídeo não pode estar vazio!!!");
        }
        URI endereco = uri.path("/videos/{id}").buildAndExpand(video.getId()).toUri();

        return ResponseEntity.created(endereco).body(video);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VideoDto> updateVideo(@PathVariable Long id, @RequestBody @Valid VideoDto dto) {
        VideoDto updatedVideo = service.updateVideo(id, dto);
        if(updatedVideo == null){
            throw new videoNotFoundException("Vídeo não encontrado para atualizar!!");
        }
        return ResponseEntity.ok().body(updatedVideo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteVideo(@PathVariable Long id) {
        service.deleteVideo(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/")
    public Page<VideoDto> searchVideoForTitulo(@RequestParam String search, @PageableDefault(size = 5) Pageable paginacao) {
        System.out.println(search);
        var dto = service.searchVideoForTitulo(search, paginacao);
        return dto.map(VideoDto::new);
    }

    @GetMapping("/free")
    public ResponseEntity<List<VideoDto>> listaVideosAcessoLivre(){
        List<VideoDto> videos = service.freeVideo();
        return ResponseEntity.ok().body(videos);
    }
}
