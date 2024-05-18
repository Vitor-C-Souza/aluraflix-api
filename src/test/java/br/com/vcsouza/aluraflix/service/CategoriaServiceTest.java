package br.com.vcsouza.aluraflix.service;

import br.com.vcsouza.aluraflix.dto.CategoriaDto;
import br.com.vcsouza.aluraflix.exception.categoriaNotFoundException;
import br.com.vcsouza.aluraflix.model.Categoria;
import br.com.vcsouza.aluraflix.model.Video;
import br.com.vcsouza.aluraflix.repository.CategoriaRepository;
import br.com.vcsouza.aluraflix.repository.VideoRepository;
import org.junit.jupiter.api.Assertions;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @InjectMocks
    private CategoriaService service;

    @Mock
    private CategoriaRepository repository;

    @Mock
    private VideoRepository videoRepository;

    private Categoria categoria = new Categoria("Categoria Teste", "Cor Teste");

    private CategoriaDto dto;

    @Captor
    private ArgumentCaptor<Categoria> categoriaArgumentCaptor;

    @Test
    void deveriaSalvarCategoriaAoSolicitar() {
        //ARRANGE
        dto = new CategoriaDto("musica", "amarelo");
        categoria = new Categoria(dto);

        //ACT
        CategoriaDto categoriaDto = service.criarCategoria(dto);

        //ASSERT
        then(repository).should().save(categoriaArgumentCaptor.capture());
        Categoria categoriaSalva = categoriaArgumentCaptor.getValue();
        Assertions.assertEquals(categoriaDto.getId(), categoriaSalva.getId());
        Assertions.assertEquals(categoria.getId(), categoriaSalva.getId());
        Assertions.assertEquals(categoriaDto.getTitulo(), categoriaSalva.getTitulo());
        Assertions.assertEquals(categoriaDto.getCor(), categoriaSalva.getCor());
    }

    @Test
    void deveriaRetornarTodasAsCategoriasSalvas(){
        //ARRANGE
        Categoria categoria1 = new Categoria("Categoria 1", "Cor 1");
        Categoria categoria2 = new Categoria("Categoria 2", "Cor 2");
        List<Categoria> categoriasList = Arrays.asList(categoria1, categoria2);
        Pageable paginacao = PageRequest.of(0, 10);
        Page<Categoria> categoriasPage = new PageImpl<>(categoriasList, paginacao, categoriasList.size());
        given(repository.findAll(paginacao)).willReturn(categoriasPage);

        //ACT
        Page<Categoria> resultado = service.findAll(paginacao);

        //ASSERT
        assertEquals(categoriasPage, resultado);
    }

    @Test
    void DeveriaRetornarUmaCategoriaPorId(){
        //ARRANGE
        Long id = 1L;
        given(repository.findById(id)).willReturn(Optional.of(categoria));
        categoria.setId(id);

        //ACT
        CategoriaDto dto = service.findById(id);

        //ASSERT
        assertEquals(categoria.getId(), dto.getId());
        assertEquals(categoria.getTitulo(), dto.getTitulo());
        assertEquals(categoria.getCor(), dto.getCor());
    }

    @Test
    void deveriaLancarExcecaoQuandoCategoriaNaoEncontrada() {
        // ARRANGE
        Long id = 1L;

        given(repository.findById(id)).willReturn(Optional.empty());

        // ACT & ASSERT
        assertThrows(categoriaNotFoundException.class, () -> service.findById(id));
    }

    @Test
    void DeveriaAtualizarUmaCategoria(){
        //ARRANGE
        Long id = 1L;
        dto = new CategoriaDto("musica", "amarelo");

        given(repository.getReferenceById(id)).willReturn(categoria);

        //ACT
        CategoriaDto categoriaDto = service.updateCategoria(id, dto);

        //ASSERT
        then(repository).should().save(categoriaArgumentCaptor.capture());
        Categoria categoriaSalva = categoriaArgumentCaptor.getValue();
        assertEquals(categoriaDto.getId(), categoriaSalva.getId());
        assertEquals(categoriaDto.getTitulo(), categoriaSalva.getTitulo());
        assertEquals(categoriaDto.getCor(), categoriaSalva.getCor());
    }

    @Test
    void DeveriaDeletarUmaCategoriaQuandoEncontrada(){
        //ARRANGE
        Long id = 1L;

        //ACT
        service.deleteCategoria(id);

        //ASSERT
        verify(repository, times(1)).deleteById(id);
    }

    @Test
    void deveriaLancarExcecaoQuandoCategoriaNaoEncontradaParaSerDeletada() {
        // ARRANGE
        Long id = 1L;

        doThrow(categoriaNotFoundException.class).when(repository).deleteById(id);

        // ACT & ASSERT
        assertThrows(categoriaNotFoundException.class, () -> service.deleteCategoria(id));
    }

    @Test
    void DeveriaEncontrarVideoPelaCategoriaSelecionada(){
        //ARRANGE
        Long id = 1L;
        categoria.setId(id);

        Video video1 = new Video("Título do Vídeo 1", "Descrição do Vídeo 1", "URL do Vídeo 1");
        Video video2 = new Video("Título do Vídeo 2", "Descrição do Vídeo 2", "URL do Vídeo 2");
        List<Video> videosList = Arrays.asList(video1, video2);

        Pageable paginacao = PageRequest.of(0, 10);
        Page<Video> videosPage = new PageImpl<>(videosList, paginacao, videosList.size());

        given(repository.getReferenceById(categoria.getId())).willReturn(categoria);
        given(videoRepository.encontrarTodosVideosPorCategoria(categoria, paginacao)).willReturn(videosPage);

        //ACT
        Page<Video> resultado = service.listarVideosPorCategoria(categoria.getId(), paginacao);

        //ASSERT
        assertNotNull(resultado);
        assertEquals(2, resultado.getTotalElements());
        assertEquals(videosPage, resultado);
    }


}