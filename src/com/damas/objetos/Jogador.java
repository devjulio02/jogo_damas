package com.damas.objetos;

// Representa um jogador, sua cor e sua pontuação.
public class Jogador {

    public static final String DEFAULT_NAME = "Anônimo";

    private String nome;
    private int pontos;
    private CorPeca cor;

    public Jogador(String nome, CorPeca cor) {
        if (validarNome(nome)) {
            this.nome = nome;
        } else {
            this.nome = DEFAULT_NAME;
        }

        this.cor = cor;
        pontos = 0;
    }

    private boolean validarNome(String nome) {
        if (nome.length() > 16) return false;
        return true;
    }

    public boolean controla(Peca peca) {
        return peca.getCor() == cor;
    }

    public void addPonto() {
        pontos++;
    }

    public void addPonto(int pontos) {
        this.pontos += pontos;
    }

    public int getPontos() {
        return pontos;
    }

    public String getNome() {
        return nome;
    }

    public CorPeca getCor() {
        return cor;
    }

    public void setNome(String nome) {
        validarNome(nome);
    }
}
