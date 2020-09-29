package org.lobo.fred.graph;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class QueryFred {
    private static final String FRED_API = "https://api.stlouisfed.org/fred/";
    private static final String API_KEY = "e12c0787f51ff6db24ac8029710fa175";

    protected  static Map<String, Map<String, Double>> getAllData(String[] seriesArray) {
        HashMap<String, Map<String, Double>> dataMaps = new HashMap<>();

        // Iterate over all input args, returning FRED queried data for each
        for (String series : seriesArray) {
            System.out.println("Getting data for: " + series);
            dataMaps.put(getTitle(series) + " (" + series + ")", getData(series));
        }

        return dataMaps;
    }

    private static String getTitle(String seriesId) {
        String title = null;

        try {
            URL url = new URL(FRED_API + "series?series_id=" + seriesId + "&api_key=" + API_KEY +
                    "&file_type=json");
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            InputStream inputStream = conn.getInputStream();

            // Jackson JsonParser
            JsonParser parser = new JsonFactory().createParser(inputStream);

            while (!parser.isClosed()) {
                JsonToken token = parser.nextToken();

                if (token == JsonToken.FIELD_NAME && "seriess".equals(parser.getCurrentName())) {
                    //System.out.println("Found seriess");

                    assert (parser.nextToken() == JsonToken.START_ARRAY);

                    while ((token = parser.nextToken()) != JsonToken.END_ARRAY) {
                        if (token == JsonToken.FIELD_NAME && "title".equals(parser.getCurrentName())) {
                            parser.nextToken();
                            title = parser.getValueAsString();
                            System.out.println("Found title: " + title);
                            return title;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return title;
    }

    private static Map<String, Double> getData(String seriesId) {
        TreeMap<String, Double> dataMap = new TreeMap<>();

        // Send a request to the FRED API
        try {
            URL url = new URL(FRED_API + "series/observations?series_id=" + seriesId + "&api_key=" + API_KEY +
                    "&file_type=json");
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            InputStream inputStream = conn.getInputStream();

            // Jackson JsonParser
            JsonParser parser = new JsonFactory().createParser(inputStream);

            int dataCount = -1;

            while (!parser.isClosed()) {
                JsonToken token = parser.nextToken();

                if (token == JsonToken.FIELD_NAME && "count".equals(parser.getCurrentName())) {
                    parser.nextToken();
                    dataCount = parser.getValueAsInt();
                    System.out.println("Data count: " + dataCount);
                }

                else if (token == JsonToken.FIELD_NAME && "observations".equals(parser.getCurrentName())) {
                    //System.out.println("Found observations");

                    assert (parser.nextToken() == JsonToken.START_ARRAY);

                    String date = null;
                    double value = 0.0;
                    while ((token = parser.nextToken()) != JsonToken.END_ARRAY) {
                        if (token == JsonToken.FIELD_NAME && "date".equals(parser.getCurrentName())) {
                            parser.nextToken();
                            date = parser.getValueAsString();
                            //System.out.println("Found date: " + date);
                        }
                        else if (token == JsonToken.FIELD_NAME && "value".equals(parser.getCurrentName())) {
                            parser.nextToken();
                            value = parser.getValueAsDouble();
                            //System.out.println("Found value: " + value);
                            dataMap.put(date, value);
                        }
                    }
                }
            }

            inputStream.close();
            conn.disconnect();

            System.out.println("  Data size: " + dataMap.size());
            assert(dataCount == dataMap.size());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataMap;
    }
}
