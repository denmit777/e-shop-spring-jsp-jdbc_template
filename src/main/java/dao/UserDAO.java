package dao;

import model.User;

import java.util.List;

public interface UserDAO {

    Long save(User user);

    User getById(Long id);

    User getByLoginAndPassword(String login, String password);

    List<User> getAll();
}
