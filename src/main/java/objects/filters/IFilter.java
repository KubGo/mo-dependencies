package objects.filters;

public interface IFilter<T> {
    boolean shouldBeUsed(T valueToCheck);
}
