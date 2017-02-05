package io.github.vipinagrahari.wardrobe;

import android.content.Context;
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

public class ViewPagerAdapter extends PagerAdapter {

    private List<Cloth> clothList;
    Context mContext;

    public ViewPagerAdapter(Context context, List<Cloth> clothList) {
        mContext = context;
        this.clothList = clothList;
    }

    @Override
    public int getCount() {
        return clothList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_image, container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView1);
        System.out.println("URI"+clothList.get(position).getImageUri().toString());
        container.addView(itemView);
        Picasso.with(mContext).load(clothList.get(position).getImageUri()).into(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }


}
