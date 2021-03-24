package fileio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Information about an user, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public final class UserInputData {
    /**
     * User's username
     */
    private final String username;
    /**
     * Subscription Type
     */
    private final String subscriptionType;
    /**
     * The history of the movies seen
     */
    private final Map<String, Integer> history;
    /**
     * Movies added to favorites
     */
    private final ArrayList<String> favoriteMovies;

    private final Map<String, Double> rating = new HashMap<>();


    public UserInputData(final String username, final String subscriptionType,
                         final Map<String, Integer> history,
                         final ArrayList<String> favoriteMovies) {
        this.username = username;
        this.subscriptionType = subscriptionType;
        this.favoriteMovies = favoriteMovies;
        this.history = history;
    }

    public String getUsername() {
        return username;
    }

    public Map<String, Integer> getHistory() {
        return history;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public ArrayList<String> getFavoriteMovies() {
        return favoriteMovies;
    }

    /**
     * Metoda adauga un rating.
     */

    public void addNewRating(final String movie, final Double r) {
        rating.put(movie, r);
    }

    /**
     * Se verifica daca un film/serial are rating.
     */

    public boolean containsRating(final String movie) {
        return rating.containsKey(movie);

    }

    /**
     * Returneaza ratingul filmului/serialului.
     */

    public double getRating(final String movie) {
        return rating.get(movie);
    }

    /**
     * Returneaza numarul de ratinguri date de utilizator.
     */

    public double getNumberRatings() {
        return rating.size();
    }


    @Override
    public String toString() {
        return "UserInputData{" + "username='"
                + username + '\'' + ", subscriptionType='"
                + subscriptionType + '\'' + ", history="
                + history + ", favoriteMovies="
                + favoriteMovies + '}';
    }

}
