package br.com.vcsouza.aluraflix.controller;

import br.com.vcsouza.aluraflix.dto.LoginDto;
import br.com.vcsouza.aluraflix.dto.TokenJWT;
import br.com.vcsouza.aluraflix.model.Login;
import br.com.vcsouza.aluraflix.security.TokenService;
import br.com.vcsouza.aluraflix.service.LoginService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
public class LoginController {

    @Autowired
    private LoginService service;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/cadastro")
    public ResponseEntity<LoginDto> cadastrar(@RequestBody @Valid LoginDto dto, UriComponentsBuilder uri) {
        String senhaEncriptada = BCrypt.hashpw(dto.getSenha(), BCrypt.gensalt());
        LoginDto login = service.cadastro(dto, senhaEncriptada);

        URI address = uri.path("/cadastro/{id}").buildAndExpand(dto.getId()).toUri();

        return ResponseEntity.created(address).body(login);
    }

    @PostMapping("/logar")
    public ResponseEntity logar(@RequestBody @Valid LoginDto dto){

        var autheticationToken = new UsernamePasswordAuthenticationToken(dto.getUsuario(), dto.getSenha());
        var authentication = manager.authenticate(autheticationToken);

        var tokenJWT =  tokenService.GerarToken((Login) authentication.getPrincipal());

        return ResponseEntity.ok(new TokenJWT(tokenJWT));
    }
}
