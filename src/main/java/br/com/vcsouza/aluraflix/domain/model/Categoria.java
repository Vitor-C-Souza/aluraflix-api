package br.com.vcsouza.aluraflix.domain.model;

import br.com.vcsouza.aluraflix.domain.dto.CategoriaDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categorias_tb")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String cor;
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Video> videos = new ArrayList<>();

    public Categoria(CategoriaDto dto) {
        this.titulo = dto.getTitulo();
        this.cor = dto.getCor();
    }

    public Categoria(String titulo, String cor) {
        this.titulo = titulo;
        this.cor = cor;
    }


    public void update(CategoriaDto dto) {
        this.titulo = dto.getTitulo();
        this.cor = dto.getCor();
    }
}
