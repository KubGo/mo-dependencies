package functionalities.affectedclasses;

import dependencies.classesinfo.IClassDependencies;

import java.util.*;

public class AffectedClassesResolver<T extends IClassDependencies> {
	private final List<Map<String, T>> trees = new ArrayList<>();
	private boolean includeChildrenClasses = false;

	private final Queue<String> affectedClasses = new LinkedList<>();

	public AffectedClassesResolver() {

	}

	@SafeVarargs
	public AffectedClassesResolver(Map<String, T>... trees) {
		this.trees.addAll(Arrays.asList(trees));
	}

	public void addDependencyTree(Map<String, T> tree) {
		trees.add(tree);
	}


	public List<String> getAffectedClasses(List<String> classes) {
		affectedClasses.clear();
		affectedClasses.addAll(classes);
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
					if (classDependencies.getParentClasses().contains(finalCurrentClass)) {
						results.add(finalCurrentClass);
						results.add(name);
						if (includeChildrenClasses) {
							affectedClasses.add(name);
						}
					}
				});
			}
			currentClass = affectedClasses.poll();
		}
		return results.stream().toList();
	}

	public void setIncludeChildrenClasses(boolean includeChildrenClasses) {
		this.includeChildrenClasses = includeChildrenClasses;
	}
}
