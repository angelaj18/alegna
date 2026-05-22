public class HrDispatchFeed implements GameEventListener {

    @Override
    public void onDispatchEvent(DispatchEvent event) {
        Incident incident = event.getIncident();
        String where = incident.getCity().getName();
        String what = incident.getStoryTitle();

        switch (event.getOutcome()) {
            case RESOLVED_SUCCESS:
                System.out.println(
                    "[HR Dispatch Log] "
                        + event.getHero().getName()
                        + " closed \""
                        + what
                        + "\" in "
                        + where
                        + ". Filed under: hero did a thing (approved)."
                );
                break;
            case RESOLVED_FAILURE:
                System.out.println(
                    "[HR Dispatch Log] "
                        + event.getHero().getName()
                        + " could not close \""
                        + what
                        + "\" in "
                        + where
                        + ". Strongly worded sigh queued for review."
                );
                break;
            case UNRESOLVED:
                System.out.println(
                    "[HR Dispatch Log] No assignee for \""
                        + what
                        + "\" in "
                        + where
                        + ". Manager still not here; ticket escalated to chaos."
                );
                break;
            default:
                break;
        }
    }
}
