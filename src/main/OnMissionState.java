public class OnMissionState implements HeroState {

    @Override
    public boolean canBeDispatched() {
        return false;
    }

    @Override
    public void onDispatched(Hero hero) {
        // Hero is already deployed.
    }

    @Override
    public void onShiftEnd(Hero hero) {
        hero.applyShiftRecovery();
        hero.setState(new ReadyState());
    }

    @Override
    public String getStatusLabel() {
        return "On mission";
    }
}
