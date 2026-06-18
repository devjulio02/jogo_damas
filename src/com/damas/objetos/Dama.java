package com.damas.objetos;

// Representa uma dama e sobrescreve apenas o movimento específico.
public class Dama extends PecaAbstrata {

    public Dama(Casa casa, CorPeca cor) {
        super(casa, cor);
    }

    @Override
    public boolean isMovimentoValido(Casa destino) {
        int distanciaX = Math.abs(destino.getX() - casa.getX());
        int distanciaY = Math.abs(destino.getY() - casa.getY());

        return distanciaX == distanciaY;
    }

    @Override
    public boolean podeMoverSemCaptura(Casa destino) {
        return isMovimentoValido(destino);
    }

    @Override
    public TipoPeca getTipo() {
        return TipoPeca.DAMA;
    }
}
