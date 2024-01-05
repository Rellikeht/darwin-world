package world.model;

public class Animal {

    // Atrybuty
    private Direction direction;
    private Vector2d position;
    private final Genome genes;
    private int energy;

    // Staty
    private int grassEaten = 0;
    private int dayOfBirth;
    private int dayOfDeath;
    private int childrenAmount = 0;

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
            case D0 -> "u";
            case D45 -> "ur";
            case D90 -> "r";
            case D135 -> "dr";
            case D180 -> "d";
            case D225 -> "dl";
            case D270 -> "l";
            case D315 -> "ul";
        };
    }

    public Vector2d getPosition() { return this.position; }
    public Direction getOrientation() { return this.direction; }

    public int getEnergy() {
        return energy;
    }
    public Genome getGenes(){return genes;}

    public void loseEnergy(int energy) { this.energy -= energy; }

    public void eatGrass(int grassEnergy) {
        this.energy += grassEnergy;
        this.grassEaten += 1;
    }

    public void die(int day) {
        this.dayOfDeath = day;
    }

    // TODO tu pasuje ustalić też jak liczymy ilosć potomków
    public void addChildren() { this.childrenAmount += 1; }

    // TODO staty
    public int getDayOfBirth(){return dayOfBirth;}
    public int getDayOfDeath(){return dayOfDeath;}
    public int getChildrenAmount(){return childrenAmount;}

}