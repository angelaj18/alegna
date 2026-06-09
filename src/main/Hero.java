public class Hero {
    private static final int RECOVERING_STRESS_THRESHOLD = 30;
    private static final int CLEAR_RECOVERING_STRESS = 25;
    private static final int MAX_READY_STRESS = 95;

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
        return state.canBeDispatched() && stressLevel < MAX_READY_STRESS;
    }

    public void dispatchToMission() {
        state.onDispatched(this);
    }

    public void recoverAfterTurn() {
        state.onShiftEnd(this);
    }

    static int getRecoveringStressThreshold() {
        return RECOVERING_STRESS_THRESHOLD;
    }

    static int getClearRecoveringStress() {
        return CLEAR_RECOVERING_STRESS;
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
