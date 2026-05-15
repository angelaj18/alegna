public class HrApprovedHeroicsStrategy implements ResolutionStrategy {

    private static final int DIFFICULTY_MULTIPLIER = 4;

    @Override
    public boolean isSuccessful(Hero hero, Incident incident) {
        int capabilityScore = hero.getPowerLevel() - hero.getStressLevel();
        return capabilityScore >= incident.getDifficultyScore() * DIFFICULTY_MULTIPLIER;
    }
}
