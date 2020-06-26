package com.equipeonetech.apptest;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Base;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.equipeonetech.apptest.dataBase.DataBaseHelper;
import com.equipeonetech.apptest.utils.Utils;

import java.util.ArrayList;
import java.util.List;


public class GraphicScreen extends AppCompatActivity {
    private AnyChartView anyChartView;
    private Button btVoltar;
    private Context context = this;
    DataBaseHelper myDB;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphic_screen);

        myDB = new DataBaseHelper(context);

        /**Initi components*/
        initComponents();

        /**Set and initi value in the graph*/
        setColumnGraph();

        /**Event Clicks*/
        eventClicks();
    }

    private void eventClicks() {
        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent calculateScreen = new Intent(context, CalculateScreen.class);
                startActivity(calculateScreen);
                finish();
            }
        });
    }

    private void setColumnGraph() {
        Cartesian cartesian = AnyChart.column();

        List<DataEntry> data = new ArrayList<>();
        Cursor res = myDB.getAllDataControleTable();
        while (res.moveToNext()) {

            double value = Double.parseDouble(Utils.formatterRegexDot(res.getString(2)));
            String date = res.getString(3);

            data.add(new ValueDataEntry(date, value));
        }

//        data.add(new ValueDataEntry("tst", 66.00));

        Column column = cartesian.column(data);

        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("R${%Value}{groupsSeparator: }");

        cartesian.animation(true);
        cartesian.title("Faturas nos últimos 6 meses.");

        //Exibir Grid de fundo
        //cartesian.xMinorGrid(true);
        //cartesian.yMinorGrid(true);

//        cartesian.

        cartesian.yScale().minimum(0d);

        cartesian.yAxis(0).labels().format("R${%Value}{groupsSeparator: }");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);


//        cartesian.xAxis(0).title("Product");
//        cartesian.yAxis(0).title("Revenue");

        anyChartView.setChart(cartesian);

    }

    private void initComponents() {
        anyChartView = findViewById(R.id.myColumnGraph);
        btVoltar = findViewById(R.id.btVoltarGraph);
    }
}
