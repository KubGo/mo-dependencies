package objects.modelica;

public interface IBuilder<T> {
    T build();

    boolean isReady();

    void reset();
}
