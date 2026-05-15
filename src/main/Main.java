import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<City> cities = new ArrayList<>();
        cities.add(new City("Skyline Bay", 75, 70));
        cities.add(new City("Metro Vale", 80, 65));

        List<Hero> heroes = new ArrayList<>();
        heroes.add(new Hero("Nova Shield", 88));
        heroes.add(new Hero("Velocity Arc", 72));
        heroes.add(new Hero("Titan Vale", 93));
        heroes.add(new Hero("Echo Warden", 67));

        IncidentGenerator generator = new IncidentGenerator();
        List<Incident> incidents = generator.generateIncidents(cities);

        DispatchCenter dispatchCenter =
            new DispatchCenter(new HrApprovedHeroicsStrategy(), new ManagerIsNotHereStrategy());
        TurnReport report = dispatchCenter.resolveTurn(cities, heroes, incidents);

        System.out.println("=== Alegna Dispach: Sprint 2 Prototype ===");
        report.print();
    }
}
