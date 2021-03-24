package Solution;

import java.util.HashMap;
import java.util.Map;

public class Ratings {

    private final Map<String, Double> ratings;

    private final Map<String, Double> numberOfRatings;

    public Ratings() {
        this.ratings = new HashMap<>();
        this.numberOfRatings = new HashMap<>();
    }

    /**
     * Adauga rating unui film/serial.
     */
    public final void addRating(final String movie, final Double rating) {
        double aux = 0;
        double count = 0;

        if (ratings.containsKey(movie)) {
            aux = aux + ratings.get(movie);
            count = count + numberOfRatings.get(movie);
        }

        aux = aux + rating;
        count++;

        ratings.put(movie, aux);
        numberOfRatings.put(movie, count);
    }

    /**
     * Adauga rating unui serial vizionat deja.
     */

    public final void addRatingSerial(final String movie, final Double rating) {
        if (ratings.containsKey(movie)) {
            ratings.put(movie, ratings.get(movie) + rating);
        }
    }

    /**
     * Verifica existenta unui film/serial.
     */

    public final boolean containsKey(final String movie) {
        return ratings.containsKey(movie);
    }

    /**
     * Returneaza ratingul final al unui film/serial.
     */
    public final double getFinalRating(final String movie) {
        double finalRating = ratings.get(movie) / numberOfRatings.get(movie);
        if (ratings.get(movie) == 0) {
            return 0;
        }
        return finalRating;
    }

}
