/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.pucrs.logic;

import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.JOptionPane;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author mac01-lexsislexsis
 */
public class Aux2 {

    public static double log2(double value) {
        return Math.log(value) / Math.log(2);
    }

    public static ArrayList<String> ReadArchive(String absolutePath) {
        String linha;
        ArrayList<String> res = new ArrayList<String>();
        try {
            BufferedReader buff = new BufferedReader(new FileReader(absolutePath));
            try {
                linha = buff.readLine();
                while (linha != null) {
                    res.add(linha);
                    linha = buff.readLine();
                }
            } finally {
                buff.close();
            }
        } catch (Exception e) {
            String erro = e.getMessage();
            JOptionPane.showMessageDialog(null, erro, "Aviso", 3);
            return null;
        }
        return res;
    }

    public static class TxtFilter extends javax.swing.filechooser.FileFilter {

        @Override
        public boolean accept(File file) {
            String filename = file.getName();
            return filename.endsWith(".txt");
        }

        @Override
        public String getDescription() {
            return "*.txt";
        }
    }

    static public int binToDec(String bin) {
        return Integer.parseInt(bin, 2);
    }

    static public void geraArquivo(int lines) {
        ArrayList<String> end = new ArrayList<String>();
        Random gen = new Random();
        String adr;
        for (int x = 0; x < lines; x++) {
            adr = "";
            for (int y = 0; y < 32; y++) {
                adr += "" + (gen.nextInt(2));
            }
            end.add(adr);
        }

        FileWriter arquivo;
        try {
            arquivo = new FileWriter(new File("Arquivo.txt"));
                for (int x = 0; x < lines; x++) {
                arquivo.write(end.get(x) + "\n");
            }
            arquivo.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
