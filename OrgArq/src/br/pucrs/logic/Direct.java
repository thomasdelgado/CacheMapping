/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.pucrs.logic;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author mac01-lexsislexsis
 */
public class Direct {

    private int bitValidity = 1, cacheSize = 0, wordSize = 0, numberOfWords = 0;
    private int line = 0, lineSize = 0;
    private double lineAdressSize = 0;
    private int tagSize = 0;
    private double dataPercentage = 0;
    public ArrayList<String> memory = new ArrayList();
    private double hitRatio = 0;
    private double hits = 0;
    private int totalReads = 0;
    final public int hitTime = 1, missPenalty = 20;

    public Direct() {
        createMemory();
    }

    public void setCacheSize(int cacheSize) {
        this.cacheSize = cacheSize;
    }

    public void setWordSize(int wordSize) {
        this.wordSize = wordSize;
    }

    public void setNumberOfWords(int numberOfWords) {
        this.numberOfWords = numberOfWords;
    }

    public void calculate() {
        double compareWith = -1;
        lineAdressSize = 0;
        while (true) {
            //cacheSize >= (numberOfWordInsideBlocks * wordSize + tagSize + 1)* 2ˆlineAddressSize
            compareWith = (numberOfWords * wordSize + (32 - Aux2.log2(numberOfWords) - lineAdressSize) + 1) * Math.pow(2, lineAdressSize);
            if (cacheSize < compareWith) {
                break;
            } else {
                lineAdressSize++;
            }
        }
        lineAdressSize--;

        tagSize = (int) (32 - lineAdressSize - Aux2.log2(numberOfWords));
        line = (int) Math.pow(2, lineAdressSize);
        lineSize = numberOfWords * wordSize + tagSize + 1;
        dataPercentage = (double) numberOfWords * wordSize / lineSize;
        createMemory();
    }

    private void createMemory() {
        if (lineSize == 0)
            return;
        double lines = cacheSize / lineSize;
        memory.clear();
        Random gen = new Random();
        String adr;

        for (int x = 0; x < lines; x++) {
            adr = "1";
            for (int y = 0; y < lineSize - 1; y++) {
                adr += "" + (gen.nextInt(2));
            }
            memory.add(adr);
        }
    }

    public void calculateResults(ArrayList<String> adress) {
        hitRatio = 0;
        hits = 0;
        totalReads = adress.size();
        boolean missed;
        if (adress.isEmpty()) {
            return;
        }
        String lineAdress, lineBitsAdr, cacheLine, tagBitsAdr, tagBitsCache ;
        for (int x = 0; x < adress.size(); x++) {
            lineAdress = adress.get(x);
            lineBitsAdr = lineAdress.substring(tagSize, tagSize + (int) lineAdressSize);
            cacheLine = memory.get(Aux2.binToDec(lineBitsAdr));
            missed = false;
            //check validity bit
            if (cacheLine.charAt(0) == '1') {
                //check tag
                tagBitsAdr = lineAdress.substring(0,tagSize);
                tagBitsCache = cacheLine.substring(1,tagSize + 1);                
                if (tagBitsAdr.equals(tagBitsCache)) {
                    hits++; //hit!
                } else missed = true;
                
            } else missed = true;
            if (missed){
                //refresh cache
                tagBitsAdr = lineAdress.substring(0,tagSize);
                memory.set( Aux2.binToDec(lineBitsAdr), "1" + tagBitsAdr + cacheLine.substring(tagSize + 1));
            }
        }
        hitRatio = hits / totalReads;
        // deverá ser entregue o resultado da taxa de acerto, o de falha, o tempo médio de acesso e o tempo total de execução.
    }

    public int getBlockAdressSize() {
        return (int) Aux2.log2(numberOfWords);
    }

    public int getTagSize() {
        return tagSize;
    }

    public int getLineAdressSize() {
        return (int) lineAdressSize;
    }

    public double getDataPercentage() {
        return dataPercentage;
    }

    public double getHitRatio() {
        return hitRatio;
    }

    public double getMissRatio() {
        return 1-hitRatio;
    }

    public double getAvarageTime() {
        return hitTime + ( getMissRatio() ) * missPenalty;
    }

    public double getTotalTime() {
        return (hitTime * hits) + ( (totalReads - hits) * missPenalty ); 
    }
}
