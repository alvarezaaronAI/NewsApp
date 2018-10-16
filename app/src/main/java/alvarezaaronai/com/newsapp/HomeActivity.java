package alvarezaaronai.com.newsapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

import alvarezaaronai.com.newsapp.utilities.NetworkUtils;

public class HomeActivity extends AppCompatActivity {
    private EditText mSearchBoxEditText;
    private TextView mUrlDisplayTextView;
    private TextView mSearchResultsTextView;
    //PART 1
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //Setting Member Variables
        mSearchBoxEditText = (EditText) findViewById(R.id.et_search_box);
        mUrlDisplayTextView = (TextView) findViewById(R.id.tv_url_display);
        mSearchResultsTextView = (TextView) findViewById(R.id.tv_newsApp_search_results_json);
        //------------------------
    }
    private void makeGithubSearchQuery() {
        String newsAppQuery = mSearchBoxEditText.getText().toString();
        URL newsAppSearchURL = NetworkUtils.buildUrl(newsAppQuery);
        mUrlDisplayTextView.setText(newsAppSearchURL.toString());
        // COMPLETED (4) Create a new GithubQueryTask and call its execute method, passing in the url to query
        new NewsAppQueryTask().execute(newsAppSearchURL);
    }

    public class NewsAppQueryTask extends AsyncTask<URL, Void, String> {

        //Override the doInBackground method to perform the query. Return the results.
        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String newsAppSearchResults = null;
            try {
                newsAppSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return newsAppSearchResults;
        }

        //Override onPostExecute to display the results in the TextView
        @Override
        protected void onPostExecute(String newsAppSearchResults) {
            if (newsAppSearchResults != null && !newsAppSearchResults.equals("")) {
                mSearchResultsTextView.setText(newsAppSearchResults);
            }
        }
    }
    //Override to create a search in toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    //Override to create an action in the toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_search) {
            makeGithubSearchQuery();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
