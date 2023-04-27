package fields;

public class ChanceField extends Field{
    public ChanceField() {
        super("Chance", "none");
    }

    @Override
    public String toString() {
        return "❓" + super.name();
    }
}
