package com.equipeonetech.apptest;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.equipeonetech.apptest.dataBase.DataBaseHelper;
import com.equipeonetech.apptest.utils.MaskEditUtils;
import com.equipeonetech.apptest.utils.Utils;

public class ConfigScreen extends AppCompatActivity {
    private EditText edtValorRecomendado;
    private Button btConfirmarValor;
    private Context context = this;
    private DataBaseHelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_screen);


        /**Inicia Componentes*/
        initComponent();
        edtValorRecomendado.addTextChangedListener(MaskEditUtils.mask(edtValorRecomendado, MaskEditUtils.FORMAT_MONEY));

        /**Event Clicks*/
        eventClicks();
        myDB = new DataBaseHelper(context);
    }

    private void eventClicks() {
        btConfirmarValor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valorRecomend = edtValorRecomendado.getText().toString();
                String data = Utils.getDateTime();
                if(valorRecomend.length()>0) {
                    Boolean result = myDB.insertDataRecomendTable(valorRecomend, data);

                    /**Testando retorno do método pra validar que salvou com sucesso.*/
                    if (result == true) {
                        Utils.mensagemDadosSalvos(context);
                        Intent calculateScreen = new Intent(context, CalculateScreen.class);
                        startActivity(calculateScreen);
                        finish();
                    } else {
                        Utils.mensagemDadosFalha(context);
                    }
                }
            }
        });
    }

    private void initComponent() {
        edtValorRecomendado = (EditText)findViewById(R.id.edtValorRecomendado);
        btConfirmarValor = (Button)findViewById(R.id.btConfirmarValor);
    }
}
