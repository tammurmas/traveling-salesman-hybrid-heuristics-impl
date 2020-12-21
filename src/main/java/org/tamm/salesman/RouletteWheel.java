package org.tamm.salesman;

import java.util.Random;

/**
 * Roulette wheel implementation
 *
 * @author Urmas T.
 */
public class RouletteWheel {
    
    private double[][] distMatrix;
    private double[][] fitMatrix;

    public RouletteWheel() {
        initRouletteWheel();
    }

    /**
     * Spinning the wheel 
     * Generate a random winning number and sum the values in matrix until reaching it
     * Average complexity (n*n)/2
     */
    public int[] spinWheel()
    {
        double winNr  = randDouble();
        double sum    = 0.0;
        int[] indices = new int[2];

        Tour managerTour = TourManager.getInstance().getTour();
        for (int i = 0; i< managerTour.tourSize(); i++)
        {
            for (int j = 0; j< managerTour.tourSize(); j++)
            {
                sum += fitMatrix[i][j];
                if(winNr < sum)//we have reached the needed range
                {
                    indices[0] = i;
                    indices[1] = j;
                    
                    return indices;
                }
            }
        }
        
        return indices;
    }

    public double[][] getDistMatrix()
    {
        return distMatrix;
    }

    public void printFitMatrix()
    {
        Tour managerTour = TourManager.getInstance().getTour();
        for (int i=0; i<managerTour.tourSize(); i++)
        {
            for (int j=0; j<managerTour.tourSize(); j++)
            {
                System.out.print(fitMatrix[i][j]+" ");
            }
            System.out.println();
        }
    }

    public void printDistMatrix()
    {
        Tour managerTour = TourManager.getInstance().getTour();
        for (int i=0; i<managerTour.tourSize(); i++)
        {
            for (int j=0; j<managerTour.tourSize(); j++)
            {
                System.out.print(distMatrix[i][j]+" ");
            }
            System.out.println();
        }
    }

    private void initRouletteWheel() {
        TourManager tourManager = TourManager.getInstance();
        double maxDistance = calculateDistanceMatrix(tourManager);
        calculateFitnessMatrix(tourManager, maxDistance);
    }

    private void calculateFitnessMatrix(TourManager tourManager, double maxDistance) {
        Tour managerTour = TourManager.getInstance().getTour();
        fitMatrix = new double[managerTour.tourSize()][managerTour.tourSize()];
        double totalFitness = 0;//sum of all fitness values to define ranges in the roulette wheel

        for (int i = 0; i< managerTour.tourSize(); i++)
        {

            for (int j = 0; j< managerTour.tourSize(); j++)
            {
                if(distMatrix[i][j] == 0)
                    fitMatrix[i][j] = 0;
                else
                {
                    fitMatrix[i][j] = 1/(distMatrix[i][j]/ maxDistance);//using the max edge as the divider shorter edges get larger fitness values
                    totalFitness += fitMatrix[i][j];
                }
            }
        }

        //calculate ranges on the roulette wheel
        calculateRanges(totalFitness);
    }

    private void calculateRanges(double totalFitness) {
        Tour managerTour = TourManager.getInstance().getTour();
        for (int i = 0; i< managerTour.tourSize(); i++)
        {
            for (int j = 0; j< managerTour.tourSize(); j++)
            {
                fitMatrix[i][j] = fitMatrix[i][j]/ totalFitness *100;
            }
        }
    }

    private double calculateDistanceMatrix(TourManager tourManager) {
        Tour managerTour = TourManager.getInstance().getTour();
        double maxDist = 0;

        distMatrix = new double[managerTour.tourSize()][managerTour.tourSize()];
        for (int i = 0; i< managerTour.tourSize(); i++)
        {
            City city1 = managerTour.getCity(i);
            for (int j = 0; j< managerTour.tourSize(); j++)
            {
                City city2 = managerTour.getCity(j);
                distMatrix[i][j] = city1.distanceTo(city2);

                if(distMatrix[i][j] > maxDist)
                    maxDist = distMatrix[i][j];
            }
        }
        return maxDist;
    }
    
    /**
     * A small helper for generating random integers
     * HINT: http://stackoverflow.com/questions/363681/generating-random-numbers-in-a-range-with-java
     * @return random double value
     */
    private static double randDouble()
    {
        Random rand = new Random();
        
        double val = rand.nextDouble() * 100.0;
        //exclude zero values
        if(val == 0.0)
        {
            val = randDouble();
        }
        
        return val;
    }

}
