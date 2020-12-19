package org.tamm.salesman;

import java.util.Random;

/**
 * Roulette wheel implementation
 *
 * @author Urmas T.
 */
public class RouletteWheel {
    
    protected double[][] distMatrix;
    protected double[][] fitMatrix;

    public RouletteWheel() {
        TourManager tourManager = TourManager.getInstance();
        this.distMatrix = new double[tourManager.numberOfCities()][tourManager.numberOfCities()];
        
        double maxDist = 0;
        
        for (int i=0; i<tourManager.numberOfCities(); i++)
        {
            City city1 = tourManager.getCity(i);
            for (int j=0; j<tourManager.numberOfCities(); j++)
            {
                City city2 = tourManager.getCity(j);
                this.distMatrix[i][j] = city1.distanceTo(city2);
                
                if(this.distMatrix[i][j] > maxDist)
                    maxDist = this.distMatrix[i][j];
            }
        }
        
        this.fitMatrix = new double[tourManager.numberOfCities()][tourManager.numberOfCities()];
        double totalFit = 0;//sum of all fitness values to define ranges in the roulette wheel
        
        for (int i=0; i<tourManager.numberOfCities(); i++)
        {
            
            for (int j=0; j<tourManager.numberOfCities(); j++)
            {
                if(this.distMatrix[i][j] == 0)
                    this.fitMatrix[i][j] = 0;
                else
                {
                    this.fitMatrix[i][j] = 1/(this.distMatrix[i][j]/maxDist);//using the max edge as the divider shorter edges get larger fitness values
                    totalFit += this.fitMatrix[i][j];
                }
            }
        }
        
        //calculate ranges on the roulette wheel
        for (int i=0; i<tourManager.numberOfCities(); i++)
        {
            
            for (int j=0; j<tourManager.numberOfCities(); j++)
            {
                 this.fitMatrix[i][j] = this.fitMatrix[i][j]/totalFit*100;
            }
        }
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
        
        for (int i=0; i<TourManager.getInstance().numberOfCities(); i++)
        {
            for (int j=0; j<TourManager.getInstance().numberOfCities(); j++)
            {
                sum += this.fitMatrix[i][j];
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
    
    /**
     * A small helper for generating random integers
     * HINT: http://stackoverflow.com/questions/363681/generating-random-numbers-in-a-range-with-java
     * @return random double value
     */
    public static double randDouble()
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
    
    public double[][] getDistMatrix()
    {
        return this.distMatrix;
    }
    
    public void printFitMatrix()
    {
        TourManager tourManager = TourManager.getInstance();
        for (int i=0; i<tourManager.numberOfCities(); i++)
        {
            for (int j=0; j<tourManager.numberOfCities(); j++)
            {
                System.out.print(this.fitMatrix[i][j]+" ");
            }
            System.out.println();
        }
    }
    
    public void printDistMatrix()
    {
        TourManager tourManager = TourManager.getInstance();
        for (int i=0; i<tourManager.numberOfCities(); i++)
        {
            for (int j=0; j<tourManager.numberOfCities(); j++)
            {
                System.out.print(this.distMatrix[i][j]+" ");
            }
            System.out.println();
        }
    }
    
}
