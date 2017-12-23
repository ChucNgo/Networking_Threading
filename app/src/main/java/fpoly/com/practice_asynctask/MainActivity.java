package fpoly.com.practice_asynctask;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextSwitcher;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText mSearchBox;

    private TextView mUrlDisplay;

    private TextView mSearchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSearchBox = findViewById(R.id.et_search_box);
        mUrlDisplay = findViewById(R.id.tv_url_display);
        mSearchResults = findViewById(R.id.tv_github_search_results_json);
    }

    /**
     * This method retrieves the search text from the EditText, constructs
     * the URL (using {@link NetworkUtils}) for the github repository you'd like to find, displays
     * that URL in a TextView, and finally fires off an AsyncTask to perform the GET request using
     * our (not yet created) {@link //GithubQueryTask}
     */

    // Query từ khóa bất kì trên Github
    private void makeGithubSearchQuerry(){
        String githubQuery = mSearchBox.getText().toString();
        // Lấy URL trên Edittext về để build rồi query
        URL githubSearchURL = NetworkUtils.buildUrl(githubQuery);
        // TextView sẽ hiện URL truy vấn mình gửi lên
        mUrlDisplay.setText(githubSearchURL.toString());

        // TODO (2) Call getResponseFromHttpUrl and display the results in mSearchResultsTextView
        // TODO (3) Surround the call to getResponseFromHttpUrl with a try / catch block to catch an IOException

//        String githubSearchResults = null;
//
//        try {
//            // Trả về String TextView Result sẽ hiển thị kết quả
//            githubSearchResults = NetworkUtils.getResponseFromHttpUrl(githubSearchURL);
//            mSearchResults.setText(githubSearchResults);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        // Câu lệnh mới, và cách gọi
        new GitHubQuerryTask().execute(githubSearchURL);
    }

    // Tác vụ query đc thực hiện bởi AsyncTask, nhấn mạnh chạy trên Background Thread
    // tránh chạy trên Main và có thể hiển thị quá trình lên Main/UI
    public class GitHubQuerryTask extends AsyncTask<URL, Void, String>{
        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String githubSearchResult = null;

            try{

                githubSearchResult = NetworkUtils.getResponseFromHttpUrl(searchUrl);

            }catch (Exception e){
                e.printStackTrace();
            }

            return githubSearchResult;
        }

        // Kết thúc và đẩy lên
        @Override
        protected void onPostExecute(String s) {
            if (s!=null && !s.equals("")){

                mSearchResults.setText(s);

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_search) {
            makeGithubSearchQuerry();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
