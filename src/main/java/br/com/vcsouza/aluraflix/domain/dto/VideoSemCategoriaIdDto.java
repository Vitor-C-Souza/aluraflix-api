package br.com.vcsouza.aluraflix.domain.dto;

import br.com.vcsouza.aluraflix.domain.model.Video;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoSemCategoriaIdDto {
        private Long id;
        private String titulo;
        private String descricao;
        private String url;

    public VideoSemCategoriaIdDto(Video video){
        this.id = video.getId();
        this.titulo = video.getTitulo();
        this.descricao = video.getDescricao();
        this.url = video.getUrl();
    }
}
