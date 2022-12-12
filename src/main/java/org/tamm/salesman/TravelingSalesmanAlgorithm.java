package org.tamm.salesman;

public interface TravelingSalesmanAlgorithm {

    Result calculate();

    /**
     * Object holding algorithm run results
     *
     * @param duration algorithm run time
     * @param distance best calculated distance
     *
     * @author Urmas Tamm
     */
    record Result(float duration, int distance) {

    }
}
