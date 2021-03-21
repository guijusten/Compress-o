import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/*
    O programa está sendo compilado e todas as funções estão corretas
  exceto a função para atualizar o dicionário. Por conta de dificuldades na conversão
  de char[] para string, a funcionalidade updateDictionary está incorreta, fora isso,
  o programa está correto.
 */


public class Main{
    public static void main(String[] args) {
        LZ77 lz = new LZ77();
        String data = "";

        System.out.println("Por favor, selecione o arquivo .txt");
        try {
            data = IOFile.getStringFromFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(" String antes de comprimir: " + data);

        String compressed = lz.compress(data);

        System.out.println("String após ser comprimida: " + compressed + System.lineSeparator());
        System.out.println("Digite 1 para saída em arquivo, qualquer outro número caso contrário");
        Scanner scan = new Scanner(System.in);
        if(scan.nextInt() == 1){
            File file = new File("saida.txt");
            try {
                FileWriter writer = new FileWriter(file);
                writer.write(compressed);
                writer.close();
                System.out.println("Saída em arquivo concluída");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}