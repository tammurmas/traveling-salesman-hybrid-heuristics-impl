package org.tamm.salesman;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * Code downloaded from:
 * <a href="http://www.theprojectspot.com/tutorial-post/simulated-annealing-algorithm-for-beginners/6">...</a>
 * Author: Lee Jacobson
 * Edited by: Urmas T.
 * Calculating optimal solution for the TSP using the SA algorithm with the swapping operator
 */
@Slf4j
public class SASwap implements TravelingSalesmanAlgorithm {

    private static final double INITIAL_TEMPERATURE = 100000;
    private static final double COOLING_RATE = 1 / INITIAL_TEMPERATURE;

    @Override
    public Result calculate() {

        StopWatch timer = new StopWatch();
        timer.start();

        // Initialize random solution
        Tour currentSolution = new Tour();
        currentSolution.generateIndividual();//using a random tour as the starting point

        //Tour currentSolution = TSP.nearestNeighbor();//using the greedy distance as the starting point

        log.info("Initial solution distance: " + currentSolution.getDistance());

        // Set as current best
        Tour best = new Tour(currentSolution.getCities());

        // Set initial temp
        double temp = INITIAL_TEMPERATURE;

        // Loop until system has cooled
        while (temp > 1) {
            // Create new neighbour tour
            Tour newSolution = new Tour(currentSolution.getCities());

            // Get a random positions in the tour
            int tourPos1 = (int) (newSolution.tourSize() * Math.random());
            int tourPos2 = (int) (newSolution.tourSize() * Math.random());

            // Get the cities at selected positions in the tour
            City citySwap1 = newSolution.getCity(tourPos1);
            City citySwap2 = newSolution.getCity(tourPos2);

            // Swap them
            newSolution.setCity(tourPos2, citySwap1);
            newSolution.setCity(tourPos1, citySwap2);

            // Get energy of solutions
            int currentEngery = currentSolution.getDistance();
            int neighbourEngery = newSolution.getDistance();

            // Decide if we should accept the neighbour
            if (acceptanceProbability(currentEngery, neighbourEngery, temp) > Math.random()) {
                currentSolution = new Tour(newSolution.getCities());
            }

            // Keep track of the best solution found
            if (currentSolution.getDistance() < best.getDistance()) {
                best = new Tour(currentSolution.getCities());
            }

            // Cool system
            temp *= 1-COOLING_RATE;
        }
        timer.stop();

        return new Result(timer.getElapsedTimeSecs(), best.getDistance());
    }

    /**
     * Function to determine whether to accept newly generated solution
     * @param engery
     * @param newEngery
     * @param temperature
     * @return 
     */
    private static double acceptanceProbability(int engery, int newEngery, double temperature) {
        // If the new solution is better, accept it
        if (newEngery < engery) {
            return 1.0;
        }
        // If the new solution is worse, calculate an acceptance probability
        return Math.exp((engery - newEngery) / temperature);
    }
}