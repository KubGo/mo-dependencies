package objects.modelica;

public class RedeclarationBuilder implements IBuilder<Redeclaration> {
    private String component = null;
    private String className = null;
    private String constrainingClass = null;

    public RedeclarationBuilder setComponent(String component) {
        this.component = component;
        return this;
    }

    public RedeclarationBuilder setClassName(String className) {
        this.className = className;
        return this;
    }

    public RedeclarationBuilder setConstrainingClass(String constrainingClass) {
        this.constrainingClass = constrainingClass;
        return this;
    }

    @Override
    public Redeclaration build() {
        return new Redeclaration(component, className, constrainingClass);
    }

    @Override
    public boolean isReady() {
        return component != null && className != null;
    }

    @Override
    public void reset() {
        className = null;
        component = null;
        constrainingClass = null;
    }
}