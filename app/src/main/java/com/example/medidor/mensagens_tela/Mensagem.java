package com.example.medidor.mensagens_tela;

import android.content.Context;
import android.widget.Toast;

public class Mensagem {
    Context context;

    public Mensagem(Context context){
        this.context = context;
    }
    public void mensagemCampoNull(){
        Toast.makeText (this.context,"É necessário preencher os campos acima!!", Toast.LENGTH_LONG).show ();
    }
    public void mensagemDadosSalvos(){
        Toast.makeText (this.context,"Operação salva com sucesso!", Toast.LENGTH_LONG).show ();
    }
    public void mensagemDadosFalha(){
        Toast.makeText (this.context,"Falha ao salvar no operação!", Toast.LENGTH_LONG).show ();
    }
}
