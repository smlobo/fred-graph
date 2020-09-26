package org.lobo.fred.graph;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // Parse command line options
        if (args.length < 1) {
            System.out.println("Usage: java ... <series-id-1> [<series-id-2> <series-id-3> ... ]");
            System.out.println("Example: java GNPCA POP");
            System.exit(1);
        }

        // Query a list of series, returning a Map of <SeriesName, Map<Date, Value>>
        Map<String, Map<String, Double>> dataMaps = QueryFred.getAllData(args);

        // Draw all series on the same graph
        DrawGraph.draw(dataMaps);
    }
}
