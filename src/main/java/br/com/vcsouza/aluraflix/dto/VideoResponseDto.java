package br.com.vcsouza.aluraflix.dto;

import br.com.vcsouza.aluraflix.model.Video;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VideoResponseDto {
    private Long id;
    private String titulo;
    private String descricao;
    private String url;
    private CategoriaDto categoria;

    public VideoResponseDto(Video video) {
        this.titulo = video.getTitulo();
        this.descricao = video.getDescricao();
        this.url = video.getUrl();
        this.categoria = new CategoriaDto(video.getCategoria());
    }
}
