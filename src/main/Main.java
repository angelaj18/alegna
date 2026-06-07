import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GameWorldFactory worldFactory = new DefaultGameWorldFactory();
        List<City> cities = worldFactory.createCities();
        List<Hero> heroes = worldFactory.createHeroes();

        IncidentGenerator generator = new IncidentGenerator();
        List<Incident> incidents = generator.generateIncidents(cities);

        List<Incident> ordered = new ArrayList<>(incidents);
        ordered.sort(Comparator.comparingInt(Incident::getDifficultyScore).reversed());

        Scanner scanner = new Scanner(System.in);
        List<DispatchCommand> dispatchCommands = collectDispatchCommands(ordered, heroes, scanner);
        scanner.close();

        DispatchNotifier dispatchNotifier = new DispatchNotifier();
        dispatchNotifier.register(new HrDispatchFeed());
        dispatchNotifier.register(new AuditTrailListener());

        DispatchCenter dispatchCenter =
            new DispatchCenter(
                new HrApprovedHeroicsStrategy(),
                new ManagerIsNotHereStrategy(),
                dispatchNotifier
            );
        TurnReport report = dispatchCenter.resolveTurnFromCommands(cities, heroes, dispatchCommands);

        System.out.println();
        System.out.println("=== Alegna Dispach: Sprint 4 ===");
        report.print();
    }

    private static List<DispatchCommand> collectDispatchCommands(
        List<Incident> orderedIncidents,
        List<Hero> roster,
        Scanner scanner
    ) {
        List<DispatchCommand> commands = new ArrayList<>();
        List<Hero> stillAvailable = new ArrayList<>(roster);

        System.out.println("=== Dispatch board: assign one responder per incident (hardest first) ===");

        for (int i = 0; i < orderedIncidents.size(); i++) {
            Incident inc = orderedIncidents.get(i);
            DispatchCommand command =
                promptDispatchForIncident(
                    stillAvailable,
                    scanner,
                    i + 1,
                    orderedIncidents.size(),
                    inc
                );
            commands.add(command);
        }

        return commands;
    }

    private static DispatchCommand promptDispatchForIncident(
        List<Hero> stillAvailable,
        Scanner scanner,
        int index,
        int total,
        Incident inc
    ) {
        System.out.println();
        System.out.println(
            "Incident "
                + index
                + " of "
                + total
                + " — "
                + inc.getStoryTitle()
                + " in "
                + inc.getCity().getName()
                + " | pressure "
                + inc.getDifficultyScore()
        );

        if (stillAvailable.isEmpty()) {
            System.out.println("No heroes left to assign. Leaving this incident unresolved.");
            return new SkipIncidentCommand(inc);
        }

        System.out.println("  0 = no dispatch (leave unresolved)");
        for (int h = 0; h < stillAvailable.size(); h++) {
            Hero hero = stillAvailable.get(h);
            System.out.println(
                "  "
                    + (h + 1)
                    + " = "
                    + hero.getName()
                    + " (power "
                    + hero.getPowerLevel()
                    + ", stress "
                    + hero.getStressLevel()
                    + ")"
            );
        }

        while (true) {
            System.out.print("Dispatch which hero? Enter number: ");
            String line = scanner.nextLine().trim();
            int pick;
            try {
                pick = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("  Enter a whole number.");
                continue;
            }
            if (pick == 0) {
                return new SkipIncidentCommand(inc);
            }
            if (pick >= 1 && pick <= stillAvailable.size()) {
                Hero chosen = stillAvailable.remove(pick - 1);
                return new AssignHeroCommand(chosen, inc);
            }
            System.out.println("  Invalid choice; try again.");
        }
    }
}
