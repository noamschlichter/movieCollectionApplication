package noam.moviecollection;

public class SingleMovieDetails {

    private int _id;//the id if for the my own use and not the real id- its beed a check key to see if the
    // movie already existed or i made a new one
    private String subject;//title
    private String body;
    private String url;
    private int No;//the id number of the movie by the API i got (No=NumberOrder)
    private int watched=0;//the times you watched the movie- always start at 0

    public SingleMovieDetails(){}

    public  SingleMovieDetails(int id, String subject , String body , String url){
        this._id = id;
        this.subject = subject;
        this.body = body;
        this.url = url;
    }


    public  SingleMovieDetails(String subject , String body , String url){
        this.subject = subject;
        this.body = body;
        this.url = url;
    }

    public  SingleMovieDetails(String subject , String body){
        this.subject = subject;
        this.body = body;

    }

    public  SingleMovieDetails(String subject){
        this.subject = subject;
    }

    public  SingleMovieDetails(int id, String subject , String body , String url,int No){
        this._id = id;
        this.subject = subject;
        this.body = body;
        this.url = url;
        this.No = No;
    }

    public  SingleMovieDetails(int id, String subject , String body , String url,int No,int watched){
        this._id = id;
        this.subject = subject;
        this.body = body;
        this.url = url;
        this.No = No;
        this.watched=watched;
    }

    public  SingleMovieDetails(String subject , String body , String url,int No){
        this.subject = subject;
        this.body = body;
        this.url = url;
        this.No = No;
    }

    public  SingleMovieDetails(String subject , String body,int No){
        this.subject = subject;
        this.body = body;
        this.No = No;
    }

    public  SingleMovieDetails(String subject,int No ){

        this.subject = subject;
        this.No = No;

    }

    public int getWatched() {
        return watched;
    }

    public void setWatched(int watched) {
        this.watched = watched;
    }

    public int getNo() {
        return No;
    }

    public void setNo(int no) {
        No = no;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
