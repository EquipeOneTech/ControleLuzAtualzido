package com.example.medidor.calculo;

import com.example.medidor.Principal;

public class Calcular {
    public static Integer numAnterior, numAtual;
    public static double resultado, consumo;
    public static double custoNormal = 0.6597;
    public static double custoMaior = 0.84246;

    public static double calculando() {

        /**Recebendo valores e convertendo para inteiro*/
        numAnterior = Integer.parseInt(Principal.edtMedidaAnterior.getText().toString());
        numAtual = Integer.parseInt(Principal.edtMedidaAtual.getText().toString());

        /**Calculando consumo para KWh(quilo watt / hora)*/
        consumo = (numAtual - numAnterior) * 1;

        if (consumo <= 50) {
            return resultado = consumo * custoNormal;

        } else {
            return  resultado = consumo * custoMaior;
        }

    }
}
