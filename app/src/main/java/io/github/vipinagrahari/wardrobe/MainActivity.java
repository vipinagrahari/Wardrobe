package io.github.vipinagrahari.wardrobe;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mlsdev.rximagepicker.RxImagePicker;
import com.mlsdev.rximagepicker.Sources;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.vipinagrahari.wardrobe.model.Cloth;
import io.github.vipinagrahari.wardrobe.model.Combo;
import io.github.vipinagrahari.wardrobe.model.Pant;
import io.github.vipinagrahari.wardrobe.model.Shirt;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import rx.functions.Action1;

import static io.github.vipinagrahari.wardrobe.Constants.Type.PANT;
import static io.github.vipinagrahari.wardrobe.Constants.Type.SHIRT;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.vp_shirt)
    ViewPager vpShirt;
    @BindView(R.id.vp_pant)
    ViewPager vpPant;

    @BindView(R.id.fab_fav)
    FloatingActionButton fabFav;

    @BindView(R.id.fab_shuffle)
    FloatingActionButton fabShuffle;

    @BindView(R.id.fab_add_pant)
    FloatingActionButton fabAddShirt;

    @BindView(R.id.fab_add_shirt)
    FloatingActionButton fabAddPant;

    @BindView(R.id.ll_viewpager)
    LinearLayout llViewPager;

    ViewPagerAdapter pantAdapter,shirtAdapter;
    List<? extends Cloth> shirtList;
    List<? extends Cloth> pantList;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(MainActivity.this);
        realm=Realm.getDefaultInstance();
        ButterKnife.bind(this);
        initPager(PANT);
        initPager(SHIRT);
        scheduleNotification(getNotification("Hey I am Custom Notification"));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        switch (newConfig.orientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                llViewPager.setOrientation(LinearLayout.HORIZONTAL);
                setLayoutParams(fabShuffle, Gravity.CENTER | GravityCompat.END);
                setLayoutParams(fabAddShirt, Gravity.TOP);
                setLayoutParams(fabAddPant, Gravity.TOP | GravityCompat.END);
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                llViewPager.setOrientation(LinearLayout.VERTICAL);
                setLayoutParams(fabShuffle, Gravity.CENTER | Gravity.BOTTOM);
                setLayoutParams(fabAddShirt, Gravity.TOP | GravityCompat.END);
                setLayoutParams(fabAddPant, Gravity.BOTTOM | GravityCompat.END);
                break;
        }



    }

    private void setLayoutParams(FloatingActionButton fab, int anchorGravity) {
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
        layoutParams.anchorGravity = anchorGravity;
        fab.setLayoutParams(layoutParams);
    }

    private void initPager(Constants.Type type){
        switch (type){
            case SHIRT:
                RealmResults<Shirt> shirts=realm.where(Shirt.class).findAll().sort("id", Sort.DESCENDING);
                shirtList=realm.copyFromRealm(shirts);
                vpShirt.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(),MainActivity.this,shirtList));
                break;
            case PANT:
                RealmResults<Pant> pants=realm.where(Pant.class).findAll().sort("id", Sort.DESCENDING);;
                pantList=realm.copyFromRealm(pants);
                vpPant.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(),MainActivity.this,pantList));
                break;
        }

    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }
    private int getOrientation() {
        return MainActivity.this.getResources().getConfiguration().orientation;
    }


    @OnClick({R.id.fab_fav, R.id.fab_add_shirt, R.id.fab_add_pant, R.id.fab_shuffle})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.fab_add_pant: showPickerDialog(PANT);break;
            case R.id.fab_add_shirt:showPickerDialog(SHIRT);break;
            case R.id.fab_fav:addFavourite();
        }
    }

    private void addFavourite() {
        realm.beginTransaction();

        Pant pant=realm.where(Pant.class).equalTo("id",
                pantList.get(vpPant.getCurrentItem()).getId()).findFirst();
        Shirt shirt=realm.where(Shirt.class).equalTo("id",
                shirtList.get(vpShirt.getCurrentItem()).getId()).findFirst();
        Combo combo=realm.createObject(Combo.class,pant.getId()+shirt.getId());
        combo.setPant(pant);
        combo.setShirt(shirt);
        realm.commitTransaction();

        Toast.makeText(this, "saved as favourite", Toast.LENGTH_SHORT).show();
    }

    private void showImagePicker(Sources source,final Constants.Type type) {
        RxImagePicker.with(MainActivity.this).requestImage(source).subscribe(new Action1<Uri>() {
            @Override
            public void call(Uri uri) {
                saveToDb(type,uri);
            }
        });
    }

    private void saveToDb(Constants.Type type,Uri uri) {
        realm.beginTransaction();
        switch(type){
            case PANT:
                Pant pant=realm.createObject(Pant.class,Calendar.getInstance(Locale.getDefault()).getTimeInMillis());
                pant.setImageUri(uri.toString());
                break;
            case SHIRT:
                Shirt shirt=realm.createObject(Shirt.class,Calendar.getInstance(Locale.getDefault()).getTimeInMillis());
                shirt.setImageUri(uri.toString());
                break;
            default:return;
        }
        realm.commitTransaction();
        initPager(type);

    }

    private void showPickerDialog(final Constants.Type type) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setItems(new CharSequence[]{"Camera", "Gallery"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        showImagePicker(Sources.CAMERA,type);
                        break;
                    case 1:
                        showImagePicker(Sources.GALLERY,type);
                        break;
                }
            }
        }).show();
    }

    private void scheduleNotification(Notification notification) {

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent
            pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 6);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
            AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    private Notification getNotification(String content) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Scheduled Notification");
        builder.setContentText(content);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        return builder.build();
    }

}


