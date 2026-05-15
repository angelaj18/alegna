public class Incident {
    private final IncidentType type;
    private final City city;
    private final int severity;
    private final int urgency;
    private final String villainCodename;

    public Incident(IncidentType type, City city, int severity, int urgency) {
        this(type, city, severity, urgency, null);
    }

    public Incident(IncidentType type, City city, int severity, int urgency, String villainCodename) {
        this.type = type;
        this.city = city;
        this.severity = severity;
        this.urgency = urgency;
        this.villainCodename = villainCodename;
    }

    public String getStoryTitle() {
        switch (type) {
            case VILLAIN_ATTACK:
                return villainCodename != null ? villainCodename : "Unidentified hostile";
            case CIVILIAN_RESCUE:
                return "Civilian rescue";
            case INFRASTRUCTURE_FAILURE:
                return "Infrastructure crisis";
            default:
                return type.name();
        }
    }

    public IncidentType getType() {
        return type;
    }

    public City getCity() {
        return city;
    }

    public int getSeverity() {
        return severity;
    }

    public int getUrgency() {
        return urgency;
    }

    public int getDifficultyScore() {
        return (severity * 3) + (urgency * 2);
    }
}
