/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tamm.salesman;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

/**
 * TSP using the greedy nearest neighbor approach
 * 
 * @author Urmas T.
 */

@Slf4j
public class NearestNeighbor implements TravelingSalesmanAlgorithm {
    
    /**
     * Calculating the path using the nearest neighbor algorithm
     */
    @Override
    public Result calculate() {
        StopWatch timer = new StopWatch();
        timer.start();

        // Initialize random solution
        Tour currentSolution = new Tour();
        currentSolution.generateIndividual();//using a random tour as the starting point
        log.info("Initial solution distance: {}", currentSolution.getDistance());

        Tour tour = new Tour();
        ArrayList<Integer> visited = new ArrayList<>();

        City current = currentSolution.getCity(0);//add the first city into our tour
        tour.setCity(0, current);
        visited.add(current.index());
        
        double minDistance = Double.MAX_VALUE;
        int nextId = current.index();
        int n = 1;

        while (visited.size() < currentSolution.getCities().size())
        {
            for (int i=1; i<currentSolution.getCities().size(); i++)
            {
                City city = currentSolution.getCity(i);
                //put aside cities we've already been to
                if(!visited.contains(city.index()))
                {
                    double distance = current.distanceTo(city);
                    if(distance < minDistance)
                    {
                        minDistance = distance;
                        nextId = city.index();
                    }
                }
                    
            }
            
            current = currentSolution.getCity(nextId);//set next city as current
            visited.add(nextId);
            tour.setCity(n, current);//add to tour
            n++;
            minDistance = Double.MAX_VALUE;
            
        }

        timer.stop();

        return new Result(timer.getElapsedTimeSecs(), tour.getDistance());
    }

}
