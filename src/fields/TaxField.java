package fields;

public class TaxField extends Field{


    private int tax;
    public TaxField(String name, int tax) {
        super(name, "none");
        this.tax = tax;
    }

    @Override
    public String toString() {
        return "🏦" + super.name();
    }

    public int getTax() {
        return tax;
    }
}
