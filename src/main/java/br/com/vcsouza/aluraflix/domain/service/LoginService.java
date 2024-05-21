package br.com.vcsouza.aluraflix.domain.service;

import br.com.vcsouza.aluraflix.domain.dto.LoginDto;
import br.com.vcsouza.aluraflix.domain.repository.LoginRespository;
import br.com.vcsouza.aluraflix.domain.model.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements UserDetailsService {

    @Autowired
    private LoginRespository respository;

    public LoginDto cadastro(LoginDto dto, String senhaEncriptada) {

        Login login = new Login(dto, senhaEncriptada);
        respository.save(login);
        dto.setId(login.getId());

        return dto;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return respository.findByUsuario(username);
    }
}
