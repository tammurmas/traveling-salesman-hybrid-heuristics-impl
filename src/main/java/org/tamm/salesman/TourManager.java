package org.tamm.salesman;

import java.util.ArrayList;

/**
 *
 * Code downloaded from: http://www.theprojectspot.com/tutorial-post/simulated-annealing-algorithm-for-beginners/6
 * Author: Lee Jacobson
 *
 * Edited by: Urmas T.
 *
 * Manages all the cities in our TSP
 */
public class TourManager {

    private static final TourManager INSTANCE = new TourManager();

    private final ArrayList<City> cities;

    private TourManager () {
        cities = new ArrayList<>();
    }

    public static TourManager getInstance(){
        return INSTANCE;
    }

    /**
     * Adds a destination city
     * @param city to be added to tour
     */
    public void addCity(City city) {
        cities.add(city);
    }
    
    /**
     * Returns a city on the given index
     * @param index - city's index in tour
     * @return get the city on given index
     */
    public City getCity(int index){
        return cities.get(index);
    }
    
    /**
     * Get the number of cities still to be visited
     * @return number of cities left as unvisited
     */
    public int numberOfCities(){
        return cities.size();
    }
    
}