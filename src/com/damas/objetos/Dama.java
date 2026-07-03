package com.damas.objetos;

//Representa uma Dama e implementa somente suas regras específicas;
public class Dama extends PecaAbstrata {

    public Dama(Casa casa, CorPeca cor) {
        super(casa, cor);
    }

    //A Dama pode andar várias casas, desde que continue na diagonal;
    @Override
    public boolean isMovimentoValido(Casa destino) {
        int distanciaX = Math.abs(destino.getX() - casa.getX());
        int distanciaY = Math.abs(destino.getY() - casa.getY());

        return distanciaX == distanciaY;
    }

    //Sem captura a Dama pode andar em qualquer sentido da diagonal;
    @Override
    public boolean podeMoverSemCaptura(Casa destino) {
        return isMovimentoValido(destino);
    }

    @Override
    public TipoPeca getTipo() {
        return TipoPeca.DAMA;
    }
}
