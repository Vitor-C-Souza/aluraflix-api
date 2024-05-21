package br.com.vcsouza.aluraflix.controller;

import br.com.vcsouza.aluraflix.domain.dto.CategoriaDto;
import br.com.vcsouza.aluraflix.domain.dto.VideoDto;
import br.com.vcsouza.aluraflix.domain.dto.VideoResponseDto;
import br.com.vcsouza.aluraflix.domain.model.Categoria;
import br.com.vcsouza.aluraflix.domain.model.Login;
import br.com.vcsouza.aluraflix.domain.model.Video;
import br.com.vcsouza.aluraflix.infra.security.TokenService;
import br.com.vcsouza.aluraflix.domain.service.VideoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class VideoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VideoService service;

    private final Categoria categoria = new Categoria();

    private final VideoDto dto = new VideoDto();

    private Login login = new Login();

    private String token;

    @Autowired
    private TokenService tokenService;

    @BeforeEach
    void setUp(){
        login.setUsuario("vitor");
        login.setSenha("6040");
        token = tokenService.GerarToken(login);
    }

    @Test
    void deveriaDevolverCodigo200ParaRequisicaoDeListarVideos() throws Exception {
        //ARRANGE
        this.categoria.setId(1L);
        this.categoria.setTitulo("Categoria Teste");
        this.categoria.setCor("Cor Teste");


        Video video1 = new Video("Título do Vídeo 1", "Descrição do Vídeo 1", "URL do Vídeo 1", categoria);
        Video video2 = new Video("Título do Vídeo 2", "Descrição do Vídeo 2", "URL do Vídeo 2", categoria);
        List<Video> videosList = Arrays.asList(video1, video2);

        Pageable paginacao = PageRequest.of(0, 15);
        Page<Video> videoPage = new PageImpl<>(videosList, paginacao, videosList.size());

        given(service.findall(paginacao)).willReturn(videoPage);

        //ACT
        MockHttpServletResponse response = mockMvc.perform(
                get("/videos")
                        .header("Authorization", "Bearer " + token)
                        .param("page", "0")
                        .param("size", "15")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200, response.getStatus());
    }

    @Test
    void DeveProcurarDepoisRetornarUmVideoCodigo200() throws Exception {
        //ARRANGE

        //ACT
        var response = mockMvc.perform(
                get("/videos/1")
                        .header("Authorization", "Bearer " + token)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200, response.getStatus());
    }

    @Test
    void DeveRetornarCodigo201ParaSalvarUmVideo() throws Exception {
        //ARRANGE
        String json = """
                {
                    "titulo": "NEW HORUS PHANTOM KNIGHTS DECK FULL COMBO - BEST ENGINE IN MASTER DUEL!",
                    "descricao": "Deck pk horus advendure",
                    "url": "https://www.youtube.com/watch?v=19TzAItIZOI",
                    "categoria_id": 2
                }
                """;
        categoria.setId(2L);
        categoria.setCor("verde");
        categoria.setTitulo("jogo");
        CategoriaDto categoriaDto = new CategoriaDto(categoria);
        VideoResponseDto videoResponseDto = new VideoResponseDto();
        videoResponseDto.setId(1L);
        videoResponseDto.setTitulo("NEW HORUS PHANTOM KNIGHTS DECK FULL COMBO - BEST ENGINE IN MASTER DUEL!");
        videoResponseDto.setDescricao("Deck pk horus advendure");
        videoResponseDto.setUrl("https://www.youtube.com/watch?v=19TzAItIZOI");
        videoResponseDto.setCategoria(categoriaDto);


        when(service.saveVideo(any(VideoDto.class))).thenReturn(videoResponseDto);
        //ACT

        var response = mockMvc.perform(
                post("/videos")
                        .header("Authorization", "Bearer " + token)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // Log da resposta
        System.out.println("Resposta: " + response.getContentAsString());

        //ASSERT
        assertEquals(201, response.getStatus());
    }

    @Test
    void DeveriaRetornarCodigo200ParaAtualizarUmVideo() throws Exception {
        //ARRANGE
        Long id = 1L;
        String json = """
                {
                    "titulo": "NEW HORUS PHANTOM KNIGHTS DECK FULL COMBO - BEST ENGINE IN MASTER DUEL!",
                    "descricao": "Deck pk horus advendure",
                    "url": "https://www.youtube.com/watch?v=19TzAItIZOI",
                    "categoria_id": 2
                }
                """;
//        VideoDto dto = new VideoDto();
        dto.setId(id);
        dto.setTitulo("NEW HORUS PHANTOM KNIGHTS DECK FULL COMBO - BEST ENGINE IN MASTER DUEL!");
        dto.setDescricao("Deck pk horus advendure");
        dto.setUrl("https://www.youtube.com/watch?v=19TzAItIZOI");
        dto.setCategoria_id(2L);

        when(service.updateVideo(eq(id), any(VideoDto.class))).thenReturn(dto);

        //ACT
        MockHttpServletResponse response = mockMvc.perform(
                put("/videos/" + id)
                        .header("Authorization", "Bearer " + token)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // Log da resposta
        System.out.println("Resposta: " + response.getContentAsString());

        //ASSERT
        assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo204ParaRequisicaoDeDeletarVideo() throws Exception {
        //ARRANGE

        //ACT
        MockHttpServletResponse response = mockMvc.perform(
                delete("/videos/1")
                        .header("Authorization", "Bearer " + token)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(204, response.getStatus());
    }

    @Test
    void searchVideoForTituloDeveRetornarCodigo200() throws Exception {
        // ARRANGE
        categoria.setId(2L);
        categoria.setCor("verde");
        categoria.setTitulo("jogo");
        String search = "NEW HORUS";
        Pageable pageable = PageRequest.of(0, 10);
        List<Video> videos = List.of(
                new Video(1L, "NEW HORUS PHANTOM KNIGHTS DECK FULL COMBO - BEST ENGINE IN MASTER DUEL!",
                        "Deck pk horus advendure", "https://www.youtube.com/watch?v=19TzAItIZOI", categoria)
        );
        Page<Video> videoPage = new PageImpl<>(videos, pageable, videos.size());

        when(service.searchVideoForTitulo(eq(search), any(Pageable.class))).thenReturn(videoPage);

        // ACT
        var response = mockMvc.perform(
                get("/videos/")
                        .header("Authorization", "Bearer " + token)
                        .param("search", search)
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // Log da resposta
        System.out.println("Resposta: " + response.getContentAsString());

        // ASSERT
        assertEquals(200, response.getStatus());
    }


}