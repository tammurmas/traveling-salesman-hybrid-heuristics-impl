package org.tamm.salesman;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * Code downloaded from:
 * <a href="http://www.theprojectspot.com/tutorial-post/simulated-annealing-algorithm-for-beginners/6">...</a>
 * Author: Lee Jacobson
 * Edited by: Urmas T.
 * Calculating optimal solution for the TSP using the modified SA algorithm with roulette wheel
 */
@Slf4j
public class SAWheel implements TravelingSalesmanAlgorithm {

    @Override
    public Result calculate() {
        
        StopWatch timer = new StopWatch();
        timer.start();
        
        // calculate the distance matrix
        // since it is part of the pre-process phase we have to re-calculate it each time            
        RouletteWheel wheel = new RouletteWheel();

        // Initialize random solution
        Tour currentSolution = new Tour();
        currentSolution.generateIndividual();//using a random tour as the starting point

        //Tour currentSolution = TSP.nearestNeighbor();//using the greedy distance as the starting point

        log.info("Initial solution distance: " + currentSolution.getDistance());
        
        // Set as current best
        Tour best = new Tour(currentSolution.getCities());
        
        double temp        = 100000;
        double coolingRate = 1/temp;

        // Loop until system has cooled
        while (temp > 1) {
            // Create new neighbour tour
            Tour newSolution = new Tour(currentSolution.getCities());

            int[] indices = wheel.spinWheel();

            newSolution.swap(indices[0], indices[1]);

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
            temp *= 1-coolingRate;
        }

        timer.stop();

        return new Result(timer.getElapsedTimeSecs(), best.getDistance());
    }
    
    /**
     * Function to determine whether to accept newly generated solution
     * @param energy
     * @param newEnergy
     * @param temperature
     * @return 
     */
    private static double acceptanceProbability(int energy, int newEnergy, double temperature) {
        // If the new solution is better, accept it
        if (newEnergy < energy) {
            return 1.0;
        }
        // If the new solution is worse, calculate an acceptance probability
        return Math.exp((energy - newEnergy) / temperature);
    }

}