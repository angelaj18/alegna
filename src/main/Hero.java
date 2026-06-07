public class Hero {
    private final String name;
    private final int powerLevel;
    private int stressLevel;
    private HeroState state;

    public Hero(String name, int powerLevel) {
        this.name = name;
        this.powerLevel = powerLevel;
        this.stressLevel = 10;
        this.state = new ReadyState();
    }

    public String getName() {
        return name;
    }

    public int getPowerLevel() {
        return powerLevel;
    }

    public int getStressLevel() {
        return stressLevel;
    }

    public String getStatusLabel() {
        return state.getStatusLabel();
    }

    public boolean isAvailable() {
        return state.canBeDispatched();
    }

    public void dispatchToMission() {
        state.onDispatched(this);
    }

    public void recoverAfterTurn() {
        state.onShiftEnd(this);
    }

    void setState(HeroState state) {
        this.state = state;
    }

    void applyMissionStress() {
        stressLevel = Math.min(100, stressLevel + 20);
    }

    void applyShiftRecovery() {
        stressLevel = Math.max(0, stressLevel - 5);
    }
}
