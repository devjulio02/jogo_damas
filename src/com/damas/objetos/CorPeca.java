package com.damas.objetos;

// Representa a cor e o sentido normal de movimento da peça.
public enum CorPeca {
    BRANCA(1),
    VERMELHA(-1);

    private int sentidoMovimento;

    CorPeca(int sentidoMovimento) {
        this.sentidoMovimento = sentidoMovimento;
    }

    public int getSentidoMovimento() {
        return sentidoMovimento;
    }
}
