package Solution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class Sort {

    private ArrayList<String> keys;
    private ArrayList<Double> values;
    private ArrayList<String> sort;

    /**
     * Se sorteaza stringurile alfabetic,
     * iar valorile crescator.
     */
    public final void init(final Map<String, Double> map) {
        this.keys = new ArrayList<>(map.keySet());
        this.values = new ArrayList<>();
        this.sort = new ArrayList<>();
        Collections.sort(keys);

        for (int i = 0; i < map.size(); i++) {
            if (!values.contains(map.get(keys.get(i)))) {
                values.add(map.get(keys.get(i)));
            }
        }

        Collections.sort(values);

    }

    /**
     *
     * @param map este sortata ascendent
     * @return lista argumentelor sortate
     */
    public ArrayList<String> sortAscendingMap(final Map<String, Double> map) {

        init(map);

        for (Double value : values) {
            for (String key : keys) {
                if (value.equals(map.get(key))) {
                    sort.add(key);
                }
            }
        }

        return sort;
    }

    /**
     *
     * @param map este sortat descendent
     * @return lista argumentelor sortate
     */
    public final ArrayList<String> sortDescendingMap(final Map<String, Double> map) {
        init(map);

        for (int i = values.size() - 1; i >= 0; i--) {
            for (int j = keys.size() - 1; j >= 0; j--) {
                if (values.get(i).equals(map.get(keys.get(j)))) {
                    sort.add(keys.get(j));
                }
            }
        }

        return sort;
    }

    /**
     *
     * @param arr sunt sterse elementele in plus
     * @param n de elemente necesare
     * @return lista de argumente editata
     */
    public final ArrayList<String> editArrayList(final ArrayList<String> arr, final Integer n) {
        if (arr.size() > n) {
            arr.subList(n, arr.size()).clear();
        }

        return arr;
    }
}
