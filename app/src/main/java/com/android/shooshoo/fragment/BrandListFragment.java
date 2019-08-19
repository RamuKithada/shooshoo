package com.android.shooshoo.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.shooshoo.R;
import com.android.shooshoo.activity.participate.MyChallengesActivity;
import com.android.shooshoo.adapter.SponsorChallengersAdapter;
import com.android.shooshoo.models.Challenge;
import com.android.shooshoo.utils.ClickListener;
import com.android.shooshoo.utils.RecyclerTouchListener;
import com.android.shooshoo.views.BaseView;

import java.util.ArrayList;
import java.util.List;

/***
 * Challenges list to show Challenges in grid view in ViewPager of the {@link BrandListFragment}
 */

public class BrandListFragment extends Fragment implements View.OnClickListener{

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    public SponsorChallengersAdapter adapter=null;
    List<Challenge> challenges=new ArrayList<Challenge>();
    RecyclerView recyclerView;
//    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BrandListFragment() {

    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static BrandListFragment newInstance(int columnCount,List<Challenge> challenges) {
        BrandListFragment fragment = new BrandListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) challenges);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            challenges=getArguments().getParcelableArrayList("list");
        }
        adapter=  new SponsorChallengersAdapter(getContext(),challenges);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_challenge_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Context context = view.getContext();
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
//                               Toast.makeText(getContext(),""+position,Toast.LENGTH_SHORT).show();
                challenges=adapter.getChallenges();
                if(challenges!=null) {
                    Intent intent = new Intent(getActivity(), MyChallengesActivity.class);
                    intent.putExtra("challenge",challenges.get(position));
                    if(challenges.get(position).getSponsoredBy()==null)
                        intent.putExtra("type",2);
                    else
                        intent.putExtra("type",1);

                    startActivity(intent);
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }
BaseView baseView=null;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof BaseView) {
            baseView = (BaseView) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement BaseView");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }
    public void setList(List<Challenge> challenges){
        if(challenges!=null)
        this.challenges.addAll(challenges);
        adapter.notifyDataSetChanged();

    }


    @Override
    public void onClick(View v) {

    }
}
