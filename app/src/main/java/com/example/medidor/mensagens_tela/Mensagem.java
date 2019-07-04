package com.example.medidor.mensagens_tela;

import android.content.Context;
import android.widget.Toast;

public class Mensagem {
    Context context;

    public Mensagem(Context context){
        this.context = context;
    }

    public void mensagemDadosSalvos(){
        Toast.makeText (this.context,"Operação salva com sucesso!", Toast.LENGTH_LONG).show ();
    }
    public void mensagemDadosFalha(){
        Toast.makeText (this.context,"Falha ao salvar no operação!", Toast.LENGTH_LONG).show ();
    }
    public void mensagemFechandoAlerta (){
        Toast.makeText(this.context, "Nenhuma operação foi realizada.", Toast.LENGTH_LONG).show();
    }

    public void mensagemItemExcluidoSucess(){
        Toast.makeText(this.context, "Item excluido com sucesso.", Toast.LENGTH_LONG).show();
    }

    public void mensagemNumAnteriorMaiorAtual(){
        Toast.makeText(this.context, "O valor anterior não pode ser maior que o valor atual.", Toast.LENGTH_LONG).show();
    }
}
