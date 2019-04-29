package com.example.medidor;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medidor.calculo.Calcular;
import com.example.medidor.dataBase.DataBaseHelper;
import com.example.medidor.mensagens_tela.Mensagem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Principal extends AppCompatActivity {
    private Button btCalcular, btConsultar, btExcluir;
    public static EditText edtMedidaAnterior;
    public static EditText edtMedidaAtual;
    public static TextView txtResultado;
    public static GridView gridView;
    private Context ctx = this;
    DataBaseHelper myDB;

    /**Instancia da classe Mensagem, para mensagens TOAST*/
    final Mensagem mensagem = new Mensagem (this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new DataBaseHelper(ctx);



        /**Declarando elementos em tela (Botões, textView, caixas de textos)*/
        btCalcular = (Button)findViewById(R.id.btCalcular);
        btConsultar = (Button)findViewById (R.id.btConsultarDados);
        btExcluir = (Button)findViewById (R.id.btExcluir);
        edtMedidaAnterior = (EditText)findViewById(R.id.edtMedidaAnterior);
        edtMedidaAtual = (EditText)findViewById(R.id.edtMedidaAtual);
        txtResultado = (TextView)findViewById(R.id.txtResultado);
        gridView = (GridView)findViewById (R.id.GridView);

        /**Ação Click do Button Calcular*/
        btCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**Testando se algum campo está vazio antes do calculo*/
                if((edtMedidaAnterior.getText().length ()!=0) && (edtMedidaAtual.getText().length ()!=0)){
                    txtResultado.setText ("Estimativa a pagar: R$ " + Calcular.calculando());
                    salvandoOperacao ();
                }else{
                    mensagem.mensagemCampoNull();
                    txtResultado.setText("");
                }

            }
        });

        /**Ação Click do Button Consultar Outros Meses*/
        btConsultar.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                consultar ();
            }
        });

        btExcluir.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                listaDialog ();
            }
        });
    }



    /**Método que resgata o valor dos campos e salva no banco (insert).*/
    private void salvandoOperacao(){
        String medidaAtual = Calcular.numAtual.toString ();
        String precoEstimado = String.valueOf (Calcular.calculando ());
        String data = getDateTime();
        Boolean result = myDB.insertData(medidaAtual, precoEstimado,data);

        /**Testando retorno do método pra validar que salvou com sucesso.*/
        if(result == true){
            mensagem.mensagemDadosSalvos();
        }else{
            mensagem.mensagemDadosFalha();
        }
    }

    /**Método que lista todos registros do banco na GridView.*/
    private void consultar(){
        Cursor res = myDB.getAllData();
        ArrayList<String> arrayList = new ArrayList<> ();
            while (res.moveToNext ()){
                arrayList.add (" ("+res.getString (0)+") "+res.getString (1));/**Campo id + ultima medida do medidor*/
                arrayList.add (res.getString (2));/**Campo valor estimado*/
                arrayList.add (res.getString (3));/**Campo data*/
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<> (this,android.R.layout.simple_list_item_1,arrayList);
            gridView.setAdapter (adapter);
    }

    /**Método para retornar sempre a data atual.*/
    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    /**Método para listar registros do banco em um alerta,
     * para selecionar o que o usuario quiser excluir.*/
    private void listaDialog(){
        Cursor res = myDB.getAllData();
        CharSequence[] charSequences = new CharSequence[0];

        ArrayList<String> arrayList = new ArrayList<> ();
        while(res.moveToNext()) {
//            String id = res.getString(0);
//            String data = res.getString(3);
//            arrayList.add (data);
//            String[] dados = new String[]{String.valueOf (arrayList)};
//            final List<String> todasDatas = Arrays.asList (dados);
            charSequences = new CharSequence[]{String.valueOf (res.getString (3)),"bla bla bla"};

        }
        System.out.println("-----------------------------------"+arrayList+"---------------------------------------------");


        final boolean[] checados = new boolean[charSequences.length];

        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle("Selecione para fazer nada.");
        builder.setMultiChoiceItems(charSequences, checados, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                checados[which] = isChecked;
            }
        });

        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StringBuilder texto = new StringBuilder("Excluidos: ");
                for(boolean ch : checados){
                    texto.append(ch).append("; ");
                }
                Toast.makeText(ctx, texto.toString(), Toast.LENGTH_LONG).show();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }
}
