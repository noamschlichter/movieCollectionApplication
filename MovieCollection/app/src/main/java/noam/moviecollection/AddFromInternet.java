package noam.moviecollection;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import java.util.List;


public class AddFromInternet extends AppCompatActivity {
    public Context mContext;
    public ListView listViewMovies;
    public MovieReaderController moviesReaderController;
    public List<SingleMovieDetails> tempMovies;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_from_internet);

        mContext = App.getContext(); // Assign the context so we can pass it to other activities

        listViewMovies = (ListView) findViewById(R.id.movieListView);
        moviesReaderController = new MovieReaderController(this);

        listViewMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                passSingleMovieData(i); // i is current list item the user clicked
            }
        });
    }

    /*
    the method is passing data of single movie from the inernet to manually activity for further uses
     */
    public void passSingleMovieData(int indexOfClickedItem) {
        String movieTitle = listViewMovies.getAdapter().getItem(indexOfClickedItem).toString();
        String baseImageUrl = "http://image.tmdb.org/t/p/w185"; // The base url for images

        tempMovies = moviesReaderController.giveMovies();

        for (int j = 0; j < tempMovies.size(); j++) {
            if (movieTitle.equals(tempMovies.get(j).getSubject())) {
                if (indexOfClickedItem == j) {
                    String description = tempMovies.get(j).getBody();
                    String imageUrl = baseImageUrl + tempMovies.get(j).getUrl();

                    Intent mAddManually = new Intent(this, AddManually.class); // Intent to AddManually class
                    mAddManually.putExtra("movieTitle", movieTitle);
                    mAddManually.putExtra("description", description);
                    mAddManually.putExtra("imageUrl", imageUrl);
                    mAddManually.putExtra("id", 1);
                    if (mContext instanceof Activity) {
                        ((Activity) mContext).startActivityForResult(mAddManually, 1);
                        finish();
                    }
                }
            }
        }
    }

    public void searchButton_onClick(View v) {
        EditText nameText = (EditText) findViewById(R.id.movieSearchEditText);
        String name = nameText.getText().toString();
        moviesReaderController.readAllMovies(name);
    }

    public void cancelButton_onClick(View v) {
        finish();
    }
}