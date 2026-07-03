package com.damas.objetos;

import java.util.ArrayList;
import java.util.List;

//Coordena a partida e delega as regras para os objetos responsáveis;
public class Jogo {

    private Tabuleiro tabuleiro;
    private Jogador jogadorUm;
    private Jogador jogadorDois;
    private int vezAtual = 1;
    private int jogadas = 0;
    private int jogadasSemComerPeca = 0;
    private Casa casaBloqueadaOrigem;

    public Jogo() {
        tabuleiro = new Tabuleiro();
        jogadorUm = new Jogador("player branco", CorPeca.BRANCA);
        jogadorDois = new Jogador("player vermelho", CorPeca.VERMELHA);

        vezAtual = 1;
        jogadas = 0;
        jogadasSemComerPeca = 0;
        casaBloqueadaOrigem = null;

        tabuleiro.colocarPecas();
    }

    //Continua em Jogo porque coordena todas as etapas de uma jogada;
    public void moverPeca(int origemX, int origemY, int destinoX, int destinoY) {
        Casa origem = tabuleiro.getCasa(origemX, origemY);
        Casa destino = tabuleiro.getCasa(destinoX, destinoY);
        Peca peca = origem.getPeca();
        List<Casa> pecasAComer = new ArrayList<Casa>();

        //As condições receberam nomes ligados às regras do jogo;
        if (jogadaPermitida(origem, peca)
                && peca.simularMovimentoEValidar(tabuleiro, destino, pecasAComer)
                && capturaObrigatoriaAtendida(pecasAComer)) {

            peca.mover(destino);
            concluirMovimento(peca, destino, pecasAComer);
        }
    }

    //Verifica se existe uma peça do jogador atual e se a origem está liberada;
    private boolean jogadaPermitida(Casa origem, Peca peca) {
        if (peca == null) return false;

        boolean jogadorControlaPeca = getJogadorAtual().controla(peca);
        boolean origemPermitida = casaBloqueadaOrigem == null || origem.equals(casaBloqueadaOrigem);

        return jogadorControlaPeca && origemPermitida;
    }

    //Durante uma sequência de capturas a próxima jogada também precisa capturar;
    private boolean capturaObrigatoriaAtendida(List<Casa> pecasAComer) {
        return casaBloqueadaOrigem == null || !pecasAComer.isEmpty();
    }

    //Atualiza captura, turno, quantidade de jogadas e promoção;
    private void concluirMovimento(Peca peca, Casa destino, List<Casa> pecasAComer) {
        if (pecasAComer.isEmpty()) {
            jogadasSemComerPeca++;
            finalizarTurno();
        } else {
            comerPecas(pecasAComer);

            if (peca.deveContinuarJogando(tabuleiro)) {
                casaBloqueadaOrigem = destino;
            } else {
                finalizarTurno();
            }
        }

        jogadas++;
        tabuleiro.transformarPedraParaDama(destino);
    }

    //Continua em Jogo porque altera pontuação e estado da partida;
    private void comerPecas(List<Casa> pecasAComer) {
        getJogadorAtual().addPonto(pecasAComer.size());

        for (Casa casa : pecasAComer) {
            casa.removerPeca();
        }

        pecasAComer.clear();
        jogadasSemComerPeca = 0;
    }

    private void finalizarTurno() {
        casaBloqueadaOrigem = null;
        trocarDeVez();
    }

    private Jogador getJogadorAtual() {
        if (getVez() == 1) return jogadorUm;
        return jogadorDois;
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
