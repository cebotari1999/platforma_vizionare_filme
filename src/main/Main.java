package main;

import Solution.*;
import checker.Checkstyle;
import checker.Checker;
import common.Constants;
import fileio.*;
import org.json.simple.JSONArray;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * Call the main checker and the coding style checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(Constants.TESTS_PATH);
        Path path = Paths.get(Constants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        File outputDirectory = new File(Constants.RESULT_PATH);

        Checker checker = new Checker();
        checker.deleteFiles(outputDirectory.listFiles());

        for (File file : Objects.requireNonNull(directory.listFiles())) {

            String filepath = Constants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }

        checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
        Checkstyle test = new Checkstyle();
        test.testCheckstyle();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                        final String filePath2) throws IOException {
        InputLoader inputLoader = new InputLoader(filePath1);
        Input input = inputLoader.readData();

        Writer fileWriter = new Writer(filePath2);
        JSONArray arrayResult = new JSONArray();

        Ratings rating = new Ratings();
        Sort sort = new Sort();
        Command command = new Command(input, fileWriter, arrayResult, rating, sort);
        Query query = new Query(input, fileWriter, arrayResult, rating, sort);
        Recommendation recommendation;
        recommendation = new Recommendation(input, fileWriter, arrayResult, rating, sort);


       for (int i = 0; i < input.getCommands().size(); i++) {
           if (input.getCommands().get(i).getActionType().equals("command")) {
               if (input.getCommands().get(i).getType().equals("favorite")) {
                   command.addFavorite(i);
               }

               if (input.getCommands().get(i).getType().equals("view")) {
                   command.addView(i);
               }

               if (input.getCommands().get(i).getType().equals("rating")) {
                   command.addRating(i);
               }
           }

           if (input.getCommands().get(i).getActionType().equals("query")) {
               if (input.getCommands().get(i).getObjectType().equals("actors")) {
                   if (input.getCommands().get(i).getCriteria().equals("average")) {
                       query.queryAverage(i);
                   }

                   if (input.getCommands().get(i).getCriteria().equals("awards")) {
                       query.queryAwards(i);
                   }

                   if (input.getCommands().get(i).getCriteria().equals("filter_description")) {
                       query.queryFilterDescrition(i);
                   }
               }

               if (input.getCommands().get(i).getObjectType().equals("movies")) {
                   if (input.getCommands().get(i).getCriteria().equals("ratings")) {
                       query.queryRaitingMovies(i);
                   }

                   if (input.getCommands().get(i).getCriteria().equals("favorite")) {
                       query.queryFavoriteMovies(i);
                   }

                   if (input.getCommands().get(i).getCriteria().equals("longest")) {
                       query.queryLongestMovies(i);
                   }

                   if (input.getCommands().get(i).getCriteria().equals("most_viewed")) {
                       query.queryMostViewedMovies(i);
                   }
               }

               if (input.getCommands().get(i).getObjectType().equals("shows")) {
                   if (input.getCommands().get(i).getCriteria().equals("ratings")) {
                       query.queryRaitingShow(i);
                   }

                   if (input.getCommands().get(i).getCriteria().equals("favorite")) {
                       query.queryFavoriteShows(i);
                   }

                   if (input.getCommands().get(i).getCriteria().equals("longest")) {
                       query.queryLongestShows(i);
                   }

                   if (input.getCommands().get(i).getCriteria().equals("most_viewed")) {
                       query.queryMostViewedSerials(i);
                   }
               }

               if (input.getCommands().get(i).getObjectType().equals("users")) {
                   if (input.getCommands().get(i).getCriteria().equals("num_ratings")) {
                       query.queryUsers(i);
                   }
               }
           }

           if (input.getCommands().get(i).getActionType().equals("recommendation")) {
               if (input.getCommands().get(i).getType().equals("standard")) {
                   recommendation.standard(i);
               }

               if (input.getCommands().get(i).getType().equals("best_unseen")) {
                  recommendation.bestUnseen(i);
               }

               if (input.getCommands().get(i).getType().equals("popular")) {
                   recommendation.popular(i);
               }

               if (input.getCommands().get(i).getType().equals("search")) {
                   recommendation.search(i);
               }

               if (input.getCommands().get(i).getType().equals("favorite")) {
                   recommendation.favorite(i);
               }
           }
       }

        fileWriter.closeJSON(arrayResult);
    }
}
