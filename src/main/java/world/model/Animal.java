package world.model;

import java.util.Random;

public class Animal implements Comparable<Animal> {

    // Atrybuty
    private Direction direction;
    private Vector2d position;
    private final Genome genes;
    private int energy;
    private int currentGene = 0;

    // Staty
    private int grassEaten = 0;
    private final int dayOfBirth;
    private int dayOfDeath;
    //private int daysOfLife = 0;
    private int childrenAmount = 0;

    private final Random random = new Random();

    // TODO ilość potomków, tutaj trzeba będzie albo mieć referencje do dzieci,
    // Albo referencje do rodziców i dodawać potomków rekurencyjnie (brzmi bardzo źle xd)

    public Animal(Vector2d position, Direction direction, int energy, Genome genes, int dayOfBirth) {
        this.position = position;
        this.direction = direction;
        this.genes = genes;
        this.energy = energy;
        this.dayOfBirth = dayOfBirth;
        // TODO staty
    }

    public Animal(Vector2d position, Direction direction, int energy, Genome genes) {
        this(position, direction, energy, genes, 0);
    }

    public String toString() {
        return switch (this.direction) {
            //case D0 -> "u";
            //case D45 -> "ur";
            //case D90 -> "r";
            //case D135 -> "dr";
            //case D180 -> "d";
            //case D225 -> "dl";
            //case D270 -> "l";
            //case D315 -> "ul";

            case D0 -> "↑";
            case D45 -> "↗";
            case D90 -> "→";
            case D135 -> "↘";
            case D180 -> "↓";
            case D225 -> "↙";
            case D270 -> "←";
            case D315 -> "↖";
        };
    }

    public Vector2d getPosition() { return this.position; }
    public Direction getOrientation() { return this.direction; }
    public int getEnergy() { return energy; }
    public Genome getGenes(){ return genes; }
    public int getCurrentGene(){ return currentGene; }

    public void loseEnergy(int energy) { this.energy -= energy; }

    public void eatGrass(int grassEnergy) {
        this.energy += grassEnergy;
        this.grassEaten += 1;
    }

    public void rotateAnimals(int n){
        int gene = genes.getGene(n);
        currentGene += 1;
        direction = Direction.getDirection(gene);
    }

    public void move(){
        Vector2d dirVector = direction.toUnitVector();
        position.add(dirVector);
    }

    public void die(int day) {
        this.dayOfDeath = day;
    }

    // TODO tu pasuje ustalić też jak liczymy ilosć potomków
    public void addChildren() { this.childrenAmount += 1; }
    //public void nextDayOfLife(){daysOfLife+=1;}

    // TODO staty
    //public int getDaysOfLife(){return  daysOfLife;}
    public int getDayOfBirth(){return dayOfBirth;}
    public int getDayOfDeath(){return dayOfDeath;}
    public int getChildrenAmount(){return childrenAmount;}

    @Override
    public int compareTo(Animal other) {
        // Porównujemy po parametrze 1
        int result = Integer.compare(other.getEnergy(), getEnergy());
        if (result == 0) {
            // result = Integer.compare(other.getDaysOfLife(), getDaysOfLife());
            result = Integer.compare(getDayOfBirth(), other.getDayOfBirth());
            if (result == 0){
                result = Integer.compare(other.getChildrenAmount(), getChildrenAmount());
                if (result==0){
                    result = random.nextInt(3)-1;
                }
            }
        }

        return result;
    }

}
