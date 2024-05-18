package br.com.vcsouza.aluraflix.service;

import br.com.vcsouza.aluraflix.dto.VideoDto;
import br.com.vcsouza.aluraflix.dto.VideoResponseDto;
import br.com.vcsouza.aluraflix.model.Categoria;
import br.com.vcsouza.aluraflix.model.Video;
import br.com.vcsouza.aluraflix.repository.CategoriaRepository;
import br.com.vcsouza.aluraflix.repository.VideoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VideoServiceTest {

    @InjectMocks
    private VideoService service;

    @Mock
    private VideoRepository repository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Captor
    private ArgumentCaptor<Video> videoArgumentCaptor;

    private Categoria categoria;
    private Video video;
    private VideoDto dto;

    @BeforeEach
    void setUp() {
        categoria = new Categoria();
        categoria.setId(1L);
        categoria.setTitulo("Categoria Teste");
        categoria.setCor("Cor Teste");

        video = new Video();
        video.setId(1L);
        video.setTitulo("Título Teste");
        video.setDescricao("Descrição Teste");
        video.setUrl("http://teste.com");
        video.setCategoria(categoria);

        dto = new VideoDto();
        dto.setTitulo("Título Atualizado");
        dto.setDescricao("Descrição Atualizada");
        dto.setUrl("http://atualizado.com");
        dto.setCategoria_id(2L);
    }



    @Test
    void DeveRetornarUmaListaDeVideos() {
        //ARRANGE
        Video video1 = new Video("Titulo 1", "descricao 1", "url 1", this.categoria);
        Video video2 = new Video("Titulo 2", "descricao 2", "url 2", this.categoria);
        List<Video> videoList = Arrays.asList(video1, video2);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Video> videoPage = new PageImpl<>(videoList, pageable, videoList.size());

        given(repository.findAll(pageable)).willReturn(videoPage);

        //ACT
        Page<Video> videos = service.findall(pageable);

        //ASSERT
        assertEquals(videoPage, videos);
    }

    @Test
    void DeveriaEncontrarUmVideoPeloId(){
        //ARRANGE
        Long id = 1L;
        given(repository.getReferenceById(id)).willReturn(this.video);
        VideoDto dto = new VideoDto(this.video);

        //ACT
        VideoDto videoDto = service.findById(id);

        //ASSERT
        assertEquals(videoDto.getTitulo(), dto.getTitulo());
        assertEquals(videoDto.getId(), dto.getId());
        assertEquals(videoDto.getUrl(), dto.getUrl());
        assertEquals(videoDto.getDescricao(), dto.getDescricao());
        assertEquals(videoDto.getCategoria_id(), dto.getCategoria_id());
    }

    @Test
    void DeveriaSalvarVideo(){
        //ARRANGE
        given(categoriaRepository.getReferenceById(dto.getCategoria_id())).willReturn(categoria);

        //ACT
        VideoResponseDto videoResponseDto = service.saveVideo(dto);

        //ASSERT
        then(repository).should().save(videoArgumentCaptor.capture());
        Video videoSalvo = videoArgumentCaptor.getValue();
        assertEquals(videoResponseDto.getId(), videoSalvo.getId());
        assertEquals(videoResponseDto.getDescricao(), videoSalvo.getDescricao());
        assertEquals(categoria, videoSalvo.getCategoria());
        assertEquals(videoResponseDto.getUrl(), videoSalvo.getUrl());
        assertEquals(videoResponseDto.getTitulo(), videoSalvo.getTitulo());
    }

    @Test
    void DeveriaFazerUpdateNoVideo(){
        //ARRANGE
        Long id = 1L;
        given(repository.getReferenceById(id)).willReturn(video);

        // ACT
        VideoDto updatedDto = service.updateVideo(id, dto);

        //ASSERT
        then(repository).should().save(videoArgumentCaptor.capture());
        Video videoUpdated = videoArgumentCaptor.getValue();
        System.out.println(videoUpdated);
        assertEquals(id, updatedDto.getId());
        assertEquals(videoUpdated.getTitulo(), updatedDto.getTitulo());
        assertEquals(videoUpdated.getDescricao(), updatedDto.getDescricao());
        assertEquals(videoUpdated.getUrl(), updatedDto.getUrl());
    }

    @Test
    void DeveDeletarUmVideoPeloId(){
        //ARRANGE
        Long id = 1L;

        //ACT
        service.deleteVideo(id);

        //ASSERT
        verify(repository, times(1)).deleteById(id);
    }

    @Test
    void DeveriaRetornarPaginaDeVideosQuandoBuscarPorTitulo() {
        // ARRANGE
        String search = "Teste";
        Pageable paginacao = PageRequest.of(0, 10);
        Page<Video> page = new PageImpl<>(List.of(video), paginacao, 1);

        given(repository.searchVideo(search, paginacao)).willReturn(page);

        //ACT
        Page<Video> videoPage = service.searchVideoForTitulo(search, paginacao);

        // ASSERT
        assertEquals(1, videoPage.getTotalElements());
        assertEquals(video, videoPage.getContent().getFirst());
    }

}