package com.trybe.conversorcsv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;

public class Conversor {

  /**
   * Função utilizada apenas para validação da solução do desafio.
   *
   * @param args Não utilizado.
   * @throws IOException Caso ocorra algum problema ao ler os arquivos de entrada ou
   *                     gravar os arquivos de saída.
   */
  public static void main(String[] args) throws IOException {
    File pastaDeEntradas = new File("./entradas/");
    File pastaDeSaidas = new File("./saidas/");
    new Conversor().converterPasta(pastaDeEntradas, pastaDeSaidas);
  }

  /**
   * Converte todos os arquivos CSV da pasta de entradas. Os resultados são gerados
   * na pasta de saídas, deixando os arquivos originais inalterados.
   *
   * @param pastaDeEntradas Pasta contendo os arquivos CSV gerados pela página web.
   * @param pastaDeSaidas Pasta em que serão colocados os arquivos gerados no formato
   *                      requerido pelo subsistema.
   *
   * @throws IOException Caso ocorra algum problema ao ler os arquivos de entrada ou
   *                     gravar os arquivos de saída.
   */
  public void converterPasta(File pastaDeEntradas, File pastaDeSaidas) throws IOException {
    File[] files = lerDiretorio(pastaDeEntradas);
    for (File file: files) {
      File outFile = new File(String.valueOf(file).replace("entradas", "saidas"));
      readAndWriteFile(file, outFile, pastaDeSaidas);
    }
  }

  private File[] lerDiretorio(@NotNull File pastaDeEntrada) {
    /**
     * Lê conteúdo do diretório.
     * @param pastaDeEntrada File com o endereço da pasta de entrada
     */
    if (pastaDeEntrada.isDirectory() && pastaDeEntrada.canRead()) {
      return pastaDeEntrada.listFiles();
    }
    return null;
  }

  private void readAndWriteFile(File fileIn, File fileOut, File outDir) {
    /**
     *
     * Lê e escreve conteúdo dos arquivos.
     */
    if (!outDir.exists()) {
      outDir.mkdir();
    }
    if (fileIn.exists()) {
      try {
        FileReader fileReader = new FileReader(fileIn);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String conteudoLinha = bufferedReader.readLine();
        String fileText = "Nome completo,Data de nascimento,Email,CPF";
        while (conteudoLinha != null) {
          conteudoLinha = bufferedReader.readLine();
          if (conteudoLinha != null) {
            fileText += "\n" + this.parseString(conteudoLinha);
          }
        }
        FileWriter fileWriter = new FileWriter(fileOut);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(fileText);
        bufferedWriter.flush();
        fileWriter.close();
        bufferedWriter.close();
        this.closeObject(fileReader, bufferedReader);
      } catch (IOException err) {
        err.printStackTrace();
      }
    }
  }

  private void closeObject(FileReader fileReader, BufferedReader bufferedReader) {
    try {
      fileReader.close();
      bufferedReader.close();
    } catch (Exception err) {
      err.printStackTrace();
    }
  }

  private String parseString(String textToFormat) {
    String[] textArray = textToFormat.split(",");
    String name = textArray[0].toUpperCase();
    String data = textArray[1];
    String email = textArray[2];
    String cpf = textArray[3];
    String regexCpfExpression = "(\\d{3})(\\d{3})(\\d{3})(\\d{2})";
    String[] destData = data.split("/");
    String dd = destData[0];
    String mm = destData[1];
    String aaaa = destData[2];
    data = aaaa + "-" + mm + "-" + dd;
    String formatedCpf = cpf
        .replaceAll(regexCpfExpression,"$1.$2.$3-$4");
    return name + "," + data + "," + email + "," + formatedCpf;
  }
}