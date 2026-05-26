public interface DispatchCommand {
    void execute(DispatchCenter center, TurnReport report);
}
