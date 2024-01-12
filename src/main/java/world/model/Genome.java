package world.model;

import java.util.*;

public class Genome {
    private final int[] genes;
    private final int length;
    private final Random random = new Random();

    public Genome(int N){
        genes = new int[N];
        length = N;
        for(int i=0;i<N;i++){ genes[i] = random.nextInt(8); }
    }

    // TODO do odstrzału
    private void setGene(int i,int g){this.genes[i]=g;}

    Genome(
            Genome genome1, Genome genome2,
            int energy1, int energy2,
            int minAmountOfMutations, int maxAmountOfMutations,
            boolean isMutationRandom
    ) {
        this(genome1.getLength());
        int proportions = length*(energy2/energy1);
        int mutateNumber = random.nextInt(maxAmountOfMutations-minAmountOfMutations)+minAmountOfMutations;

        if(random.nextInt(2) == 0) {
            for (int i = 0; i < length-proportions; i++) {
                this.setGene(i, genome2.getGene(i));
            }
            for (int i = length-proportions; i < length; i++) {
                this.setGene(i, genome1.getGene(i));
            }
        }

        else{
            for (int i = 0; i < proportions; i++) {
                this.setGene(i, genome1.getGene(i));
            }
            for (int i = proportions; i < length; i++) {
                this.setGene(i, genome2.getGene(i));
            }
        }

        if(isMutationRandom){
            for(int i=0;i<mutateNumber;i++) {
                int genIndex = random.nextInt(genes.length);
                int newGene = random.nextInt(8);
                genes[genIndex] = newGene;
            }
        } else{
            for(int i=0;i<mutateNumber;i++) {
                int firstGenIndex=random.nextInt(genes.length);
                int secondGenIndex=random.nextInt(genes.length);
                if(firstGenIndex==secondGenIndex) {
                    while (secondGenIndex == firstGenIndex) {
                        secondGenIndex = random.nextInt(genes.length);
                    }
                }

                int temp = genes[firstGenIndex];
                genes[firstGenIndex] = genes[secondGenIndex];
                genes[secondGenIndex] = temp;
            }
        }
    }

    public int getGene(int i){ return genes[i]; }
    public int getLength() { return genes.length; }

}