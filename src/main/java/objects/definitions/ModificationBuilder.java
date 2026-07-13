package objects.definitions;

import java.util.List;

public class ModificationBuilder implements IBuilder<Modification> {
    private String component = null;
    private String value = null;
    private String constrainingClass = null;

    public ModificationBuilder setComponent(List<String> componentNames) {
        this.component = String.join(".", componentNames);
        return this;
    }
    public ModificationBuilder setComponent(String component) {
        this.component = component;
        return this;
    }

    public ModificationBuilder setValue(String value) {
        this.value = value;
        return this;
    }

    public ModificationBuilder setConstrainingClass(String constrainingClass) {
        this.constrainingClass = constrainingClass;
        return this;
    }

    @Override
    public Modification build() {
        return new Modification(component, value, constrainingClass);
    }

    @Override
    public boolean isReady() {
        return component != null && value != null;
    }

    @Override
    public void reset() {
        this.component = null;
        this.value = null;
        this.constrainingClass = null;
    }
}