import numpy as np
import matplotlib.pyplot as plt
import pandas as pd
from sklearn.neighbors import KNeighborsClassifier
from sklearn.preprocessing import MinMaxScaler
from sklearn.model_selection import train_test_split

df = pd.read_csv('paises2.csv')

X = df[['Esperanza de vida al nacer', 'PIB per cápita $US', 'Emisiones de CO2 per cápita']]
y = df['Clase']

# scaler = MinMaxScaler()
# X = scaler.fit_transform(X)

Xtrain, Xtest, ytrain, ytest = train_test_split(X, y, test_size=20, random_state=42)

knn = KNeighborsClassifier(n_neighbors=20)
knn.fit(Xtrain, ytrain)

prediccion = knn.predict(Xtest)

print('\nCon los datos')
print(Xtest)
print('La categoría predicha es:')
print(prediccion)

print('Cantidad de High income en los paises de prueba: ', np.count_nonzero(prediccion == 'High income'))
print('Cantidad de Upper middle income en los paises de prueba: ', np.count_nonzero(prediccion == 'Upper middle income'))
print('Cantidad de Lower middle income en los paises de prueba: ', np.count_nonzero(prediccion == 'Lower middle income'))
print('Cantidad de Low income en los paises de prueba: ', np.count_nonzero(prediccion == 'Low income'))

fig = plt.figure()
ax = fig.add_subplot(projection='3d')

ax.set_xlabel('Esperanza de vida al nacer')
ax.set_ylabel('PIB per cápita $US')
ax.set_zlabel('Emisiones de CO2 per cápita')    
 
ax.scatter(Xtrain[ytrain == 'Low income']['Esperanza de vida al nacer'],
           Xtrain[ytrain == 'Low income']['PIB per cápita $US'],
           Xtrain[ytrain == 'Low income']['Emisiones de CO2 per cápita'],
           c="purple", label='Low income')

ax.scatter(Xtrain[ytrain == 'Lower middle income']['Esperanza de vida al nacer'],
           Xtrain[ytrain == 'Lower middle income']['PIB per cápita $US'],
           Xtrain[ytrain == 'Lower middle income']['Emisiones de CO2 per cápita'],
           c="violet", label='Lower middle income')

ax.scatter(Xtrain[ytrain == 'Upper middle income']['Esperanza de vida al nacer'],
           Xtrain[ytrain == 'Upper middle income']['PIB per cápita $US'],
           Xtrain[ytrain == 'Upper middle income']['Emisiones de CO2 per cápita'],
           c="palegreen", label='Upper middle income')

ax.scatter(Xtrain[ytrain == 'High income']['Esperanza de vida al nacer'],
           Xtrain[ytrain == 'High income']['PIB per cápita $US'],
           Xtrain[ytrain == 'High income']['Emisiones de CO2 per cápita'],
           c="greenyellow", label='High income')

ax.scatter(Xtest['Esperanza de vida al nacer'],
           Xtest['PIB per cápita $US'],
           Xtest['Emisiones de CO2 per cápita'],
           c="black", marker='x', label = 'Paises introducidos')
ax.legend()
plt.show()