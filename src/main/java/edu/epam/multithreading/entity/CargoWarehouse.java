package edu.epam.multithreading.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CargoWarehouse {

    private static final Logger LOGGER = LogManager.getLogger(CargoWarehouse.class);
    private static final int MAX_CAPACITY = 20;
    private static final CargoWarehouse INSTANCE = new CargoWarehouse();
    private final List<Container> containers = new ArrayList<>();

    public static CargoWarehouse getInstance() {
        return INSTANCE;
    }

    public Optional<Container> getContainer() {
        Optional<Container> optional = Optional.empty();
        if (!containers.isEmpty()) {
            optional = Optional.of(containers.remove(0));
        }
        return optional;
    }

    public void addContainer(Container container) {
        if (containers.size() < MAX_CAPACITY) {
            containers.add(container);
        } else {
            LOGGER.warn("Unable to add container, warehouse is full!");
        }
    }

    public int size() {
        return containers.size();
    }
}
