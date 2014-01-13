/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tamm.aa.project;

import java.util.ArrayList;

/**
 * TSP using the greedy nearest neighbor approach
 * 
 * @author Urmas T.
 */
public class TSP {
    
    /**
     * Calculating the path using the nearest neighbor algorithm
     */
    public static Tour nearestNeighbor()
    {
        Tour tour = new Tour();
        ArrayList<Integer> visited = new ArrayList<Integer>();
        
        City current = TourManager.getCity(0);//add the first city into our tour
        tour.setCity(0, current);
        
        visited.add(current.getI());
        
        double minDistance = Double.MAX_VALUE;
        int nextId = current.getI();
        
        int n = 1;
        
        while (visited.size() < TourManager.numberOfCities())
        {
            
            for (int i=1; i<TourManager.numberOfCities(); i++)
            {
                City city = TourManager.getCity(i);
                
                //put aside cities we've already been to
                if(visited.contains(city.getI()) == false)
                {
                    double distance = current.distanceTo(city);
                    
                    if(distance < minDistance)
                    {
                        minDistance = distance;
                        nextId = city.getI();
                    }
                }
                    
            }
            
            current = TourManager.getCity(nextId);//set next city as current
            visited.add(nextId);
            
            tour.setCity(n, current);//add to tour
            n++;
            
            minDistance = Double.MAX_VALUE;
            
        }
        
        return tour;
    }
}
