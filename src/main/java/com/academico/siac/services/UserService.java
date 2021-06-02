package com.academico.siac.services;

import com.academico.siac.models.User;
import com.academico.siac.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    private User user;

    @Autowired
    private UsersRepository repositoryUsers;

    public User encontrarPorEmail(String email) {
        User foundUser = repositoryUsers.findByEmail(email);

        if (foundUser != null) {
            this.user = foundUser;
        }

        return foundUser;
    }

    public void salvar(User user) {
        String hashPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashPassword);
        repositoryUsers.save(user);

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
