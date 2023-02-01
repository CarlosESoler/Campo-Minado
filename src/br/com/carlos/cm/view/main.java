package br.com.carlos.cm.view;

import br.com.carlos.cm.model.Tabuleiro;

public class main {
    public static void main(String[] args) {
        Tabuleiro tabuleiro = new Tabuleiro(6, 6, 6);
        new TabuleiroConsole(tabuleiro);
    }
}
