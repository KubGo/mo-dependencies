package objects.modelica;

import java.util.List;

public interface IRedeclarable<T> {
    T redeclare(List<Redeclaration> redeclarations);
}
