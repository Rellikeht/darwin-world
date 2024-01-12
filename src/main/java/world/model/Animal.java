package world.model;

import java.util.Random;

public class Animal implements Comparable<Animal> {

    // Atrybuty
    private Direction direction;
    private Vector2d position;
    private final Genome genes;
    private int energy;
    private int currentGeneNumber;

    // Staty
    private int grassEaten = 0;
    private final int dayOfBirth;
    // Nie jestem pewien, czy to dobry pomysł, ale ogólnie
    // jak tu jest null to jest żywy i to nam pozwala na
    // 1-linijkowy getter
    private Integer dayOfDeath;
    private int childrenAmount = 0;
    private int offspringsAmount = 0;

    private final Animal parent1;
    private final Animal parent2;

    // Bo czemu nie
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

        currentGeneNumber = random.nextInt(genes.getLength());

        // TODO staty
    }

    public Animal(
            Vector2d position, Direction direction, int energy, Genome genes
    ) {
        this(position, direction, energy, genes, 0, null, null);
    }

    private void addOffspring() {
        this.offspringsAmount += 1;
        if (parent1 != null) parent1.addOffspring();
        if (parent2 != null) parent2.addOffspring();
    }

    void setPosition(Vector2d position){this.position=position;}
    void loseEnergy(int energy) { this.energy -= energy; }

    void eatGrass(int grassEnergy) {
        this.energy += grassEnergy;
        this.grassEaten += 1;
    }

    void activateGene(){
        direction = Direction.getDirection(genes.getGene(currentGeneNumber));
        currentGeneNumber = (currentGeneNumber +1)%genes.getLength();
    }

    void move(){
        Vector2d dirVector = direction.toUnitVector();
        position.add(dirVector);
    }

    void die(int day) {
        this.dayOfDeath = day;
    }

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

    public Vector2d getPosition() { return this.position; }
    public Direction getDirection() { return this.direction; }
    public int getEnergy() { return energy; }
    public int getCurrentGeneNumber() { return currentGeneNumber; }
    Genome getGenes() { return genes; }


    public String toString() { return this.direction.toString(); }
    public int getDayOfBirth() { return dayOfBirth; }
    public Integer getDayOfDeath() { return dayOfDeath; }
    public int getChildrenAmount() { return childrenAmount; }
    public int getOffspringsAmount() { return offspringsAmount; }

}
