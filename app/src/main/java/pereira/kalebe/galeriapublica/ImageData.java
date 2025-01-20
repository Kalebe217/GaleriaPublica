package pereira.kalebe.galeriapublica;

import android.graphics.Bitmap;
import android.net.Uri;

import java.util.Date;

public class ImageData {


    public Uri uri;
    public Bitmap thumb;
    public String fileName;
    public Date date;
    public int size;

    //define imageData
    public ImageData(Uri uri, Bitmap thumb, String fileName, Date date, int size) {
        this.uri = uri;
        this.thumb = thumb;
        this.fileName = fileName;
        this.date = date;
        this.size = size;
    }
}