package com.damas.objetos;

import java.util.ArrayList;

// Reúne os métodos de validação que antes estavam na classe Jogo.
public class ValidadorMovimento {
    
    private Tabuleiro tabuleiro;
    private ArrayList<Casa> pecasAComer;

    public ValidadorMovimento(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;
        this.pecasAComer = new ArrayList<Casa>();
    }

    public boolean simularMovimentoEValidar(Casa origem, Casa destino) {
        Peca peca = origem.getPeca();
        int casasComPecaSeguidas = 0;

        pecasAComer.clear();

        if (peca == null || destino.getPeca() != null) return false;
        if (!peca.isMovimentoValido(destino)) return false;

        int sentidoX = destino.getX() - origem.getX();
        int sentidoY = destino.getY() - origem.getY();
        int distanciaX = Math.abs(sentidoX);
        int distanciaY = Math.abs(sentidoY);

        if (distanciaX == 0 || distanciaY == 0 || distanciaX != distanciaY) return false;

        sentidoX = sentidoX / distanciaX;
        sentidoY = sentidoY / distanciaY;

        int i = origem.getX();
        int j = origem.getY();

        while (!((i == destino.getX()) || (j == destino.getY()))) {
            i += sentidoX;
            j += sentidoY;

            Casa alvo = tabuleiro.getCasa(i, j);
            Peca pecaAlvo = alvo.getPeca();

            if (pecaAlvo != null) {
                casasComPecaSeguidas++;

                if (pecaAlvo.getCor() == peca.getCor()) {
                    pecasAComer.clear();
                    return false;
                }
            } else {
                if (casasComPecaSeguidas == 1) {
                    Casa casa = tabuleiro.getCasa(alvo.getX() - sentidoX, alvo.getY() - sentidoY);
                    pecasAComer.add(casa);
                }

                casasComPecaSeguidas = 0;
            }

            if (casasComPecaSeguidas == 2) {
                pecasAComer.clear();
                return false;
            }
        }

        if (pecasAComer.isEmpty() && !peca.podeMoverSemCaptura(destino)) return false;

        return true;
    }

    private boolean percorrerEVerificar(Casa origem, int deltaX, int deltaY) {
        int x = origem.getX() + deltaX;
        int y = origem.getY() + deltaY;

        while (tabuleiro.posicaoValida(x, y)) {
            Casa destino = tabuleiro.getCasa(x, y);

            if (simularMovimentoEValidar(origem, destino) && !pecasAComer.isEmpty()) {
                pecasAComer.clear();
                return true;
            }

            pecasAComer.clear();
            x += deltaX;
            y += deltaY;
        }

        return false;
    }

    public boolean deveContinuarJogando(Casa origem) {
        int[][] direcoesDiagonais = {
            {-1, 1},
            {1, 1},
            {1, -1},
            {-1, -1}
        };

        for (int[] direcao : direcoesDiagonais) {
            if (percorrerEVerificar(origem, direcao[0], direcao[1])) {
                return true;
            }
        }

        return false;
    }

    public ArrayList<Casa> getPecasAComer() {
        return pecasAComer;
    }
}
