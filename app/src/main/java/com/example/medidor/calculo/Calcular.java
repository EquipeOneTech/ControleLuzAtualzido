package com.example.medidor.calculo;

import com.example.medidor.Principal;

public class Calcular {
    public Integer numAnterior, numAtual;
    public double resultado, consumo;
    public double custoNormal = 0.6597;
    public double custoMaior = 0.84246;

    public Integer getNumAnterior() {
        return numAnterior;
    }

    public void setNumAnterior(Integer numAnterior) {
        this.numAnterior = numAnterior;
    }

    public Integer getNumAtual() {
        return numAtual;
    }

    public void setNumAtual(Integer numAtual) {
        this.numAtual = numAtual;
    }

    public double calculando() {

        /**Calculando consumo para KWh(quilo watt / hora)*/
        consumo = (getNumAtual () - getNumAnterior ()) * 1;

        if (consumo <= 50) {
            return resultado = consumo * custoNormal;

        } else {
            return  resultado = consumo * custoMaior;
        }

    }
}
