package snowroller.notes.Models;

/**
 * Created by Martin on 2017-02-09.
 */

public class Note {
    public long _id;
    public String title;
    public String body;

    public String getShortBody()
    {
        int endindex = 14;
        if( body.length() < 15)
            endindex = body.length()-1;

        return body.substring(0,endindex);
    }
}
