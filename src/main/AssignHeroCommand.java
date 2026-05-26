public class AssignHeroCommand implements DispatchCommand {
    private final Hero hero;
    private final Incident incident;

    public AssignHeroCommand(Hero hero, Incident incident) {
        this.hero = hero;
        this.incident = incident;
    }

    public Hero getHero() {
        return hero;
    }

    public Incident getIncident() {
        return incident;
    }

    @Override
    public void execute(DispatchCenter center, TurnReport report) {
        center.dispatchHeroToIncident(hero, incident, report);
    }
}
