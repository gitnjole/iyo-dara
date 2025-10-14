package iyo.dara.menza.domain;

public enum Store {
    McDonalds("McDonald's"),
    Chill_Kantina("Chill Kantina"),
    Supermarket("Supermarket"),
    Takeout("Takeout");

    private final String displayName;

    Store(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
