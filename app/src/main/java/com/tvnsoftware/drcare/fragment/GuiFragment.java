package com.tvnsoftware.drcare.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tvnsoftware.drcare.R;
import com.tvnsoftware.drcare.Utils.Constants;

public class GuiFragment extends Fragment {
    private int fragVal;
//    @BindView(R.id.image_frag_gui)
//    ImageView mImageViewGuiPic;
private ImageView mImageViewGuiPic;

    public static GuiFragment init(int val) {
        GuiFragment truitonFrag = new GuiFragment();
        // Supply val input as an argument.
        Bundle args = new Bundle();
        args.putInt(Constants.INTENT_KEY_PAGER_POSIITION, val);
        truitonFrag.setArguments(args);
        return truitonFrag;
    }

    public GuiFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragVal = getArguments() != null ? getArguments().getInt(Constants.INTENT_KEY_PAGER_POSIITION) : 1;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gui, container, false);
//        ButterKnife.bind(getActivity(), view);
        mImageViewGuiPic = (ImageView) view.findViewById(R.id.image_frag_gui);
        Glide.with(getActivity()).load(getPic(fragVal)).into(mImageViewGuiPic);
        return view;
    }

    private int getPic(int pos) {
        switch (pos) {
            case 0:
                return R.drawable.guidance_01;
            case 1:
                return R.drawable.guidance_02;
            default:
                return R.drawable.guidance_01;
        }
    }
}
