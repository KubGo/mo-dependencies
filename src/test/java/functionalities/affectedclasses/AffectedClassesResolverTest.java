package functionalities.affectedclasses;

import dependencies.DependencyTree;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.Utils;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AffectedClassesResolverTest {

	static DependencyTree tree;
	static String buildingLibraryPath;
	static AffectedClassesResolver affectedClassesResolver;

	private final List<String> bouncingBallAffectedClasses = Stream.of(
			"BuildingsLite.Tests.BouncingBall", "BuildingsLite.Tests.SimpleModel",
			"BuildingsLite.Tests.MultipleExtendsModel").sorted().toList();

	@BeforeAll
	static void setUp() {
		tree = new DependencyTree();
		buildingLibraryPath = Utils.getPathAsString("BuildingsLite");
		tree.generateLibraryDependencies(buildingLibraryPath, "BuildingsLite");
		affectedClassesResolver = new AffectedClassesResolver(tree.getClassDependenciesMap());
	}

	@Test
	void getAffectedClasses_BouncingBall_classesMatch() {
		List<String> affectedClasses = affectedClassesResolver.getAffectedClasses("BuildingsLite.Tests.BouncingBall");
		assertEquals(bouncingBallAffectedClasses, affectedClasses);
	}


}