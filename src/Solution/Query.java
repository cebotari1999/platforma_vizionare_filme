package Solution;

import org.json.simple.JSONArray;
import actor.ActorsAwards;
import java.io.IOException;
import java.util.*;
import fileio.*;

@SuppressWarnings("unchecked")
public class Query extends Actions {
    /**
     * Coding style.
     */
    public Query(final Input inputFile, final Writer outputFile,
                 final JSONArray array, final Ratings rating, final Sort sort) {
        super(inputFile, outputFile, array, rating, sort);
    }

    /**
     *Scrie in fisier rezultatul la query.
     */
    public final void writeQuery(final ArrayList<String> listQuery,
                                 final Integer point) throws IOException {
        getArrayResult().add(getOutput().writeFile(
                getInput().getCommands().get(point).getActionId(),
                "query", "Query result: " + listQuery.toString()));
    }

    /**
     * Sorteaza si editeaza rezultatul la query, inainte de afisare, dupa
     * parametrii dati(asendent, descendent, numar N). Apeleaza metoda
     * writeQuery pentru listQuery.
     */
    public final void sortQuery(final Map<String, Double> mapQuery,
                                final Integer point) throws IOException {
        ArrayList<String> listQuery = new ArrayList<>();
        if (getInput().getCommands().get(point).getSortType().equals("asc")) {
            listQuery = getSort().sortAscendingMap(mapQuery);
        }

        if (getInput().getCommands().get(point).getSortType().equals("desc")) {
            listQuery =  getSort().sortDescendingMap(mapQuery);
        }

        if (getInput().getCommands().get(point).getNumber() < listQuery.size()) {
            listQuery = getSort().editArrayList(listQuery,
                                    getInput().getCommands().get(point).getNumber());
        }

        writeQuery(listQuery, point);

    }

    /**
     * Aceasta metoda sorteaza filmele dupa doua criterii, anul aparitiei si genul.
     */
    public final Map<String, Double> sortFiltersMovies(final Integer point) {
        Map<String, Double> mapFilters = new HashMap<>();

        for (int j = 0; j < getInput().getMovies().size(); j++) {
            if (getInput().getCommands().get(point).getFilters().get(0).get(0) != null
                    && getInput().getCommands().get(point).getFilters().get(1).get(0) != null) {
                if (getInput().getMovies().get(j).getGenres().contains(
                        getInput().getCommands().get(point).getFilters().get(1).get(0))) {
                    if (getInput().getMovies().get(j).getYear() > 0) {
                        if (getInput().getCommands().get(point).getFilters().get(0).get(0).
                            equals(Integer.toString(getInput().getMovies().get(j).getYear()))) {
                            mapFilters.put(getInput().getMovies().get(j).getTitle(), (double) 0);
                        }
                    }
                }
            } else {
                if (getInput().getCommands().get(point).getFilters().get(0).get(0) != null) {
                    if (getInput().getCommands().get(point).getFilters().get(0).get(0).
                            equals(Integer.toString(getInput().getMovies().get(j).getYear()))) {
                        mapFilters.put(getInput().getMovies().get(j).getTitle(), (double) 0);
                    }
                } else if (getInput().getCommands().get(point).getFilters().get(1).get(0) != null) {
                    if (getInput().getMovies().get(j).getGenres().contains(
                            getInput().getCommands().get(point).getFilters().get(1).get(0))) {
                        mapFilters.put(getInput().getMovies().get(j).getTitle(), (double) 0);
                    }
                } else {
                    mapFilters.put(getInput().getMovies().get(j).getTitle(), (double) 0);

                }
            }
        }

        return mapFilters;
    }

    /**
     * Aceasta metoda sorteaza serialele dupa parametrii, anul lansarii si genul.
     */
    public final Map<String, Double> sortFiltersShows(Integer point) {
        Map<String, Double> mapFilters = new HashMap<>();

        for (int i = 0; i < getInput().getSerials().size(); i++) {
            if (getInput().getCommands().get(point).getFilters().get(0).get(0) != null
                    && getInput().getCommands().get(point).getFilters().get(1).get(0) != null) {
                if (getInput().getSerials().get(i).getGenres().contains(
                        getInput().getCommands().get(point).getFilters().get(1).get(0))) {
                    if (getInput().getSerials().get(i).getYear() > 0) {
                        if (getInput().getCommands().get(point).getFilters().get(0).get(0).
                            equals(Integer.toString(getInput().getSerials().get(i).getYear()))) {
                            mapFilters.put(getInput().getSerials().get(i).getTitle(), (double) 0);
                        }
                    }
                }
            } else {
                if (getInput().getCommands().get(point).getFilters().get(0).get(0) != null) {
                    if (getInput().getCommands().get(point).getFilters().get(0).get(0).
                            equals(Integer.toString(getInput().getSerials().get(i).getYear()))) {
                        mapFilters.put(getInput().getSerials().get(i).getTitle(), (double) 0);
                    }
                }

                if (getInput().getCommands().get(point).getFilters().get(1).get(0) != null) {
                    if (getInput().getSerials().get(i).getGenres().contains(
                            getInput().getCommands().get(point).getFilters().get(1).get(0))) {
                        mapFilters.put(getInput().getSerials().get(i).getTitle(), (double) 0);
                    }
                }
            }
        }

        return mapFilters;
    }

    /**
     * Se parcurg toate filmele si serialele. Se face media aritmetica a ratingurilor
     * pentru toate filmele si serialele in care sa filmat un anumit actor.
     * Se procedeaza astfel pentru toti actorii. Apoi  se afiseaza rezultatul la query.
     */
    public final void queryAverage(final Integer point) throws IOException {
        double sumRatings;
        int numberOfRaitings;

        Map<String, Double> mapAverage = new HashMap<>();

        for (int i = 0; i < getInput().getActors().size(); i++) {
            sumRatings = 0;
            numberOfRaitings = 0;

            for (int j = 0; j < getInput().getMovies().size(); j++) {
                if (getInput().getActors().get(i).getFilmography().contains(
                        getInput().getMovies().get(j).getTitle())) {
                    if (getRatings().containsKey(getInput().getMovies().get(j).getTitle())) {
                        sumRatings = sumRatings + getRatings().getFinalRating(
                                                    getInput().getMovies().get(j).getTitle());
                        numberOfRaitings++;
                    }
                }
            }

            for (int j = 0; j < getInput().getSerials().size(); j++) {
                if (getInput().getActors().get(i).getFilmography().contains(
                                getInput().getSerials().get(j).getTitle())) {
                    if (getRatings().containsKey(getInput().getSerials().get(j).getTitle())) {
                        sumRatings = sumRatings + (getRatings().getFinalRating(
                                    getInput().getSerials().get(j).getTitle())
                                        / getInput().getSerials().get(j).getNumberSeason());
                        numberOfRaitings++;
                    }
                }
            }

            if (sumRatings > 0) {
                mapAverage.put(getInput().getActors().get(i).getName(),
                                        sumRatings / numberOfRaitings);
            }

        }

        sortQuery(mapAverage, point);
    }

    /**
     * Se parcurge informatia despre  toti actorii din baza de date, se verifica daca
     * acestia au toate premiile, date ca parametru. Daca se respecta conditia, se insumeaza
     * numarul premiilor oferite. In functie de acestea o sa fie sortate in querySort.
     */
    public final void queryAwards(final Integer point) throws IOException {
        int count;
        double aux;

        Map<String, Double> mapAwards = new HashMap<>();

        for (int j = 0; j < getInput().getActors().size(); j++) {
            if (getInput().getActors().get(j).getAwards().size()
                    >= getInput().getCommands().get(point).getFilters().get(3).size()) {
                aux = 0;
                count = 0;

                for (int k = 0; k < getInput().getCommands().get(point).
                                                    getFilters().get(3).size(); k++) {
                    if (getInput().getCommands().get(point).getFilters().
                                            get(3).get(k).equals("BEST_PERFORMANCE")) {
                        if (getInput().getActors().get(j).getAwards().
                                            containsKey(ActorsAwards.BEST_PERFORMANCE)) {
                            count++;
                        }
                    }

                    if (getInput().getCommands().get(point).getFilters().
                                                get(3).get(k).equals("BEST_DIRECTOR")) {
                        if (getInput().getActors().get(j).getAwards().
                                                containsKey(ActorsAwards.BEST_DIRECTOR)) {
                            count++;
                        }
                    }

                    if (getInput().getCommands().get(point).getFilters().get(3).
                                                    get(k).equals("PEOPLE_CHOICE_AWARD")) {
                        if (getInput().getActors().get(j).getAwards().
                                            containsKey(ActorsAwards.PEOPLE_CHOICE_AWARD)) {
                            count++;
                        }
                    }

                    if (getInput().getCommands().get(point).getFilters().get(3).
                                                        get(k).equals("BEST_SUPPORTING_ACTOR")) {
                        if (getInput().getActors().get(j).getAwards().
                                                containsKey(ActorsAwards.BEST_SUPPORTING_ACTOR)) {
                            count++;
                        }
                    }

                    if (getInput().getCommands().get(point).getFilters().get(3).
                                                                get(k).equals("BEST_SCREENPLAY")) {
                        if (getInput().getActors().get(j).getAwards().
                                                        containsKey(ActorsAwards.BEST_SCREENPLAY)) {
                            count++;
                        }
                    }
                }

                if (count == getInput().getCommands().get(point).getFilters().get(3).size()) {
                    if (getInput().getActors().get(j).getAwards().
                                                    containsKey(ActorsAwards.BEST_PERFORMANCE)) {
                        aux = aux + getInput().getActors().get(j).
                                                getAwards().get(ActorsAwards.BEST_PERFORMANCE);
                    }

                    if (getInput().getActors().get(j).getAwards().
                                                        containsKey(ActorsAwards.BEST_DIRECTOR)) {
                        aux = aux + getInput().getActors().get(j).
                                                    getAwards().get(ActorsAwards.BEST_DIRECTOR);
                    }

                    if (getInput().getActors().get(j).getAwards().
                                            containsKey(ActorsAwards.PEOPLE_CHOICE_AWARD)) {
                        aux = aux + getInput().getActors().get(j).
                                            getAwards().get(ActorsAwards.PEOPLE_CHOICE_AWARD);
                    }

                    if (getInput().getActors().get(j).getAwards().
                                                containsKey(ActorsAwards.BEST_SUPPORTING_ACTOR)) {
                        aux = aux + getInput().getActors().get(j).
                                                getAwards().get(ActorsAwards.BEST_SUPPORTING_ACTOR);
                    }

                    if (getInput().getActors().get(j).getAwards().
                                                    containsKey(ActorsAwards.BEST_SCREENPLAY)) {
                        aux = aux + getInput().getActors().get(j).
                                                    getAwards().get(ActorsAwards.BEST_SCREENPLAY);
                    }
                }

                if (count == getInput().getCommands().get(point).getFilters().get(3).size()) {
                    mapAwards.put(getInput().getActors().get(j).getName(), aux);
                }
            }
        }

        sortQuery(mapAwards, point);
    }

    /**
     * Se verifica ca descrierea actorilor sa contina toate cuvintele cheie,
     * apoi se afiseaza conform parametrilor dati in querySort.
     */
    public final void queryFilterDescrition(final Integer point) throws IOException {
        int count;

        ArrayList<String> actorsList = new ArrayList<>();
        ArrayList<String> actorsDescendingList = new ArrayList<>();

        for (int i = 0; i < getInput().getActors().size(); i++) {
            count = 0;
            for (int j = 0; j < getInput().getCommands().get(point).
                                                    getFilters().get(2).size(); j++) {
                String key = getInput().getCommands().
                                            get(point).getFilters().get(2).get(j) + " ";
                if (getInput().getActors().get(i).getCareerDescription().
                                                            toLowerCase().contains(key)) {
                    count++;
                }

            }

            if (count == getInput().getCommands().get(point).getFilters().get(2).size()) {

                actorsList.add(getInput().getActors().get(i).getName());
            }
        }

        if (getInput().getCommands().get(point).getSortType().equals("asc")) {
            Collections.sort(actorsList);
            writeQuery(actorsList, point);

        }

        if (getInput().getCommands().get(point).getSortType().equals("desc")) {
            Collections.sort(actorsList);

            for (int j = actorsList.size() - 1; j >= 0; j--) {
                actorsDescendingList.add(actorsList.get(j));
            }
            writeQuery(actorsDescendingList, point);
        }
    }

    /**
     * Initial se stocheaza toate filmele care corespund filtrelor.
     * Se sorteaza dupa ratingurile obtinute.
     */
    public final void queryRaitingMovies(final Integer point) throws IOException {

        Map<String, Double> mapRatings = sortFiltersMovies(point);
        Map<String, Double> mapMovies = new HashMap<>();

        for (int i = 0; i < getInput().getMovies().size(); i++) {
            if (mapRatings.containsKey(getInput().getMovies().get(i).getTitle())) {
                if (getRatings().containsKey(getInput().getMovies().get(i).getTitle())) {
                    mapMovies.put(getInput().getMovies().get(i).getTitle(),
                            getRatings().getFinalRating(
                                            getInput().getMovies().get(i).getTitle()));
                }
            }
        }

        sortQuery(mapMovies, point);
    }

    /**
     * Initial se stocheaza toate serialele care corespund filtrelor.
     * Apoi se sorteaza numarul de seriale cerut, sortate dupa rating.
     */
    public final void queryRaitingShow(final Integer point) throws IOException {
        Map<String, Double> mapRatings = sortFiltersShows(point);
        Map<String, Double> mapShows = new HashMap<>();

        for (int i = 0; i < getInput().getSerials().size(); i++) {
            if (mapRatings.containsKey(getInput().getSerials().get(i).getTitle())) {
                if (getRatings().containsKey(getInput().getSerials().get(i).getTitle())) {
                    mapShows.put(getInput().getSerials().get(i).getTitle(),
                                getRatings().getFinalRating(
                                        getInput().getSerials().get(i).getTitle()));

                }
            }
        }
        sortQuery(mapShows, point);
    }

    /**
     * Initial se parcurg toate filmele si se selecteaza doar cele care corespund filtrelor.
     * Se parcurg toti utilizatorii si se insumeaza numarul de aparitii pentru fiecare film,
     * selectat anterior, in campul favorites.
     */
    public final void queryFavoriteMovies(final Integer point) throws IOException {
        double aux;
        Map<String, Double> mapFavorites = sortFiltersMovies(point);

        for (int i = 0; i < getInput().getUsers().size(); i++) {
            for (int j = 0; j < getInput().getUsers().get(i).getFavoriteMovies().size(); j++) {
                if (mapFavorites.containsKey(getInput().getUsers().
                                                        get(i).getFavoriteMovies().get(j))) {
                    aux = mapFavorites.get(getInput().getUsers().
                                                            get(i).getFavoriteMovies().get(j));
                    mapFavorites.put(getInput().getUsers().get(i).
                                        getFavoriteMovies().get(j), aux + 1);
                }
            }
        }

        for (int i = 0; i < getInput().getMovies().size(); i++) {
            if (mapFavorites.containsKey(getInput().getMovies().get(i).getTitle())) {
                aux = mapFavorites.get(getInput().getMovies().get(i).getTitle());
                if (aux == 0) {
                    mapFavorites.remove(getInput().getMovies().get(i).getTitle());
                }
            }
        }

        sortQuery(mapFavorites, point);
    }

    /**
     * Se selecteaza toate serialele, care corespund filtrelor. Apoi se insumeaza numarul
     * de aparitii al acestor seriale in campul favorities al userilor. Se sorteaza si se
     * afiseaza conform cerintelor in sortQuery si writeQuery.
     */
    public final void queryFavoriteShows(final Integer point) throws IOException {
        double aux;
        Map<String, Double> mapFavorities = sortFiltersShows(point);

        for (int i = 0; i < getInput().getUsers().size(); i++) {
            for (int j = 0; j < getInput().getUsers().get(i).getFavoriteMovies().size(); j++) {
                if (mapFavorities.containsKey(getInput().getUsers().
                                                        get(i).getFavoriteMovies().get(j))) {
                    aux = mapFavorities.get(getInput().getUsers().
                                                            get(i).getFavoriteMovies().get(j));
                    mapFavorities.put(getInput().getUsers().get(i).
                                                        getFavoriteMovies().get(j), aux + 1);
                }
            }
        }

        for (int i = 0; i < getInput().getSerials().size(); i++) {
            if (mapFavorities.containsKey(getInput().getSerials().get(i).getTitle())) {
                aux = mapFavorities.get(getInput().getSerials().get(i).getTitle());
                if (aux == 0) {
                    mapFavorities.remove(getInput().getSerials().get(i).getTitle());
                }
            }
        }

        sortQuery(mapFavorities, point);
    }

    /**
     * Se parcurg toate filmele. Se verifica care corespund filtrelor. Se stocheaza
     * filmul si durata lui.
     */
    public final void queryLongestMovies(final Integer point) throws IOException {

        Map<String, Double> mapLongestMovies = new HashMap<>();

        if (getInput().getCommands().get(point).getFilters().get(0).get(0) != null
                && getInput().getCommands().get(point).getFilters().get(1).get(0) != null) {
            for (int i = 0; i < getInput().getMovies().size(); i++) {
                if (getInput().getMovies().get(i).getYear() != 0) {
                    if (getInput().getCommands().get(point).getFilters().get(0).get(0).
                            equals(Integer.toString(getInput().getMovies().get(i).getYear()))) {
                        if (getInput().getMovies().get(i).getGenres().contains(
                               getInput().getCommands().get(point).getFilters().get(1).get(0))) {
                            mapLongestMovies.put(getInput().getMovies().get(i).getTitle(),
                                    (double) getInput().getMovies().get(i).getDuration());
                        }
                    }
                }
            }

        } else {
            if (getInput().getCommands().get(point).getFilters().get(0).get(0) != null) {
                for (int i = 0; i < getInput().getMovies().size(); i++) {
                    if (getInput().getMovies().get(i).getYear() != 0) {
                        if (getInput().getCommands().get(point).getFilters().get(0).get(0).
                            equals(Integer.toString(getInput().getMovies().get(i).getYear()))) {
                            mapLongestMovies.put(getInput().getMovies().get(i).getTitle(),
                                    (double) getInput().getMovies().get(i).getDuration());
                        }
                    }
                }
            }

            if (getInput().getCommands().get(point).getFilters().get(1).get(0) != null) {
                for (int i = 0; i < getInput().getMovies().size(); i++) {
                    if (getInput().getMovies().get(i).getGenres().contains(
                            getInput().getCommands().get(point).getFilters().get(1).get(0))) {
                        mapLongestMovies.put(getInput().getMovies().get(i).getTitle(),
                                (double) getInput().getMovies().get(i).getDuration());
                    }
                }
            }
        }

        sortQuery(mapLongestMovies, point);
    }

    /**
     * Se parcurg toate serialele, cele care corespund cerintelor, se stocheaza
     * impreuna cu durata lor.
     */
    public final void queryLongestShows(final Integer point) throws IOException {

        double aux;
        Map<String, Double> mapLongestShows = new HashMap<>();

        if (getInput().getCommands().get(point).getFilters().get(0).get(0) != null
                && getInput().getCommands().get(point).getFilters().get(1).get(0) != null) {
            for (int i = 0; i < getInput().getSerials().size(); i++) {
                if (getInput().getSerials().get(i).getYear() != 0) {
                    if (getInput().getCommands().get(point).getFilters().get(0).get(0).
                        equals(Integer.toString(getInput().getSerials().get(i).getYear()))) {
                        if (getInput().getSerials().get(i).getGenres().contains(
                             getInput().getCommands().get(point).getFilters().get(1).get(0))) {
                            aux  = 0;
                            for (int j = 0; j < getInput().getSerials().
                                                        get(i).getNumberSeason(); j++) {
                                aux = aux + getInput().getSerials().get(i).
                                                    getSeasons().get(j).getDuration();
                            }
                            mapLongestShows.put(getInput().getSerials().get(i).getTitle(), aux);
                        }
                    }
                }
            }

        } else {
            if (getInput().getCommands().get(point).getFilters().get(0).get(0) != null) {
                for (int i = 0; i < getInput().getSerials().size(); i++) {
                    if (getInput().getSerials().get(i).getYear() != 0) {
                        if (getInput().getCommands().get(point).getFilters().get(0).get(0).
                            equals(Integer.toString(getInput().getSerials().get(i).getYear()))) {
                            aux  = 0;
                            for (int j = 0; j < getInput().getSerials().get(i).
                                                                    getNumberSeason(); j++) {
                                aux = aux + getInput().getSerials().get(i).
                                                            getSeasons().get(j).getDuration();
                            }
                            mapLongestShows.put(getInput().getSerials().get(i).getTitle(), aux);
                        }
                    }
                }
            }

            if (getInput().getCommands().get(point).getFilters().get(1).get(0) != null) {
                for (int i = 0; i < getInput().getSerials().size(); i++) {
                    if (getInput().getSerials().get(i).getGenres().contains(
                            getInput().getCommands().get(point).getFilters().get(1).get(0))) {
                        aux  = 0;
                        for (int j = 0; j < getInput().getSerials().
                                                          get(i).getNumberSeason(); j++) {
                            aux = aux + getInput().getSerials().
                                                    get(i).getSeasons().get(j).getDuration();
                        }
                        mapLongestShows.put(getInput().getSerials().get(i).getTitle(), aux);
                    }
                }
            }
        }

        sortQuery(mapLongestShows, point);
    }

    /**
     * Se parcurg toate filmele si se verifica daca corespund parametrilor.
     * In cazul in care parametrii coincid, se stocheaza. Apoi se parcurg toti
     * utilizatorii pentru a determina vizualizarile totale pentru fiecare film.
     */
    public final void queryMostViewedMovies(final Integer point) throws IOException {
        double aux;

        ArrayList<String> arrayFilterMovies = new ArrayList<>();
        Map<String, Double> mapMostViewed = new HashMap<>();

        if (getInput().getCommands().get(point).getFilters().get(0).get(0) != null
               && getInput().getCommands().get(point).getFilters().get(1).get(0) != null) {
            for (int i = 0; i < getInput().getMovies().size(); i++) {
                if (getInput().getMovies().get(i).getYear() != 0) {
                    if (getInput().getCommands().get(point).getFilters().get(0).get(0).
                            equals(Integer.toString(getInput().getMovies().get(i).getYear()))) {
                        if (getInput().getMovies().get(i).getGenres().contains(
                            getInput().getCommands().get(point).getFilters().get(1).get(0))) {
                            arrayFilterMovies.add(getInput().getMovies().get(i).getTitle());
                        }
                    }
                }
            }
        } else {
            if (getInput().getCommands().get(point).getFilters().get(0).get(0) != null) {
                for (int i = 0; i < getInput().getMovies().size(); i++) {
                    if (getInput().getMovies().get(i).getYear() != 0) {
                        if (getInput().getCommands().get(point).getFilters().get(0).get(0).
                            equals(Integer.toString(getInput().getMovies().get(i).getYear()))) {
                            arrayFilterMovies.add(getInput().getMovies().get(i).getTitle());
                        }
                    }
                }
            }

            if (getInput().getCommands().get(point).getFilters().get(1).get(0) != null) {
                for (int i = 0; i < getInput().getMovies().size(); i++) {
                    if (getInput().getMovies().get(i).getGenres().contains(
                            getInput().getCommands().get(point).getFilters().get(1).get(0))) {
                        arrayFilterMovies.add(getInput().getMovies().get(i).getTitle());
                    }
                }
            }
        }

        for (int i = 0; i < getInput().getUsers().size(); i++) {
            for (String arrayFilterMovie : arrayFilterMovies) {
                if (getInput().getUsers().get(i).getHistory().containsKey(arrayFilterMovie)) {
                    if (mapMostViewed.containsKey(arrayFilterMovie)) {
                        aux = mapMostViewed.get(arrayFilterMovie);
                        aux = aux + getInput().getUsers().get(i).
                                    getHistory().get(arrayFilterMovie);
                        mapMostViewed.put(arrayFilterMovie, aux);

                    } else {
                        mapMostViewed.put(arrayFilterMovie,
                                Double.valueOf(getInput().getUsers().get(i).
                                        getHistory().get(arrayFilterMovie)));
                    }
                }
            }
        }

        sortQuery(mapMostViewed, point);

    }

    /**
     * Se parcurg toate serialele pentru a se verifica care din ele corespund cerintelor.
     * Apoi pentru aceste seriale se insumeaza vizualizarile, separat pentru fiecare.
     * Se apleaza sortQuery.
     */
    public final void queryMostViewedSerials(final Integer point) throws IOException {

        double aux;

        ArrayList<String> arrayFilterMovies = new ArrayList<>();
        Map<String, Double> mapMostViewed = new HashMap<>();

        if (getInput().getCommands().get(point).getFilters().get(0).get(0) != null
               && getInput().getCommands().get(point).getFilters().get(1).get(0) != null) {
            for (int i = 0; i < getInput().getSerials().size(); i++) {
                if (getInput().getSerials().get(i).getYear() != 0) {
                    if (getInput().getCommands().get(point).getFilters().get(0).get(0).
                            equals(Integer.toString(getInput().getSerials().get(i).getYear()))) {
                        if (getInput().getSerials().get(i).getGenres().contains(
                            getInput().getCommands().get(point).getFilters().get(1).get(0))) {
                            arrayFilterMovies.add(getInput().getSerials().get(i).getTitle());
                        }
                    }
                }
            }
        } else {
            if (getInput().getCommands().get(point).getFilters().get(0).get(0) != null) {
                for (int i = 0; i < getInput().getSerials().size(); i++) {
                    if (getInput().getSerials().get(i).getYear() != 0) {
                        if (getInput().getCommands().get(point).getFilters().get(0).get(0).
                            equals(Integer.toString(getInput().getSerials().get(i).getYear()))) {
                            arrayFilterMovies.add(getInput().getSerials().get(i).getTitle());
                        }
                    }
                }
            }

            if (getInput().getCommands().get(point).getFilters().get(1).get(0) != null) {
                for (int i = 0; i < getInput().getSerials().size(); i++) {
                    if (getInput().getSerials().get(i).getGenres().contains(
                            getInput().getCommands().get(point).getFilters().get(1).get(0))) {
                        arrayFilterMovies.add(getInput().getSerials().get(i).getTitle());
                    }
                }
            }

        }

        for (int i = 0; i < getInput().getUsers().size(); i++) {
            for (String arrayFilterMovie : arrayFilterMovies) {
                if (getInput().getUsers().get(i).getHistory().containsKey(arrayFilterMovie)) {
                    if (mapMostViewed.containsKey(arrayFilterMovie)) {
                        aux = mapMostViewed.get(arrayFilterMovie);
                        aux = aux + getInput().getUsers().get(i).getHistory().get(arrayFilterMovie);
                        mapMostViewed.put(arrayFilterMovie, aux);

                    } else {
                        mapMostViewed.put(arrayFilterMovie,
                                Double.valueOf(getInput().getUsers().get(i).
                                        getHistory().get(arrayFilterMovie)));
                    }
                }
            }
        }

        sortQuery(mapMostViewed, point);
    }

    /**
     * Se stocheaza toti utilizatorii si numarul de raitinguri pe care l-au oferit
     * filmelor/serialelor in mapQueryUsers. Se apeleaza sortQuery pentru mapQueryUsers.
     */
    public final void queryUsers(final Integer point) throws IOException {
        Map <String, Double> mapQueryUsers = new HashMap<>();

        for (int j = 0; j < getInput().getUsers().size(); j++) {
            if (getInput().getUsers().get(j).getNumberRatings() != 0) {
                mapQueryUsers.put(getInput().getUsers().get(j).getUsername(),
                        getInput().getUsers().get(j).getNumberRatings());
            }
        }
        sortQuery(mapQueryUsers, point);
    }
}


