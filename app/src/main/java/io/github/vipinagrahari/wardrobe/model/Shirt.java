package io.github.vipinagrahari.wardrobe.model;

import org.parceler.Parcel;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by vipin on 6/2/17.
 */
@Parcel
public class Shirt extends RealmObject implements Cloth {
    @PrimaryKey
    long id; // Using CurrentTimeInMillis for the sake of simplicity
    String imageUri;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
