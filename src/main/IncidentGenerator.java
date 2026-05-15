import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IncidentGenerator {
    private static final int MIN_SCALE = 1;
    private static final int MAX_SCALE = 5;
    private final Random random;

    public IncidentGenerator() {
        this.random = new Random();
    }

    public List<Incident> generateIncidents(List<City> cities) {
        List<Incident> incidents = new ArrayList<>();
        IncidentType[] types = IncidentType.values();

        for (City city : cities) {
            IncidentType type = types[random.nextInt(types.length)];
            int severity = random.nextInt(MAX_SCALE - MIN_SCALE + 1) + MIN_SCALE;
            int urgency = random.nextInt(MAX_SCALE - MIN_SCALE + 1) + MIN_SCALE;
            if (type == IncidentType.VILLAIN_ATTACK) {
                incidents.add(
                    new Incident(type, city, severity, urgency, VillainCodex.randomCodename())
                );
            } else {
                incidents.add(new Incident(type, city, severity, urgency));
            }
        }

        return incidents;
    }
}
