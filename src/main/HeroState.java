public interface HeroState {
    boolean canBeDispatched();

    void onDispatched(Hero hero);

    void onShiftEnd(Hero hero);

    String getStatusLabel();
}
