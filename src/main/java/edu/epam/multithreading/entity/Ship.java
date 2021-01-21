package edu.epam.multithreading.entity;

import edu.epam.multithreading.state.IState;
import edu.epam.multithreading.state.impl.ArriveState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Ship extends Thread {

    private static final Logger logger = LogManager.getLogger(Ship.class);
    private static final int INVALID_DOCK_ID = -1;
    private final int shipId;
    private final int capacity;
    private int pierId;
    private List<Container> containers = new ArrayList<>();
    private IState state;

    public Ship(int shipId, int capacity, List<Container> containers) {
        this.shipId = shipId;
        this.capacity = capacity;
        this.containers = containers;
        pierId = INVALID_DOCK_ID;
        state = ArriveState.getInstance();
    }

    public Ship(int shipId, int capacity) {
        this.shipId = shipId;
        this.capacity = capacity;
        pierId = INVALID_DOCK_ID;
        state = ArriveState.getInstance();
    }

    public Object getCurrentState() {
        return state;
    }

    public void setCurrentState(IState currentState) {
        this.state = currentState;
    }

    public int getShipId() {
        return shipId;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getPierId() {
        return pierId;
    }

    public void setPierId(int pierId) {
        this.pierId = pierId;
    }

    public void resetPierId() {
        pierId = INVALID_DOCK_ID;
    }

    public int getSize() {
        return containers.size();
    }

    public void loadContainer(Container container) {
        if ((containers.size() < capacity)) {
            containers.add(container);
        } else {
            logger.error("Unable to load container, it's more than ship capacity!");
        }
    }

    public Optional<Container> unloadContainer(int idContainer) {
        Container container = containers.remove(idContainer);
        return container == null ? Optional.empty() : Optional.of(container);
    }

    @Override
    public void run() {
        state.requestPier(this);
        state.moorToPier(this);
        state.unloadContainers(this);
        state.loadContainers(this);
        state.leavePier(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ship ship = (Ship) o;
        if (shipId != ship.shipId) {
            return false;
        }
        if (capacity != ship.capacity) {
            return false;
        }
        if (pierId != ship.pierId) {
            return false;
        }
        if (!state.equals(ship.state)) {
            return false;
        }
        return containers.equals(ship.containers);
    }

    @Override
    public int hashCode() {
        int result = shipId;
        result = 31 * result + capacity;
        result = 31 * result + state.hashCode();
        result = 31 * result + pierId;
        result = 31 * result + containers.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Ship{");
        sb.append("shipId=").append(shipId);
        sb.append(", capacity=").append(capacity);
        sb.append(", currentState=").append(state);
        sb.append(", pierId=").append(pierId);
        sb.append(", containers=").append(containers);
        sb.append('}');
        return sb.toString();
    }

}
