package com.damas.objetos;

// Armazena as casas, posiciona as peças e verifica os limites do tabuleiro.
public class Tabuleiro {

    private static final int MAX_LINHAS = 8;
    private static final int MAX_COLUNAS = 8;

    private Casa[][] casas;

    public Tabuleiro() {
        montarTabuleiro();
    }

    private void montarTabuleiro() {
        casas = new Casa[MAX_LINHAS][MAX_COLUNAS];

        for (int x = 0; x < MAX_LINHAS; x++) {
            for (int y = 0; y < MAX_COLUNAS; y++) {
                casas[x][y] = new Casa(x, y);
            }
        }
    }

    // Método colocarPecas movido da classe Jogo.
    public void colocarPecas() {
        for (int x = 0; x < MAX_LINHAS; x++) {
            for (int y = 0; y < 3; y++) {
                if ((x % 2 == 0 && y % 2 == 0) || (x % 2 != 0 && y % 2 != 0)) {
                    new Pedra(getCasa(x, y), CorPeca.BRANCA);
                }
            }
        }

        for (int x = 0; x < MAX_LINHAS; x++) {
            for (int y = 5; y < MAX_COLUNAS; y++) {
                if ((x % 2 == 0 && y % 2 == 0) || (x % 2 != 0 && y % 2 != 0)) {
                    new Pedra(getCasa(x, y), CorPeca.VERMELHA);
                }
            }
        }
    }

    // Reúne a validação e a transformação que antes estavam separadas em Jogo.
    public void transformarPedraParaDama(Casa casa) {
        Peca peca = casa.getPeca();

        if (peca == null || peca.getTipo() != TipoPeca.PEDRA) return;

        boolean pedraBrancaNoLimite = peca.getCor() == CorPeca.BRANCA && casa.getY() == MAX_COLUNAS - 1;
        boolean pedraVermelhaNoLimite = peca.getCor() == CorPeca.VERMELHA && casa.getY() == 0;

        if (pedraBrancaNoLimite || pedraVermelhaNoLimite) {
            new Dama(casa, peca.getCor());
        }
    }

    public boolean posicaoValida(int x, int y) {
        return x >= 0 && x < MAX_LINHAS && y >= 0 && y < MAX_COLUNAS;
    }

    public Casa getCasa(int x, int y) {
        return casas[x][y];
    }

    public int getMaxLinhas() {
        return MAX_LINHAS;
    }

    public int getMaxColunas() {
        return MAX_COLUNAS;
    }
}
