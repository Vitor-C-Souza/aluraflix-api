package br.com.vcsouza.aluraflix.controller;


import br.com.vcsouza.aluraflix.domain.dto.CategoriaDto;
import br.com.vcsouza.aluraflix.domain.dto.VideoSemCategoriaIdDto;
import br.com.vcsouza.aluraflix.infra.exception.NullCategoriaCreatedException;
import br.com.vcsouza.aluraflix.infra.exception.categoriaNotFoundException;
import br.com.vcsouza.aluraflix.infra.exception.videoNotFoundException;
import br.com.vcsouza.aluraflix.domain.model.Categoria;
import br.com.vcsouza.aluraflix.domain.model.Video;
import br.com.vcsouza.aluraflix.domain.service.CategoriaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/categorias")
@SecurityRequirement(name = "bearer-key")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    @GetMapping
    public Page<CategoriaDto> listarCategorias(@PageableDefault(size = 5) Pageable paginacao) {
        Page<Categoria> categorias = service.findAll(paginacao);
        if(categorias == null){
            throw new categoriaNotFoundException();
        }
        return categorias.map(CategoriaDto::new);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDto> procuraCategoria(@PathVariable Long id) {
        CategoriaDto dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<CategoriaDto> criarCategoria(@RequestBody @Valid CategoriaDto dto, UriComponentsBuilder uriComponentsBuilder) {

        CategoriaDto categoriaCreated = service.criarCategoria(dto);
        if (categoriaCreated == null) {
            throw new NullCategoriaCreatedException("Categoria criada não pode estar vazia!!!");
        }
        URI endereco = uriComponentsBuilder.path("/categorias/{id}").buildAndExpand(categoriaCreated.getId()).toUri();

        return ResponseEntity.created(endereco).body(categoriaCreated);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDto> updateCategoria(@PathVariable Long id, @RequestBody @Valid CategoriaDto dto) {
        CategoriaDto categoriaUpdated = service.updateCategoria(id, dto);
        return ResponseEntity.ok().body(categoriaUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCategoria(@PathVariable Long id) {
        service.deleteCategoria(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/videos")
    public Page<VideoSemCategoriaIdDto> procuraVideoPorCategoria(@PathVariable Long id, @PageableDefault(size = 5) Pageable paginacao) {

        Page<Video> dto = service.listarVideosPorCategoria(id, paginacao);
        if(dto==null){
            throw new videoNotFoundException("Videos dessa categoria não encontrado");
        }
        return dto.map(VideoSemCategoriaIdDto::new);
    }

}
