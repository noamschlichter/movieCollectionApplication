package noam.moviecollection;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.util.List;

public class AddManually extends AppCompatActivity {

    EditText titleEditText; // title of each movie
    EditText descriptionEditText; // description of the movie
    EditText urlOfImageEditText; // url of the movie image
    LinearLayout parentLinearLayout;
    ImageView imageView;
    int id;
    int check;
    MoviesDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_manually);
        imageView = (ImageView) findViewById(R.id.imageImageView);
        titleEditText = (EditText) findViewById(R.id.titleEditText);
        descriptionEditText = (EditText) findViewById(R.id.descriptionEditText);
        urlOfImageEditText = (EditText) findViewById(R.id.urlEditText);
        parentLinearLayout = (LinearLayout) findViewById(R.id.parentLinearLayout);

        db = new MoviesDatabase(this); // Creating Instance of MoviesDatabase

        Intent mAddFromInternet = getIntent(); // get the intent to extract data of movie

        Bundle singleMovieExtras = mAddFromInternet.getExtras(); // get the extras - detials about the movie
        if (singleMovieExtras != null) {
            String movieTitle = (String) singleMovieExtras.get("movieTitle");
            String description = (String) singleMovieExtras.get("description");
            String urlOfImage = (String) singleMovieExtras.get("imageUrl");
            id = (int) singleMovieExtras.get("id");

            if (movieTitle == null) {
                check = 0;
            } else {
                check = -1;
            }

            titleEditText.setText(movieTitle);
            descriptionEditText.setText(description);
            urlOfImageEditText.setText(urlOfImage);

        }
    }

    public void okButton_onClick(View v) {
        MoviesDatabase db = new MoviesDatabase(this);
        //if the movie already exist so the user want to edit
        // delete the last version and creating new one instead -- actually update!
        if (check == -1) {
            db.deleteMovie(id);
        }
        Intent returnIntent = new Intent();
        String movieTitle = titleEditText.getText().toString();
        List<SingleMovieDetails> mSingleMovieDetails = db.getAllMovieList();
        boolean nameExist = true;
        for (int i = 0; i < mSingleMovieDetails.size(); i++) {
            if (movieTitle.equals(mSingleMovieDetails.get(i).getSubject())) {
                nameExist = false;
            }
        }
        if (nameExist) {
            String description = descriptionEditText.getText().toString();
            String imageUrl = urlOfImageEditText.getText().toString();
            if (movieTitle.equals("")) { //if the user left the title empty
                Toast.makeText(this, R.string.titleMustBeFilled, Toast.LENGTH_SHORT).show(); // toast to fill the title text
            } else {

                returnIntent.putExtra("movieTitle", movieTitle);
                returnIntent.putExtra("description", description);
                returnIntent.putExtra("imageUrl", imageUrl);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        } else {
            Toast.makeText(this,R.string.movieExiest, Toast.LENGTH_SHORT).show(); // if the user fiil the title with title that
                                                                                                    // already in his collection/list.
        }


    }

    public void cancelButton_onClick(View v) {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }

    public void showImageButton_onClick(View v) { //shows the image of the movie
        String imageUrl = urlOfImageEditText.getText().toString();
        new DisplayImage(this, parentLinearLayout, this, imageView, imageUrl).execute();
    }
}
