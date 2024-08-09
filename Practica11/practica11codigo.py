import pandas as pd
from matplotlib import pyplot as plot
import sklearn.tree as skl
from sklearn.tree import plot_tree
from sklearn.preprocessing import LabelEncoder


#Adquiere los datos desde un archivo csv usando biblioteca PANDAS
dataframe = pd.read_csv('medicamentos.csv', encoding='ISO-8859-1')

#Preprocesamiento (Transformar valores categóricos a numéricos)
edad=LabelEncoder()
sexo=LabelEncoder()
presionSanguinea=LabelEncoder()
colesterol=LabelEncoder()
medicamento=LabelEncoder()

dataframe['Edad']=edad.fit_transform(dataframe['Edad'])
dataframe['Sexo']=sexo.fit_transform(dataframe['Sexo'])
dataframe['PresionSanguinea']=presionSanguinea.fit_transform(dataframe['PresionSanguinea'])
dataframe['Colesterol']=colesterol.fit_transform(dataframe['Colesterol'])
dataframe['Medicamento']=medicamento.fit_transform(dataframe['Medicamento'])

#Prepara los datos
features_cols=['Edad','Sexo','PresionSanguinea','Colesterol']
X=dataframe[features_cols]
y=dataframe.Medicamento

#Entrenamiento
tree = skl.DecisionTreeClassifier(criterion='gini')
tree.fit(X,y)

#Visualización
px = 1/plot.rcParams['figure.dpi']  # Pixel in pulgadas
fig = plot.figure(figsize=(1000*px,1000*px)) 
_ = plot_tree(tree, feature_names=features_cols, class_names=['No','Yes'], filled=True)


# Probar el Modelo

dfprueba = pd.DataFrame()
dfprueba[ 'Edad'] = [1] # rain
dfprueba[ 'Sexo'] = [0] # cool
dfprueba[ 'PresionSanguinea'] = [0] # high
dfprueba[ 'Colesterol'] =[0] # strong

prediccion = tree.predict(dfprueba)

print('Resultado de la prueba')
print('**********************')
print('Con los datos')
print(dfprueba)
print('\nSe recomienda:')
if(prediccion[0]==0):
    print('Medicamento tipo A')
else:
    print('Medicamento tipo B')
print("**********************")