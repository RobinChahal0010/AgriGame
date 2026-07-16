package grid;

import models.Crop;

public class FieldPatch {
    public enum PatchType { SOIL, ROAD, MARKET }
    public enum PatchState { EMPTY, PLOWED, SEEDED, READY_FOR_HARVEST }
    
    private final PatchType type;
    private PatchState state = PatchState.EMPTY;
    private Crop currentCrop = null;

    public FieldPatch(PatchType type) {
        this.type = type;
    }

    public synchronized void plow() {
        if (type == PatchType.SOIL && state == PatchState.EMPTY) {
            state = PatchState.PLOWED;
        }
    }

    public synchronized void plantCrop(Crop crop) {
        if (type == PatchType.SOIL && state == PatchState.PLOWED) {
            this.currentCrop = crop;
            this.state = PatchState.SEEDED;
        }
    }

    public synchronized Crop harvest() {
        if (state != PatchState.READY_FOR_HARVEST) {
            throw new IllegalStateException("Crop not ready!");
        }
        Crop harvested = currentCrop;
        this.currentCrop = null;
        this.state = PatchState.EMPTY;
        return harvested;
    }

    public synchronized void updateGrowth() {
        if (type == PatchType.SOIL && state == PatchState.SEEDED && currentCrop != null) {
            currentCrop.grow();
            if (currentCrop.isFullyGrown()) {
                state = PatchState.READY_FOR_HARVEST;
            }
        }
    }

    public PatchType getType() { return type; }
    public synchronized PatchState getState() { return state; }
    public synchronized Crop getCurrentCrop() { return currentCrop; }
}
