package com.damas.gui;

import java.awt.Color;
import javax.swing.JPanel;

import com.damas.objetos.Casa;
import com.damas.objetos.CorPeca;
import com.damas.objetos.Jogo;
import com.damas.objetos.Peca;
import com.damas.objetos.Tabuleiro;
import com.damas.objetos.TipoPeca;

// Representação gráfica do tabuleiro.
public class TabuleiroGUI extends JPanel {

    private JanelaPrincipal janela;
    private CasaGUI[][] casas;

    public TabuleiroGUI() {
    }

    public TabuleiroGUI(JanelaPrincipal janela) {
        this.janela = janela;
        initComponents();
        criarCasas();
    }

    private void criarCasas() {
        casas = new CasaGUI[8][8];

        for (int y = 7; y >= 0; y--) {
            for (int x = 0; x < 8; x++) {
                Color cor = calcularCor(x, y);
                CasaGUI casa = new CasaGUI(x, y, cor, this);
                casas[x][y] = casa;
                add(casa);
            }
        }
    }

    private Color calcularCor(int x, int y) {
        return (x % 2 + y % 2) % 2 == 0 ? CasaGUI.COR_ESCURA : CasaGUI.COR_CLARA;
    }

    public void atualizar(Jogo jogo) {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                CasaGUI casaGUI = casas[x][y];
                Tabuleiro tabuleiro = jogo.getTabuleiro();
                Casa casa = tabuleiro.getCasa(x, y);

                if (casa.possuiPeca()) {
                    Peca peca = casa.getPeca();

                    if (peca.getTipo() == TipoPeca.PEDRA) {
                        if (peca.getCor() == CorPeca.BRANCA) {
                            casaGUI.desenharPedraBranca();
                        } else {
                            casaGUI.desenharPedraVermelha();
                        }
                    } else {
                        if (peca.getCor() == CorPeca.BRANCA) {
                            casaGUI.desenharDamaBranca();
                        } else {
                            casaGUI.desenharDamaVermelha();
                        }
                    }
                } else {
                    casaGUI.apagarPeca();
                }
            }
        }
    }

    public JanelaPrincipal getJanela() {
        return janela;
    }

    private void initComponents() {
        setLayout(new java.awt.GridLayout(8, 8));
    }
}
