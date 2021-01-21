package edu.epam.multithreading.state.impl;

import edu.epam.multithreading.entity.Port;
import edu.epam.multithreading.entity.Ship;
import edu.epam.multithreading.state.IState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

public class LeavingPierState implements IState {

    private static final Logger logger = LogManager.getLogger(LeavingPierState.class);
    private static final LeavingPierState INSTANCE = new LeavingPierState();
    private static final Port PORT = Port.getInstance();
    private static final int LEAVING_DURATION = 4;

    private LeavingPierState() {
    }

    public static LeavingPierState getInstance() {
        return INSTANCE;
    }

    @Override
    public void loadContainers(Ship ship) {
        logger.error("Invalid action, ship {} is in leaving state!", ship.getShipId());
    }

    @Override
    public void unloadContainers(Ship ship) {
        logger.error("Invalid action, ship {} is in leaving state!", ship.getShipId());
    }

    @Override
    public void requestPier(Ship ship) {
        logger.error("Invalid action, ship {} is in leaving state!", ship.getShipId());
    }

    @Override
    public void leavePier(Ship ship) {
        logger.info("Ship {} is leaving pier № {}", ship.getShipId(), ship.getPierId());
        int pierId = ship.getPierId();
        try {
            TimeUnit.SECONDS.sleep(LEAVING_DURATION);
        } catch (InterruptedException e) {
            logger.error(e);
        }
        logger.info("Ship {} has left pier number {}!", ship.getShipId(), ship.getPierId());
        PORT.leavePier(pierId);
    }

    @Override
    public void moorToPier(Ship ship) {logger.error("Invalid action, ship {} is in leaving state!", ship.getShipId());}
}
