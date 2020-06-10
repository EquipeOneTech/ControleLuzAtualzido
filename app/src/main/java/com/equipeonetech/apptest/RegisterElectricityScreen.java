package com.equipeonetech.apptest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.equipeonetech.apptest.dataBase.DataBaseHelper;
import com.equipeonetech.apptest.utils.MaskEditUtils;
import com.equipeonetech.apptest.utils.Utils;
import java.util.Calendar;


public class RegisterElectricityScreen extends AppCompatActivity {

    private Button btCadastraConta, btVoltarCad;
    private EditText edtValorConta, edtDate;
    private Context context = this;
    private DatePicker datePicker;
    DataBaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_electricity_screen);
        /**Init Database*/
        myDB = new DataBaseHelper(context);

        /**Init the Components*/
        initComponents();

        /**init date field*/
        initDate();

        /**Event click*/
        eventClick();

        /**Add mask on field (000,000,000,)*/
        maskField();

    }

    private void maskField() {
        edtValorConta.addTextChangedListener(MaskEditUtils.mask(edtValorConta, MaskEditUtils.FORMAT_MONEY));
    }

    private void initDate() {
        Calendar calendar = Calendar.getInstance();
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                edtDate.setText(datePicker.getDayOfMonth()+"/"+(datePicker.getMonth()+1)+"/"+datePicker.getYear());
            }
        });
    }

    private void eventClick() {
        btCadastraConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edtDate.getText().toString().isEmpty() && !edtValorConta.getText().toString().isEmpty()){
                    saveData();
                }else {
                    edtDate.setError("insira uma data");
                    edtValorConta.setError("insira um valor");
                }
            }
        });

        btVoltarCad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent calculateScreen = new Intent(context, CalculateScreen.class);
                startActivity(calculateScreen);
                finish();
            }
        });
    }

    private void initComponents() {
        datePicker = findViewById(R.id.datePicker);
        btCadastraConta = findViewById(R.id.btCadastrarConta);
        btVoltarCad = findViewById(R.id.btVoltarCad);
        edtValorConta = findViewById(R.id.edtValorContaCad);
        edtDate = findViewById(R.id.edtDate);
    }

    private void saveData(){
        String medida = "-";
        String valor = edtValorConta.getText().toString();
        String date = edtDate.getText().toString();
        Boolean result = myDB.insertDataControleTable(medida, valor, date);
        /**Testando retorno do método pra validar que salvou com sucesso.*/
        if(result == true){
            Utils.mensagemDadosSalvos(context);
        }else{
            Utils.mensagemDadosFalha(context);
        }
    }

}
