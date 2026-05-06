package dependencies.classesinfo;

import org.junit.jupiter.api.Test;
import utils.Utils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParentDependenciesResolverTest {

    static final List<String> partialSimpleModelClasses = Stream.of(
            "Modelica.Units.SI.Length",
            "Modelica.Units.SI.Area",
            "HeatTransfer.Radiosity.Constant"
    ).sorted().toList();
    static final List<String> SimpleModelClasses = Stream.of(
            "Modelica.Units.SI.Length",
            "Modelica.Units.SI.Area",
            "HeatTransfer.Radiosity.Constant",
            "HeatTransfer.Conduction.SingleLayer",
            "Controls.Discrete.BooleanDelay",
            "BuildingsLite.HeatTransfer.Data.Solids.Concrete",
            "Airflow.Multizone.MediumColumn",
            "BouncingBall",
            "OtherLibrary.HeatTransfer.Conduction.DiscretizedConduction"
    ).sorted().toList();
    TreeMap<String, ClassDependenciesResolver> baseTree = new TreeMap<>();
    Map<String, String> modelPaths = Map.of(
            "SimpleModel", Utils.SimpleModel,
            "PartialSimpleModel", Utils.PartialSimpleModel
    );

    @Test
    void resolveParentDependencies_twoElementsTree_extendingDependenciesResolved() {
        modelPaths.forEach((name, path) -> {
            try {
                ClassDependenciesResolver classDependenciesResolver = new ClassDependenciesResolver(name, Utils.getModelicaTextFromResources(path));
                classDependenciesResolver.resolveInternalDependencies();
                baseTree.put(name, classDependenciesResolver);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        ParentDependenciesResolver parentDependenciesResolver = new ParentDependenciesResolver(
                baseTree
        );
        TreeMap<String, ClassDependenciesResolver> resolvedTree = parentDependenciesResolver.resolveParentDependencies(baseTree);

        assertEquals(
                partialSimpleModelClasses,
                resolvedTree.get("PartialSimpleModel")
                        .getAbsolutePathsClassList()
                        .stream().sorted()
                        .toList()
        );
        assertEquals(
                SimpleModelClasses,
                resolvedTree.get("SimpleModel")
                        .getAbsolutePathsClassList()
                        .stream().sorted()
                        .toList()
        );

    }

}