package edu.epam.multithreading.entity.state.impl;

import edu.epam.multithreading.entity.Port;
import edu.epam.multithreading.entity.Ship;
import edu.epam.multithreading.entity.state.AbstractState;
import edu.epam.multithreading.exception.ResourceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ArriveState implements AbstractState {
    private static final Logger logger = LogManager.getLogger(ArriveState.class);
    private static final ArriveState INSTANCE = new ArriveState();
    private static final Port PORT = Port.getInstance();

    private ArriveState() {
    }

    public static ArriveState getInstance() {
        return INSTANCE;
    }

    @Override
    public void loadContainers(Ship ship) {
        logger.error("Unable to load containers in arriving state!");
    }

    @Override
    public void unloadContainers(Ship ship) {
        logger.error("Unable to unload containers in arriving state!");
    }

    @Override
    public void requestPier(Ship ship) {
        try {
            PORT.requestPier(ship);
        } catch (ResourceException e) {
            throw new RuntimeException("Unable to get pier!", e);
        }
        ship.setCurrentState(MooringState.getInstance());
    }

    @Override
    public void leavePier(Ship ship) {
        logger.error("Invalid action, ship {} is in arriving state!", ship.getShipId());
    }

    @Override
    public void moorToPier(Ship ship) {
        logger.error("Invalid action, ship {} is in arriving state!", ship.getShipId());
    }
}
