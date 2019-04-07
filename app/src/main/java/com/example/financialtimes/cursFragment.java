package com.example.financialtimes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class cursFragment extends Fragment{

    List<Double> curs_valori = new ArrayList<Double>();
    List<Date> curs_date = new ArrayList<Date>();

    public double euro_price, usd_price, gbp_price, chf_price, huf_price;
    public int curr_len_curs = 0; // Can be accessed by Thread lambda expression below and retains the dynamic length of the values
    public int curr_len_date = 0;

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
                        curr_len_curs++;
                    }

                    for(Element data: data_curs){
                        curs_date.add(convertToDate(data.text()));
                        curr_len_date++;
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
                LineGraphSeries<DataPoint> series1 = new LineGraphSeries<>(fetchVal());
                GraphView graph1 = view.findViewById(R.id.graph1);

                graph1.addSeries(series1);
                graph1.getViewport().setScrollable(true);
                graph1.getViewport().setScalableY(true);
                graph1.getViewport().setScalable(true);
                graph1.getViewport().setScalableY(true);
                graph1.getViewport().setMinY(4.6);
                graph1.getViewport().setMaxY(5);

                graph1.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
                graph1.getGridLabelRenderer().setNumHorizontalLabels(3);
                graph1.getGridLabelRenderer().setHumanRounding(true);

                GridLabelRenderer glr = graph1.getGridLabelRenderer();
                glr.setPadding(36);
            }
        });

        thread.start();
        setEuroRate();
        setUsdRate();
        setGbpRate();
        setChfRate();
        setHufRate();
        // set last update

        TextView updatedText = view.findViewById(R.id.updatedOn);
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        String formattedDate = formatter.format(currentTime);
        updatedText.setText("Updated on: " + formattedDate.toString());

        //Initialize the spinner
        Spinner currencySelect = view.findViewById(R.id.fromOptions);
        currencySelect.setPrompt("Selectati moneda");

        // Convert button listener
        Button convertBtn = view.findViewById(R.id.convertBtn);
        convertBtn.setOnClickListener(new View.OnClickListener(){
            @Override
                public void onClick(View view){
                    optionController();
            }
        });
        return view;
    }

    public DataPoint[] fetchVal(){

        DataPoint[] values = new DataPoint[curr_len_curs];

        for (int i = 0; i < this.curr_len_curs; i++){ // 28 is for the last 28 days
            DataPoint temp = new DataPoint(curs_date.get(i), curs_valori.get(i));
            values[i] = temp;
        }
        return values;
    }

    public Date convertToDate(String buff){
        Date dateConv = new Date();
        SimpleDateFormat formatData = new SimpleDateFormat("dd.MM.yyyy");
        try{
            dateConv = formatData.parse(buff);
        }catch(ParseException e){
            e.printStackTrace();
        }

        return dateConv;
    }

    public void setEuroRate() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document docEuro = Jsoup.connect("https://www.conso.ro/curs-valutar").get();
                    Elements euro_value = docEuro.select("table tbody tr:first-child td:nth-child(5)");

                    for (Element euro : euro_value) {
                        euro_price = Double.parseDouble(euro.text());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                TextView eurRon = getView().findViewById(R.id.eur_ron);
                eurRon.setText(Double.valueOf(euro_price).toString());
            }
        });

        thread.start();
    }
    public void setUsdRate() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document docEuro = Jsoup.connect("https://www.conso.ro/curs-valutar").get();
                    Elements usd_value = docEuro.select("table tbody tr:nth-child(2) td:nth-child(5)");

                    for (Element usd : usd_value) {
                        usd_price = Double.parseDouble(usd.text());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                TextView usdRon = getView().findViewById(R.id.usd_ron);
                usdRon.setText(Double.valueOf(usd_price).toString());
            }
        });
        thread.start();

    }
    public void setGbpRate() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document docEuro = Jsoup.connect("https://www.conso.ro/curs-valutar").get();
                    Elements gbp_value = docEuro.select("table tbody tr:nth-child(3) td:nth-child(5)");

                    for (Element gbp : gbp_value) {
                        gbp_price = Double.parseDouble(gbp.text().toString());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                TextView gbpRon = getView().findViewById(R.id.gbp_ron);
                gbpRon.setText(Double.valueOf(gbp_price).toString());
            }
        });

        thread.start();
    }

    public void setChfRate() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document docEuro = Jsoup.connect("https://www.conso.ro/curs-valutar").get();
                    Elements chf_value = docEuro.select("table tbody tr:nth-child(5) td:nth-child(5)");

                    for (Element chf : chf_value) {
                        chf_price = Double.parseDouble(chf.text().toString());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                TextView chfRon = getView().findViewById(R.id.chf_ron);
                chfRon.setText(Double.valueOf(chf_price).toString());
            }
        });

        thread.start();
    }
    public void setHufRate() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document docEuro = Jsoup.connect("https://www.conso.ro/curs-valutar").get();
                    Elements huf_value = docEuro.select("table tbody tr:nth-child(6) td:nth-child(5)");

                    for (Element huf : huf_value) {
                        huf_price  = Double.parseDouble(huf.text());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                StringBuilder builder = new StringBuilder();

                builder.append(Double.valueOf(huf_price).toString()).append(" ").append("RON");

                TextView hufRon = getView().findViewById(R.id.huf_ron);
                hufRon.setText(builder);
            }
        });

        thread.start();
    }

    public void optionController(){
        Spinner fromOption_Item = getView().findViewById(R.id.fromOptions);
        Spinner toOption_Item = getView().findViewById(R.id.toOptions);
        EditText fromPrice = getView().findViewById(R.id.fromValue);
        EditText toPrice = getView().findViewById(R.id.toValue);

        String selectedFromOpt = fromOption_Item.getSelectedItem().toString();
        String selectedToOpt = toOption_Item.getSelectedItem().toString();

        double parity = calcParity(selectedFromOpt, selectedToOpt);
        double cashInserted = Double.parseDouble(fromPrice.getText().toString());
        double convertedPrice = cashInserted * parity;
        String convertedPriceStr = Double.valueOf(convertedPrice).toString();
        toPrice.setText(convertedPriceStr);
    }

    public double calcParity(String from, String to){
        double fromTemp = 0;
        double toTemp = 0;
        switch(from){
            case "EUR":
                fromTemp = euro_price;
                break;
            case "USD":
                fromTemp = usd_price;
                break;
            case "GBP":
                fromTemp = gbp_price;
                break;
            case "CHF":
                fromTemp = chf_price;
                break;
            case "RON":
                fromTemp = 1;
                break;
            case "HUF":
                fromTemp = huf_price / 100;
                break;
        }

        switch(to){
            case "EUR":
                toTemp = euro_price;
                break;
            case "USD":
                toTemp = usd_price;
                break;
            case "GBP":
                toTemp = gbp_price;
                break;
            case "CHF":
                toTemp = chf_price;
                break;
            case "RON":
                toTemp = 1;
                break;
            case "HUF":
                toTemp = huf_price / 100;
                break;
        }

        return (fromTemp/toTemp);
    }
}
