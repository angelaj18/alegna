import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DispatchCenter {

    private final ResolutionStrategy resolutionStrategy;

    public DispatchCenter(ResolutionStrategy resolutionStrategy) {
        this.resolutionStrategy = resolutionStrategy;
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
            boolean success = resolutionStrategy.isSuccessful(assignedHero, incident);
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
        );
    }
}
