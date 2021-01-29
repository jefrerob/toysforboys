package be.vdab.toysforboys.forms;

import java.util.Set;

public class OrdersToShipForm {

    private final Set<Long> orderIdsToShip;

    public OrdersToShipForm(Set<Long> orderIdsToShip) {
        this.orderIdsToShip = orderIdsToShip;
    }

    public Set<Long> getOrderIdsToShip() {
        return orderIdsToShip;
    }
}
