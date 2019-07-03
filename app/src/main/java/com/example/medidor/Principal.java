package com.example.medidor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medidor.calculo.Calcular;
import com.example.medidor.dataBase.DataBaseHelper;
import com.example.medidor.mensagens_tela.Mensagem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Felipe Coelho
 * @email equipeonetech@gmail.com
 * */

public class Principal extends AppCompatActivity {
    private Button btCalcular, btConsultar;
    public  EditText edtMedidaAnterior;
    public  EditText edtMedidaAtual;
    public  ListView listView;
    private Context ctx = this;
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> adapter;
    DataBaseHelper myDB;
    Calcular calcular;
    Mensagem mensagem;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new DataBaseHelper(ctx);
        calcular = new Calcular ();
        mensagem = new Mensagem (ctx);

        /**Declarando elementos em tela (Botões, textView, caixas de textos)*/
        btCalcular = (Button)findViewById(R.id.btCalcular);
        btConsultar = (Button)findViewById (R.id.btConsultarDados);
        edtMedidaAnterior = (EditText)findViewById(R.id.edtMedidaAnterior);
        edtMedidaAtual = (EditText)findViewById(R.id.edtMedidaAtual);
        listView = (ListView)findViewById (R.id.listView);

        registerForContextMenu (listView);
        consultar();



        /**
         * @Ação Click do Button Calcular
         **/
        btCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**Testando se algum campo está vazio antes do calculo*/
                if((edtMedidaAnterior.getText().length ()!=0) && (edtMedidaAtual.getText().length ()!=0)){
                    int numAnterior = Integer.parseInt (edtMedidaAnterior.getText().toString ());
                    int numAtual =Integer.parseInt (edtMedidaAtual.getText ().toString ());

                            /**@RN001- O campo Medida Anterior não pode ser maior ou igual o campo Medida Atual.*/
                            if(numAnterior >= numAtual){
                                mensagem.mensagemNumAnteriorMaiorAtual ();
                            }else{
                                /**regatando valores para realizar calculo*/
                                calcular.setNumAnterior (numAnterior);
                                calcular.setNumAtual (numAtual);
                                alertResultado (calcular.calculando ());
                            }

                }else{
                    mensagem.mensagemCampoNull();
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
        String precoEstimado = String.valueOf (calcular.calculando ());
        String data = getDateTime();
        Boolean result = myDB.insertData(medidaAtual, precoEstimado,data);

        /**Testando retorno do método pra validar que salvou com sucesso.*/
        if(result == true){
            mensagem.mensagemDadosSalvos();
        }else{
            mensagem.mensagemDadosFalha();
        }
    }

    /**
     * @Método que lista todos registros do banco na @ListView.
     **/
    private void consultar(){
        Cursor res = myDB.getAllData();
        arrayList = new ArrayList<> ();
            while (res.moveToNext ()){
                arrayList.add (" ("+res.getString (0)+") "+res.getString (1)+" - "+res.getString (2)+" - "+res.getString (3));
            }
            adapter = new ArrayAdapter<> (this,android.R.layout.simple_list_item_1,arrayList);//simple_list_item_multiple_choice
            listView.setAdapter (adapter);
            //listView.setChoiceMode (ListView.CHOICE_MODE_MULTIPLE);
    }

    private void excluirItem(String idItem){
        mensagem.mensagemItemExcluidoSucess();
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
        builder.setMessage("Estimativa a pagar: R$ "+ resultado);

        builder.setPositiveButton ("Sim", new DialogInterface.OnClickListener () {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                salvandoOperacao ();
            }
        });
        builder.setNegativeButton ("Não, fechar", new DialogInterface.OnClickListener () {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mensagem.mensagemFechandoAlerta (); 
            }
        });
        AlertDialog alertDialog = builder.create ();
        alertDialog.show ();
    }

    /**
     * @Método Cria Menu (pop-up) opção 'Excluir' item da lista
     **/
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.listView) {
            menu.add("Excluir");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case 0:
                arrayList.remove(info.position);
                adapter.notifyDataSetChanged();
        }
        return true;
    }

}
