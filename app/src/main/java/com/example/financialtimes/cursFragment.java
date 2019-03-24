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

public class cursFragment extends Fragment {

    private double[] curs_valori = new double[30];

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.curs_fragment, container, false);

        Thread thread = new Thread(new Runnable() {
            @Override

            public void run() {
                int i = 0;
                try{
                    Document doc = Jsoup.connect("https://www.cursbnr.ro/curs-valutar-bnr").get();
                    Elements data_curs = doc.select("table tbody tr td:first-child");
                    Elements pret_curs = doc.select("table tbody tr td:nth-child(2)");

                    for (Element pret: pret_curs){
                        curs_valori[i] = Double.parseDouble(pret.text());
                        i++;
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
        DataPoint[] values = new DataPoint[curs_valori.length];

        for (int i = 0; i < curs_valori.length; i++){
            DataPoint temp = new DataPoint(i, curs_valori[i]);
            values[i] = temp;
        }
        return values;
    }
}
