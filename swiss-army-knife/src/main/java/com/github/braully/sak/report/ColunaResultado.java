package com.github.braully.sak.report;

import java.io.Serializable;

@SuppressWarnings("rawtypes")
public class ColunaResultado implements Serializable {

    private static final long serialVersionUID = 1L;
    private String nome;
    private String propriedade;
    private Class classe;
    private Integer tamanho;
    private int ordem;

    public ColunaResultado(int i, String col, String prop, Class classe) {
        this.nome = col;
        this.propriedade = prop;
        this.setOrdem(i);
        this.classe = classe;
    }

    public ColunaResultado(int i, String col, String prop) {
        this(i, col, prop, null);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPropriedade() {
        return propriedade;
    }

    public void setPropriedade(String propriedade) {
        this.propriedade = propriedade;
    }

    public Class getClasse() {
        return classe;
    }

    public void setClasse(Class classe) {
        this.classe = classe;
    }

    public Integer getTamanho() {
        return tamanho;
    }

    public void setTamanho(Integer tamanho) {
        this.tamanho = tamanho;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!getClass().isAssignableFrom(obj.getClass())) {
            return false;
        }
        ColunaResultado other = (ColunaResultado) obj;
        if (nome == null) {
            if (other.nome != null) {
                return false;
            }
        } else if (!nome.equals(other.nome)) {
            return false;
        }
        return true;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    public int getOrdem() {
        return ordem;
    }
}
