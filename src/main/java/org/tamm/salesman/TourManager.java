package org.tamm.salesman;

import lombok.Getter;

import java.util.ArrayList;

/**
 *
 * Code downloaded from: <a href="http://www.theprojectspot.com/tutorial-post/simulated-annealing-algorithm-for-beginners/6">...</a>
 * Author: Lee Jacobson
 * Edited by: Urmas T.
 * Manages all the cities in our TSP
 */
@Getter
public class TourManager {

    private static final TourManager INSTANCE = new TourManager();

    private final Tour tour;

    private TourManager () {
        tour = new Tour(new ArrayList<>());
    }

    public static TourManager getInstance(){
        return INSTANCE;
    }
    
}