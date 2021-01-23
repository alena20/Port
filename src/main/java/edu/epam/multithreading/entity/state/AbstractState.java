package edu.epam.multithreading.entity.state;

import edu.epam.multithreading.entity.Ship;

public interface AbstractState {
    void moorToPier(Ship ship);

    void unloadContainers(Ship ship);

    void loadContainers(Ship ship);

    void leavePier(Ship ship);

    void requestPier(Ship ship);
}
