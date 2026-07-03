package com.damas.objetos;

//Identifica o tipo da peça e se ela ainda pode ser promovida;
public enum TipoPeca {
    PEDRA(true),
    DAMA(false);

    private boolean podeSerPromovida;

    TipoPeca(boolean podeSerPromovida) {
        this.podeSerPromovida = podeSerPromovida;
    }

    public boolean podeSerPromovida() {
        return podeSerPromovida;
    }
}
