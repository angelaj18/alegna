public class RecoveringState implements HeroState {

    @Override
    public boolean canBeDispatched() {
        return false;
    }

    @Override
    public void onDispatched(Hero hero) {
        // HR has not cleared this hero for redeployment yet.
    }

    @Override
    public void onShiftEnd(Hero hero) {
        hero.applyShiftRecovery();
        if (hero.getStressLevel() < Hero.getClearRecoveringStress()) {
            hero.setState(new ReadyState());
        }
    }

    @Override
    public String getStatusLabel() {
        return "Recovering (HR hold)";
    }
}
