package objects.modelica;

import java.util.List;

public interface IDeclarationsResolver {
    IDeclarationsResolver resolveDeclaration(List<Declaration> declarations);
}
