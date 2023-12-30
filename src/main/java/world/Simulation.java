package world;

import world.model.*;

import java.util.ArrayList;
import java.util.List;

public class Simulation implements Runnable {
    private final List<Animal> animals;
    private final List<MoveDirection> moves;
    private final WorldMap map;
    private int frameNum;

    public Simulation(List<Vector2d> startingPositions,
    List<MoveDirection> moves, WorldMap map) {
        int amount = startingPositions.size();
        animals = new ArrayList<Animal>();
        this.moves = moves;
        this.map = map;

        for (int i = 0; i < amount; i++) {
            Animal animal = new Animal(startingPositions.get(i));
            try {
                map.place(animal);
                animals.add(animal);
            } catch (PositionAlreadyOccupiedException ignored) {}
        }
    }

    private void prepare() {
        this.frameNum = 0;
    }

    private void frame() {
        int curAnimalN = frameNum % this.animals.size();
        Animal curAnimal = this.animals.get(curAnimalN);
        map.move(curAnimal, moves.get(frameNum));
        frameNum++;
    }

    public void run() {
        for (int i = 0; i < moves.size(); i++) {
            frame();
            try { Thread.sleep(250); }
            catch (InterruptedException e) { throw new RuntimeException(e); }
        }
    }
}