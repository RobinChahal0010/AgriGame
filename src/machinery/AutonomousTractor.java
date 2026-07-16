package machinery;

import models.Crop;
import java.util.ArrayList;
import java.util.List;

public class AutonomousTractor {
    private int x = 1; // Start positions
    private int y = 1;
    private final List<Crop> inventory = new ArrayList<>();
    private final int capacity = 5;

    public int getX() { return x; }
    public int getY() { return y; }

    public void move(int dx, int dy, int gridSize) {
        int newX = x + dx;
        int newY = y + dy;
        if (newX >= 0 && newX < gridSize && newY >= 0 && newY < gridSize) {
            this.x = newX;
            this.y = newY;
        }
    }

    public boolean addToInventory(Crop crop) {
        if (inventory.size() < capacity) {
            inventory.add(crop);
            return true;
        }
        return false;
    }

    public List<Crop> clearInventory() {
        List<Crop> soldCrops = new ArrayList<>(inventory);
        inventory.clear();
        return soldCrops;
    }

    public int getInventoryCount() {
        return inventory.size();
    }
}
