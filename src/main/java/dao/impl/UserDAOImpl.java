package dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import dao.UserDAO;
import model.User;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import javax.sql.DataSource;

@Repository
public class UserDAOImpl implements UserDAO {

    private static final String QUERY_SELECT_FROM_USER = "SELECT * FROM user";
    private static final String QUERY_INSERT_INTO_USER = "INSERT INTO user (login, password) VALUES (?,?)";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Long save(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(QUERY_INSERT_INTO_USER, new String[]{"id"});

            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());

            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public User getById(Long id) {
        return getAll().stream()
                .filter(user -> id.equals(user.getId()))
                .findAny()
                .orElseThrow((() -> new NoSuchElementException(String.format("User with id %s not found", id))));
    }

    @Override
    public User getByLoginAndPassword(String login, String password) {
        return getAll().stream()
                .filter(user -> login.equals(user.getLogin())
                        && password.equals(user.getPassword()))
                .findAny()
                .orElse(new User("Unknown"));
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query(QUERY_SELECT_FROM_USER, BeanPropertyRowMapper.newInstance(User.class));
    }
}
