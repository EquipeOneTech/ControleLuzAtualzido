package com.equipeonetech.apptest.mensagens_tela;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class Utils {

    private Utils() {
    }

    public static void mensagemDadosSalvos(final Context context){
        Toast.makeText (context,"Operação salva com sucesso!", Toast.LENGTH_LONG).show ();
    }
    public static void mensagemDadosFalha(final Context context){
        Toast.makeText (context,"Falha ao salvar no operação!", Toast.LENGTH_LONG).show ();
    }
    public static void mensagemFechandoAlerta (final Context context){
        Toast.makeText(context, "Nenhuma operação foi realizada.", Toast.LENGTH_LONG).show();
    }

    public static void mensagemItemExcluidoSuccess(final Context context){
        Toast.makeText(context, "Item excluido com sucesso.", Toast.LENGTH_LONG).show();
    }

    public static void mensagemNumAnteriorMaiorAtual(final Context context){
        Toast toast = Toast.makeText(context, "O valor anterior não pode ser maior que o valor atual.", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    //criar metodo para formatar
    public static String formatarValor(double result){
        return String.format("%.2f",result);
    }
}
