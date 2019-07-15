package com.android.shooshoo.fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.shooshoo.R;
import com.android.shooshoo.activity.FeedsActivity;
import com.android.shooshoo.adapter.FeedsImagesAdapter;
public class FeedListFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView list;
    FeedsImagesAdapter adapter;
    GridLayoutManager layoutManager;
    int[] images=new int[]{R.drawable.food_context1,R.drawable.food_context2,R.drawable.food_context3,R.drawable.food_context4,R.drawable.food_context5};


    // TODO: Rename and change types of parameters
    private int mParam1;

    public FeedListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param position Parameter 1.
     * @return A new instance of fragment FeedListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FeedListFragment newInstance(int position) {
        FeedListFragment fragment = new FeedListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list = view.findViewById(R.id.list);
        layoutManager = new GridLayoutManager(getActivity(),2);
//        SnapHelper snapHelper = new PagerSnapHelper();
        list.setLayoutManager(layoutManager);
//        snapHelper.attachToRecyclerView(list);
        adapter = new FeedsImagesAdapter(images);
        adapter.setListener(this);
        list.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        Intent intent=new Intent(getActivity(), FeedsActivity.class);
        startActivity(intent);
    }
}
