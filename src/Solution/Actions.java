package Solution;

import fileio.Input;
import fileio.Writer;
import org.json.simple.JSONArray;

public class Actions {
    private final Input input;
    private final  Writer output;
    private final  JSONArray arrayResult;

    private final Ratings ratings;
    private final Sort sort;

    /**
     *
     *@param inputFile fisierul de input
     *@param outputFile fisierul de output
     *@param array folosita pentru scriarea in fisierul de ouput
     *@param rating folosita pentru a duce o evidenta de ratinguri adaugate
     *      *      de toti utilizatorii
     * @param sort parametru utilizat sortati
     */
    public Actions(final Input inputFile, final Writer outputFile,
                   final JSONArray array, final Ratings rating, final Sort sort)  {
        this.input = inputFile;
        this.output =  outputFile;
        this.arrayResult = array;
        this.ratings =  rating;
        this.sort = sort;
    }

    /**
     *Coding style.
     */
    public final Input getInput() {
        return input;
    }

    /**
     *Coding style.
     */
    public final Writer getOutput() {
        return output;
    }

    /**
     *Coding style.
     */
    public final JSONArray getArrayResult() {
        return arrayResult;
    }

    /**
     *Coding style.
     */
    public final Ratings getRatings() {
        return ratings;
    }

    /**
     *Coding style.
     */
    public final Sort getSort() {
        return sort;
    }
}
