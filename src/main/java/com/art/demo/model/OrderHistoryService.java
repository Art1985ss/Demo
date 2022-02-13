package com.art.demo.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderHistoryService {
    //TODO Momento pattern
    private final Map<Long, Deque<Order.State>> ordersHistories = new HashMap<>();
    private static OrderHistoryService instance;

    public static OrderHistoryService getInstance() {
        if (instance == null)
            instance = new OrderHistoryService();
        return instance;
    }

    public void save(final Order order) {
        if (ordersHistories.containsKey(order.getId())) {
            ordersHistories.get(order.getId()).push(order.getState());
        } else {
            final Deque<Order.State> history = new ArrayDeque<>();
            history.push(order.getState());
            ordersHistories.put(order.getId(), history);
        }
    }

    public void undo(final Order order) {
        if (ordersHistories.containsKey(order.getId())) {
            final Deque<Order.State> history = ordersHistories.get(order.getId());
            if (!history.isEmpty())
                order.setState(history.pop());
        }
    }

    public void remove(final Order order) {
        ordersHistories.remove(order.getId());
    }
}
