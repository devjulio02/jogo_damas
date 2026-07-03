package com.damas.objetos;

//Representa a cor, o sentido do movimento e a linha de promoção;
public enum CorPeca {
    BRANCA(1, 7),
    VERMELHA(-1, 0);

    private int sentidoMovimento;
    private int linhaPromocao;

    CorPeca(int sentidoMovimento, int linhaPromocao) {
        this.sentidoMovimento = sentidoMovimento;
        this.linhaPromocao = linhaPromocao;
    }

    public int getSentidoMovimento() {
        return sentidoMovimento;
    }

    public boolean chegouNaLinhaPromocao(int linha) {
        return linha == linhaPromocao;
    }
}
