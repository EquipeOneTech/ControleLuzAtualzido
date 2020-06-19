package com.equipeonetech.apptest;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;

import java.util.ArrayList;
import java.util.List;


public class GraphicScreen extends AppCompatActivity {
    private AnyChartView anyChartView;
    private Button btVoltar;
    private Context context = this;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphic_screen);

        /**Initi components*/
        initComponents();

        /**Set and initi value in the graph*/
        setColumnGraph();
   }

    private void setColumnGraph() {
        Cartesian cartesian = AnyChart.column();

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Jan", 289.2));
        data.add(new ValueDataEntry("Fev", 285.11));
        data.add(new ValueDataEntry("Mar", 280.44));
        data.add(new ValueDataEntry("Abr", 325.14));
        data.add(new ValueDataEntry("Mai", 247.20));
        data.add(new ValueDataEntry("Jun", 246.88));

        Column column = cartesian.column(data);

        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("${%Value}{groupsSeparator: }");

        cartesian.animation(true);
        cartesian.title("Faturas nos últimos 6 meses.");


        cartesian.yScale().minimum(0d);

        cartesian.yAxis(0).labels().format("${%Value}{groupsSeparator: }");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);



//        cartesian.xAxis(0).title("Product");
//        cartesian.yAxis(0).title("Revenue");

        anyChartView.setChart(cartesian);

    }

    private void initComponents() {
        anyChartView = findViewById(R.id.myColumnGraph);
        btVoltar = findViewById(R.id.btVoltar);
    }
}
