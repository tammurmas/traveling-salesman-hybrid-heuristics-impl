/**
 * Code downloaded from: http://www.theprojectspot.com/tutorial-post/simulated-annealing-algorithm-for-beginners/6
 * Author: Lee Jacobson
 * <p>
 * Edited by: Urmas T.
 * <p>
 * Defines a city object based on its coordinates and index number
 */

package org.tamm.salesman;

/**
 * Defines a city with it's x and y coordinates and index number
 *
 * @param xCoordinate
 * @param yCoordinate
 * @param index
 */
public record City(int xCoordinate, int yCoordinate, int index) {

    /**
     * Calculates distance between the city object and the given city
     * @param city other city object
     * @return distance between two cities
     */
    public double distanceTo(City city) {
        int xDistance = Math.abs(xCoordinate() - city.xCoordinate());
        int yDistance = Math.abs(yCoordinate() - city.yCoordinate());
        return Math.sqrt((xDistance * xDistance) + (yDistance * yDistance));
    }

}