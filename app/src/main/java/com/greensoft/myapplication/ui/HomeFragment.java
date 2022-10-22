package com.greensoft.myapplication.ui;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.badoualy.datepicker.DatePickerTimeline;
import com.greensoft.myapplication.Context_maker;
import com.greensoft.myapplication.Medication;
import com.greensoft.myapplication.MedicationAdapter;
import com.greensoft.myapplication.R;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {



    //the recyclerview
    private RecyclerView recyclerView;
    private MedicationAdapter recyclerAdapter;

    public List<Medication> getZooma() {
        return zooma;
    }

    public void setZooma(List<Medication> zooma) {
        this.zooma = zooma;
    }

    List<Medication> zooma;
    DatePickerTimeline timeline;
    String json;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.medication_recyclerview);

        LocalTime my_time = LocalTime.now();
        LocalDate my_date = LocalDate.now();
        timeline = view.findViewById(R.id.datePickerTimeline);


        timeline.setFirstVisibleDate(my_date.getYear(), my_date.getMonthValue()-1, my_date.getDayOfMonth());
        //timeline.setLastVisibleDate(2022, Calendar.JUNE, 14);
       /* timeline.setDateLabelAdapter(new MonthView.DateLabelAdapter() {
            @Override
            public CharSequence getLabel(Calendar calendar, int index) {

                return Integer.toString(calendar.get(Calendar.MONTH)) + "-" + (calendar.get(Calendar.YEAR) % 2000);
            }
        });*/

        timeline.setOnDateSelectedListener(new DatePickerTimeline.OnDateSelectedListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDateSelected(int year, int month, int day, int index) {
                //"2022-06-20"
                DecimalFormat df = new DecimalFormat("00");
                String m = df.format(month+1);   // 0009
                String d = df.format(day);
                String time_ = year+"-"+m+"-"+d;
                Context_maker.getInstance().setTheTempTimeDealer(time_);
                produce_zooma();
                //recyclerAdapter.notifyDataSetChanged();


                Toast.makeText(getContext(),year+"-"+m+"-"+d,Toast.LENGTH_SHORT).show();
            }
        });


          produce_zooma();


        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void produce_zooma()
    {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        setZooma(new Medication().populate());
        //creating recyclerview adapter
        recyclerAdapter = new MedicationAdapter(getContext(),getZooma());

        //setting adapter to recyclerview
        recyclerView.setAdapter(recyclerAdapter);
    }

}