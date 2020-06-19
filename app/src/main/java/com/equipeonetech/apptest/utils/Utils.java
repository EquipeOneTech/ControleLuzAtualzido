package com.equipeonetech.apptest.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public static void mensagemSucessoLogin(final Context context){
        Toast.makeText(context,"Login com Sucesso.", Toast.LENGTH_SHORT).show();
    }
    public static void mensagemErrorLogin(final Context context){
        Toast.makeText(context,"Error Login", Toast.LENGTH_LONG).show();
    }
    public static void messageUserRegister(final Context context){
        Toast.makeText(context,"Cadastro com sucesso.", Toast.LENGTH_LONG).show();
    }

    public static void messageDynamic(final Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    //criar metodo para formatar
    public static String valueFormatter(double result){
        return String.format("%.2f",result);
    }



    /**
     * @Método para pegar sempre a data atual.
     **/
    public static String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String formatterRegex(String value) {
        return value.replaceAll("[^0-9.]", "").replaceAll("[,]",".");

    }
}
