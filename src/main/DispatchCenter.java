import java.util.List;

public class DispatchCenter {

    private final ResolutionStrategy hrApprovedHeroics;
    private final ResolutionStrategy managerIsNotHere;
    private final GameEventListener eventListener;

    public DispatchCenter(
        ResolutionStrategy hrApprovedHeroics,
        ResolutionStrategy managerIsNotHere,
        GameEventListener eventListener
    ) {
        this.hrApprovedHeroics = hrApprovedHeroics;
        this.managerIsNotHere = managerIsNotHere;
        this.eventListener = eventListener;
    }

    public TurnReport resolveTurn(
        List<City> cities,
        List<Hero> heroes,
        List<Incident> incidentsInDispatchOrder,
        List<Hero> assignedHeroes
    ) {
        if (incidentsInDispatchOrder.size() != assignedHeroes.size()) {
            throw new IllegalArgumentException(
                "Incidents and assignments lists must have the same length."
            );
        }
        TurnReport report = new TurnReport();

        for (int i = 0; i < incidentsInDispatchOrder.size(); i++) {
            Incident incident = incidentsInDispatchOrder.get(i);
            Hero assignedHero = assignedHeroes.get(i);

            if (assignedHero == null || !assignedHero.isAvailable()) {
                applyUnresolvedIncidentImpact(incident.getCity(), report, incident);
                notifyListener(new DispatchEvent(DispatchOutcome.UNRESOLVED, incident, null));
                continue;
            }

            assignedHero.dispatchToMission();
            ResolutionStrategy strategy = strategyFor(incident);
            boolean success = strategy.isSuccessful(assignedHero, incident);
            applyOutcome(incident, assignedHero, success, report);
            DispatchOutcome outcome =
                success ? DispatchOutcome.RESOLVED_SUCCESS : DispatchOutcome.RESOLVED_FAILURE;
            notifyListener(new DispatchEvent(outcome, incident, assignedHero));
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

    private void notifyListener(DispatchEvent event) {
        if (eventListener != null) {
            eventListener.onDispatchEvent(event);
        }
    }
}
