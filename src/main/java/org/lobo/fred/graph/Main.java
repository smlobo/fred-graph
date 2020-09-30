package org.lobo.fred.graph;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // Parse command line options
        if (args.length < 1) {
            System.out.println("Usage: java ... <series-id-1> [[:] <series-id-2> [:] <series-id-3> ... ]");
            System.out.println("Example: java GNPCA POP");
            System.exit(1);
        }

        // List of the series data in each Y-axis
        List<Map<String, Map<String, Double>>> listOfDataMaps = new LinkedList<>();

        // List of the series strings in 1 Y-axis
        LinkedList<String> yAxisGroup = new LinkedList<>();

        // Iterate over the input args
        for (String input : args) {
            // ':' indicates a new Y-axis, get the data, and add to the data list
            if (":".equals(input)) {
                System.out.println();
                listOfDataMaps.add(QueryFred.getAllData(yAxisGroup));
                yAxisGroup.clear();
            }
            // Else, accumulate list of the series strings in the current Y-axis
            else {
                System.out.print(input + ", ");
                yAxisGroup.add(input);
            }
        }

        // The last set of series strings for the last Y-axis
        System.out.println();
        listOfDataMaps.add(QueryFred.getAllData(yAxisGroup));

        // Draw all series on the same graph (with multiple Y axis)
        DrawGraph.draw(listOfDataMaps);
    }
}
