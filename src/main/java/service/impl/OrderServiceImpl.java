package service.impl;

import dao.OrderDAO;
import model.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import service.OrderService;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER = LogManager.getLogger(OrderServiceImpl.class.getName());

    private static final String ORDER_NOT_PLACED = "order not placed yet";
    private static final String RESULT_ORDER = "your order:";

    private final OrderDAO orderDAO;

    public OrderServiceImpl(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @Override
    public void save(Long userId, BigDecimal totalPrice) {
        Order order = new Order();

        order.setUserId(userId);
        order.setTotalPrice(totalPrice);

        if (!totalPrice.equals(BigDecimal.valueOf(0))) {
            order.setId(orderDAO.save(order));

            LOGGER.info("New order: {}", order);
        }
    }

    @Override
    public List<Order> getAll() {
        return orderDAO.getAll();
    }

    @Override
    public String orderResult(BigDecimal totalPrice) {
        if (totalPrice.equals(BigDecimal.valueOf(0))) {

            LOGGER.info(ORDER_NOT_PLACED);

            return ORDER_NOT_PLACED;
        }

        return RESULT_ORDER;
    }
}
