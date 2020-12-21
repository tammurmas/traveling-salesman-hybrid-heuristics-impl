package org.tamm.salesman;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * Code downloaded from: http://www.theprojectspot.com/tutorial-post/simulated-annealing-algorithm-for-beginners/6
 * Author: Lee Jacobson
 *
 * Edited by: Urmas T.
 *
 * Defines a path
 */
@Getter
@Setter
public class Tour {

    // Holds our tour of cities
    private ArrayList<City> cities;
    // Cache
    private int distance = 0;

    // Constructs a blank tour
    public Tour() {
        cities = new ArrayList<>();
        for (int i = 0; i < TourManager.getInstance().getTour().tourSize(); i++) {
            cities.add(null);
        }
    }

    // Constructs a tour from a list of cities
    public Tour(ArrayList<City> cities){
        this.cities = cities;
    }

    /**
     * Generates a new tour
     */
    public void generateIndividual() {
        // Loop through all our destination cities and add them to our tour
        for (int cityIndex = 0; cityIndex < TourManager.getInstance().getTour().tourSize(); cityIndex++) {
          setCity(cityIndex, TourManager.getInstance().getTour().getCity(cityIndex));
        }
        // Randomly reorder the tour
        Collections.shuffle(cities);
    }

    // Gets a city from the tour
    public City getCity(int tourPosition) {
        return cities.get(tourPosition);
    }

    /**
     * Sets a city in a certain position within a tour
     * 
     * @param tourPosition - position of city in tour
     * @param city - the city to be set on given index
     */
    public void setCity(int tourPosition, City city) {
        cities.set(tourPosition, city);
        // If the tours been altered we need to reset the fitness and distance
        distance = 0;
    }
    
    /**
     * Calculates the total distance of a tour
     * @return total distance of tour
     */
    public int getDistance() {
        if (distance == 0) {
            int tourDistance = 0;
            // Loop through our tour's cities
            for (int cityIndex=0; cityIndex < tourSize(); cityIndex++) {
                // Get city we're traveling from
                City fromCity = getCity(cityIndex);
                // City we're traveling to
                City destinationCity;
                // Check we're not on our tour's last city, if we are set our 
                // tour's final destination city to our starting city
                if (cityIndex + 1 < tourSize()){
                    destinationCity = getCity(cityIndex+1);
                }
                else{
                    destinationCity = getCity(0);
                }
                // Get the distance between the two cities
                tourDistance += fromCity.distanceTo(destinationCity);
            }
            distance = tourDistance;
        }
        return distance;
    }

    /**
     * "Swap" the "sink" city by removing it from its original location and placing behind the "source" city
     * @param sourceId id of the source city
     * @param sinkId id of the sink city
     */
    public void swap(int sourceId, int sinkId)
    {
        Tour baseTour = TourManager.getInstance().getTour();
        City sink = baseTour.getCity(sinkId);

        ArrayList<City> copy = new ArrayList<>(cities);
        cities.clear();
        
        for (int cityIndex = 0; cityIndex < baseTour.tourSize(); cityIndex++) {
            
            if((copy.get(cityIndex)).getI() != sinkId)
            {
                cities.add(copy.get(cityIndex));
            }
            
            if((copy.get(cityIndex)).getI() == sourceId)
            {
                cities.add(sink);
            }
        }
    }

    /**
     * Helper to get the tour size
     * @return number of cities in tour
     */
    public int tourSize() {
        return cities.size();
    }
    
    @Override
    public String toString() {
        StringBuilder tour = new StringBuilder();
        for (int i = 0; i < tourSize(); i++) {
            if(i+1 < tourSize())
                tour.append("line ").append(getCity(i)).append(" ").append(getCity(i + 1)).append("\n");
        }
        return tour.toString();
    }
}