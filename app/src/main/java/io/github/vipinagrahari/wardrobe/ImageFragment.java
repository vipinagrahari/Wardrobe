package io.github.vipinagrahari.wardrobe;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.BindView;

import static io.github.vipinagrahari.wardrobe.Constants.CLOTH;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageFragment extends Fragment {

  @BindView(R.id.imageView1) ImageView imageView;
  Cloth cloth;

  public ImageFragment() {
    // Required empty public constructor
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_image, container, false);
  }

  public static ImageFragment newInstance() {
    Bundle args = new Bundle();
    ImageFragment fragment = new ImageFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      cloth = getArguments().getParcelable(CLOTH);
    }
  }
}
