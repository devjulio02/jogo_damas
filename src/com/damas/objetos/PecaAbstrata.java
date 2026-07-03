package com.damas.objetos;

import java.util.ArrayList;
import java.util.List;

//Centraliza o estado comum e o caminho percorrido pelas peças;
//A regra específica do movimento continua em Pedra e Dama;
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

    //Método que antes estava em ValidadorMovimento;
    //A peça conhece sua casa, sua cor e chama sua própria regra de movimento;
    @Override
    public boolean simularMovimentoEValidar(Tabuleiro tabuleiro, Casa destino, List<Casa> pecasAComer) {
        int casasComPecaSeguidas = 0;
        pecasAComer.clear();

        if (destino.getPeca() != null) return false;
        if (!isMovimentoValido(destino)) return false;//Executa a regra de Pedra ou Dama: polimorfismo;

        int sentidoX = destino.getX() - casa.getX();
        int sentidoY = destino.getY() - casa.getY();
        int distanciaX = Math.abs(sentidoX);
        int distanciaY = Math.abs(sentidoY);

        if (distanciaX == 0 || distanciaY == 0 || distanciaX != distanciaY) return false;

        sentidoX = sentidoX / distanciaX;
        sentidoY = sentidoY / distanciaY;

        int x = casa.getX();
        int y = casa.getY();

        while (x != destino.getX() || y != destino.getY()) {
            x += sentidoX;
            y += sentidoY;

            Casa alvo = tabuleiro.getCasa(x, y);
            Peca pecaAlvo = alvo.getPeca();

            if (pecaAlvo != null) {
                casasComPecaSeguidas++;

                if (pecaAlvo.getCor() == cor) {
                    pecasAComer.clear();
                    return false;
                }
            } else {
                if (casasComPecaSeguidas == 1) {
                    Casa casaCapturada = tabuleiro.getCasa(x - sentidoX, y - sentidoY);
                    pecasAComer.add(casaCapturada);
                }

                casasComPecaSeguidas = 0;
            }

            if (casasComPecaSeguidas == 2) {
                pecasAComer.clear();
                return false;
            }
        }

        //Pedra e Dama decidem de formas diferentes se podem andar sem captura;
        if (pecasAComer.isEmpty() && !podeMoverSemCaptura(destino)) return false;

        return true;
    }

    //Método que antes estava em ValidadorMovimento;
    //Percorre uma diagonal e reutiliza a validação da própria peça;
    private boolean percorrerEVerificar(Tabuleiro tabuleiro, int deltaX, int deltaY) {
        int x = casa.getX() + deltaX;
        int y = casa.getY() + deltaY;

        while (tabuleiro.posicaoValida(x, y)) {
            Casa destino = tabuleiro.getCasa(x, y);
            List<Casa> pecasAComer = new ArrayList<Casa>();

            if (simularMovimentoEValidar(tabuleiro, destino, pecasAComer) && !pecasAComer.isEmpty()) {
                return true;
            }

            x += deltaX;
            y += deltaY;
        }

        return false;
    }

    //Método que antes estava em ValidadorMovimento;
    //Verifica se a própria peça ainda possui captura em alguma diagonal;
    @Override
    public boolean deveContinuarJogando(Tabuleiro tabuleiro) {
        int[][] direcoesDiagonais = {
            {-1, 1},
            {1, 1},
            {1, -1},
            {-1, -1}
        };

        for (int[] direcao : direcoesDiagonais) {
            if (percorrerEVerificar(tabuleiro, direcao[0], direcao[1])) return true;
        }

        return false;
    }

    @Override
    public CorPeca getCor() {
        return cor;
    }
}
