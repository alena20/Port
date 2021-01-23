package edu.epam.multithreading.entity.state.impl;

import edu.epam.multithreading.entity.Container;
import edu.epam.multithreading.entity.Port;
import edu.epam.multithreading.entity.Ship;
import edu.epam.multithreading.entity.state.AbstractState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class LoadState implements AbstractState {

    private static final Logger logger = LogManager.getLogger(LoadState.class);
    private static final LoadState INSTANCE = new LoadState();
    private static final Port PORT = Port.getInstance();
    private static final int LOAD_NUMBER = 5;
    private static final int LOAD_DURATION = 2;

    private LoadState() {
    }

    public static LoadState getInstance() {
        return INSTANCE;
    }

    @Override
    public void loadContainers(Ship ship) {
        for (int i = 0; i < LOAD_NUMBER; i++) {
            Optional<Container> optional = PORT.getContainer();
            if (optional.isPresent()) {
                Container container = optional.get();
                ship.loadContainer(container);
                logger.info("Container {} was moved from cargo warehouse to ship {}, cargo warehouse size is {}",
                        container.getContainerId(), ship.getShipId(), PORT.warehouseSize());
                try {
                    TimeUnit.SECONDS.sleep(LOAD_DURATION);
                } catch (InterruptedException e) {
                    logger.error(e);
                }
            }
        }
        ship.setCurrentState(LeavingPierState.getInstance());
    }

    @Override
    public void unloadContainers(Ship ship) {
        while (ship.getSize() != 0) {
            Optional<Container> optional = ship.unloadContainer(0);
            if (optional.isPresent()) {
                Container container = optional.get();
                PORT.addContainer(container);
                logger.info("Container {} was moved from ship {} to warehouse, warehouse size is {}",
                        container.getContainerId(), ship.getShipId(), PORT.warehouseSize());
                try {
                    TimeUnit.SECONDS.sleep(LOAD_DURATION);
                } catch (InterruptedException e) {
                    logger.error(e);
                }
            }
        }
    }

    @Override
    public void requestPier(Ship ship) {
        logger.error("Pier was already requested!");
    }

    @Override
    public void leavePier(Ship ship) {
        logger.error("Invalid action, ship {} is in loading state!", ship.getShipId());
    }

    @Override
    public void moorToPier(Ship ship) {logger.error("Invalid action, ship {} is in loading state!", ship.getShipId());}
}
