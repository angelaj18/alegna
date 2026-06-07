import java.util.ArrayList;
import java.util.List;

public class TurnReport {
    private final List<String> events;
    private final List<String> cityStatuses;
    private final List<String> heroStatuses;

    public TurnReport() {
        this.events = new ArrayList<>();
        this.cityStatuses = new ArrayList<>();
        this.heroStatuses = new ArrayList<>();
    }

    public void addEvent(String event) {
        events.add(event);
    }

    public void addCityStatus(City city) {
        cityStatuses.add(
            city.getName()
                + " | Safety: "
                + city.getSafetyScore()
                + " | Public Trust: "
                + city.getPublicTrust()
        );
    }

    public void addHeroStatus(Hero hero) {
        heroStatuses.add(
            hero.getName()
                + " | Status: "
                + hero.getStatusLabel()
                + " | Power: "
                + hero.getPowerLevel()
                + " | Stress: "
                + hero.getStressLevel()
        );
    }

    public void print() {
        System.out.println();
        System.out.println("Incident Outcomes:");
        for (String event : events) {
            System.out.println("- " + event);
        }

        System.out.println();
        System.out.println("City Status:");
        for (String status : cityStatuses) {
            System.out.println("- " + status);
        }

        System.out.println();
        System.out.println("Hero Status:");
        for (String status : heroStatuses) {
            System.out.println("- " + status);
        }
    }
}
