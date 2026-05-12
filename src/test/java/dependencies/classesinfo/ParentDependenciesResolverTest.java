package dependencies.classesinfo;

import dependencies.DependencyTreeResolver;
import org.junit.jupiter.api.Test;
import utils.Utils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParentDependenciesResolverTest {

    static final List<String> partialSimpleModelClassesTrivialExample = Stream.of(
            "Modelica.Units.SI.Length",
            "Modelica.Units.SI.Area",
            "HeatTransfer.Radiosity.Constant"
    ).sorted().toList();
    static final List<String> simpleModelClassesTrivialExample = Stream.of(
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
    static final List<String> partialSimpleModelClassesBuildingLite = Stream.of(
            "Modelica.Units.SI.Length",
            "Modelica.Units.SI.Area",
            "BuildingsLite.HeatTransfer.Radiosity.Constant"
    ).sorted().toList();
    static final List<String> simpleModelClassesBuildingLite = Stream.of(
            "Modelica.Units.SI.Length",
            "Modelica.Units.SI.Area",
            "BuildingsLite.HeatTransfer.Radiosity.Constant",
            "BuildingsLite.HeatTransfer.Conduction.SingleLayer",
            "BuildingsLite.Controls.Discrete.BooleanDelay",
            "BuildingsLite.HeatTransfer.Data.Solids.Concrete",
            "BuildingsLite.Airflow.Multizone.MediumColumn",
            "BuildingsLite.Tests.BouncingBall",
            "OtherLibrary.HeatTransfer.Conduction.DiscretizedConduction"
    ).sorted().toList();

    static final List<String> multipleExtendsModelClasses = Stream.of(
            "Modelica.Units.SI.Length",
            "Modelica.Units.SI.Area",
            "BuildingsLite.HeatTransfer.Radiosity.Constant",
            "BuildingsLite.HeatTransfer.Conduction.SingleLayer",
            "BuildingsLite.Controls.Discrete.BooleanDelay",
            "BuildingsLite.HeatTransfer.Data.Solids.Concrete",
            "BuildingsLite.Airflow.Multizone.MediumColumn",
            "BuildingsLite.Tests.BouncingBall",
            "OtherLibrary.HeatTransfer.Conduction.DiscretizedConduction",
            "BuildingsLite.Controls.Predictors.ElectricalLoad"
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

        ParentDependenciesResolver<ClassDependenciesResolver> parentDependenciesResolver = new ParentDependenciesResolver(
                baseTree
        );
        TreeMap<String, ClassDependenciesResolver> resolvedTree = parentDependenciesResolver.resolveParentDependencies(baseTree);
        assertEquals(
                partialSimpleModelClassesTrivialExample,
                resolvedTree.get("PartialSimpleModel")
                        .getClasses()
                        .stream().sorted()
                        .toList()
        );
        assertEquals(
                simpleModelClassesTrivialExample,
                resolvedTree.get("SimpleModel")
                        .getClasses()
                        .stream().sorted()
                        .toList()
        );
    }

    @Test
    void resolveParentDependencies_BuildingsLite_resolveWholeLibraryDependencies() {
        DependencyTreeResolver tree = new DependencyTreeResolver();
        String buildingLibraryPath = Utils.getPathAsString(Utils.BuildingsLite);
        tree.generateLibraryDependencies(buildingLibraryPath, "BuildingsLite");
        tree.includeParentsDependentClasses();
        assertEquals(
                partialSimpleModelClassesBuildingLite,
                tree.getClassDependencies("BuildingsLite.Tests.PartialSimpleModel").getClasses()
                        .stream().sorted().toList()
        );
        assertEquals(
                simpleModelClassesBuildingLite,
                tree.getClassDependencies("BuildingsLite.Tests.SimpleModel").getClasses()
                        .stream().sorted().toList()
        );
        assertEquals(
                multipleExtendsModelClasses,
                tree.getClassDependencies("BuildingsLite.Tests.MultipleExtendsModel").getClasses()
                        .stream().sorted().toList()
        );
    }
}