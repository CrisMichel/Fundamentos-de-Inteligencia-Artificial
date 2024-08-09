# Importar las bibliotecas necesarias
from sklearn import datasets
from sklearn.model_selection import train_test_split
from sklearn.tree import DecisionTreeClassifier, export_text
from sklearn import tree
import graphviz

# Cargar un conjunto de datos de ejemplo (iris dataset)
iris = datasets.load_iris()
X = iris.data
y = iris.target

# Dividir el conjunto de datos en datos de entrenamiento y prueba
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

# Crear un clasificador de árbol de decisiones
clf = DecisionTreeClassifier()

# Entrenar el clasificador
clf.fit(X_train, y_train)

# Evaluar el rendimiento en el conjunto de prueba
accuracy = clf.score(X_test, y_test)
print(f'Accuracy: {accuracy:.2f}')

# Visualizar el árbol de decisiones
dot_data = tree.export_graphviz(clf, out_file=None,
                                feature_names=iris.feature_names,
                                class_names=iris.target_names,
                                filled=True, rounded=True,
                                special_characters=True)

graph = graphviz.Source(dot_data)
graph.render("iris_tree")  # Esto generará un archivo "iris_tree.pdf" con la visualización del árbol

# Imprimir reglas de decisión
tree_rules = export_text(clf, feature_names=iris.feature_names)
print("Decision Tree Rules:\n", tree_rules)
