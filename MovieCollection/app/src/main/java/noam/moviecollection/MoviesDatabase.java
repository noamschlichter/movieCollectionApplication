package noam.moviecollection;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;

import java.util.ArrayList;
import java.util.List;


//this class handles the all database tasks and extends the SQLiteOpenHelper (a build class)
//in this class we must to implement the methods "onCreate" and "onUpgrade" from the SQLiteOpenHelper class
public class MoviesDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "movieLab";

    public static final String TABLE_MOVIES = "Movies";

    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "movieName";
    public static final String KEY_DESCRIPTION = "movieDescription";
    public static final String KEY_URL = "movieURL";


    //We need to pass database information along to superclass because the super class doesn't have any default constructor

    public MoviesDatabase(Context contex) {
        super(contex, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //in the first time that we run this app we want to create our table in order to store data
    @Override
    public void onCreate(SQLiteDatabase db) {


        String query = "CREATE TABLE " + TABLE_MOVIES + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_URL + " TEXT " + ")";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);

        // Create tables again
        onCreate(db);
    }

    public void clear() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MOVIES, null, null);
        db.execSQL("delete from " + TABLE_MOVIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);

        onCreate(db);
    }


    public void addMovie(SingleMovieDetails movie) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NAME, movie.getSubject());
        values.put(KEY_DESCRIPTION, movie.getBody());
        values.put(KEY_URL, movie.getUrl());


        db.insert(TABLE_MOVIES, null, values);
        db.close();
    }


    public boolean deleteMovie(int delID) {

        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(TABLE_MOVIES, KEY_ID + "=" + delID, null) > 0;

    }


    public List<SingleMovieDetails> getAllMovieList() {


        List<SingleMovieDetails> MovieList = new ArrayList<SingleMovieDetails>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MOVIES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                SingleMovieDetails movie = new SingleMovieDetails();
                movie.set_id(Integer.parseInt(cursor.getString(0)));
                movie.setSubject(cursor.getString(1));
                movie.setBody(cursor.getString(2));
                movie.setUrl(cursor.getString(3));

                // Adding contact to list
                MovieList.add(movie);

            } while (cursor.moveToNext());
        }

        // return contact list
        return MovieList;
    }

}

