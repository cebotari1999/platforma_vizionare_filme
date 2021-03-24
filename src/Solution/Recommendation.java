package Solution;

import fileio.Input;
import fileio.Writer;
import java.io.IOException;
import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class Recommendation extends Actions {

    /**
     * Coding style.
     */
    public Recommendation(final Input inputFile, final Writer outputFile,
                          final JSONArray array, final Ratings rating,
                                final Sort sort) {
        super(inputFile, outputFile, array, rating, sort);
    }

    /**
     * Se realiza scrierea in fisierul de iesire.
     */
    public final void writeRecommendation(final String write, final String movie,
                                          final Integer point) throws IOException {
        if (write.equals("Successtandard")) {
            getArrayResult().add(getOutput().writeFile(
                    getInput().getCommands().get(point).getActionId(),
                        "Success", "StandardRecommendation result: " + movie));
        }

        if (write.equals("Errorstandard")) {
            getArrayResult().add(getOutput().writeFile(
                    getInput().getCommands().get(point).getActionId(),
                    "Success", "StandardRecommendation cannot be applied!"));
        }
        if (write.equals("SuccesBestRatedUnseenRecommendation")) {
            getArrayResult().add(getOutput().writeFile(
                    getInput().getCommands().get(point).getActionId(),
                    "Success", "BestRatedUnseenRecommendation result: " + movie));
        }

        if (write.equals("ErrorBestRatedUnseenRecommendation")) {
            getArrayResult().add(getOutput().writeFile(
                    getInput().getCommands().get(point).getActionId(),
                    "Error", "BestRatedUnseenRecommendation cannot be applied!"));
        }

        if (write.equals("ErrorPopular")) {
            getArrayResult().add(getOutput().writeFile(
                    getInput().getCommands().get(point).getActionId(),
                    "Error", "PopularRecommendation cannot be applied!"));
        }

        if (write.equals("SuccessPopular")) {
            getArrayResult().add(getOutput().writeFile(
                    getInput().getCommands().get(point).getActionId(),
                    "Succes", "PopularRecommendation result: " + movie));
        }

        if (write.equals("ErrorFavorite")) {
            getArrayResult().add(getOutput().writeFile(
                    getInput().getCommands().get(point).getActionId(),
                    "Error", "FavoriteRecommendation cannot be applied!"));
        }

        if (write.equals("SuccessFavorite")) {
            getArrayResult().add(getOutput().writeFile(
                    getInput().getCommands().get(point).getActionId(),
                    "Succes", "FavoriteRecommendation result: " + movie));
        }
    }

    /**
     * Retuneaza primul film din baza de date, pe care nu la vizualizat utilizatorul.
     */
    public final void standard(final Integer point) throws IOException {

        int user = 0;

        for (int i = 0; i < getInput().getUsers().size(); i++) {
            if (getInput().getUsers().get(i).getUsername().equals(
                                        getInput().getCommands().get(point).getUsername())) {
                user = i;
            }
        }

        for (int i = 0; i < getInput().getMovies().size(); i++) {
            if (!getInput().getUsers().get(user).getHistory().containsKey(
                                                        getInput().getMovies().get(i).getTitle())) {
                writeRecommendation("Successtandard",
                                            getInput().getMovies().get(i).getTitle(), point);
                return;
            }
        }

        for (int i = 0; i < getInput().getSerials().size(); i++) {
            if (!getInput().getUsers().get(user).getHistory().containsKey(
                    getInput().getSerials().get(i).getTitle())) {
                writeRecommendation("Successtandard",
                        getInput().getSerials().get(i).getTitle(), point);
                return;
            }
        }

        writeRecommendation("Errorstandard", null, point);
    }

    /**
     * In aceasta metoda se sorteaza filmele dupa rating, se ofera ca recomandare primul
     * film pe care nu la vazut utilizatorul. Daca toate filmele care au primit recomandari
     * au fost vizionate, se returneaza primul film nevizualizat din baza de date.
     */
    public final void bestUnseen(final Integer point) throws IOException {

        int user = 0;
        ArrayList<String> arrayRatings = new ArrayList<>();
        Map <String, Double> mapRating = new HashMap<>();

        for (int i = 0; i < getInput().getMovies().size(); i++) {
            if (getRatings(

            ).containsKey(getInput().getMovies().get(i).getTitle())) {
                mapRating.put(getInput().getMovies().get(i).getTitle(),
                            getRatings(

                            ).getFinalRating(getInput().getMovies().get(i).getTitle()));
            }
        }

        for (int i = 0; i < getInput().getSerials().size(); i++) {
            if (getRatings(

            ).containsKey(getInput().getSerials().get(i).getTitle())) {
                mapRating.put(getInput().getSerials().get(i).getTitle(),
                             getRatings(

                             ).getFinalRating(getInput().getSerials().get(i).getTitle()));
            }
        }

        if (mapRating.size() > 0) {
            arrayRatings = getSort().sortDescendingMap(mapRating);

            for (String arrayRating : arrayRatings) {
                if (!getInput().getUsers().get(user).getHistory().containsKey(arrayRating)) {
                    writeRecommendation("SuccesBestRatedUnseenRecommendation",
                                                arrayRating, point);
                    return;
                }
            }
        } else {
            for (String arrayRating : arrayRatings) {
                if (!getInput().getUsers().get(user).getHistory().containsKey(arrayRating)) {
                    writeRecommendation("SuccesBestRatedUnseenRecommendation",
                                                arrayRating, point);
                    return;
                }
            }
        }

        for (int i = 0; i < getInput().getMovies().size(); i++) {
            if (!getInput().getUsers().get(user).getHistory().containsKey(
                                                    getInput().getMovies().get(i).getTitle())) {
                writeRecommendation("SuccesBestRatedUnseenRecommendation",
                                        getInput().getMovies().get(i).getTitle(), point);
                return;
            }
        }

        for (int i = 0; i < getInput().getSerials().size(); i++) {
            if (!getInput().getUsers().get(user).getHistory().containsKey(
                    getInput().getSerials().get(i).getTitle())) {
                writeRecommendation("SuccesBestRatedUnseenRecommendation",
                        getInput().getSerials().get(i).getTitle(), point);
                return;
            }
        }


        writeRecommendation("ErrorBestRatedUnseenRecommendation", null, point);
    }

    /**
     * Se parcurge istoria la toti userii si se calculeaza numarul de vizualizari pentru fiecare
     * film/serial in parte. Apoi se parcurg toate filmele, se salveaza
     * toate genurile lor, cu numarul de vizualizari insumat pentru toate filmele de acest
     * gen. Acestea se sorteaza. Se afiseaza primul film nevizualizat.
     * Daca toate filmele au fost vizionate de acest utilizator, recomandarea nu se poate oferi.
     */

    public final void popular(final Integer point) throws IOException {

        int user = 0;

        Map<String, Double> mapMovies = new HashMap<>();
        ArrayList<String> arrayGenres = new ArrayList<>();
        Map<String, Double> mapGenres = new HashMap<>();
        ArrayList<String> arraySortedGenres;

        for (int i = 0; i < getInput().getUsers().size(); i++) {
            if (getInput().getUsers().get(i).getUsername().equals(
                                        getInput().getCommands().get(point).getUsername())) {
                user = i;
            }
        }

        if (!getInput().getUsers().get(user).getSubscriptionType().equals("PREMIUM")) {
            writeRecommendation("ErrorPopular", null, point);
            return;
        }

        for (int i = 0; i < getInput().getUsers().size(); i++) {
            for (int j = 0; j < getInput().getMovies().size(); j++) {
                if (getInput().getUsers().get(i).getHistory().containsKey(
                                                getInput().getMovies().get(j).getTitle())) {
                    if (mapMovies.containsKey(getInput().getMovies().get(j).getTitle())) {

                        double aux = mapMovies.get(getInput().getMovies().get(j).getTitle());
                        aux = aux + getInput().getUsers().get(i).getHistory().get(
                                                    getInput().getMovies().get(j).getTitle());
                        mapMovies.put(getInput().getMovies().get(j).getTitle(), aux);

                    } else {
                        mapMovies.put(getInput().getMovies().get(j).getTitle(),
                                Double.valueOf(getInput().getUsers().get(i).getHistory().get(
                                                    getInput().getMovies().get(j).getTitle())));
                    }
                }
            }
        }

        for (int i = 0; i < getInput().getUsers().size(); i++) {
            for (int j = 0; j < getInput().getSerials().size(); j++) {
                if (getInput().getUsers().get(i).getHistory().
                                    containsKey(getInput().getSerials().get(j).getTitle())) {
                    if (mapMovies.containsKey(getInput().getSerials().get(j).getTitle())) {

                        double aux = mapMovies.get(getInput().getSerials().get(j).getTitle());
                        aux = aux + getInput().getUsers().get(i).getHistory().get(
                                                    getInput().getSerials().get(j).getTitle());
                        mapMovies.put(getInput().getSerials().get(j).getTitle(), aux);
                    } else {
                        mapMovies.put(getInput().getSerials().get(j).getTitle(),
                                Double.valueOf(getInput().getUsers().get(i).getHistory().get(
                                                    getInput().getSerials().get(j).getTitle())));
                    }
                }
            }
        }

        for (int i = 0; i < getInput().getMovies().size(); i++) {
            for (int j = 0; j < getInput().getMovies().get(i).getGenres().size(); j++) {
                if (!arrayGenres.contains(getInput().getMovies().get(i).getGenres().get(j))) {
                    arrayGenres.add(getInput().getMovies().get(i).getGenres().get(j));
                }
            }
        }

        for (int i = 0; i < getInput().getSerials().size(); i++) {
            for (int j = 0; j < getInput().getSerials().get(i).getGenres().size(); j++) {
                if (!arrayGenres.contains(getInput().getSerials().get(i).getGenres().get(j))) {
                    arrayGenres.add(getInput().getSerials().get(i).getGenres().get(j));
                }
            }
        }

        for (int i = 0; i < getInput().getMovies().size(); i++) {
            for (String arrayGenre : arrayGenres) {
                if (getInput().getMovies().get(i).getGenres().contains(arrayGenre)) {
                    if (mapMovies.containsKey(getInput().getMovies().get(i).getTitle())) {
                        if (mapGenres.containsKey(arrayGenre)) {

                            double aux = mapGenres.get(arrayGenre);
                            aux = aux + mapMovies.get(getInput().getMovies().get(i).getTitle());
                            mapGenres.put(arrayGenre, aux);

                        } else {
                            mapGenres.put(arrayGenre, mapMovies.get(
                                                        getInput().getMovies().get(i).getTitle()));
                        }
                    }
                }
            }
        }

        for (int i = 0; i < getInput().getSerials().size(); i++) {
            for (String arrayGenre : arrayGenres) {
                if (getInput().getSerials().get(i).getGenres().contains(arrayGenre)) {
                    if (mapMovies.containsKey(getInput().getSerials().get(i).getTitle())) {
                        if (mapGenres.containsKey(arrayGenre)) {

                            double aux = mapGenres.get(arrayGenre);
                            aux = aux + mapMovies.get(getInput().getSerials().get(i).getTitle());
                            mapGenres.put(arrayGenre, aux);

                        } else {
                            mapGenres.put(arrayGenre, mapMovies.get(
                                    getInput().getSerials().get(i).getTitle()));
                        }
                    }
                }
            }
        }

        if (mapGenres.size() > 0) {
            arraySortedGenres = getSort().sortDescendingMap(mapGenres);
        } else {
            writeRecommendation("ErrorPopular", null, point);
            return;
        }

        for (int i = 0; i < getInput().getMovies().size(); i++) {
            for (String arraySortedGenre : arraySortedGenres) {
                if (getInput().getMovies().get(i).getGenres().contains(arraySortedGenre)) {
                    if (!getInput().getUsers().get(user).getHistory().containsKey(
                                                getInput().getMovies().get(i).getTitle())) {
                        writeRecommendation("SuccessPopular",
                                            getInput().getMovies().get(i).getTitle(), point);
                        return;
                    }
                }
            }
        }

        for (int i = 0; i < getInput().getSerials().size(); i++) {
            for (String arraySortedGenre : arraySortedGenres) {
                if (getInput().getSerials().get(i).getGenres().contains(arraySortedGenre)) {
                    if (!getInput().getUsers().get(user).getHistory().containsKey(
                                                getInput().getSerials().get(i).getTitle())) {
                        writeRecommendation("SuccessPopular",
                                            getInput().getSerials().get(i).getTitle(), point);
                        return;

                    }
                }
            }
        }

        writeRecommendation("ErrorPopular", null, point);
    }

    /**
     * Se parcurg toti utilizatorii si se insumeaza numarul de aparitii a filmelor
     * in campul filmelor favorite. Apoi se sorteaza si se recomanda primul film
     * pe care nu la vazut utilizatorul. Daca toate filmele au fost vizionate,
     * recomandarea nu se poate aplica.
     */
    public final void favorite(final Integer point) throws IOException {
        int user = 0;
        double aux;

        Map<String, Double> listFavorites = new HashMap<>();
        ArrayList<String> finalList;

        for (int i = 0; i < getInput().getUsers().size(); i++) {
            if (getInput().getUsers().get(i).getUsername().equals(
                                getInput().getCommands().get(point).getUsername())) {
                user = i;
            }
        }

        if (!getInput().getUsers().get(user).getSubscriptionType().equals("PREMIUM")) {
            writeRecommendation("ErrorFavorite", null, point);
            return;
        }

        for (int i = 0; i < getInput().getUsers().size(); i++) {
            for (int j = 0; j < getInput().getUsers().get(i).getFavoriteMovies().size(); j++) {
                if (listFavorites.containsKey(
                                    getInput().getUsers().get(i).getFavoriteMovies().get(j))) {
                    aux = listFavorites.get(getInput().getUsers().
                                                        get(i).getFavoriteMovies().get(j));
                    listFavorites.put(getInput().getUsers().get(i).
                                                    getFavoriteMovies().get(j), aux + 1);
                } else {
                    listFavorites.put(getInput().getUsers().get(i).getFavoriteMovies().get(j),
                                        (double) 1);
                }
            }
        }

        finalList = getSort().sortDescendingMap(listFavorites);

        if (finalList.size() > 0) {
            for (String s : finalList) {
                if (!getInput().getUsers().get(user).getHistory().containsKey(s)) {
                    writeRecommendation("SuccessFavorite", s, point);
                    return;
                }
            }
        }

        writeRecommendation("ErrorFavorite", null, point);
    }

    /**
     * Se parcurg toate filmele, pentru a verifica in ce genuri se incadreaza. Daca
     * contin genul dat ca parametru se salveaza. La final se sorteaza si se
     * recomanda primul film nevizualizat de utilizator. Altfel recomandarea
     * nu se poate aplica.
     */
    public final void search(final Integer point) throws IOException {
        int user = 0;
        Map<String, Double> listRatings = new HashMap<>();
        ArrayList<String> listMovies;

        for (int i = 0; i < getInput().getUsers().size(); i++) {
            if (getInput().getUsers().get(i).getUsername().equals(
                                getInput().getCommands().get(i).getUsername())) {
                user = i;
            }
        }

        if (!getInput().getUsers().get(user).getSubscriptionType().equals("PREMIUM")) {
            getArrayResult().add(getOutput().writeFile(getInput().
                            getCommands().get(point).getActionId(),
                    "Error", "SearchRecommendation cannot be applied!"));
            return;

        }

        for (int i = 0; i < getInput().getMovies().size(); i++) {
            if (getInput().getMovies().get(i).getGenres().contains(
                                            getInput().getCommands().get(point).getGenre())) {
                if (!getInput().getUsers().get(user).getHistory().containsKey(
                                                    getInput().getMovies().get(i).getTitle())) {
                    if (getRatings().containsKey(getInput().getMovies().get(i).getTitle())) {
                        listRatings.put(getInput().getMovies().get(i).getTitle(),
                                getRatings().getFinalRating(
                                        getInput().getMovies().get(i).getTitle()));
                    } else {
                        listRatings.put(getInput().getMovies().get(i).getTitle(), (double) 0);
                    }
                }
            }
        }

        for (int i = 0; i < getInput().getSerials().size(); i++) {
            if (getInput().getSerials().get(i).getGenres().contains(
                                                getInput().getCommands().get(point).getGenre())) {
                if (!getInput().getUsers().get(user).getHistory().containsKey(
                                                getInput().getSerials().get(i).getTitle())) {
                    if (getRatings().containsKey(getInput().getSerials().get(i).getTitle())) {
                        listRatings.put(getInput().getSerials().get(i).getTitle(),
                                getRatings(

                                ).getFinalRating(getInput().getSerials().get(i).getTitle()));
                    } else {
                        listRatings.put(getInput().getSerials().get(i).getTitle(), (double) 0);
                    }
                }
            }
        }

        listMovies = getSort().sortAscendingMap(listRatings);


        if (listMovies.size() > 0) {
                getArrayResult().add(getOutput().writeFile(getInput().
                                getCommands().get(point).getActionId(),
                        "Succes", "SearchRecommendation result: " + listMovies));
                return;
        }


        getArrayResult().add(getOutput().writeFile(getInput().
                        getCommands().get(point).getActionId(),
                    "Error", "SearchRecommendation cannot be applied!"));
    }
}
