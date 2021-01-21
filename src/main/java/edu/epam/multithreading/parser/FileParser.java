package edu.epam.multithreading.parser;

import edu.epam.multithreading.entity.Container;
import edu.epam.multithreading.entity.Ship;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FileParser {
    private static final String REGEX_SHIP = "(?<shipId>\\d+)\\s(?<shipCapacity>\\d+):\\s(?<containers>.+)";
    private static final String REGEX_CONTAINER = "(?<containerId>\\d+)\\s(?<containerWeight>\\d+.\\d+);?";
    public static final String SHIP_ID = "shipId";
    public static final String SHIP_CAPACITY = "shipCapacity";
    public static final String CONTAINERS = "containers";
    public static final String CONTAINER_ID = "containerId";
    public static final String CONTAINER_WEIGHT = "containerWeight";

    public List<Ship> parse(List<String> lines) {
        return lines.stream().map(this::parse).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public Ship parse(String line) {
        Pattern pattern = Pattern.compile(REGEX_SHIP);
        Matcher matcher = pattern.matcher(line);
        Ship ship = null;
        if (matcher.find()) {
            String stringShipId = matcher.group(SHIP_ID);
            int shipId = Integer.parseInt(stringShipId);
            String stringShipCapacity = matcher.group(SHIP_CAPACITY);
            int shipCapacity = Integer.parseInt(stringShipCapacity);
            String stringContainers = matcher.group(CONTAINERS);
            List<Container> containers = parseContainers(stringContainers);
            ship = new Ship(shipId, shipCapacity, containers);
        }
        return ship;
    }

    private List<Container> parseContainers(String data) {
        List<Container> containers = new ArrayList<>();
        Pattern pattern = Pattern.compile(REGEX_CONTAINER);
        Matcher matcher = pattern.matcher(data);
        while (matcher.find()) {
            String stringContainerId = matcher.group(CONTAINER_ID);
            int containerId = Integer.parseInt(stringContainerId);
            String stringContainerWeight = matcher.group(CONTAINER_WEIGHT);
            double containerWeight = Double.parseDouble(stringContainerWeight);
            Container container = new Container(containerId, containerWeight);
            containers.add(container);
        }
        return containers;
    }
}
