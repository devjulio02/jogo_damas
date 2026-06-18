package com.damas.gui;

import com.damas.objetos.Jogo;

// Contém o texto que antes era montado pelo toString de Jogo.
public class FormatadorJogo {

    public String formatar(Jogo jogo) {
        String retorno = "Vez: ";

        if (jogo.getVez() == 1) {
            retorno += jogo.getJogadorUm().getNome();
            retorno += "\n";
        } else if (jogo.getVez() == 2) {
            retorno += jogo.getJogadorDois().getNome();
            retorno += "\n";
        }

        retorno += "Nº de jogadas: " + jogo.getJogada() + "\n";
        retorno += "Jogadas sem comer peça: " + jogo.getJogadasSemComerPecas() + "\n";
        retorno += "\n";
        retorno += "Informações do(a) jogador(a) " + jogo.getJogadorUm().getNome() + "\n";
        retorno += "Pontos: " + jogo.getJogadorUm().getPontos() + "\n";
        retorno += "Nº de peças restantes: " + (12 - jogo.getJogadorDois().getPontos()) + "\n";
        retorno += "\n";
        retorno += "Informações do(a) jogador(a) " + jogo.getJogadorDois().getNome() + "\n";
        retorno += "Pontos: " + jogo.getJogadorDois().getPontos() + "\n";
        retorno += "Nº de peças restantes: " + (12 - jogo.getJogadorUm().getPontos()) + "\n";

        if (jogo.getCasaBloqueada() != null) {
            retorno += "\n";
            retorno += "Mova a peça na casa " + jogo.getCasaBloqueada().getX() + ":" + jogo.getCasaBloqueada().getY() + "!";
        }

        return retorno;
    }
}
