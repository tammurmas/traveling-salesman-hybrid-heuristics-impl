package org.tamm.salesman;

/**
 * Object holding algorithm run results
 *
 * @param duration algorithm run time
 * @param distance best calculated distance
 *
 * @author Urmas Tamm
 */
public record Result(float duration, int distance) {

}
