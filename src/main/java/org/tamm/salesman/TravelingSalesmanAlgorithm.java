package org.tamm.salesman;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public interface TravelingSalesmanAlgorithm {

    String getName();

    Result calculate();

    static void readTourFromFile(String fileName) {
        String line;
        try (BufferedReader inputStream = new BufferedReader(new FileReader(fileName))) {
            int i = 0;
            while ((line = inputStream.readLine()) != null) {
                //skip first line
                if (i > 0) {
                    String[] lineParts = line.split("\\s+");//any number of whitespace characters as delimiter
                    int x = Integer.parseInt(lineParts[1]);
                    int y = Integer.parseInt(lineParts[2]);
                    //we start city indices from 0 to populate the distance matrix in correct manner
                    City city = new City(x, y, i - 1);
                    TourManager.getInstance().getTour().getCities().add(city);
                }
                i++;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
