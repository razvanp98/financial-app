package com.example.financialtimes;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.net.HttpURLConnection;
import java.io.IOException;
import java.util.*;

public class cursFragment extends Fragment {

    List<Double> curs_valori = new ArrayList<Double>();
    public int curr_length_curs = 0; // Can be accessed by Thread lambda expression below and retains the dynamic length of the values

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.curs_fragment, container, false);

        Thread thread = new Thread(new Runnable() {
            @Override

            public void run() {
                try{
                    Document doc = Jsoup.connect("https://www.cursbnr.ro/curs-valutar-bnr").get();
                    Elements data_curs = doc.select("table tbody tr td:first-child");
                    Elements pret_curs = doc.select("table tbody tr td:nth-child(2)");

                    for (Element pret: pret_curs){
                        curs_valori.add(Double.parseDouble(pret.text()));
                        curr_length_curs++;
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }

                LineGraphSeries<DataPoint> series1 = new LineGraphSeries<>(fetchVal());
                GraphView graph1 = view.findViewById(R.id.graph1);

                graph1.addSeries(series1);
                graph1.getViewport().setScrollable(true);
                graph1.getViewport().setScalable(true);
                graph1.getViewport().setMinY(4.6);
                graph1.getViewport().setMaxY(4.9);

                GridLabelRenderer glr = graph1.getGridLabelRenderer();
                glr.setPadding(36);
            }
        });

        thread.start();

        return view;
    }

    public DataPoint[] fetchVal(){

        DataPoint[] values = new DataPoint[curr_length_curs];

        for (int i = 0; i < this.curr_length_curs; i++){
            DataPoint temp = new DataPoint(i, curs_valori.get(i));
            values[i] = temp;
        }
        return values;
    }
}
