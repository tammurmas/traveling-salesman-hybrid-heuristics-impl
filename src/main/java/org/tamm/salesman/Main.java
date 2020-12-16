package org.tamm.salesman;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Provide input file name!");
            return;
        }
        log.info("Provided input file name: {}", args[0]);

        TravelingSalesmanAlgorithm.readTourFromFile(args[0]);

        SASwap saSwap = new SASwap();
        Result saSwapResult = saSwap.calculate();
        logResults(saSwap, saSwapResult);

        SAWheel saWheel = new SAWheel();
        Result saWheelResult = saWheel.calculate();
        logResults(saWheel, saWheelResult);

        NearestNeighbor nearestNeighbor = new NearestNeighbor();
        Result nearestNeighborResult = nearestNeighbor.calculate();
        logResults(nearestNeighbor, nearestNeighborResult);
    }

    private static void logResults(TravelingSalesmanAlgorithm algorithm, Result result) {
        log.info("{}: distance = {}; time = {}", algorithm.getName(), result.getDistance(), result.getDuration());
    }
}
