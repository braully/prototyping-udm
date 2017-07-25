package com.github.braully.sak.report;

/**
 *
 * @author braullyrocha
 */
public class Value {

    String name, value;

    public Value() {
    }

    public Value(String nome, String valor) {
        this.name = nome;
        this.value = valor;
    }

    public Value(Detail det, Object valor) {
        this.name = det.getName();
        if (valor != null) {
            this.value = det.format(valor);
        } else {
            valor = "";
        }
    }

    public String getNome() {
        return name;
    }

    public void setNome(String nome) {
        this.name = nome;
    }

    public String getValor() {
        return value;
    }

    public void setValor(String valor) {
        this.value = valor;
    }
}
