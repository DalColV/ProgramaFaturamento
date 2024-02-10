package Faturamento;

import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Faturamento
{
    private String file;
    private JSONArray JSONfile;

    public Faturamento(String fileToOpen) {
        this.file = fileToOpen;
        this.ReadFile();
    }

    // métodos da classe
    private void ReadFile()
    {
        this.JSONfile = null;
        try (FileReader fReader = new FileReader(this.file)) {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(fReader);
            this.JSONfile = (JSONArray) obj;
        }
        catch (IOException | ParseException e) {
            System.out.println("O arquivo especificado não foi encontrado (" + this.file + "). StackTrace:");
            e.printStackTrace();
        }
    }

    public void exibirFaturamento() {
        double menorFat = Double.MAX_VALUE;
        double maiorFat = Double.MIN_VALUE;
        double totalFaturado = 0;
        double diasComFaturamentoAcimaDaMedia = 0;
        int totalDias = 0;

        for (Object obj : JSONfile) {
            JSONObject dia = (JSONObject) obj;
            double faturamento = Double.parseDouble(dia.get("valor").toString());

            if (faturamento > 0) {
                if (faturamento < menorFat) {
                    menorFat = faturamento;
                }
                if (faturamento > maiorFat) {
                    maiorFat = faturamento;
                }
                totalFaturado += faturamento;
                totalDias++;
            }
        }

        double mediaMensal = totalFaturado / (double) totalDias;

        for (Object obj : JSONfile) {
            JSONObject dia = (JSONObject) obj;
            double faturamento = Double.parseDouble(dia.get("valor").toString());
            if (faturamento > mediaMensal) {
                diasComFaturamentoAcimaDaMedia++;
            }
        }

        System.out.println("Menor faturamento diário: " + menorFat);
        System.out.println("Maior faturamento diário: " + maiorFat);
        System.out.println("Total faturado: " + totalFaturado);
        System.out.println("Número de dias com faturamento acima da média mensal: " + (int) diasComFaturamentoAcimaDaMedia);
    }
}