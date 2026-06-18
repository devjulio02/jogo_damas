package com.damas.objetos;

// Representa uma pedra comum.
public class Pedra extends PecaAbstrata {

    public Pedra(Casa casa, CorPeca cor) {
        super(casa, cor);
    }

    @Override
    public boolean isMovimentoValido(Casa destino) {
        int distanciaX = Math.abs(destino.getX() - casa.getX());
        int distanciaY = Math.abs(destino.getY() - casa.getY());

        if (distanciaX == 0 || distanciaY == 0) return false;

        return distanciaX <= 2 && distanciaX == distanciaY;
    }

    @Override
    public boolean podeMoverSemCaptura(Casa destino) {
        int distanciaX = Math.abs(destino.getX() - casa.getX());
        int distanciaY = destino.getY() - casa.getY();

        return distanciaX == 1 && Math.abs(distanciaY) == 1
                && Integer.signum(distanciaY) == cor.getSentidoMovimento();
    }

    @Override
    public TipoPeca getTipo() {
        return TipoPeca.PEDRA;
    }
}
