package io.github.vipinagrahari.wardrobe;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.vipinagrahari.wardrobe.model.Cloth;

import static io.github.vipinagrahari.wardrobe.Constants.CLOTH;
import static io.github.vipinagrahari.wardrobe.Constants.URI;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageFragment extends Fragment {


  @BindView(R.id.imageView1) ImageView imageView;
  Uri imageUri;

  public ImageFragment() {
    // Required empty public constructor
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View v=inflater.inflate(R.layout.fragment_image, container, false);
    ButterKnife.bind(this,v);
    Picasso.with(getContext()).load(imageUri).resize(400,400).into(imageView);
    return v;
  }

  public static ImageFragment newInstance(Uri imageUri) {
    Bundle args = new Bundle();
    args.putParcelable(URI, imageUri);
    ImageFragment fragment = new ImageFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      imageUri = getArguments().getParcelable(URI);
    }
  }
}
