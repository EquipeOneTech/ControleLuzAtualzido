package com.equipeonetech.apptest;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.BarGraphSeries;


public class GraphicScreen extends AppCompatActivity {

    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphic_screen);
        final GraphView graph = (GraphView) findViewById(R.id.graph);
        /**Initi the components*/
        initComponents();
        graph.setVisibility(View.VISIBLE);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    BarGraphSeries< DataPoint > series = new BarGraphSeries < > (new DataPoint[] {
                            new DataPoint(0,0),
                            new DataPoint(1, 5),
                            new DataPoint(2, 3),
                            new DataPoint(3, 2),
                            new DataPoint(4, 10),
                            new DataPoint(5, 8),
                            new DataPoint(6, 35),

                    });

                    graph.addSeries(series);

                    

                    // activate horizontal zooming and scrolling
                    graph.getViewport().setScalable(true);

                    // activate horizontal scrolling
                    graph.getViewport().setScrollable(true);

                    // activate horizontal and vertical zooming and scrolling
                    graph.getViewport().setScalableY(true);

                    // activate vertical scrolling
                    graph.getViewport().setScrollableY(true);

                    double maxX = 7.0;
                    graph.getViewport().setXAxisBoundsManual(true);
                    graph.getViewport().setMaxX(maxX);
                    series.setSpacing(50);
                    series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
                        @Override
                        public int get(DataPoint data) {
                            return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6),100);
                        }
                    });

                    //series.setDrawValuesOnTop(true);
                    series.setValuesOnTopColor(Color.RED);
//                    series.setDataWidth(20.0);
//                    series.setAnimated(true);
//                    series.setSpacing(10);
//                    series.setColor(R.color.colorGreen);


//                    StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
//                    staticLabelsFormatter.setHorizontalLabels(new String[]{"Jan","Fev","Mar","Abr"});

                } catch (IllegalArgumentException e) {
                    Toast.makeText(GraphicScreen.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });




    }

    private void initComponents() {
//        firstNum_1 = findViewById(R.id.firstNum_1);
//        secondNum_1 = findViewById(R.id.secondNum_1);
//        firstNum_2 = findViewById(R.id.firstNum_2);
//        secondNum_2 = findViewById(R.id.secondNum_2);
//        firstNum_3 = findViewById(R.id.firstNum_3);
//        secondNum_3 = findViewById(R.id.secondNum_3);
//        firstNum_4 = findViewById(R.id.firstNum_4);
//        secondNum_4 = findViewById(R.id.secondNum_4);

        button = findViewById(R.id.addButton);
    }
}
