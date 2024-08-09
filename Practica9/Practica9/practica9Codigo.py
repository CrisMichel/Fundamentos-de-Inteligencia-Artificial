import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from sklearn.cluster import KMeans
from random import randint

dataframe = pd.read_csv('global_rankings.csv')

X = pd.DataFrame()
X['off'] = dataframe['off']
X['def'] = dataframe['def']

colores =['red','orange','green','pink','blue', 'purple', 'lime', 'skyblue', 'yellow', 'tomato']
inertias = []

for k in range(1, 11):

	kmeansModel = KMeans(n_clusters = k)

	kmeansModel.fit(X)
	etiquetas = kmeansModel.predict(X)
	inertias.append(kmeansModel.inertia_)

	dataframe['etiquetas'] = etiquetas
	if k == 3:
		dataframe.to_csv('rankingEtiquetado.csv')

	coloresEtiquetas = [colores[i] for i in etiquetas]

	ax = plt.axes()
	ax.set_title(f'Global Club Soccer Rankings. Valor k = {k}')
	ax.scatter(X['off'], X['def'], c = coloresEtiquetas)
	plt.xlabel('Ofensiva')
	plt.ylabel('Defensiva')
	plt.grid(color='gray', linestyle='--', linewidth = 0.5)
	plt.show()

#Grafica de codo
plt.figure(figsize=(16,8))
plt.plot(range(1, 11), inertias, 'bx-')
plt.xlabel('k')
plt.ylabel('Inercia')
plt.xticks(range(1, 11))
plt.title('Método del codo para mostrar el valor óptimo de K')
plt.show()