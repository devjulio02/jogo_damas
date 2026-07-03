package com.damas.objetos;

import java.util.List;

//Contrato comum das peças do jogo;
public interface Peca {

    void mover(Casa destino);

    boolean isMovimentoValido(Casa destino);

    boolean podeMoverSemCaptura(Casa destino);

    boolean simularMovimentoEValidar(Tabuleiro tabuleiro, Casa destino, List<Casa> pecasAComer);

    boolean deveContinuarJogando(Tabuleiro tabuleiro);

    CorPeca getCor();

    TipoPeca getTipo();
}
