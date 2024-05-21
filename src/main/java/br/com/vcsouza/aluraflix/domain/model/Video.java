package br.com.vcsouza.aluraflix.domain.model;

import br.com.vcsouza.aluraflix.domain.dto.VideoDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "videos_TB")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descricao;
    private String url;
    @ManyToOne
    private Categoria categoria;

    public Video(VideoDto dto, Categoria categoria) {
        this.titulo = dto.getTitulo();
        this.descricao = dto.getDescricao();
        this.url = dto.getUrl();
        this.categoria = categoria;
    }

    public Video(String titulo, String descricao, String url, Categoria categoria) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.url = url;
        this.categoria = categoria;
    }

    public Video(String titulo, String descricao, String url) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.url = url;
    }

    public void update(VideoDto dto) {
        this.titulo = dto.getTitulo();
        this.descricao = dto.getDescricao();
        this.url = dto.getUrl();
    }


}
