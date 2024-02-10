package Program;

import Faturamento.*;

public class Main {
    public static void main(String[] args) {
        String fileToOpen = "faturamento.json";
        Faturamento fat = new Faturamento(fileToOpen);
        fat.exibirFaturamento();
    }
}