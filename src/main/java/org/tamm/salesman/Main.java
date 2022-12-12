package org.tamm.salesman;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.tamm.salesman.TravelingSalesmanAlgorithm.Result;

@Slf4j
public class Main {

    private static final String FILE_NAME = "att48.txt";

    public static void main(String[] args) {
        readTourFromFile();
        runAlgorithm(new SASwap());
        runAlgorithm(new SAWheel());
        runAlgorithm(new NearestNeighbor());
    }

    private static void runAlgorithm(TravelingSalesmanAlgorithm algorithm) {
        Result result = algorithm.calculate();
        log.info("{}: distance = {}; time = {}", algorithm.getClass().getSimpleName(), result.distance(),
                result.duration());
    }

    private static void readTourFromFile() {
        try {
            URL url = Objects.requireNonNull(Main.class.getClassLoader().getResource(FILE_NAME));
            String filePath = Paths.get(url.toURI()).toString();
            Stream<String> lines = Files.lines(Paths.get(filePath));
            List<String> citiesList = lines.toList();
            for (int i = 0; i < citiesList.size(); i++) {
                City city = readCityData(citiesList.get(i), i);
                TourManager.getInstance().getTour().getCities().add(city);
            }
            lines.close();
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException("Unable to read from file=[" + FILE_NAME + "]", e);
        }
    }

    private static City readCityData(String line, int index) {
        String[] lineParts = line.split("\\s+");
        int x = Integer.parseInt(lineParts[1]);
        int y = Integer.parseInt(lineParts[2]);
        return new City(x, y, index);
    }
}
