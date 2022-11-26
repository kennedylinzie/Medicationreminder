package com.greensoft.myapplication.ui;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.greensoft.myapplication.Context_maker;
import com.greensoft.myapplication.R;
import com.greensoft.myapplication.jsony.Patient;
import com.greensoft.myapplication.jsony.second_prescription;
import com.greensoft.myapplication.jsony.shared_persistence;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class SettingsFragment extends Fragment {

    private List<Patient> generalInfoObject;
    // variable for our bar chart
    BarChart barChart;

    PieChart pieChart;
    EditText et_emerge;
    Button btn_save_emrge;

    // variable for our bar data set.
    BarDataSet barDataSet1, barDataSet2;

    // array list for storing entries.
    private ArrayList barEntries;

    // creating a string array for displaying days.
    String[] days = new String[]{"Sunday", "Monday", "Tuesday", "Thursday", "Friday", "Saturday"};


    public SettingsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
       // Bundle args = new Bundle();
       // args.putString(ARG_PARAM1, param1);
       // args.putString(ARG_PARAM2, param2);
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_charts, container, false);
        //pie chart
        pieChart = view.findViewById(R.id.pieChart_view);
        showPieChart();



        shared_persistence shady = new shared_persistence();
        ////////////////////////////barchart
        // initializing variable for bar chart.
        barChart = view.findViewById(R.id.idBarChart);//for bar graph
        et_emerge = view.findViewById(R.id.et_chart_emergency_number);
        btn_save_emrge = view.findViewById(R.id.save_emerge);
        et_emerge.setText(shady.get_emergency_number(requireContext()));
        //et_emerge.setText(shady.get_emergency_number(requireContext()));
        String nn = shady.get_emergency_number(requireContext());
        btn_save_emrge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String phone = et_emerge.getText().toString();
               if(!phone.isEmpty()){
                       shady.save_emergency_number(requireContext(),phone);
                       et_emerge.setText(shady.get_emergency_number(requireContext()));
                       Toast.makeText(getContext(), "Successfully", Toast.LENGTH_SHORT).show();

               }
            }
        });




        // creating a new bar data set.
        barDataSet1 = new BarDataSet(getBarEntriesOne(), "First Set");//for bar graph
        barDataSet1.setColor(getContext().getResources().getColor(R.color.purple_200));//for bar graph
        barDataSet2 = new BarDataSet(getBarEntriesTwo(), "Second Set");//for bar graph
        barDataSet2.setColor(Color.BLUE);//for bar graph
        // below line is to add bar data set to our bar data.
        BarData data = new BarData(barDataSet1, barDataSet2);//for bar graph
        // after adding data to our bar data we
        // are setting that data to our bar chart.
        barChart.setData(data);//for bar graph
        // below line is to remove description
        // label of our bar chart.
        barChart.getDescription().setEnabled(false);//for bar graph
        // below line is to get x axis
        // of our bar chart.
        XAxis xAxis = barChart.getXAxis();//for bar graph
        // below line is to set value formatter to our x-axis and
        // we are adding our days to our x axis.
        xAxis.setValueFormatter(new IndexAxisValueFormatter(days));//for bar graph
        // below line is to set center axis
        // labels to our bar chart.
        xAxis.setCenterAxisLabels(true);//for bar graph
        // below line is to set position
        // to our x-axis to bottom.
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//for bar graph
        // below line is to set granularity
        // to our x axis labels.
        xAxis.setGranularity(1);//for bar graph
        // below line is to enable
        // granularity to our x axis.
        xAxis.setGranularityEnabled(true);//for bar graph
        // below line is to make our
        // bar chart as draggable.
        barChart.setDragEnabled(true);//for bar graph
        // below line is to make visible
        // range for our bar chart.
        barChart.setVisibleXRangeMaximum(3);//for bar graph
        // below line is to add bar
        // space to our chart.
        float barSpace = 0.1f;//for bar graph
        // below line is use to add group
        // spacing to our bar chart.
        float groupSpace = 0.5f;//for bar graph
        // we are setting width of
        // bar in below line.
        data.setBarWidth(0.15f);//for bar graph
        // below line is to set minimum
        // axis to our chart.
        barChart.getXAxis().setAxisMinimum(0);//for bar graph
        // below line is to
        // animate our chart.
        barChart.animate();//for bar graph
        // below line is to group bars
        // and add spacing to it.
        barChart.groupBars(0, groupSpace, barSpace); //for bar graph
        // below line is to invalidate
        // our bar chart.
        barChart.invalidate();//for bar graph

        generalInfoObject =null;
        shared_persistence shad = new shared_persistence();
        String output = shad.get_json(getContext());
        if(output.equals("None")){

        }else {
            Gson gson = new Gson();
            Type foundlistType = new TypeToken<ArrayList<Patient>>(){}.getType();
            generalInfoObject = gson.fromJson(output, foundlistType);
        }


      /*List<String> name = new ArrayList<>();

        for (Patient dss : generalInfoObject) {
            //System.out.println(dss.getName());

            for (int i = 0; i < dss.getmDosage().size(); i++) {

                    if(dss.getmDosage().get(i).getMedicationName().equals("panado")){
                        // Toast.makeText(getContext(), "got through", Toast.LENGTH_SHORT).show();
                       name.add(dss.getmDosage().get(i).getMedicationName());
                    }
                }
           }*/
        return view;
    }

    private void save_emerge(String number) {
        shared_persistence sh = new shared_persistence();
        sh.save_emergency_number(requireContext(),number);

    }

    // array list for first set for bar graph
    private ArrayList<BarEntry> getBarEntriesOne() {

        // creating a new array list
        barEntries = new ArrayList<>();

        // adding new entry to our array list with bar
        // entry and passing x and y axis value to it.
        barEntries.add(new BarEntry(1f, 4));
        barEntries.add(new BarEntry(2f, 6));
        barEntries.add(new BarEntry(3f, 8));
        barEntries.add(new BarEntry(4f, 2));
        barEntries.add(new BarEntry(5f, 4));
        barEntries.add(new BarEntry(6f, 1));
        return barEntries;
    }

    // array list for second set. for bar graph
    private ArrayList<BarEntry> getBarEntriesTwo() {

        // creating a new array list
        barEntries = new ArrayList<>();

        // adding new entry to our array list with bar
        // entry and passing x and y axis value to it.
        barEntries.add(new BarEntry(1f, 8));
        barEntries.add(new BarEntry(2f, 12));
        barEntries.add(new BarEntry(3f, 4));
        barEntries.add(new BarEntry(4f, 1));
        barEntries.add(new BarEntry(5f, 7));
        barEntries.add(new BarEntry(6f, 3));
        return barEntries;
    }

    private void showPieChart(){ // for piechart


        List<second_prescription> generalInfoObject_pre;
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        String label = "Medications";

        //initializing data
        Map<String, Integer> typeAmountMap = new HashMap<>();

        generalInfoObject_pre =null;
        shared_persistence shad = new shared_persistence();
        String output = shad.get_json_user_prep(Context_maker.getInstance().getMyContext());
        Gson gson = new Gson();

        Type foundlistType = new TypeToken<ArrayList<second_prescription>>(){}.getType();
        generalInfoObject_pre = gson.fromJson(output, foundlistType);

        if(generalInfoObject_pre != null)
        {

            String tempname="";
            for (int i = 0; i < generalInfoObject_pre.size(); i++) {
                typeAmountMap.put(generalInfoObject_pre.get(i).getDrug_name(),Integer.parseInt(generalInfoObject_pre.get(i).getTotal_dosage()));
            }

        }




        //initializing colors for the entries
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#304567"));
        colors.add(Color.parseColor("#309967"));
        colors.add(Color.parseColor("#476567"));
        colors.add(Color.parseColor("#890567"));
        colors.add(Color.parseColor("#a35567"));
        colors.add(Color.parseColor("#ff5f67"));
        colors.add(Color.parseColor("#3ca567"));

        //input data and fit data into pie chart entry
        for(String type: typeAmountMap.keySet()){
            pieEntries.add(new PieEntry(typeAmountMap.get(type).floatValue(), type));
        }

        //collecting the entries with label name
        PieDataSet pieDataSet = new PieDataSet(pieEntries,label);
        //setting text size of the value
        pieDataSet.setValueTextSize(12f);
        //providing color list for coloring different entries
        pieDataSet.setColors(colors);
        //grouping the data set from entry to chart
        PieData pieData = new PieData(pieDataSet);
        //showing the value of the entries, default true if not set
        pieData.setDrawValues(true);

        pieChart.setData(pieData);
        pieChart.invalidate();
    }


}