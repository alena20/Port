package edu.epam.multithreading.entity;

import edu.epam.multithreading.exception.ResourceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Port {
    private static final Logger LOGGER = LogManager.getLogger(Port.class);
    private static final Port INSTANCE = new Port();
    private static final CargoWarehouse CARGO_WAREHOUSE = CargoWarehouse.getInstance();
    private static final int PIERS_NUMBER = 3;
    private final Semaphore pierSemaphore = new Semaphore(PIERS_NUMBER, true);
    private final Lock warehouseLock = new ReentrantLock();
    private final Queue<MarinePier> freePiers;
    private final List<MarinePier> usedPiers;

    public static Port getInstance() {
        return INSTANCE;
    }

    private Port() {
        freePiers = new LinkedList<>();
        usedPiers = new ArrayList<>();
        for (int i = 1; i <= PIERS_NUMBER; i++) {
            MarinePier pier = new MarinePier(i);
            freePiers.add(pier);
        }
    }

    public Optional<Container> getContainer() {
        warehouseLock.lock();
        Optional<Container> optional = CARGO_WAREHOUSE.getContainer();
        warehouseLock.unlock();
        return optional;
    }

    public void addContainer(Container container) {
        warehouseLock.lock();
        CARGO_WAREHOUSE.addContainer(container);
        warehouseLock.unlock();
    }

    public int warehouseSize() {
        return CARGO_WAREHOUSE.size();
    }

    public void requestPier(Ship ship) throws ResourceException {
        MarinePier pier;
        try {
            LOGGER.info("{} marine piers available for ship № {} , {} ships in queue",
                    freePiers.size(),  ship.getShipId(),
                    pierSemaphore.getQueueLength());
            pierSemaphore.acquire();
        } catch (InterruptedException e) {
            throw new ResourceException(e);
        }
        pier = freePiers.poll();
        usedPiers.add(pier);
        pier.setShip(ship);
        ship.setPierId(pier.getPierId());
        LOGGER.info("Ship № {} got a pier № {}, {} piers available, {} ships in queue",
                ship.getShipId(), pier.getPierId(), freePiers.size(), pierSemaphore.getQueueLength());
    }

    public void leavePier(int pierId) {
        Optional<MarinePier> optionalPier =
                usedPiers.stream().filter(d -> d.getPierId() == pierId).findFirst();
        if (optionalPier.isPresent()) {
            MarinePier pier = optionalPier.get();
            Optional<Ship> optionalShip = pier.getShip();
            if (optionalShip.isPresent()) {
                Ship ship = optionalShip.get();
                int shipId = ship.getShipId();
                pier.removeShip();
                ship.resetPierId();
                if (usedPiers.remove(pier)) {
                    freePiers.offer(pier);
                    LOGGER.info("Ship {} left pier number {}", shipId, pier.getPierId());
                    pierSemaphore.release();
                }
            }
        }
    }
}
