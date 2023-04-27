package fields;

public class TaxField extends Field{
    public TaxField(String name) {
        super(name, "none");
    }

    @Override
    public String toString() {
        return "🏦" + super.name();
    }
}
