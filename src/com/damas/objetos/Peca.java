package com.damas.objetos;

// Contrato comum das peças do jogo.
public interface Peca {

    void mover(Casa destino);

    boolean isMovimentoValido(Casa destino);

    boolean podeMoverSemCaptura(Casa destino);

    CorPeca getCor();

    TipoPeca getTipo();
}
