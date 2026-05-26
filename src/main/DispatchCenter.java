import java.util.List;

public class DispatchCenter {

    private final ResolutionStrategy hrApprovedHeroics;
    private final ResolutionStrategy managerIsNotHere;
    private final DispatchNotifier dispatchNotifier;

    public DispatchCenter(
        ResolutionStrategy hrApprovedHeroics,
        ResolutionStrategy managerIsNotHere,
        DispatchNotifier dispatchNotifier
    ) {
        this.hrApprovedHeroics = hrApprovedHeroics;
        this.managerIsNotHere = managerIsNotHere;
        this.dispatchNotifier = dispatchNotifier;
    }

    public TurnReport resolveTurnFromCommands(
        List<City> cities,
        List<Hero> heroes,
        List<DispatchCommand> commands
    ) {
        TurnReport report = new TurnReport();

        for (DispatchCommand command : commands) {
            command.execute(this, report);
        }

        for (Hero hero : heroes) {
            hero.recoverAfterTurn();
        }

        for (City city : cities) {
            report.addCityStatus(city);
        }

        for (Hero hero : heroes) {
            report.addHeroStatus(hero);
        }

        return report;
    }

    public void dispatchHeroToIncident(Hero hero, Incident incident, TurnReport report) {
        if (hero == null || !hero.isAvailable()) {
            skipIncident(incident, report);
            return;
        }

        hero.dispatchToMission();
        ResolutionStrategy strategy = strategyFor(incident);
        boolean success = strategy.isSuccessful(hero, incident);
        applyOutcome(incident, hero, success, report);
        DispatchOutcome outcome =
            success ? DispatchOutcome.RESOLVED_SUCCESS : DispatchOutcome.RESOLVED_FAILURE;
        notifyListeners(new DispatchEvent(outcome, incident, hero));
    }

    public void skipIncident(Incident incident, TurnReport report) {
        applyUnresolvedIncidentImpact(incident.getCity(), report, incident);
        notifyListeners(new DispatchEvent(DispatchOutcome.UNRESOLVED, incident, null));
    }

    private ResolutionStrategy strategyFor(Incident incident) {
        if (incident.getType() == IncidentType.VILLAIN_ATTACK) {
            return managerIsNotHere;
        }
        return hrApprovedHeroics;
    }

    private void applyOutcome(Incident incident, Hero hero, boolean success, TurnReport report) {
        City city = incident.getCity();
        if (success) {
            city.adjustSafety(5);
            city.adjustTrust(4);
            report.addEvent(
                hero.getName()
                    + " resolved "
                    + incident.getStoryTitle()
                    + " in "
                    + city.getName()
                    + " successfully."
                    + villainOutcomeFlavor(incident, true)
            );
            return;
        }

        city.adjustSafety(-8);
        city.adjustTrust(-6);
        report.addEvent(
            hero.getName()
                + " failed to contain "
                + incident.getStoryTitle()
                + " in "
                + city.getName()
                + "."
                + villainOutcomeFlavor(incident, false)
        );
    }

    private void applyUnresolvedIncidentImpact(City city, TurnReport report, Incident incident) {
        city.adjustSafety(-10);
        city.adjustTrust(-8);
        report.addEvent(
            "No hero available for "
                + incident.getStoryTitle()
                + " in "
                + city.getName()
                + ". Incident remained unresolved."
                + villainUnresolvedFlavor(incident)
        );
    }

    private String villainOutcomeFlavor(Incident incident, boolean success) {
        if (incident.getType() != IncidentType.VILLAIN_ATTACK) {
            return "";
        }
        if (success) {
            return " (Dispatch desk: Manager Is Not Here doctrine — win logged as \"extremely okay.\")";
        }
        return " (Dispatch desk: Manager Is Not Here doctrine — HR is sending a strongly worded sigh.)";
    }

    private String villainUnresolvedFlavor(Incident incident) {
        if (incident.getType() != IncidentType.VILLAIN_ATTACK) {
            return "";
        }
        return " (Nobody could cover; the manager is still not here. Chaos wins this round.)";
    }

    private void notifyListeners(DispatchEvent event) {
        if (dispatchNotifier != null) {
            dispatchNotifier.notifyListeners(event);
        }
    }
}
