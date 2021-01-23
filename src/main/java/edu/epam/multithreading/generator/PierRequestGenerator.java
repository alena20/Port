package edu.epam.multithreading.generator;

import edu.epam.multithreading.entity.Ship;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class PierRequestGenerator {
    private static final Logger logger = LogManager.getLogger(PierRequestGenerator.class);
    private static final int DURATION = 2;
    private static final long MIN_DURATION = 1;

    public void generateRequests(List<Ship> ships) {
        for (Ship ship : ships) {
            try {
                long duration = new Random().nextInt(DURATION) + MIN_DURATION;
                TimeUnit.SECONDS.sleep(duration);
            } catch (InterruptedException e) {
                logger.error(e);
            }
            ship.start();
        }
    }
}
