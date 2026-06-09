import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GameWorldFactory worldFactory = new DefaultGameWorldFactory();
        List<City> cities = worldFactory.createCities();
        List<Hero> heroes = worldFactory.createHeroes();

        DispatchNotifier dispatchNotifier = new DispatchNotifier();
        dispatchNotifier.register(new HrDispatchFeed());
        dispatchNotifier.register(new AuditTrailListener());

        DispatchCenter dispatchCenter =
            new DispatchCenter(
                new HrApprovedHeroicsStrategy(),
                new ManagerIsNotHereStrategy(),
                dispatchNotifier
            );

        IncidentGenerator incidentGenerator = new IncidentGenerator();
        Scanner scanner = new Scanner(System.in);

        CliDispatchShift shiftRunner =
            new CliDispatchShift(cities, heroes, dispatchCenter, incidentGenerator, scanner);

        System.out.println("=== Alegna Dispach: Sprint 5 ===");
        for (int shift = 1; shift <= CliDispatchShift.getTotalShifts(); shift++) {
            shiftRunner.runShift(shift);
        }

        scanner.close();
        System.out.println();
        System.out.println("All shifts complete. Dispatch desk signing off.");
    }
}
