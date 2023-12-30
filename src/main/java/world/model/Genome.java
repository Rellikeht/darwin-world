package world.model;

import java.util.Random;

public class Genome {
    private int[] genes;
    private int activeGene;

    public Genome(int N){
        genes = new int[N];
        Random random = new Random();
        for(int i=0;i<N;i++){ genes[i] = random.nextInt(8); }
        activeGene = random.nextInt(N);
    }

    public int getGene(int i){
        return genes[i];
    }

    private void randomMutate(){
        Random random = new Random();
        int genIndex = random.nextInt(genes.length);
        int newGene = random.nextInt(8);
        genes[genIndex] = newGene;
    }

    private void swapMutate(){
        Random random=new Random();
        int firstGenIndex=random.nextInt(genes.length);
        int secondGenIndex=random.nextInt(genes.length);
        if(firstGenIndex==secondGenIndex) {
            while (secondGenIndex == firstGenIndex) {
                secondGenIndex = random.nextInt(genes.length);
            }
        }

        int temp=genes[firstGenIndex];
        genes[firstGenIndex]=genes[secondGenIndex];
        genes[secondGenIndex]=temp;
    }

    public int getActiveGene() {
        return activeGene;
    }
}