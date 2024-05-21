package br.com.vcsouza.aluraflix.domain.dto;

import br.com.vcsouza.aluraflix.domain.model.Categoria;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CategoriaDto {
    private Long id;
    @NotBlank(message = "Campo titulo obrigatório.")
    private String titulo;
    @NotBlank(message = "Campo cor obrigatório.")
    private String cor;


    public CategoriaDto(Categoria categoria) {
        this.id = categoria.getId();
        this.titulo = categoria.getTitulo();
        this.cor = categoria.getCor();
    }

    public CategoriaDto(String titulo, String cor) {
        this.titulo = titulo;
        this.cor = cor;
    }
}
