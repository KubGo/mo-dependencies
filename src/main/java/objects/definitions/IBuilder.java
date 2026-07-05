package objects.definitions;

public interface IBuilder<T> {
    T build();

    boolean isReady();
}
