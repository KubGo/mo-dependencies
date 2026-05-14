package functionalities.tests;

import dependencies.classesinfo.IClassDependencies;

import java.util.*;

public class TestCasesResolver {
	private final Map<String, ? extends IClassDependencies> testingLibraryTree;

	public TestCasesResolver(Map<String, ? extends IClassDependencies> testingLibraryTree) {
		this.testingLibraryTree = testingLibraryTree;
	}

	List<String> getTestCasesToRun(List<String> affectedClasses) {
		Queue<String> modelsToTest = new LinkedList<>(affectedClasses);
		Set<String> results = new HashSet<>();
		String currentClass = modelsToTest.poll();
		while (currentClass != null) {
			String finalCurrentClass = currentClass;
			testingLibraryTree.forEach((name, classDependencies) -> {
				if (classDependencies.isModel()) {
					if (classDependencies.getClasses().contains(finalCurrentClass)) {
						results.add(name);
					}
					if (classDependencies.getParentClasses().contains(finalCurrentClass)) {
						results.add(name);
						modelsToTest.add(name);
					}
				}
			});
			currentClass = modelsToTest.poll();
		}
		return results.stream().toList();
	}
}
