package br.com.vcsouza.aluraflix.domain.service;

import br.com.vcsouza.aluraflix.domain.dto.CategoriaDto;
import br.com.vcsouza.aluraflix.domain.model.Categoria;
import br.com.vcsouza.aluraflix.domain.model.Video;
import br.com.vcsouza.aluraflix.domain.repository.CategoriaRepository;
import br.com.vcsouza.aluraflix.domain.repository.VideoRepository;
import br.com.vcsouza.aluraflix.infra.exception.categoriaNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    @Autowired
    private VideoRepository videoRepository;

    public Page<Categoria> findAll(Pageable paginacao) {
        return repository.findAll(paginacao);
    }

    public CategoriaDto findById(Long id) {
        Optional<Categoria> categoria = repository.findById(id);

        return new CategoriaDto(categoria.orElseThrow(categoriaNotFoundException::new));
    }

    public CategoriaDto criarCategoria(CategoriaDto dto) {
        Categoria categoria = new Categoria(dto.getTitulo(), dto.getCor());
        repository.save(categoria);
        dto.setId(categoria.getId());
        return dto;
    }

    public CategoriaDto updateCategoria(Long id, CategoriaDto dto) {
        Categoria categoria = repository.getReferenceById(id);
        categoria.update(dto);
        repository.save(categoria);
        dto.setId(categoria.getId());
        return dto;
    }

    public void deleteCategoria(Long id) {
        repository.deleteById(id);
    }

    public Page<Video> listarVideosPorCategoria(Long id, Pageable paginacao) {
        var categoriaEncontrada = repository.getReferenceById(id);
        return videoRepository.encontrarTodosVideosPorCategoria(categoriaEncontrada, paginacao);
    }
}
