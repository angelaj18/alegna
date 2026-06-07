public class ReadyState implements HeroState {

    @Override
    public boolean canBeDispatched() {
        return true;
    }

    @Override
    public void onDispatched(Hero hero) {
        hero.applyMissionStress();
        hero.setState(new OnMissionState());
    }

    @Override
    public void onShiftEnd(Hero hero) {
        // Already ready for the next assignment.
    }

    @Override
    public String getStatusLabel() {
        return "Ready";
    }
}
