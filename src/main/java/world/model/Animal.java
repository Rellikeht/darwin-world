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
    private int offspringsAmount = 0;

    private final Animal parent1;
    private final Animal parent2;
    private final Random random = new Random();

    public Animal(
            Vector2d position, Direction direction, int energy,
            Genome genes, int dayOfBirth, Animal parent1, Animal parent2
    ) {
        this.position = position;
        this.direction = direction;
        this.genes = genes;
        this.energy = energy;

        this.dayOfBirth = dayOfBirth;
        this.parent1 = parent1;
        this.parent2 = parent2;

        if (parent1 != null) {
            parent1.childrenAmount += 1;
            parent1.addOffspring();
        }
        if (parent2 != null) {
            parent2.childrenAmount += 1;
            parent2.addOffspring();
        }

        // TODO staty
    }

    public Animal(
            Vector2d position, Direction direction, int energy, Genome genes
    ) {
        this(position, direction, energy, genes, 0, null, null);
    }

    //private void addChild() {
    //    this.childrenAmount += 1;
    //    if (parent1 != null) parent1.addOffspring();
    //    if (parent2 != null) parent2.addOffspring();
    //}

    private void addOffspring() {
        this.offspringsAmount += 1;
        if (parent1 != null) parent1.addOffspring();
        if (parent2 != null) parent2.addOffspring();
    }

    public String toString() {
        return this.direction.toString();
    }

    public Vector2d getPosition() { return this.position; }
    public void setPosition(Vector2d position){this.position=position;}
    public Direction getDirection() { return this.direction; }
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

    // TODO staty
    public int getDayOfBirth() { return dayOfBirth; }
    public int getDayOfDeath() { return dayOfDeath; }
    public int getChildrenAmount() { return childrenAmount; }
    public int getOffspringsAmount() { return offspringsAmount; }

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
