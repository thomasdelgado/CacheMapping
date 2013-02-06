/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.pucrs.logic;
import java.util.Random;
import java.util.ArrayList;
/**
 *
 * @author mac01-lexsislexsis
 */
public class Associative {
    private int cacheSize, numberOfLines, wordSize, numberOfWords, tagSize;
    private ArrayList<String> memory = new ArrayList();
    private ArrayList<Integer> timer = new ArrayList();
    private double hitRatio = 0;
    private double hits = 0;
    private int totalReads = 0;
    final public int hitTime = 1, missPenalty = 20;
    public Associative(){}
    
    
    public void createMemory(){
        if (wordSize == 0)
            return;
        
        Random gen = new Random();
        memory.clear();
        timer.clear();
        tagSize = wordSize - getBlockAdressSize();
        for (int i = 0; i < numberOfLines; i++) {
            String adr = "";
            for (int j = 0; j < tagSize; j++) {
                adr = adr + (gen.nextInt(2));
            }
            memory.add(adr);
            timer.add(0);
        }
    }

    public void LRU(ArrayList<String> Adr){
        totalReads = Adr.size();
        hits = 0;
        hitRatio = 0;
        for(int x = 0; x < Adr.size(); x++){
            String element = Adr.get(x);
            boolean contains = false;
            int position;
            String adress = element.substring(0, tagSize);
            for(int i = 0; i < memory.size(); i++){
                String aux = memory.get(i);
                if(aux.equals(adress)){
                    setHigher(i);
                    contains = true;
                    hits++;
                }
            }
            if(contains == false){
                position = getPositionMinor();
                setHigher(position);
                memory.set(position, adress);
            }
        }
        hitRatio = hits / totalReads;   
    }
    
    private void setHigher(int position){
        for(int i = 0; i < memory.size(); i++){
            if(timer.get(position) <= timer.get(i)){
                timer.set(position, timer.get(i) + 1);
            }
        }
    }
    private int getPositionMinor(){
        int value = timer.get(0);
        int position = 0;
        for(int i = 0; i < memory.size(); i++){
            if(value > timer.get(i)){
                value = timer.get(i);
                position = i;
            }
        }
        return position;
    }
        
    public int getCacheSize(){
        return cacheSize;
    }
    public int getWordSize(){
        return wordSize;
    }
    public int getNumberOfLines(){
        return numberOfLines;
    }
    public int getBlockAdressSize() {
        return (int) Aux2.log2(numberOfWords);
    }
    public int getTagSize(){
        return tagSize;
    }
    public void setCacheSize(int value){
        cacheSize = value;
    }
    public void setWordSize(int value){
        wordSize = value;
    }
    public void setNumberOfWords(int value){
        numberOfWords = value;
    }
    public void setNumberOfLines(int cache, int line){
        numberOfLines = cache/line;
    }
    
                    
    public double getHitRatio(){
        return hitRatio;
    }
    public double getMissRatio(){
        return 1 - hitRatio;
    }
    public double getTotalTime() {
        double fuck = (hitTime * hits) + ( (totalReads - hits) * missPenalty );
        int tico = 0;
        return (hitTime * hits) + ( (totalReads - hits) * missPenalty ); 
    }
    public double getAvarageTime() {
        return hitTime + ( getMissRatio() ) * missPenalty;
    }
    
}
