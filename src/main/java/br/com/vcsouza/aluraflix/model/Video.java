package br.com.vcsouza.aluraflix.model;

import br.com.vcsouza.aluraflix.dto.VideoDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "videos_TB")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descricao;
    private String url;

    public Video(VideoDto dto) {
        this.titulo = dto.getTitulo();
        this.descricao = dto.getDescricao();
        this.url = dto.getUrl();
    }

    public void update(VideoDto dto){
        this.titulo = dto.getTitulo();
        this.descricao = dto.getDescricao();
        this.url = dto.getUrl();
    }
}
