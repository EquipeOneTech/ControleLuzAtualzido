package com.equipeonetech.apptest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.equipeonetech.apptest.utils.Utils;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

/**
 * @author Felipe Coelho
 * @email equipeonetech@gmail.com
 * */

public class CalculateScreen extends AppCompatActivity {
    private Button btCalcular, btConsultar;
    private ImageButton btConfigScreen;
    private TextView txtViewGraphic,txtValorRecommend, txtvalorMes;
    public  EditText edtMedidaAnterior;
    public  EditText edtMedidaAtual;
    public  ListView listView;
    private Context context = this;
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

        /**Declarando elementos em tela (Botões, textView, caixas de textos)*/
        initComponents();

        /**Inicia Menu Options*/
    //    initiMenuOptions();


        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        //Others Screens
        final Intent configScreen = new Intent (this, ConfigScreen.class);
        final Intent graphicScreen = new Intent(this, GraphicScreen.class);

        myDB = new DataBaseHelper(context);
        calcular = new Calcular ();

        //getvalor recomendado test
    //    setValorRecomendado();



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
                                Utils.mensagemNumAnteriorMaiorAtual(context);
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


        /**Inicia valor recomendado*/
        initValueRecommend();



    }

    /**Verificar valores e setar cor no texto*/
    @SuppressLint("ResourceAsColor")
    private void setColorValue(String currentValue, String recommendValue){
        if (!currentValue.isEmpty() && !recommendValue.isEmpty()) {
            float valueScreen = Integer.parseInt(Utils.formatterRegex(currentValue));
            float recommendvlue = Integer.parseInt(Utils.formatterRegex(recommendValue));
            if (valueScreen > recommendvlue) {
                txtvalorMes.setTextColor(getResources().getColor(R.color.colorRed, getResources().newTheme()));
            }else{
                txtvalorMes.setTextColor(getResources().getColor(R.color.colorGreen, getResources().newTheme()));
            }
        }else {
            txtvalorMes.setTextColor(getResources().getColor(R.color.colorBlack, getResources().newTheme()));
        }


    }

    private void initValueRecommend() {
        Cursor res = myDB.getAllDataRecomendTable();
        StringBuffer stringBuffer = new StringBuffer();
        if(res != null && res.getCount()>0) {
            int cont = 0;
            int v = res.getCount();
            while (res.moveToNext()) {
                if((v-1)==cont) {
                    stringBuffer.append(res.getString(1));
                }
                cont++;
            }
        }
        txtValorRecommend.setText(stringBuffer.toString());
        setColorValue(txtvalorMes.getText().toString(), txtValorRecommend.getText().toString());


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itProfile:
                Intent profileScreen = new Intent(context, ProfileScreen.class);
                startActivity(profileScreen);
                finish();
                return true;
            case R.id.itHelp:
                Utils.messageDynamic(context,"Em testes.");
                return true;
            case R.id.itLimparValorRecommend:
                clearValueRecommend();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void clearValueRecommend() {
        myDB.deleteAllValueRecommend();
        Intent calculateScreen = new Intent(this, CalculateScreen.class);
        startActivity(calculateScreen);
        Utils.messageDynamic(context, "Valor recomendado excluido.");
    }

    private void initComponents() {
        btConfigScreen = (ImageButton)findViewById(R.id.btConfigLuz);
        txtViewGraphic = (TextView)findViewById(R.id.txtVerGraficos);
        btCalcular = (Button)findViewById(R.id.btCalcular);
        btConsultar = (Button)findViewById (R.id.btConsultarDados);
        edtMedidaAnterior = (EditText)findViewById(R.id.edtMedidaAnterior);
        edtMedidaAtual = (EditText)findViewById(R.id.edtMedidaAtual);
        txtValorRecommend= (TextView)findViewById(R.id.txtValorRecommend);
        txtvalorMes = (TextView)findViewById(R.id.valorMes);
    }


    /**
     * @Método que resgata o valor dos campos e salva no banco (insert).
     **/
    private void salvandoOperacao(){
        String medidaAtual = calcular.getNumAtual().toString ();
        String precoEstimado = Utils.valueFormatter(calcular.calculando ());
        String data = Utils.getDateTime();
        Boolean result = myDB.insertDataControleTable(medidaAtual, precoEstimado,data);

        /**Testando retorno do método pra validar que salvou com sucesso.*/
        if(result == true){
            Utils.mensagemDadosSalvos(context);
        }else{
            Utils.mensagemDadosFalha(context);
        }
    }

    /**
     * @Método que lista todos registros do banco na @ListView.
     **/
    private void consultar(){
        Cursor res = myDB.getAllDataControleTable();
        arrayList = new ArrayList<> ();
            while (res.moveToNext ()){
                arrayList.add (/*" ("+res.getString (0)+") "+*/res.getString (1)+" - "+res.getString (2)+" - "+res.getString (3));
            }
            adapter = new ArrayAdapter<> (this,android.R.layout.simple_list_item_1,arrayList);//simple_list_item_multiple_choice
//            listView.setAdapter (adapter);
            //listView.setChoiceMode (ListView.CHOICE_MODE_MULTIPLE);
    }

    private void excluirItem(String idItem){
        Utils.mensagemItemExcluidoSuccess(context);
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
                Utils.mensagemFechandoAlerta (context);
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
