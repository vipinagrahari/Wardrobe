package io.github.vipinagrahari.wardrobe;

import android.net.Uri;
import org.parceler.Parcel;

/**
 * Created by vipin on 5/2/17.
 */
@Parcel
public class Cloth {
    int id;
    Uri imageUri;
    Type type;

     public enum Type{
        PANT,SHIRT;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
