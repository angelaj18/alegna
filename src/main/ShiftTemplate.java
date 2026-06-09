import java.util.List;

public abstract class ShiftTemplate {
    protected final List<City> cities;
    protected final List<Hero> heroes;
    protected final DispatchCenter dispatchCenter;

    protected ShiftTemplate(List<City> cities, List<Hero> heroes, DispatchCenter dispatchCenter) {
        this.cities = cities;
        this.heroes = heroes;
        this.dispatchCenter = dispatchCenter;
    }

    public final TurnReport runShift(int shiftNumber) {
        onShiftStart(shiftNumber);
        List<Incident> incidents = generateAndOrderIncidents();
        List<DispatchCommand> commands = collectCommands(incidents, shiftNumber);
        TurnReport report = dispatchCenter.resolveTurnFromCommands(cities, heroes, commands);
        onShiftComplete(report, shiftNumber);
        return report;
    }

    protected abstract void onShiftStart(int shiftNumber);

    protected abstract List<Incident> generateAndOrderIncidents();

    protected abstract List<DispatchCommand> collectCommands(List<Incident> incidents, int shiftNumber);

    protected abstract void onShiftComplete(TurnReport report, int shiftNumber);
}
