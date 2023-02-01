package br.com.carlos.cm.view;

import br.com.carlos.cm.control.Explosao;
import br.com.carlos.cm.control.Sair;
import br.com.carlos.cm.model.Tabuleiro;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class TabuleiroConsole {
    private Tabuleiro tabuleiro;
    Scanner entrada = new Scanner(System.in);
    public TabuleiroConsole(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;

        executarJogo();
    }
    public void executarJogo() {
        try {
            boolean continuar = true;
            while (continuar) {
                cicloDoJogo();
                System.out.println("Outra partida -> (Sim / nao)");
                String resposta = entrada.nextLine();

                if("nao".equalsIgnoreCase(resposta)) {
                    continuar = false;
                }
                else {
                    tabuleiro.reiniciar();
                }
            }

        }
        catch(Sair e){
            System.out.println("Saindo");
        }
        finally {
            entrada.close();
        }
    }
    private void cicloDoJogo() {
        try{

            while(!tabuleiro.objetivoAlcancado()) {
                System.out.println(tabuleiro);

                String digitado = capturarValorDigitado("Digite (x , y): " );
                Iterator<Integer> xy = Arrays.stream(digitado.split(",")).map(e -> Integer.parseInt(e.trim())).iterator();
                digitado = capturarValorDigitado("1 - Abrir ou 2 - (Des)marcar");

                if (digitado.equals("1")) {
                    tabuleiro.abrir(xy.next(), xy.next());
                } else if(digitado.equals("2")) {
                    tabuleiro.alterarMarcacao(xy.next(), xy.next());
                }
            }



            System.out.println(tabuleiro);
            System.out.println("Voce ganhou");
        } catch(Explosao e) {
            System.out.println(tabuleiro);
            System.out.println("Voce perdeu");
        }
    }
    private String capturarValorDigitado(String texto) {
        System.out.print(texto);
        String digitado = entrada.nextLine();
        if("sair".equalsIgnoreCase(digitado)) {
            throw new Sair();
        }
        return digitado;
    }
}
