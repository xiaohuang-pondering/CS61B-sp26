package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

public class HistoryTextHandler extends NgordnetQueryHandler {
    private NGramMap ngm;

    public HistoryTextHandler(NGramMap ngm) {
        this.ngm = ngm;
    }

    @Override
    public String handle(NgordnetQuery q) {
        String response = "";
        for (String word : q.words()) {
            response += word + ": " + ngm.weightHistory(word, q.startYear(), q.endYear()).toString() + "\n";
        }
        return response;
    }
}
