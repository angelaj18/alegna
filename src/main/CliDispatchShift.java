import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class CliDispatchShift extends ShiftTemplate {
    private static final int TOTAL_SHIFTS = 3;

    private final IncidentGenerator incidentGenerator;
    private final Scanner scanner;

    public CliDispatchShift(
        List<City> cities,
        List<Hero> heroes,
        DispatchCenter dispatchCenter,
        IncidentGenerator incidentGenerator,
        Scanner scanner
    ) {
        super(cities, heroes, dispatchCenter);
        this.incidentGenerator = incidentGenerator;
        this.scanner = scanner;
    }

    public static int getTotalShifts() {
        return TOTAL_SHIFTS;
    }

    @Override
    protected void onShiftStart(int shiftNumber) {
        System.out.println();
        System.out.println("=== Shift " + shiftNumber + " of " + TOTAL_SHIFTS + " ===");
    }

    @Override
    protected List<Incident> generateAndOrderIncidents() {
        List<Incident> ordered = new ArrayList<>(incidentGenerator.generateIncidents(cities));
        ordered.sort(Comparator.comparingInt(Incident::getDifficultyScore).reversed());
        return ordered;
    }

    @Override
    protected List<DispatchCommand> collectCommands(List<Incident> orderedIncidents, int shiftNumber) {
        List<DispatchCommand> commands = new ArrayList<>();
        List<Hero> stillAvailable = new ArrayList<>();
        for (Hero hero : heroes) {
            if (hero.isAvailable()) {
                stillAvailable.add(hero);
            }
        }

        System.out.println("=== Dispatch board: assign one responder per incident (hardest first) ===");

        for (int i = 0; i < orderedIncidents.size(); i++) {
            Incident inc = orderedIncidents.get(i);
            DispatchCommand command =
                promptDispatchForIncident(stillAvailable, i + 1, orderedIncidents.size(), inc);
            commands.add(command);
        }

        return commands;
    }

    @Override
    protected void onShiftComplete(TurnReport report, int shiftNumber) {
        System.out.println();
        System.out.println("=== Alegna Dispach: Shift " + shiftNumber + " report ===");
        report.print();
    }

    private DispatchCommand promptDispatchForIncident(
        List<Hero> stillAvailable,
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
                    + " ("
                    + hero.getStatusLabel()
                    + ", power "
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
