package noam.moviecollection;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;


public class MovieReaderController extends MovieController {
    public Activity contex;

    public List<SingleMovieDetails> giveMovies() {
        return allMoviesData;
    }

    public MovieReaderController(Activity activity) {
        super(activity);
        contex = activity;
    }


    public void readAllMovies(String name) {
        HttpRequest httpRequest = new HttpRequest(this);
        httpRequest.execute("https://api.themoviedb.org/3/search/movie?api_key=d58497888609e0997ec9d262ef4d19da&query=" + name + "&page=1");
    }


    public void onSuccess(String downloadedText) {

        try {
            List<SingleMovieDetails> tempList = new ArrayList<>();

            JSONObject jsonArray = new JSONObject(downloadedText);

            JSONArray resultArray = jsonArray.getJSONArray("results");


            Movies = new ArrayList<>();


            for (int i = 0; i < resultArray.length(); i++) {


                JSONObject jsonObject = resultArray.getJSONObject(i);
                String name = jsonObject.getString("title");
                String desc = jsonObject.getString("overview");
                String image = jsonObject.getString("poster_path");

                SingleMovieDetails movie = new SingleMovieDetails(name, desc, image);
                tempList.add(i, movie);


                Movies.add(name);
            }

            allMoviesData = tempList;
            ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, Movies);


            listViewMovies.setAdapter(adapter);

        } catch (JSONException ex) {
            Toast.makeText(activity, "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }


        progressDialog.dismiss();


    }
}