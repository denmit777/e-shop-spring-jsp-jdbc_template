package dao;

import model.Order;

import java.util.List;

public interface OrderDAO {

    Long save(Order order);

    List<Order> getAll();
}
