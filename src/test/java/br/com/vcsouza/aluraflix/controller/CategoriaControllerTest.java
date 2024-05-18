package br.com.vcsouza.aluraflix.controller;


import br.com.vcsouza.aluraflix.dto.CategoriaDto;
import br.com.vcsouza.aluraflix.model.Categoria;
import br.com.vcsouza.aluraflix.model.Video;
import br.com.vcsouza.aluraflix.service.CategoriaService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class CategoriaControllerTest {

    @MockBean
    private CategoriaService service;

    @Mock
    private Categoria categoria;

    private CategoriaDto dto;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deveriaDevolverCodigo200ParaRequisicaoDeListarCategorias() throws Exception {
        //ARRANGE
        Page<Categoria> categoriasPage = new PageImpl<>(List.of(categoria));
        when(service.findAll(any(Pageable.class))).thenReturn(categoriasPage);

        //ACT
        MockHttpServletResponse response = mockMvc.perform(
                get("/categorias")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200, response.getStatus());

        String responseBody = response.getContentAsString();
        System.out.println("Resposta: " + responseBody);
    }

    @Test
    void deveriaDevolverCodigo201ParaRequisicaoDeCriarCategorias() throws Exception {
        //ARRANGE
        String json = """
                {
                    "titulo": "musica",
                    "cor": "verde"
                }
                """;
        dto = new CategoriaDto("musica", "verde");
        dto.setId(1L);

        when(service.criarCategoria(any(CategoriaDto.class))).thenReturn(dto);
        //ACT
        var response = mockMvc.perform(
                post("/categorias")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // Log da resposta
        System.out.println("Resposta: " + response.getContentAsString());

        //ASSERT
        assertEquals(201, response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo400ParaRequisicaoDeCriarCategoria() throws Exception {
        //ARRANGE
        String json = """
                {
                }
                """;

        //ACT
        MockHttpServletResponse response = mockMvc.perform(
                post("/categorias")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(400, response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo200ParaRequisicaoDeProcurarCategoria() throws Exception {
        //ACT
        MockHttpServletResponse response = mockMvc.perform(
                get("/categorias/1")
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo400ParaRequisicaoDeProcurarCategoria() throws Exception {
        //ACT
        MockHttpServletResponse response = mockMvc.perform(
                get("/categorias/a")
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(400, response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo200ParaRequisicaoDeAtualizarCategorias() throws Exception {
        //ARRANGE
        String json = """
                {
                     "titulo": "musica",
                     "cor": "verde"
                }
                """;

        //ACT
        MockHttpServletResponse response = mockMvc.perform(
                put("/categorias/1")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo400ParaRequisicaoDeAtualizarCategorias() throws Exception {
        //ARRANGE
        String json = """
                {
                }
                """;

        //ACT
        MockHttpServletResponse response = mockMvc.perform(
                put("/categorias/1")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(400, response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo204ParaRequisicaoDeDeletarCategorias() throws Exception {
        //ARRANGE

        //ACT
        MockHttpServletResponse response = mockMvc.perform(
                delete("/categorias/1")
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(204, response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo400ParaRequisicaoDeDeletarCategoriasComParametroNoFormatoErrado() throws Exception {
        //ARRANGE

        //ACT
        MockHttpServletResponse response = mockMvc.perform(
                delete("/categorias/a")
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(400, response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo200ParaRequisicaoDeProcuraVideosPorCategoria() throws Exception {
        //ARRANGE
        Video video = new Video("Título do Vídeo", "Descrição do Vídeo", "URL do Vídeo");
        video.setId(1L);

        Page<Video> videosPage = new PageImpl<>(List.of(video));

        when(service.listarVideosPorCategoria(anyLong(), any(Pageable.class))).thenReturn(videosPage);
        //ACT
        MockHttpServletResponse response = mockMvc.perform(
                get("/categorias/1/videos")
                        .param("page", "0")
                        .param("size", "10")
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200, response.getStatus());

        // Verificar o corpo da resposta
        String responseBody = response.getContentAsString();
        System.out.println("Resposta: " + responseBody);
    }

    @Test
    void deveriaDevolverCodigo400ParaRequisicaoDeProcuraVideosPorCategoria() throws Exception {
        //ARRANGE

        //ACT
        MockHttpServletResponse response = mockMvc.perform(
                get("/categorias/a/videos")
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(400, response.getStatus());
    }
}