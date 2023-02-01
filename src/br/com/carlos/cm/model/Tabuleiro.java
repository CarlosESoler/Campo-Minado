package br.com.carlos.cm.model;

import br.com.carlos.cm.control.Explosao;

import java.util.ArrayList;
import java.util.List;

public class Tabuleiro {

    private int linhas;
    private int colunas;
    private int minas;

    private final List<Campo> campos = new ArrayList<>();

    public Tabuleiro(int linhas, int colunas, int minas) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.minas = minas;

        gerarCampos();
        associarVizinhos();
        sortearMinas();
    }

    public void abrir(int linha, int coluna) {
        try{
            campos.stream().filter(c -> c.getLinha() == linha && c.getColuna() == coluna).findFirst().ifPresent(c -> c.abrir());
        }
        catch (Explosao e) {
            campos.forEach(c -> c.setAberto(true));
            throw e;
        }
    }
    public void alterarMarcacao(int linha, int coluna) {
        campos.stream().filter(c -> c.getLinha() == linha && c.getColuna() == coluna).findFirst().ifPresent(c -> c.alterarBandeira());
    }

    private void gerarCampos() {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                campos.add(new Campo(i, j));
            }
        }

    }
    private void associarVizinhos() {
        for (Campo c1 : campos) {
            for (Campo c2 : campos) {
                c1.adicionarVizinho(c2);
            }
        }

    }
    private void sortearMinas() {
        long minasArmadas;

        do {
            int aleatorio = (int) (Math.random() * campos.size());
            campos.get(aleatorio).minar();
            minasArmadas = campos.stream().filter(m -> m.isMinado()).count();

        } while(minasArmadas < minas);
    }
    public boolean objetivoAlcancado() {
        return campos.stream().allMatch(c -> c.objetivoAlcancado());
    }
    public void reiniciar() {
        campos.stream().forEach(c -> c.reiniciar());
        sortearMinas();
    }
    public String toString() {
        StringBuilder sB =new StringBuilder();

        sB.append("  ");
        for (int i = 0; i < colunas; i++) {
            sB.append(" ");
            sB.append(i);
            sB.append(" ");
        }
        sB.append("\n");

        int valorCampo = 0;

        for (int i = 0; i < linhas; i++) {

            sB.append(i);
            sB.append(" ");

            for (int j = 0; j < colunas; j++) {
                sB.append(" ");
                sB.append(campos.get(valorCampo));
                sB.append(" ");

                valorCampo++;
            }
            sB.append("\n");
        }
        return sB.toString();
    }
}