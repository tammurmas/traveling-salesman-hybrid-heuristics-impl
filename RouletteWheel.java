package tamm.aa.project;

import java.util.ArrayList;
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
        this.distMatrix = new double[TourManager.numberOfCities()][TourManager.numberOfCities()];
        
        double maxDist = 0;
        
        for (int i=0; i<TourManager.numberOfCities(); i++)
        {
            City city1 = TourManager.getCity(i);
            for (int j=0; j<TourManager.numberOfCities(); j++)
            {
                City city2 = TourManager.getCity(j);
                this.distMatrix[i][j] = city1.distanceTo(city2);
                
                if(this.distMatrix[i][j] > maxDist)
                    maxDist = this.distMatrix[i][j];
            }
        }
        
        this.fitMatrix = new double[TourManager.numberOfCities()][TourManager.numberOfCities()];
        double totalFit = 0;
        
        for (int i=0; i<TourManager.numberOfCities(); i++)
        {
            
            for (int j=0; j<TourManager.numberOfCities(); j++)
            {
                if(this.distMatrix[i][j] == 0)
                    this.fitMatrix[i][j] = 0;
                else
                {
                    this.fitMatrix[i][j] = 1/(this.distMatrix[i][j]/maxDist);//guess we should put the max edge as the divider
                    totalFit += this.fitMatrix[i][j];
                }
            }
        }
        
        this.printFitMatrix();
        //System.out.println("Total fit: "+totalFit);
        
        double totalSum = 0;
        for (int i=0; i<TourManager.numberOfCities(); i++)
        {
            
            for (int j=0; j<TourManager.numberOfCities(); j++)
            {
                 this.fitMatrix[i][j] = this.fitMatrix[i][j]/totalFit*100;
            }
        }
    }
    
    //just for testing
    public static void main(String[] args)
    {
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        
        list.remove(1);
        list.add(3, 2);
        
        for (int i=0; i<list.size(); i++)
        {
            //System.out.print(list.get(i)+" ");
            TourManager.getCity(i);
        }
        
    }
    
    /**
     * Spinning the wheel 
     * Generate a random winning number and sum the values in matrix until reaching it
     * Average complexity (n*n)/2
     */
    public int[] spinWheel()
    {
        double winNr = randDouble();
        //System.out.println("Winning number is: "+winNr);
        double sum = 0.0;
        int[] indices = new int[2];
        
        for (int i=0; i<TourManager.numberOfCities(); i++)
        {
            for (int j=0; j<TourManager.numberOfCities(); j++)
            {
                sum += this.fitMatrix[i][j];
                if(winNr < sum)
                {
                    //System.out.println(i+" "+j);
                    //System.out.println("Sum: "+sum);
                    
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
     * @param min
     * @param max
     * @return 
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
        for (int i=0; i<TourManager.numberOfCities(); i++)
        {
            for (int j=0; j<TourManager.numberOfCities(); j++)
            {
                System.out.print(this.fitMatrix[i][j]+" ");
            }
            System.out.println();
        }
    }
    
    public void printDistMatrix()
    {
        for (int i=0; i<TourManager.numberOfCities(); i++)
        {
            for (int j=0; j<TourManager.numberOfCities(); j++)
            {
                System.out.print(this.distMatrix[i][j]+" ");
            }
            System.out.println();
        }
    }
    
}
