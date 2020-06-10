package com.equipeonetech.apptest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.BarGraphSeries;


public class GraphicScreen extends AppCompatActivity {
    EditText firstNum_1, secondNum_1;
    EditText firstNum_2, secondNum_2;
    EditText firstNum_3, secondNum_3;
    EditText firstNum_4, secondNum_4;
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
                String firstInput_1, secondInput_1;
                String firstInput_2, secondInput_2;
                String firstInput_3, secondInput_3;
                String firstInput_4, secondInput_4;
                //  1 and 5
//                firstInput_1 = firstNum_1.getText().toString();
//                secondInput_1 = secondNum_1.getText().toString();
//                firstInput_2 = firstNum_2.getText().toString();
//                secondInput_2 = secondNum_2.getText().toString();
//                firstInput_3 = firstNum_3.getText().toString();
//                secondInput_3 = secondNum_3.getText().toString();
//                firstInput_4 = firstNum_4.getText().toString();
//                secondInput_4 = secondNum_4.getText().toString();
                try {
                    BarGraphSeries< DataPoint > series = new BarGraphSeries < > (new DataPoint[] {
                            new DataPoint(0,5),
                            new DataPoint(Integer.valueOf(1), Integer.valueOf(2)),
                            new DataPoint(Integer.valueOf(3), Integer.valueOf(4)),
                            new DataPoint(Integer.valueOf(5), Integer.valueOf(6)),
                            new DataPoint(Integer.valueOf(7), Integer.valueOf(8))
                    });
//                    series.setTitle("test1");
////                    series.setDataWidth(20.0);
////                    series.setAnimated(true);
////                    series.setSpacing(10);
////                    series.setColor(R.color.colorGreen);
                    graph.addSeries(series);

//                    StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
//                    staticLabelsFormatter.setHorizontalLabels(new String[]{"Jan","Fev","MAR","ABR"});

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
