package fields;

public class JailField extends SpecialField{
    public JailField() {
        super("⛓Jail");
    }

    @Override
    public String toString() {
        return super.name();
    }
}
