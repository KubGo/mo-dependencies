package functionalities.tests;

import dependencies.classesinfo.IClassDependencies;

import java.util.*;

public class TestCasesResolver {
	private final Map<String, IClassDependencies> testingLibraryTree;

	public TestCasesResolver(Map<String, IClassDependencies> testingLibraryTree) {
		this.testingLibraryTree = testingLibraryTree;
	}

	List<String> getTestCasesToRun(List<String> affectedClasses) {
		Queue<String> modelsToTest = new LinkedList<>(affectedClasses);
		Set<String> results = new HashSet<>();
		String currentClass = modelsToTest.poll();
		while (currentClass != null) {
			testingLibraryTree.forEach((name, classDependencies) -> {
				if (classDependencies.getClasses().contains(currentClass)) {
					results.add(name);
				}
				if (classDependencies.getParentClasses().contains(currentClass)) {
					results.add(name);
					modelsToTest.add(name);
				}
			});
		}
		return results.stream().toList();
	}
}
