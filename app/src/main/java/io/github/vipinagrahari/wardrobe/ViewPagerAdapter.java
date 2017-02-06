package io.github.vipinagrahari.wardrobe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by vipin on 5/2/17.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<Cloth> clothList;
    Context mContext;

    public ViewPagerAdapter(FragmentManager fm,Context context, List<Cloth> clothList) {
        super(fm);
        mContext = context;
        this.clothList = clothList;
    }

    @Override
    public int getCount() {
        return clothList.size();
    }

    @Override public Fragment getItem(int position) {
        return new ImageFragment();
    }


}
