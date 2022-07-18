package dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import dao.GoodDAO;
import model.Good;

import javax.sql.DataSource;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class GoodDAOImpl implements GoodDAO {

    private static final String QUERY_SELECT_FROM_GOOD = "SELECT * FROM good";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GoodDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Good> getAll() {
        return jdbcTemplate.query(QUERY_SELECT_FROM_GOOD, BeanPropertyRowMapper.newInstance(Good.class));
    }

    @Override
    public Good getByTitleAndPrice(String title, String price) {
        return getAll().stream()
                .filter(good -> title.equals(good.getTitle())
                        && price.equals(String.valueOf(good.getPrice())))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException(String.format("Good with title %s and price %s not found", title, price)));
    }
}
