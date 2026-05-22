public class DispatchEvent {
    private final DispatchOutcome outcome;
    private final Incident incident;
    private final Hero hero;

    public DispatchEvent(DispatchOutcome outcome, Incident incident, Hero hero) {
        this.outcome = outcome;
        this.incident = incident;
        this.hero = hero;
    }

    public DispatchOutcome getOutcome() {
        return outcome;
    }

    public Incident getIncident() {
        return incident;
    }

    public Hero getHero() {
        return hero;
    }
}
