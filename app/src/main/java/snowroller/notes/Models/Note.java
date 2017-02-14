package snowroller.notes.models;

/**
 * Created by Martin on 2017-02-09.
 */

public class Note {
    public long _id;
    public String title;
    public String body;

    public String getShortBody()
    {
        if( body == null)
            return "";
        if( body.length() < 15)
            return body;

        return body.substring(0, 14) + "...";

        /*
        int endindex = 14;
        if( body.length() < 15)
            endindex = body.length()-1;
        return body.substring(0, endindex);*/
    }

    @Override
    public int hashCode() {
        return (int) this._id;
    }

    @Override
    public boolean equals(Object obj) {
        boolean isEqual= false;

        if (obj != null && obj instanceof Note)
        {
            isEqual = (this._id == ((Note) obj)._id);
        }
        return isEqual;
    }
}
