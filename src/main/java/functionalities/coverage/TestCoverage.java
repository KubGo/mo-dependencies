package functionalities.coverage;

import dependencies.classesinfo.IClassDependencies;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TestCoverage {
	private int totalClasses = 0;
	private int testedClasses = 0;
	private final String libraryName;
	private final List<String> modelsToTest;
	private final List<String> testedModels = new ArrayList<>();
	private List<String> modelsWithoutTest;


	public TestCoverage(
			Map<String, ? extends IClassDependencies> library,
			Map<String, ? extends IClassDependencies> testLibrary) {
		modelsToTest = library.keySet().stream().toList();
		libraryName = Arrays.stream(modelsToTest.getFirst().split("\\.")).toList().getFirst();
		totalClasses = modelsToTest.size();
		testLibrary.forEach((test, classDependencies) -> {
			classDependencies.getClasses().forEach(cls -> {
				if (!testedModels.contains(cls)) {
					if (modelsToTest.contains(cls)) {
						testedModels.add(cls);
						testedClasses++;
					}
				}
			});
		});
		getModelsWithoutTests();

	}

	private void getModelsWithoutTests() {
		modelsWithoutTest = new ArrayList<>(modelsToTest);
		modelsWithoutTest.removeAll(testedModels);
	}

	public double getPercentageCoverage() {
		return Math.round(((float) testedClasses / totalClasses) * 10000) / 100.0;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Test coverage of ");
		sb.append(libraryName);
		sb.append(":\nTest coverage: ");
		sb.append(testedClasses).append(" / ").append(totalClasses);
		sb.append(" (").append(getPercentageCoverage()).append(" %)\n");
		if (modelsWithoutTest.isEmpty()) {
			sb.append("All models are tested.");
		}
		else {
			sb.append("These models need tests:\n");
			modelsWithoutTest.forEach(mod -> sb.append(" - ").append(mod).append("\n"));
		}

		return sb.toString();
	}
}
