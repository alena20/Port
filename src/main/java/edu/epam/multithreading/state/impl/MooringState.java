package edu.epam.multithreading.state.impl;

import edu.epam.multithreading.entity.Ship;
import edu.epam.multithreading.state.IState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

public class MooringState implements IState {
    private static final Logger logger = LogManager.getLogger(MooringState.class);
    private static final MooringState INSTANCE = new MooringState();
    private static final int MOORING_DURATION = 4;

    private MooringState() {
    }

    public static MooringState getInstance() {
        return INSTANCE;
    }

    @Override
    public void loadContainers(Ship ship) {
        logger.error("Invalid action, ship {}: unable to load containers in mooring state!", ship.getShipId());
    }

    @Override
    public void unloadContainers(Ship ship) {
        logger.error("Invalid action, ship {}: unable to unload containers in mooring state!", ship.getShipId());
    }

    @Override
    public void requestPier(Ship ship) {
        logger.error("Invalid action, ship {}: dock was already requested!", ship.getShipId());
    }

    @Override
    public void leavePier(Ship ship) {
        logger.error("Invalid action, ship {} is in mooring state!", ship.getShipId());
    }

    @Override
    public void moorToPier(Ship ship) {
        logger.info("Ship {} is mooring to dock № {}", ship.getShipId(), ship.getPierId());
        try {
            TimeUnit.SECONDS.sleep(MOORING_DURATION);
        } catch (InterruptedException e) {
            logger.error(e);
        }
        logger.info("Ship {} arrived to pier № {}", ship.getShipId(), ship.getPierId());
        ship.setCurrentState(LoadState.getInstance());
    }
}
