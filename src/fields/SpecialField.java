package fields;

public abstract class SpecialField extends Field {
    public SpecialField(String name) {
        super(name, "none");
    }

    @Override
    public String toString() {
        return "SpecialField";
    }
}
