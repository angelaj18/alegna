import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DispatchCenter {

    private final ResolutionStrategy hrApprovedHeroics;
    private final ResolutionStrategy managerIsNotHere;

    public DispatchCenter(ResolutionStrategy hrApprovedHeroics, ResolutionStrategy managerIsNotHere) {
        this.hrApprovedHeroics = hrApprovedHeroics;
        this.managerIsNotHere = managerIsNotHere;
    }

    public TurnReport resolveTurn(List<City> cities, List<Hero> heroes, List<Incident> incidents) {
        TurnReport report = new TurnReport();

        List<Incident> orderedIncidents = new ArrayList<>(incidents);
        orderedIncidents.sort(Comparator.comparingInt(Incident::getDifficultyScore).reversed());

        for (Incident incident : orderedIncidents) {
            Hero assignedHero = findBestAvailableHero(heroes);
            if (assignedHero == null) {
                applyUnresolvedIncidentImpact(incident.getCity(), report, incident);
                continue;
            }

            assignedHero.dispatchToMission();
            ResolutionStrategy strategy = strategyFor(incident);
            boolean success = strategy.isSuccessful(assignedHero, incident);
            applyOutcome(incident, assignedHero, success, report);
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

    private Hero findBestAvailableHero(List<Hero> heroes) {
        Hero bestHero = null;
        for (Hero hero : heroes) {
            if (!hero.isAvailable()) {
                continue;
            }
            if (bestHero == null || hero.getPowerLevel() > bestHero.getPowerLevel()) {
                bestHero = hero;
            }
        }
        return bestHero;
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
                    + incident.getType()
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
                + incident.getType()
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
                + incident.getType()
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
}
