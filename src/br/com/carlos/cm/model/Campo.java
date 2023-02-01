package br.com.carlos.cm.model;

import br.com.carlos.cm.control.Explosao;

import java.util.ArrayList;
import java.util.List;

public class Campo {

    private final int linha;
    private final int coluna;

    private boolean minado;
    private boolean aberto;
    private boolean marcado;

    private List<Campo> vizinhos = new ArrayList<>();

    Campo(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }
     boolean adicionarVizinho(Campo vizinho) {
        boolean linhaDiferente = this.linha != vizinho.linha;
        boolean colunaDiferente = this.coluna != vizinho.coluna;
        boolean diagonal = linhaDiferente && colunaDiferente;

        int deltaLinha = Math.abs(this.linha - vizinho.linha);
        int deltaColuna = Math.abs(this.coluna - vizinho.coluna);
        int deltaGeral = deltaLinha + deltaColuna;

        if (deltaGeral == 1 && !diagonal) return vizinhos.add(vizinho);
        if (deltaGeral == 2 && diagonal) return vizinhos.add(vizinho);
        return false;
    }
    void alterarBandeira() {
        if(!this.aberto) {
            this.marcado = !this.marcado; // Se não estiver aberto, altera a situação da marcação (se aberto, fechado)
        }
    }
    boolean abrir() {
        if (!this.aberto && !this.marcado) {
            this.aberto = true;

            if (minado) {
                throw new Explosao();
            }

            if (vizinhancaSegura()) {
                vizinhos.forEach(v -> v.abrir());
            }
            return true;
        }
        return false;
    }
    boolean vizinhancaSegura() {
        return vizinhos.stream().noneMatch(v -> v.minado);
    }

    void minar() {
     this.minado = true;
    }

    public boolean isMarcado() {
        return this.marcado;
    }

    void setAberto(boolean aberto) {
        this.aberto = aberto;
    }
    public boolean isAberto() {
        return this.aberto;
    }

    public int getLinha() {
        return this.linha;
    }

    public int getColuna() {
        return this.coluna;
    }

    boolean objetivoAlcancado() {
        boolean desventado = !minado && !aberto;
        boolean proteger = minado && marcado;

        return desventado || proteger;
    }

    long minasNaVizinhanca() {
        return vizinhos.stream().filter(v -> v.minado).count();
    }

    void reiniciar() {
        this.aberto = false;
        this.minado = false;
        this.marcado = false;
    }

    public String toString(){
        if (marcado) {
            return "x";
        }
        if (aberto && minado) {
            return "*";
        }
        if (aberto && minasNaVizinhanca() > 0) {
            return Long.toString(minasNaVizinhanca());
        }
        if (aberto) {
            return " ";
        }
        return "?";
    }
    boolean isMinado() {
        return minado;
    }
}
