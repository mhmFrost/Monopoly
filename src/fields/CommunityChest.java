package fields;

public class CommunityChest extends Field{
    public CommunityChest() {
        super("Community Chest", "none");
    }

    @Override
    public String toString() {
        return "♦️" + super.name();
    }
}
