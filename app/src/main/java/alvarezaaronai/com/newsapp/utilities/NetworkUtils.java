package alvarezaaronai.com.newsapp.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    /**
     * These utilities will be used to communicate with the network.
     */
    final static String NEWSAPP_BASE_URL =
            "https://newsapi.org/v2/everything";
    final static String PARAM_QUERY = "q";


    /**
     * Builds the URL used to query GitHub.
     *
     * @param newsAppSearchQuery The keyword that will be queried for.
     * @return The URL to use to query the GitHub.
     */
    public static URL buildUrl(String newsAppSearchQuery) {
        Uri builtUri = Uri.parse(NEWSAPP_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_QUERY, newsAppSearchQuery)
                .appendQueryParameter("apiKey","f58eb6e9d24a424289aa8acbe8c753a1")
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }
    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
