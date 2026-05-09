package functionalities.affectedclasses;

import dependencies.classesinfo.ClassDependenciesResolver;

import java.util.*;

public class AffectedClassesResolver {
	private final List<TreeMap<String, ClassDependenciesResolver>> trees = new ArrayList<>();

	private final Queue<String> affectedClasses = new LinkedList<>();

	@SafeVarargs
	public AffectedClassesResolver(TreeMap<String, ClassDependenciesResolver>... trees) {
		this.trees.addAll(Arrays.asList(trees));
	}

	public List<String> getAffectedClasses(String... className) {
		affectedClasses.clear();
		affectedClasses.addAll(Arrays.asList(className));
		Set<String> results = new HashSet<>();
		String currentClass = affectedClasses.poll();
		while (currentClass != null) {
			for (var tree : trees) {
				String finalCurrentClass = currentClass;
				tree.forEach((name, classDependencies) -> {
					if (classDependencies.getClasses().contains(finalCurrentClass)) {
						results.add(finalCurrentClass);
						results.add(name);
					}
				});
			}
			currentClass = affectedClasses.poll();
		}
		return results.stream().toList();
	}


}
