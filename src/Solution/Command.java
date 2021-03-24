package Solution;

import fileio.Input;
import fileio.Writer;
import org.json.simple.JSONArray;
import java.io.IOException;



@SuppressWarnings("unchecked")
public class Command extends Actions {

    /**
     *Coding style.
     */
    public Command(final Input inputFile, final Writer outputFile,
                   final JSONArray array, final Ratings rating, final Sort sort) {
        super(inputFile, outputFile, array, rating, sort);
    }

    /**
     * Formateaza textul, populeaza array-ul JSON pentru scrierea in
     * fisierul de ouput.
     */
    public final void writeView(final int id, final String movie,
                                        final Integer view) throws IOException {
        getArrayResult().add(getOutput().writeFile(id, "success", "success -> "
                            + movie + " was viewed with total views of " + view));

    }

    /**
     * Formateaza textul, populeaza array-ul JSON pentru scrierea in
     * fisierul de ouput.
     */
    public final void writeFavorite(final String write, final Integer id,
                                                final String movie) throws IOException {
        if (write.equals("Success")) {

            getArrayResult().add(getOutput().writeFile(id, "Success", "success -> "
                                                + movie + " was added as favourite"));
        }

        if (write.equals("Error1")) {
            getArrayResult().add(getOutput().writeFile(id, "Error1", "error -> "
                                            + movie + " is already in favourite list"));
        }

        if (write.equals("Error2")) {
            getArrayResult().add(getOutput().writeFile(id, "Error2", "error -> "
                                                + movie + " is not seen"));

        }
    }
    /**
     * Formateaza textul, populeaza array-ul JSON pentru scrierea in
     * fisierul de ouput.
     */
    public final void writeRaiting(final String write, final Integer id,
                                            final String movie, final String user,
                                            final Double rating) throws IOException {

        if (write.equals("Success")) {
            getArrayResult().add(getOutput().writeFile(id, "success", "success -> "
                                                        + movie + " was rated with "
                                                        + rating + " by " + user));
        }

        if (write.equals("Error1")) {

            getArrayResult().add(getOutput().writeFile(id, "Error1", "error -> "
                                                + movie + " has been already rated"));
        }

        if (write.equals("Error2")) {
            getArrayResult().add(getOutput().writeFile(id, "Error2", "error -> "
                                                    + movie + " is not seen"));
        }
    }
    /**
     * In aceasta metoda se marcheaza/se adauga o vizualizare pentru un film in
     * istoria utilizatorului.
     */
    public final void addView(final Integer point) throws IOException {
        int user = 0;

        for (int i = 0; i < getInput().getUsers().size(); i++) {
            if (getInput().getUsers().get(i).getUsername().
                                equals(getInput().getCommands().get(point).getUsername())) {
                user = i;
            }
        }

        if (getInput().getUsers().get(user).getHistory().
                                containsKey(getInput().getCommands().get(point).getTitle())) {
            int aux = getInput().getUsers().get(user).getHistory().
                                            get(getInput().getCommands().get(point).getTitle());

            getInput().getUsers().get(user).getHistory().
                                    put(getInput().getCommands().get(point).getTitle(), aux + 1);

            writeView(getInput().getCommands().get(point).getActionId(),
                                    getInput().getCommands().get(point).getTitle(), aux + 1);

        } else {
            getInput().getUsers().get(user).getHistory().
                                            put(getInput().getCommands().get(point).getTitle(), 1);

            writeView(getInput().getCommands().get(point).getActionId(),
                                            getInput().getCommands().get(point).getTitle(), 1);
        }
    }

    /**
     * Aceasta metoda adauga in campul de ratings al utilizatorului un rating nou
     * si intr-o clasa separata doar pentru ratinguri.
     */
    public final void addRating(final Integer point) throws IOException {

        int user = 0;
        int numberOfSeason = 0;
        boolean addRating = false;

        for (int i = 0; i < getInput().getUsers().size(); i++) {
            if (getInput().getUsers().get(i).getUsername().
                                equals(getInput().getCommands().get(point).getUsername())) {
                user = i;
            }
        }

        if (!getInput().getUsers().get(user).getHistory().
                                containsKey(getInput().getCommands().get(point).getTitle())) {
            writeRaiting("Error2", getInput().getCommands().get(point).getActionId(),
                            getInput().getCommands().get(point).getTitle(), null, null);

        } else {
            if (getInput().getCommands().get(point).getSeasonNumber() == 0) {
                if (!getInput().getUsers().get(user).containsRating(
                                            getInput().getCommands().get(point).getTitle())) {
                    getInput().getUsers().get(user).addNewRating(
                            getInput().getCommands().get(point).getTitle(),
                                              getInput().getCommands().get(point).getGrade());

                    getRatings().addRating(getInput().getCommands().get(point).getTitle(),
                                               getInput().getCommands().get(point).getGrade());

                    writeRaiting("Success", getInput().getCommands().get(point).getActionId(),
                                                getInput().getCommands().get(point).getTitle(),
                                                getInput().getUsers().get(user).getUsername(),
                                                getInput().getUsers().get(user).getRating(
                                                getInput().getCommands().get(point).getTitle()));

                } else {
                    writeRaiting("Error1", getInput().getCommands().get(point).getActionId(),
                                getInput().getCommands().get(point).getTitle(), null, null);
                }
            } else {
                for (int i = 0; i < getInput().getSerials().size(); i++) {
                    if (getInput().getSerials().get(i).getTitle().
                                        equals(getInput().getCommands().get(point).getTitle())) {
                        numberOfSeason = getInput().getSerials().get(i).getNumberSeason();
                    }
                }

                if (!getInput().getUsers().get(user).containsRating(
                        getInput().getCommands().get(point).getTitle()
                                + getInput().getCommands().get(point).getSeasonNumber())) {
                    getInput().getUsers().get(user).addNewRating(
                                    getInput().getCommands().get(point).getTitle()
                                            + getInput().getCommands().get(point).getSeasonNumber(),
                                                    getInput().getCommands().get(point).getGrade());


                    for (int j = 0; j < numberOfSeason; j++) {
                        if (getInput().getUsers().get(user).containsRating(
                                getInput().getCommands().get(point).getTitle() + j)) {
                            addRating = true;
                            break;
                        }
                    }

                    if (addRating) {
                        getRatings().addRatingSerial(getInput().getCommands().get(point).getTitle(),
                                                getInput().getCommands().get(point).getGrade());
                    } else {
                        getRatings().addRating(getInput().getCommands().get(point).getTitle(),
                                          getInput().getCommands().get(point).getGrade());
                    }

                    writeRaiting("Success", getInput().getCommands().get(point).getActionId(),
                                    getInput().getCommands().get(point).getTitle(),
                                    getInput().getUsers().get(user).getUsername(),
                                    getInput().getUsers().get(user).getRating(
                                    getInput().getCommands().get(point).getTitle()
                                    + getInput().getCommands().get(point).getSeasonNumber()));
                } else {
                    writeRaiting("Error1", getInput().getCommands().get(point).getActionId(),
                                    getInput().getCommands().get(point).getTitle(), null, null);
                }
            }
        }
    }

    /**
     * Se verifica daca filmul a fost vizionat de utilizator, in functie de aceasta
     *  se adauga/nu se adauga in lista de favorite.
     */
    public final void addFavorite(final Integer point) throws IOException {
        int user = 0;

        for (int i = 0; i < getInput().getUsers().size(); i++) {
            if (getInput().getUsers().get(i).getUsername().equals(
                                         getInput().getCommands().get(point).getUsername())) {
                user = i;
            }
        }

        if (getInput().getUsers().get(user).getHistory().containsKey(
                                            getInput().getCommands().get(point).getTitle())) {
            if (getInput().getUsers().get(user).getFavoriteMovies().
                                   contains(getInput().getCommands().get(point).getTitle())) {

                writeFavorite("Error1", getInput().getCommands().get(point).getActionId(),
                                              getInput().getCommands().get(point).getTitle());
            } else {
                getInput().getUsers().get(user).getFavoriteMovies().add(
                                              getInput().getCommands().get(point).getTitle());
                writeFavorite("Success", getInput().getCommands().get(point).getActionId(),
                        getInput().getCommands().get(point).getTitle());
            }
        } else {
            writeFavorite("Error2", getInput().getCommands().get(point).getActionId(),
                                            getInput().getCommands().get(point).getTitle());
        }
    }
}
