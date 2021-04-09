package com.academico.siac.services;

import com.academico.siac.models.Users;
import com.academico.siac.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UsersRepository repositoryUsers;


    public Users encontrarPorEmail(String email) {
        return repositoryUsers.findByEmail(email);
    }

    public void salvar(Users user) {
        String hashPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashPassword);
        repositoryUsers.save(user);

    }
}
