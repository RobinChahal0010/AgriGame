package models;

public class Crop {
    private final String name;
    private final int requiredGrowthTicks;
    private int currentTicks = 0;
    private final int marketValue;

    public Crop(String name, int requiredGrowthTicks, int marketValue) {
        this.name = name;
        this.requiredGrowthTicks = requiredGrowthTicks;
        this.marketValue = marketValue;
    }

    public void grow() {
        if (currentTicks < requiredGrowthTicks) {
            currentTicks++;
        }
    }

    public boolean isFullyGrown() {
        return currentTicks >= requiredGrowthTicks;
    }

    public String getName() { return name; }
    public int getMarketValue() { return marketValue; }
    public int getCurrentTicks() { return currentTicks; }
    public int getRequiredGrowthTicks() { return requiredGrowthTicks; }
}
