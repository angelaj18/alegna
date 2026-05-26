public class AuditTrailListener implements GameEventListener {

    @Override
    public void onDispatchEvent(DispatchEvent event) {
        String heroLabel =
            event.getHero() != null ? event.getHero().getName() : "unassigned";
        System.out.println(
            "[Audit] "
                + event.getOutcome()
                + " | "
                + event.getIncident().getStoryTitle()
                + " @ "
                + event.getIncident().getCity().getName()
                + " | hero="
                + heroLabel
        );
    }
}
