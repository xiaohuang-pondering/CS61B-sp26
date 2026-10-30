package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import browser.Plotter;
import org.knowm.xchart.XYChart;

import java.util.ArrayList;
import java.util.List;

public class HistoryHandler extends NgordnetQueryHandler {
    private NGramMap ngm;

    public HistoryHandler(NGramMap ngm) {
        this.ngm = ngm;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = new ArrayList<>();
        for (String word : q.words()) {
            words.add(word);
        }

        List<TimeSeries> lts = new ArrayList<>();
        for (String word : q.words()) {
            lts.add(ngm.weightHistory(word, q.startYear(), q.endYear()));
        }

        XYChart chart = Plotter.generateTimeSeriesChart(words, lts);
        return Plotter.encodeChartAsString(chart);
    }
}
