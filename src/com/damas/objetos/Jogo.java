package com.damas.objetos;

import java.util.ArrayList;

// Coordena a partida e delega as validações aos objetos responsáveis.
public class Jogo {

    private Tabuleiro tabuleiro;
    private Jogador jogadorUm;
    private Jogador jogadorDois;
    private int vezAtual = 1;
    private int jogadas = 0;
    private int jogadasSemComerPeca = 0;
    private Casa casaBloqueadaOrigem;
    private ValidadorMovimento validadorMovimento;

    public Jogo() {
        tabuleiro = new Tabuleiro();
        jogadorUm = new Jogador("player branco", CorPeca.BRANCA);
        jogadorDois = new Jogador("player vermelho", CorPeca.VERMELHA);
        validadorMovimento = new ValidadorMovimento(tabuleiro);

        vezAtual = 1;
        jogadas = 0;
        jogadasSemComerPeca = 0;
        casaBloqueadaOrigem = null;

        tabuleiro.colocarPecas();
    }

    public void moverPeca(int origemX, int origemY, int destinoX, int destinoY) {
        Casa origem = tabuleiro.getCasa(origemX, origemY);
        Casa destino = tabuleiro.getCasa(destinoX, destinoY);
        Peca peca = origem.getPeca();

        if (casaBloqueadaOrigem == null) {
            if ((getVez() == 1 && jogadorUm.controla(peca)) ||
                (getVez() == 2 && jogadorDois.controla(peca))) {

                if (peca.isMovimentoValido(destino)) {
                    if (validadorMovimento.simularMovimentoEValidar(origem, destino)) {
                        peca.mover(destino);

                        if (validadorMovimento.getPecasAComer().size() > 0) {
                            comerPecas();

                            if (validadorMovimento.deveContinuarJogando(destino)) {
                                casaBloqueadaOrigem = destino;
                            } else {
                                trocarDeVez();
                            }
                        } else {
                            jogadasSemComerPeca++;
                            trocarDeVez();
                        }

                        jogadas++;
                        tabuleiro.transformarPedraParaDama(destino);
                    }
                }
            }
        } else {
            if (origem.equals(casaBloqueadaOrigem)) {
                if (validadorMovimento.simularMovimentoEValidar(origem, destino)) {
                    if (validadorMovimento.getPecasAComer().size() != 0) {
                        casaBloqueadaOrigem = null;
                        moverPeca(origemX, origemY, destinoX, destinoY);
                    }
                }
            }
        }
    }

    // Permanece em Jogo porque altera pontuação e estado da partida.
    private void comerPecas() {
        ArrayList<Casa> pecasAComer = validadorMovimento.getPecasAComer();
        int pecasComidas = pecasAComer.size();

        if (getVez() == 1) jogadorUm.addPonto(pecasComidas);
        if (getVez() == 2) jogadorDois.addPonto(pecasComidas);

        for (Casa casa : pecasAComer) {
            casa.removerPeca();
        }

        pecasAComer.clear();
        jogadasSemComerPeca = 0;
    }

    public void trocarDeVez() {
        if (vezAtual == 1) {
            vezAtual = 2;
        } else {
            vezAtual = 1;
        }
    }

    public int getGanhador() {
        if (jogadorUm.getPontos() == 12) return 1;
        if (jogadorDois.getPontos() == 12) return 2;
        return 0;
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public void setJogadorUm(Jogador jogador) {
        jogadorUm = jogador;
    }

    public void setJogadorDois(Jogador jogador) {
        jogadorDois = jogador;
    }

    public Jogador getJogadorUm() {
        return jogadorUm;
    }

    public Jogador getJogadorDois() {
        return jogadorDois;
    }

    public int getVez() {
        return vezAtual;
    }

    public int getJogadasSemComerPecas() {
        return jogadasSemComerPeca;
    }

    public int getJogada() {
        return jogadas;
    }

    public Casa getCasaBloqueada() {
        return casaBloqueadaOrigem;
    }
}
