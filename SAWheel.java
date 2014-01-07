/**
 * 
 * Code downloaded from: http://www.theprojectspot.com/tutorial-post/simulated-annealing-algorithm-for-beginners/6
 * Author: Lee Jacobson
 * 
 * Edited by: Urmas T.
 * 
 * Calculating optimal solution for the TSP using the modified SA algorithm with roulette wheel
 */

package tamm.aa.project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SAWheel {

    private static final String fileName = "berlin52.txt";//using input files from lectures to test

    public static void main(String[] args) throws IOException {

        BufferedReader inputStream = null;

        String line;
        
        try {
            inputStream = new BufferedReader(new FileReader(fileName));
            
            int i = 0;
            
            while( (line = inputStream.readLine()) != null)
            {
                //skip first line
                if(i > 0)
                {
                    String[] lineParts = line.split("\\s+");//any number of whitespace characters as delimiter
                    
                    int x = Integer.parseInt(lineParts[1]);
                    int y = Integer.parseInt(lineParts[2]);
                    
                    City city = new City(x, y, i-1);//we start city indices from 0 to populate the distance matrix in correct manner

                    TourManager.addCity(city);
                            
                }
                i++;
            }
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
        finally{
             if (inputStream != null) {
                inputStream.close();
            }
        }
        
        StopWatch timer = new StopWatch();
        
        // calculate the distance matrix
        // since it is part of the pre-process phase we have to re-calculate it each time            
        RouletteWheel wheel = new RouletteWheel();

        // Initialize intial solution
        Tour currentSolution = new Tour();
        currentSolution.generateIndividual();

        // Set as current best
        Tour best = new Tour(currentSolution.getTour());

        //Tour currentSolution = TSP.nearestNeighbor();

        System.out.println("Initial solution distance: " + currentSolution.getDistance());

        double temp        = 100000;
        double coolingRate = 1/temp;

        timer.start();

        // Loop until system has cooled
        while (temp > 1) {
            // Create new neighbour tour
            Tour newSolution = new Tour(currentSolution.getTour());

            int[] indices = wheel.spinWheel();

            newSolution.swap(indices[0], indices[1]);

            // Get energy of solutions
            int currentEngery = currentSolution.getDistance();
            int neighbourEngery = newSolution.getDistance();

            // Decide if we should accept the neighbour
            if (acceptanceProbability(currentEngery, neighbourEngery, temp) > Math.random()) {
                currentSolution = new Tour(newSolution.getTour());
            }

            // Keep track of the best solution found
            if (currentSolution.getDistance() < best.getDistance()) {
                best = new Tour(currentSolution.getTour());
            }

            // Cool system
            temp *= 1-coolingRate;
        }

        timer.stop();

        System.out.println("Final solution distance: " + best.getDistance());
        System.out.println("Elapsed time: " + timer.getElapsedTimeSecs());
        System.out.println();
            
    }
    
    /**
     * Function to determine whether or not accept the newly generated solution
     * @param engery
     * @param newEngery
     * @param temperature
     * @return 
     */
    public static double acceptanceProbability(int engery, int newEngery, double temperature) {
        // If the new solution is better, accept it
        if (newEngery < engery) {
            return 1.0;
        }
        // If the new solution is worse, calculate an acceptance probability
        return Math.exp((engery - newEngery) / temperature);
    }
}