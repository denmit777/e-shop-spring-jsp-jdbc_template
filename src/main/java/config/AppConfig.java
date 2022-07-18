package config;

import javax.sql.DataSource;

import dao.GoodDAO;
import dao.OrderDAO;
import dao.UserDAO;
import dao.impl.GoodDAOImpl;
import dao.impl.OrderDAOImpl;
import dao.impl.UserDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import service.CartService;
import service.GoodService;
import service.OrderService;
import service.UserService;
import service.impl.CartServiceImpl;
import service.impl.GoodServiceImpl;
import service.impl.OrderServiceImpl;
import service.impl.UserServiceImpl;

import java.util.Objects;

@Configuration
@ComponentScan("main")
@PropertySource("classpath:db/db.properties")
public class AppConfig {

    @Autowired
    private Environment environment;

    private static final String URL = "db.URL";
    private static final String USER = "db.userName";
    private static final String DRIVER = "db.driver";
    private static final String PASSWORD = "db.password";

    @Bean
    DataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();

        driverManagerDataSource.setUrl(environment.getProperty(URL));
        driverManagerDataSource.setUsername(environment.getProperty(USER));
        driverManagerDataSource.setPassword(environment.getProperty(PASSWORD));
        driverManagerDataSource.setDriverClassName(Objects.requireNonNull(environment.getProperty(DRIVER)));

        return driverManagerDataSource;
    }

    @Bean
    public UserDAO userDAO() {
        return new UserDAOImpl(dataSource());
    }

    @Bean
    public GoodDAO goodDAO() {
        return new GoodDAOImpl(dataSource());
    }

    @Bean
    public OrderDAO orderDAO() {
        return new OrderDAOImpl(dataSource());
    }

    @Bean
    public UserService userService() {
        return new UserServiceImpl(userDAO());
    }

    @Bean
    public GoodService goodService() {
        return new GoodServiceImpl(goodDAO());
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(orderDAO());
    }

    @Bean
    public CartService cartService() {
        return new CartServiceImpl(userDAO(), goodDAO());
    }
}
