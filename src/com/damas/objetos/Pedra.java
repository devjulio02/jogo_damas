package com.damas.objetos;

//Representa uma Pedra e implementa somente suas regras específicas;
public class Pedra extends PecaAbstrata {

    public Pedra(Casa casa, CorPeca cor) {
        super(casa, cor);
    }

    //A Pedra pode andar no máximo duas casas na diagonal;
    @Override
    public boolean isMovimentoValido(Casa destino) {
        int distanciaX = Math.abs(destino.getX() - casa.getX());
        int distanciaY = Math.abs(destino.getY() - casa.getY());

        if (distanciaX == 0 || distanciaY == 0) return false;

        return distanciaX <= 2 && distanciaX == distanciaY;
    }

    //Sem captura a Pedra anda uma casa no sentido definido pela sua cor;
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
