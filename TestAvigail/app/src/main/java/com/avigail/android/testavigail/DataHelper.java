package com.avigail.android.testavigail;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;

/**
 * Created by Avigail on 25/01/2019.
 */

public final class DataHelper {


    // covert the json to array list of Month's
    public static ArrayList<Month> getGraphsDataFromJson(String fileName, Context context){

        String KEY_GRAPHDATA = "graphData";
        String KEY_MONTHNAME = "monthName";

        String KEY_NAMECHILD = "nameChild";

        String KEY_WEEKS = "weeks";

        String KEY_S = "sunday";
        String KEY_M = "monday";
        String KEY_TU = "tuesday";
        String KEY_W = "wednesday";
        String KEY_TH = "thursday";
        String KEY_F = "friday";
        String KEY_SA = "saturday";

        ArrayList<Month> monthsData = new ArrayList<Month>();
        ArrayList<Week> weeksData = new ArrayList<Week>();

        try {
            // Load the JSONArray from the file
            String jsonString = loadJsonFromFile(fileName, context);

            JSONObject json = new JSONObject(jsonString);

            JSONArray jsonGraphs = json.getJSONArray(KEY_GRAPHDATA);

            String name = json.getString(KEY_NAMECHILD);

            // Create the list of Graphs of Months
            for (int i = 0; i < jsonGraphs.length(); i++) {

                String monthName = jsonGraphs.getJSONObject(i).getString(KEY_MONTHNAME);

                JSONArray jsonWeeks = jsonGraphs.getJSONObject(i).getJSONArray(KEY_WEEKS);


                // Create the list of Weeks of Months
                for (int x = 0; x < jsonWeeks.length(); x++) {

                    JSONObject jsonObjWeek = jsonWeeks.getJSONObject(x);

                    int sunday = jsonObjWeek.getInt(KEY_S);
                    int monday = jsonObjWeek.getInt(KEY_M);
                    int tuesday = jsonObjWeek.getInt(KEY_TU);
                    int wednesday = jsonObjWeek.getInt(KEY_W);
                    int thursday = jsonObjWeek.getInt(KEY_TH);
                    int friday = jsonObjWeek.getInt(KEY_F);
                    int saturday = jsonObjWeek.getInt(KEY_SA);

                    weeksData.add(new Week(sunday,monday,tuesday,wednesday,thursday,friday,saturday));

                }

                monthsData.add(new Month((weeksData),monthName));

                weeksData = new ArrayList<Week>();

            }

            // set the Name Child if the graph is not empty:
            if(monthsData.size()>0){
                monthsData.get(0).setNameChild(name);
            }

        } catch (JSONException e) {
            return monthsData;
        }

        return monthsData;

    }


    // -----------------------------------------------------------

    // function that reads the json file and returns it.
    private static String loadJsonFromFile(String filename,Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
