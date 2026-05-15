public class ManagerIsNotHereStrategy implements ResolutionStrategy {

    private static final int DIFFICULTY_MULTIPLIER = 5;

    @Override
    public boolean isSuccessful(Hero hero, Incident incident) {
        int capabilityScore = hero.getPowerLevel() - hero.getStressLevel();
        return capabilityScore >= incident.getDifficultyScore() * DIFFICULTY_MULTIPLIER;
    }
}
