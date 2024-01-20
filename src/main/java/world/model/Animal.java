package world.model;

import java.util.Random;

public class Animal implements Comparable<Animal> {

    // Bo czemu nie
    private final Random random = new Random();

    // Atrybuty
    private Direction direction;
    public Direction getDirection() { return this.direction; }

    private Vector2d position;
    public Vector2d getPosition() { return this.position; }
    void setPosition(Vector2d position) { this.position=position; }

    private final Animal parent1;
    private final Animal parent2;

    // Staty
    // 7. Po zatrzymaniu programu można oznaczyć jednego zwierzaka jako wybranego do śledzenia.
    // Od tego momentu (do zatrzymania śledzenia) UI powinien przekazywać
    // nam informacje o jego statusie i historii:
    //   * jaki ma genom,
    private final Genome genes;
    public Genome getGenes() { return genes; }

    //   * która jego część jest aktywowana,
    private int currentGeneNumber;
    public int getCurrentGeneNumber() { return currentGeneNumber; }
    void activateGene(){
        direction = Direction.getDirection(genes.getGene(currentGeneNumber));
        currentGeneNumber = (currentGeneNumber +1)%genes.getLength();
    }

    //   * ile ma energii,
    private int energy;
    public int getEnergy() { return energy; }
    void loseEnergy(int energy) { this.energy -= energy; }

    //   * ile zjadł roślin,
    private int grassEaten = 0;
    public int getGrassEaten() { return grassEaten; }
    void eatGrass(int grassEnergy) {
        this.energy += grassEnergy;
        this.grassEaten += 1;
    }

    //   * ile posiada dzieci,
    private int childrenAmount = 0;
    public int getChildrenAmount() { return childrenAmount; }

    //   * ile posiada potomków (niekoniecznie będących bezpośrednio dziećmi),
    private int offspringsAmount = 0;
    public int getOffspringsAmount() { return offspringsAmount; }
    private void addOffspring() {
        this.offspringsAmount += 1;
        if (parent1 != null) parent1.addOffspring();
        if (parent2 != null) parent2.addOffspring();
    }

    //   * ile dni już żyje (jeżeli żyje),
    private final int dayOfBirth;
    public int getDayOfBirth() { return dayOfBirth; }

    //   * którego dnia zmarło (jeżeli żywot już skończyło).
    // Nie jestem pewien, czy to dobry pomysł, ale ogólnie
    // jak tu jest null to jest żywy i to nam pozwala na
    // 1-linijkowy getter
    private Integer dayOfDeath;
    public Integer getDayOfDeath() { return dayOfDeath; }
    void die(int day) { this.dayOfDeath = day; }

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
    }

    public Animal(
            Vector2d position, Direction direction, int energy, Genome genes
    ) {
        this(position, direction, energy, genes, 0, null, null);
    }

    void move() {
        Vector2d dirVector = direction.toUnitVector();
        position.add(dirVector);
    }

    @Override
    public int compareTo(Animal other) {
        // Porównujemy po parametrze 1
        int result = Integer.compare(other.getEnergy(), getEnergy());
        if (result == 0) {
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

    public String toString() { return this.direction.toString(); }

}
