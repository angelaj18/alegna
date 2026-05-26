public class SkipIncidentCommand implements DispatchCommand {
    private final Incident incident;

    public SkipIncidentCommand(Incident incident) {
        this.incident = incident;
    }

    public Incident getIncident() {
        return incident;
    }

    @Override
    public void execute(DispatchCenter center, TurnReport report) {
        center.skipIncident(incident, report);
    }
}
