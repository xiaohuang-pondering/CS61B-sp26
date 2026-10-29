package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * An object for mapping a year number (e.g. 1996) to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {

    /** If it helps speed up your code, you can assume year arguments to your NGramMap
     * are between 1400 and 2100. We've stored these values as the constants
     * MIN_YEAR and MAX_YEAR here. */
    public static final int MIN_YEAR = 1400;
    public static final int MAX_YEAR = 2100;

    /**
     * Constructs a new empty TimeSeries.
     */
    public TimeSeries() {
        super();
    }

    /**
     * Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     * inclusive of both end points.
     */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        for (Map.Entry<Integer, Double> entry :
                ts.subMap(startYear, true, endYear, true).entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    /**
     *  Returns all years for this time series in ascending order.
     */
    public List<Integer> years() {
        List<Integer> years = new ArrayList<>();
        for (int year : keySet()) {
            years.add(year);
        }
        return years;
    }

    /**
     *  Returns all data for this time series. Must correspond to the
     *  order of years().
     */
    public List<Double> data() {
        List<Double> data = new ArrayList<>();
        for (Map.Entry<Integer, Double> entry : entrySet()) {
            data.add(entry.getValue());
        }
        return data;
    }

    /**
     * Returns the year-wise sum of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should return a
     * new TimeSeries (does not modify this TimeSeries).
     *
     * If both TimeSeries don't contain any years, return an empty TimeSeries.
     * If one TimeSeries contains a year that the other one doesn't, the returned TimeSeries
     * should store the value from the TimeSeries that contains that year.
     */
    public TimeSeries plus(TimeSeries ts) {
        TimeSeries total = new TimeSeries(this, 1400, 2100);
        for (Map.Entry<Integer, Double> entry : ts.entrySet()) {
            int year = entry.getKey();
            Double data = total.get(year);
            if (data == null) {
                total.put(year, entry.getValue());
            } else {
                total.put(year, data + entry.getValue());
            }
        }
        return total;
    }

    /**
     * Returns the quotient of the value for each year this TimeSeries divided by the
     * value for the same year in TS. Should return a new TimeSeries (does not modify this
     * TimeSeries).
     *
     * If TS is missing a year that exists in this TimeSeries, throw an
     * IllegalArgumentException.
     * If TS has a year that is not in this TimeSeries, ignore it.
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        TimeSeries quotients = new TimeSeries();
        for (Map.Entry<Integer, Double> entry : entrySet()) {
            Double divisor = ts.get(entry.getKey());
            if (divisor == null) {
                throw new IllegalArgumentException();
            }
            quotients.put(entry.getKey(), entry.getValue() / divisor);
        }
        return quotients;
    }
}
