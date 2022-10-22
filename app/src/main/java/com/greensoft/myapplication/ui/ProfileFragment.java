package com.greensoft.myapplication.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.greensoft.myapplication.R;
import com.greensoft.myapplication.jsony.second_prescription;
import com.greensoft.myapplication.medications_tab.user_medications_Adapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    private RecyclerView recyclerView;
    private user_medications_Adapter recyclerAdapter;
    private List<second_prescription> zooma;
    private ImageView drug_meg;

    public List<second_prescription> getZooma() {
        return zooma;
    }

    public void setZooma(List<second_prescription> zooma) {
        this.zooma = zooma;
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_profile, container, false);
        View view = inflater.inflate(R.layout.fragment_user_medications, container, false);
        recyclerView = view.findViewById(R.id.medication_tab_recyclerview);
        drug_meg = view.findViewById(R.id.med_tab_drug_image);

        YoYo.with(Techniques.BounceIn)
                .duration(700)
                .playOn(view.findViewById(R.id.med_tab_drug_image));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        man_fred(getContext());

        return view;
    }




    @RequiresApi(api = Build.VERSION_CODES.O)
    public void man_fred(Context context){


        setZooma(new second_prescription().populate());
        //creating recyclerview adapter


        recyclerAdapter = new user_medications_Adapter(getZooma());

        //setting adapter to recyclerview
        recyclerView.setAdapter(recyclerAdapter);

    }

    @SuppressLint("NotifyDataSetChanged")
    public void self_stim(Context context) {

        recyclerAdapter.notifyDataSetChanged();

    }
}