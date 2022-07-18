package dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import dao.OrderDAO;
import model.Order;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Repository
public class OrderDAOImpl implements OrderDAO {

    private static final String QUERY_SELECT_FROM_ORDER = "SELECT * FROM orders";
    private static final String QUERY_INSERT_INTO_ORDER = "INSERT INTO orders (user_id, total_price) VALUES (?,?)";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public OrderDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Long save(Order order) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(QUERY_INSERT_INTO_ORDER, new String[]{"id"});

            ps.setLong(1, order.getUserId());
            ps.setBigDecimal(2, order.getTotalPrice());

            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public List<Order> getAll() {
        return jdbcTemplate.query(QUERY_SELECT_FROM_ORDER, BeanPropertyRowMapper.newInstance(Order.class));
    }
}
