package edu.epam.multithreading.state;

import edu.epam.multithreading.entity.Ship;

public interface IState {

    void moorToPier(Ship ship);

    void unloadContainers(Ship ship);

    void loadContainers(Ship ship);

    void leavePier(Ship ship);

    void requestPier(Ship ship);
}
