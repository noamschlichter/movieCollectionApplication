package noam.moviecollection;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import java.util.List;

public class CustomDialog extends Dialog implements
        android.view.View.OnClickListener {

    public Activity activity;
    public Button delete, edit;
    public String name;
    public int movieId;
    MoviesDatabase db;
    List<SingleMovieDetails> names;

    public CustomDialog(Activity a,String title,int id) {
        super(a);
        name=title;
        movieId=id;
        this.activity = a;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog);
        delete = (Button) findViewById(R.id.deleteButton);
        edit = (Button) findViewById(R.id.editButton);
        delete.setOnClickListener(this);
        edit.setOnClickListener(this);
        db= new MoviesDatabase(activity);
        names=db.getAllMovieList();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.deleteButton:
                db.deleteMovie(movieId);
                activity.recreate();
                break;
            case R.id.editButton:
                int i=0;
                for(i=0;i<names.size();i++){
                    if(name.equals(names.get(i).getSubject())){
                        String title =names.get(i).getSubject();
                        String description =names.get(i).getBody();
                        String imageUrl =names.get(i).getUrl();
                        int id = names.get(i).get_id();

                        Intent editActivity = new Intent(activity, AddManually.class);
                        editActivity.putExtra("movieTitle",title);
                        editActivity.putExtra("description",description);
                        editActivity.putExtra("imageUrl",imageUrl);
                        editActivity.putExtra("id",id);
                        activity.startActivityForResult(editActivity,1);
                        break;
                    }
                }


            default:
                break;
        }
        dismiss();
    }
}