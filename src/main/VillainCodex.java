import java.util.Random;

public final class VillainCodex {
    private static final Random RANDOM = new Random();

    private static final String[] CODENAMES = {
        "The Clipboard Kaiser",
        "Parking Lot Phantom",
        "Synergy Serpent",
        "The PTO Revenant",
        "Spreadsheet Sorcerer",
        "Zoom Goblin",
        "The Stapler Sovereign",
        "Reply-All Lich",
        "Middle Management Mothman",
        "The Dumpster Phoenix",
        "The Memo Hydra",
        "Casual Friday Fiend",
        "The Copier Wraith",
    };

    private VillainCodex() {}

    public static String randomCodename() {
        return CODENAMES[RANDOM.nextInt(CODENAMES.length)];
    }
}
