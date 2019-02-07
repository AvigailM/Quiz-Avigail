package com.avigail.android.testavigail;

/**
 * Created by Michal on 22/01/2019.
 */

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;

import java.util.ArrayList;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

/**
 * Created by Avigail on 25/01/2019.
 */


public class SliderAdapter extends PagerAdapter{

    private LineChart chart;

    private ArrayList<LineData> lineDatas;

    private ArrayList<LineDataSet> dataSets;

    private ArrayList<Entry> entries;

    private XAxis xAxis;

    boolean first=true;

    private ArrayList<Month> graphsData;

    private Context context;
    private LayoutInflater layoutInflater;

    //Customizing x axis value
    final String[] weeks = new String[]{"W1", "W2", "W3", "W4"};


    public SliderAdapter(Context context, ArrayList<Month> dataList) {
        this.graphsData = dataList;
        this.context = context;
    }

    //----------------------------------------------------
    @Override
    public int getCount() {
        return graphsData.size();
    }

    //----------------------------------------------------
    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == (RelativeLayout) o;
    }

    //----------------------------------------------------
    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = layoutInflater.inflate(R.layout.activity_graph, container, false);

        chart = (LineChart) view.findViewById(R.id.chart);

        TextView title1 = (TextView) view.findViewById(R.id.textTitleGraph);

        if(first){
            createOnce();
            first=false;
        }


        title1.setText(graphsData.get(position).getMonth());

        // Custom the Graph
        setCustomGraph();

        // set the size of the text in the graph
        lineDatas.get(position).setValueTextSize(14f);

        // set the data of the chart
        chart.setData(lineDatas.get(position));

        // refresh the graph
        chart.notifyDataSetChanged();
        chart.invalidate();


        container.addView(view);

        return view;

    }


    @Override
    public void destroyItem (ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }

    //----------------------------------------------------
    // function that Customs the Graph
    private void setCustomGraph(){

        // Set Touch, Drag and Scale to enable
        chart.setTouchEnabled(false);
        chart.setDragEnabled(false);
        chart.setScaleEnabled(false);

        // Controlling right side of y axis
        YAxis yAxisRight = chart.getAxisRight();
        yAxisRight.setEnabled(false);

        // Controlling left side of y axis
        YAxis yAxisLeft = chart.getAxisLeft();
        yAxisLeft.setGranularity(1f);

        yAxisLeft.setAxisLineColor(Color.TRANSPARENT);
        yAxisLeft.setDrawLabels(false);

        chart.getDescription().setEnabled(false);

        chart.getAxisLeft().setDrawGridLines(false);
        chart.getXAxis().setDrawGridLines(false);


        //Controlling X axis
        xAxis = chart.getXAxis();
        //Set the xAxis position to bottom
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        IAxisValueFormatter formatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return weeks[(int) value];
            }
        };

        // minimum axis-step interval is 1
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(formatter);

    }

    //----------------------------------------------------
    // function for initializing data for the graph :
    // set the chart data:
    public void createOnce(){

        //Entry
        entries = new ArrayList<Entry>();

       //ArrayList of LineData
        lineDatas = new ArrayList<LineData>();

        //LineDataSet :
        dataSets = new ArrayList<LineDataSet>();


        for (int x=0; x<graphsData.size(); x++){

            dataSets.add(new LineDataSet(new ArrayList<Entry>(),"Data in " + graphsData.get(x).getMonth()));

            for(int i=0; i<graphsData.get(x).getMonthList().size(); i++){
                dataSets.get(x).addEntry(new Entry(i, graphsData.get(x).getMonthList().get(i).getAverage()));
                dataSets.get(x).getEntryForIndex(i).setIcon(ContextCompat.getDrawable(context,R.drawable.screen));
            }

            dataSets.get(x).setColor(Color.rgb(0,187,207));

            lineDatas.add(new LineData(dataSets.get(x)));

            chart.notifyDataSetChanged();
            chart.invalidate();

        }

    }


}