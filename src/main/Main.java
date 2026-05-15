import java.util.List;

public class Main {
    public static void main(String[] args) {
        GameWorldFactory worldFactory = new DefaultGameWorldFactory();
        List<City> cities = worldFactory.createCities();
        List<Hero> heroes = worldFactory.createHeroes();

        IncidentGenerator generator = new IncidentGenerator();
        List<Incident> incidents = generator.generateIncidents(cities);

        DispatchCenter dispatchCenter =
            new DispatchCenter(new HrApprovedHeroicsStrategy(), new ManagerIsNotHereStrategy());
        TurnReport report = dispatchCenter.resolveTurn(cities, heroes, incidents);

        System.out.println("=== Alegna Dispach: Sprint 2 Prototype ===");
        report.print();
    }
}
