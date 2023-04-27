package fields;

public class FreeParkingField extends SpecialField{
    public FreeParkingField() {
        super("🚗Free Parking");
    }

    @Override
    public String toString() {
        return super.name();
    }
}
