package io.github.vipinagrahari.wardrobe.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by vipin on 6/2/17.
 */

public class Combo extends RealmObject {
    @PrimaryKey
    long id; // Using CurrentTimeInMillis for the sake of simplicity
    Shirt shirt;
    Pant pant;
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public Shirt getShirt() {
        return shirt;
    }

    public void setShirt(Shirt shirt) {
        this.shirt = shirt;
    }

    public Pant getPant() {
        return pant;
    }

    public void setPant(Pant pant) {
        this.pant = pant;
    }
}
