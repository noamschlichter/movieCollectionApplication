package noam.moviecollection;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    MoviesDatabase db;
    int i;
    LinearLayout linearLayout;
    boolean next;
    List<SingleMovieDetails> names;
    String tempTitle;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App.setContext(this);
        db = new MoviesDatabase(this);
        next = true;
        linearLayout = (LinearLayout) findViewById(R.id.mainLinearLayout);
        loadMovie();
    }

    public void deleteAll() {
        db.clear();
        restart();
    }

    public void restart() {
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
        this.finishAffinity();
    }

    public void loadMovie() {
        names = db.getAllMovieList();
        i = 0;
        if (names.size() == i) {
            TextView empty = (TextView) findViewById(R.id.emptyCollectionTextView);
            empty.setVisibility(View.VISIBLE);
        } else {
            while (i < names.size()) {
                String s = names.get(i).getSubject();
                tempTitle = s;
                String u = names.get(i).getUrl();
                String d = names.get(i).getBody();
                int id = names.get(i).get_id();
                makeMovie(s, d, u, id);

                i++;
            }
        }
    }

    public void makeMovie(String name, String desc, String url, int id) {

        ImageView image = new ImageView(this);
        TextView title = new TextView(this);
        TextView description = new TextView(this);
        LinearLayout linearLayout = new LinearLayout(this);
        LinearLayout linealayoutInside = new LinearLayout(this);

        setLinearLayoutInside(linealayoutInside);
        setTextDes(description);
        setLinearLayout(linearLayout);
        setImageView(image);
        setTextView(title);

        addPicture(image, url);

        image.setTag(name);

        title.setText(name);
        description.setText(desc);

        final CustomDialog cdd = new CustomDialog(MainActivity.this, name, id);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit(view);
            }
        });
        image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                cdd.show();
                return false;
            }
        });

        linealayoutInside.addView(title);
        linealayoutInside.addView(description);


        linearLayout.addView(image);
        linearLayout.addView(linealayoutInside);

        this.linearLayout.addView(linearLayout);
    }

    public void setLinearLayoutInside(LinearLayout linearLayout) {
        LinearLayout.LayoutParams positionRules = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(positionRules);
        linearLayout.getLayoutParams().height = 880;
        positionRules.setMargins(25, 0, 0, 5);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
    }

    //set the DESCRIPTION
    public void setTextDes(TextView description) {
        LinearLayout.LayoutParams positionRules = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        description.setLayoutParams(positionRules);
        description.setTextColor(Color.BLACK);
        description.setTextSize(15);
        positionRules.setMargins(5, 5, 5, 5);
        description.getLayoutParams().height = 500;
    }

    public void setLinearLayout(LinearLayout linearLayout) {
        LinearLayout.LayoutParams positionRules = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(positionRules);
        positionRules.setMargins(25, 25, 25, 25);
        linearLayout.setLayoutParams(positionRules);
        linearLayout.getLayoutParams().height = 900;
        linearLayout.setBackgroundResource(R.drawable.layout_main_style);
    }

    //set the TITLE
    public void setTextView(TextView title) {
        LinearLayout.LayoutParams positionRules = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        title.setLayoutParams(positionRules);
        title.setTextColor(getResources().getColor(R.color.orange));
        title.setTextSize(30);
        positionRules.setMargins(15, 5, 15, 15);
    }

    //set the IMAGE
    public void setImageView(ImageView image) {
        LinearLayout.LayoutParams positionRules = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        image.setLayoutParams(positionRules);
        positionRules.setMargins(15, 15, 25, 0);
        image.getLayoutParams().height = 600;
        image.getLayoutParams().width = 200;
    }

    public void addPicture(ImageView b, String u) {
        if (u.equals("")) {
            b.setBackgroundResource(R.drawable.none_image);
            b.getBackground().setAlpha(150);
        } else {
            new DisplayImage(this, linearLayout, this, b, u).execute();
        }
    }


    public void edit(View v) {
        String movieTitle = v.getTag().toString();

        for (i = 0; i < names.size(); i++) {
            if (movieTitle.equals(names.get(i).getSubject())) {
                String title = names.get(i).getSubject();
                String description = names.get(i).getBody();
                String imageUrl = names.get(i).getUrl();
                int id = names.get(i).get_id();

                Intent editActivity = new Intent(this, AddManually.class);
                editActivity.putExtra("movieTitle", title);
                editActivity.putExtra("description", description);
                editActivity.putExtra("imageUrl", imageUrl);
                editActivity.putExtra("id", id);
                this.startActivityForResult(editActivity, 1);

            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);


        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        //the menu
        int itemId = item.getItemId();

        switch (itemId) {

            case R.id.addMovies:

                return true;
            case R.id.addFromInternetItem: // when the user will click for internet mode
                Intent net = new Intent(this, AddFromInternet.class);
                startActivity(net);
                return true;
            case R.id.addMenuallyItem:// when the user will click for manually mode
                Intent add = new Intent(this, AddManually.class);
                add.putExtra("id", -1);
                startActivityForResult(add, 1);
                return true;
            case R.id.exitItem: //exit option on menu
                finish();
                return true;
            case R.id.deleteAllMoviesItem: // in case the user want to clear his collection
                deleteAll();
                return true;

        }

        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {

                String name = data.getStringExtra("movieTitle");
                String description = data.getStringExtra("description");
                String url1 = data.getStringExtra("imageUrl");

                if (description.equals("")) {
                    if (url1.equals("")) {
                        SingleMovieDetails movie = new SingleMovieDetails(name, "", "");
                        db.addMovie(movie);
                    } else {
                        SingleMovieDetails movie = new SingleMovieDetails(name, "", url1);
                        db.addMovie(movie);
                    }
                } else if (url1.equals("")) {
                    SingleMovieDetails movie = new SingleMovieDetails(name, description, "");
                    db.addMovie(movie);
                } else {
                    SingleMovieDetails movie = new SingleMovieDetails(name, description, url1);
                    db.addMovie(movie);
                }

                this.recreate();


            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}