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
import android.widget.Toast;

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
    public int price_length = 0;
    public int dates_length = 0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.curs_fragment, container, false);

        TextView eurRon = view.findViewById(R.id.eur_ron);
        TextView usdRon = view.findViewById(R.id.usd_ron);
        TextView gbpRon = view.findViewById(R.id.gbp_ron);
        TextView chfRon = view.findViewById(R.id.chf_ron);
        TextView hufRon = view.findViewById(R.id.huf_ron);


        // Get data for graph
        Thread thread = new Thread(new Runnable() {
            @Override

            public void run() {
                try{
                    Document doc = Jsoup.connect("https://www.cursbnr.ro/curs-valutar-bnr").get();
                    Elements data_curs = doc.select("table tbody tr td:first-child");
                    Elements pret_curs = doc.select("table tbody tr td:nth-child(2)");

                    for (Element pret: pret_curs){
                        curs_valori.add(Double.parseDouble(pret.text()));
                        price_length++;
                    }

                    for(Element data: data_curs){
                        curs_date.add(convertToDate(data.text()));
                        dates_length++;
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
                LineGraphSeries<DataPoint> series1 = new LineGraphSeries<>(fetchVal());
                GraphView graph1 = view.findViewById(R.id.graph1);

                // Graph settings
                graph1.addSeries(series1);
                graph1.getViewport().setScalable(true);
                graph1.getViewport().setScalableY(true);
                graph1.getViewport().setScrollable(true);
                graph1.getViewport().setMinY(4.7);
                graph1.getViewport().setMaxY(4.9);
                graph1.getViewport().setMinimalViewport(0,0,4.7, 4.85);

                GridLabelRenderer glr = graph1.getGridLabelRenderer();

                // Label settings
                glr.setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
                glr.setNumHorizontalLabels(5);
                glr.setHumanRounding(true);
                glr.setHorizontalLabelsAngle(45);
                glr.setLabelsSpace(10);
                glr.setPadding(30);
            }
        });

        thread.start();

        // settings the rates for each currency
        setEuroRate(eurRon);
        setUsdRate(usdRon);
        setGbpRate(gbpRon);
        setChfRate(chfRon);
        setHufRate(hufRon);

        // Set last updated

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

    // Function provides datapoints for the graph
    public DataPoint[] fetchVal(){

        DataPoint[] values = new DataPoint[price_length];

        for (int i = 0; i < this.price_length; i++){
            DataPoint temp = new DataPoint(curs_date.get(i), curs_valori.get(i));
            values[i] = temp;
        }
        return values;
    }

    // Date converter for last updated status
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

    // Methods for parsing the html page and bringing the currency value into app
    public void setEuroRate(final TextView eurRon) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document docEuro = Jsoup.connect("https://www.conso.ro/curs-valutar").get();
                    Elements euro_value = docEuro.select("table tbody tr:first-child td:nth-child(5)");

                    for (Element euro : euro_value) {
                        euro_price = Double.parseDouble(euro.text());
                    }

                }catch (Exception e) {
                    e.printStackTrace();
                }
                eurRon.setText(Double.valueOf(euro_price).toString());
            }
        });

        thread.start();
    }

    public void setUsdRate(final TextView usdRon) {
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

                usdRon.setText(Double.valueOf(usd_price).toString());
            }
        });
        thread.start();
    }

    public void setGbpRate(final TextView gbpRon) {
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

                gbpRon.setText(Double.valueOf(gbp_price).toString());
            }
        });

        thread.start();
    }

    public void setChfRate(final TextView chfRon) {
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

                chfRon.setText(Double.valueOf(chf_price).toString());
            }
        });

        thread.start();
    }

    public void setHufRate(final TextView hufRon) {
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

                hufRon.setText(Double.valueOf(huf_price).toString());
            }
        });

        thread.start();
    }

    // Calculator option handler
    public void optionController(){
        Spinner fromOption_Item = getView().findViewById(R.id.fromOptions);
        Spinner toOption_Item = getView().findViewById(R.id.toOptions);

        EditText fromPrice = getView().findViewById(R.id.fromValue);
        EditText toPrice = getView().findViewById(R.id.toValue);

        String fromOption = fromOption_Item.getSelectedItem().toString();
        String toOption = toOption_Item.getSelectedItem().toString();

        String cashInsertedText = fromPrice.getText().toString();
        String toPriceText = toPrice.getText().toString();

        // Error handling for empty textboxes
        if (cashInsertedText.equals("") && toPriceText.equals("")){
            Toast.makeText(getActivity(), "From textbox must not be empty!", Toast.LENGTH_SHORT).show();
        }else if(cashInsertedText.equals("") && !toPriceText.equals("")) {
            Toast.makeText(getActivity(), "Invalid conversion!", Toast.LENGTH_SHORT).show();
        }else{
            double parity = calculateParity(fromOption, toOption);
            double cashInserted = Double.parseDouble(cashInsertedText);
            double convertedPrice = cashInserted * parity;
            String convertedPriceStr = Double.valueOf(convertedPrice).toString();
            toPrice.setText(convertedPriceStr);
        }
    }

    // Returns the parity between the two options selected
    public double calculateParity(String from, String to){
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
            default:
                fromTemp = 9999999;
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
            default:
                toTemp = 9999999;
                break;
        }

        return (fromTemp/toTemp);
    }
}
