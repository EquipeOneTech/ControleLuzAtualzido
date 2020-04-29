package com.equipeonetech.apptest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.equipeonetech.apptest.calculo.Calcular;
import com.equipeonetech.apptest.dataBase.DataBaseHelper;
import com.equipeonetech.apptest.mensagens_tela.Utils;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Felipe Coelho
 * @email equipeonetech@gmail.com
 * */

public class CalculateScreen extends AppCompatActivity {
    private Button btCalcular, btConsultar;
    private ImageButton btConfigScreen;
    private TextView txtViewGraphic;
    public  EditText edtMedidaAnterior;
    public  EditText edtMedidaAtual;
    public  ListView listView;
    private Context ctx = this;
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> adapter;
    DataBaseHelper myDB;
    Calcular calcular;
    private FirebaseAnalytics mFirebaseAnalytics;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate);
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        //Others Screens
        final Intent configScreen = new Intent (this, ConfigScreen.class);
        final Intent graphicScreen = new Intent(this, GraphicScreen.class);

        myDB = new DataBaseHelper(ctx);
        calcular = new Calcular ();

        /**Declarando elementos em tela (Botões, textView, caixas de textos)*/
        btConfigScreen = (ImageButton)findViewById(R.id.btConfigLuz);
        txtViewGraphic = (TextView)findViewById(R.id.txtVerGraficos);
        btCalcular = (Button)findViewById(R.id.btCalcular);
        btConsultar = (Button)findViewById (R.id.btConsultarDados);
        edtMedidaAnterior = (EditText)findViewById(R.id.edtMedidaAnterior);
        edtMedidaAtual = (EditText)findViewById(R.id.edtMedidaAtual);

        /**
         * @Action Click for TextView open the Graphics Screen
         * **/
        txtViewGraphic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(graphicScreen);
            }
        });

        /**
         * @Action Click for Button open the Config Screen
         * **/
        btConfigScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(configScreen);
            }
        });


        /**
         * @Action Click do Button Calcular
         **/
        btCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**Testando se algum campo está vazio antes do calculo*/
                if((edtMedidaAnterior.getText().length() !=0) && (edtMedidaAtual.getText().length ()!=0)){
                    int numAnterior = Integer.parseInt (edtMedidaAnterior.getText().toString ());
                    int numAtual =Integer.parseInt (edtMedidaAtual.getText ().toString ());

                            /**@RN001- O campo Medida Anterior não pode ser maior ou igual o campo Medida Atual.*/
                            if(numAnterior >= numAtual){
                                Utils.mensagemNumAnteriorMaiorAtual(ctx);
                                edtMedidaAnterior.setError("Valor inválido.");
                                edtMedidaAtual.setError("Valor inválido.");
                            }else{
                                /**regatando valores para realizar calculo*/
                                calcular.setNumAnterior (numAnterior);
                                calcular.setNumAtual (numAtual);
                                alertResultado (calcular.calculando ());
                            }

                }else{
                    edtMedidaAnterior.setError("Preencha o campo!");
                    edtMedidaAtual.setError("Preencha o campo!");
                }

            }
        });

        /**
         * @Ação Click do Button Consultar Outros Meses
         **/
        btConsultar.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                consultar ();
            }
        });
    }


    /**
     * @Método que resgata o valor dos campos e salva no banco (insert).
     **/
    private void salvandoOperacao(){
        String medidaAtual = calcular.getNumAtual().toString ();
        String precoEstimado = Utils.formatarValor(calcular.calculando ());
        String data = getDateTime();
        Boolean result = myDB.insertData(medidaAtual, precoEstimado,data);

        /**Testando retorno do método pra validar que salvou com sucesso.*/
        if(result == true){
            Utils.mensagemDadosSalvos(ctx);
        }else{
            Utils.mensagemDadosFalha(ctx);
        }
    }

    /**
     * @Método que lista todos registros do banco na @ListView.
     **/
    private void consultar(){
        Cursor res = myDB.getAllData();
        arrayList = new ArrayList<> ();
            while (res.moveToNext ()){
                arrayList.add (/*" ("+res.getString (0)+") "+*/res.getString (1)+" - "+res.getString (2)+" - "+res.getString (3));
            }
            adapter = new ArrayAdapter<> (this,android.R.layout.simple_list_item_1,arrayList);//simple_list_item_multiple_choice
//            listView.setAdapter (adapter);
            //listView.setChoiceMode (ListView.CHOICE_MODE_MULTIPLE);
    }

    private void excluirItem(String idItem){
        Utils.mensagemItemExcluidoSuccess(ctx);
    }
    /**
    * @Método para pegar sempre a data atual.
    **/
    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    /**
     * @Método de pop-up apresentando estimativa
     **/
    private void alertResultado(double resultado){
        final AlertDialog.Builder builder = new AlertDialog.Builder (this);
        builder.setTitle("Deseja salvar calculo?");
        builder.setMessage(String.format("Estimativa a pagar: R$ %.2f", resultado));

        builder.setPositiveButton ("Sim", new DialogInterface.OnClickListener () {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                salvandoOperacao ();
                consultar();
                edtMedidaAnterior.setText("");
                edtMedidaAtual.setText("");
            }
        });
        builder.setNegativeButton ("Não, fechar", new DialogInterface.OnClickListener () {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Utils.mensagemFechandoAlerta (ctx);
            }
        });
        AlertDialog alertDialog = builder.create ();
        alertDialog.show ();
    }

    /**
     * @Método Cria Menu (pop-up) opção 'Excluir' item da lista
     **/
//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        if (v.getId() == R.id.listView) {
//            menu.add("Excluir");
//        }
//    }

//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//        switch (item.getItemId()) {
//            case 0:
//                String value = getValueList(info.position);
//                myDB.excluir(value);
//                adapter.notifyDataSetChanged();
//                Utils.mensagemItemExcluidoSuccess(ctx);
//                consultar();
//        }
//        return true;
//    }
//    public String getValueList(int position){
//        String value = arrayList.get(position).substring(0,4);
//        return value.trim();
//    }

}