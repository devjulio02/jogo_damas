package com.damas.objetos;

// Centraliza o estado e o comportamento realmente comum das peças.
public abstract class PecaAbstrata implements Peca {

    protected Casa casa;
    protected CorPeca cor;

    public PecaAbstrata(Casa casa, CorPeca cor) {
        this.casa = casa;
        this.cor = cor;
        casa.colocarPeca(this);
    }

    @Override
    public void mover(Casa destino) {
        casa.removerPeca();
        destino.colocarPeca(this);
        casa = destino;
    }

    @Override
    public CorPeca getCor() {
        return cor;
    }
}
