package main;

import edu.princeton.cs.algs4.In;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static main.TimeSeries.MAX_YEAR;
import static main.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    private Map<String, TimeSeries> wordCountsByYear;
    private TimeSeries totalCountByYear;

    /**
     * Constructs an NGramMap from WORDHISTORYFILENAME and YEARHISTORYFILENAME.
     */
    public NGramMap(String wordHistoryFilename, String yearHistoryFilename) {
        wordCountsByYear = new HashMap<>();
        In inWord = new In(wordHistoryFilename);
        while (!inWord.isEmpty()) {
            String[] splitLine = inWord.readLine().split("\t");
            String word = splitLine[0];
            if (!wordCountsByYear.containsKey(word)) {
                wordCountsByYear.put(word, new TimeSeries());
            }
            wordCountsByYear.get(word).put(Integer.parseInt(splitLine[1]), Double.parseDouble(splitLine[2]));
        }

        totalCountByYear = new TimeSeries();
        In inTotal = new In(yearHistoryFilename);
        while (!inTotal.isEmpty()) {
            String[] splitLine = inTotal.readLine().split(",");
            totalCountByYear.put(Integer.parseInt(splitLine[0]), Double.parseDouble(splitLine[1]));
        }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        return new TimeSeries(wordCountsByYear.get(word), startYear, endYear);
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        return new TimeSeries(wordCountsByYear.get(word), MIN_YEAR, MAX_YEAR);
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        return new TimeSeries(totalCountByYear, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        TimeSeries wordCountHistory = new TimeSeries(wordCountsByYear.get(word), startYear, endYear);
        return wordCountHistory.dividedBy(totalCountByYear);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        TimeSeries wordCountHistory = wordCountsByYear.get(word);
        if (wordCountHistory == null) {
            return new TimeSeries();
        }
        return wordCountHistory.dividedBy(totalCountByYear);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        TimeSeries summedHistory = new TimeSeries();
        for (String word : words) {
            summedHistory = summedHistory.plus(new TimeSeries(
                    wordCountsByYear.get(word), startYear, endYear).dividedBy(totalCountByYear));
        }
        return summedHistory;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        TimeSeries summedHistory = new TimeSeries();
        for (String word : words) {
            TimeSeries wordHistory = wordCountsByYear.get(word);
            if (wordHistory != null) {
                summedHistory = summedHistory.plus(wordHistory.dividedBy(totalCountByYear));
            }
        }
        return summedHistory;
    }
}
