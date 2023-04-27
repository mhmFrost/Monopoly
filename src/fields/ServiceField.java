package fields;

public class ServiceField extends Field {
    public ServiceField(String name) {
        super(name, "none");
    }

    @Override
    public String toString() {
        return super.name();
    }
}
