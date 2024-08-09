import numpy as np
import matplotlib.pyplot as plt

# Datos de ejemplo
x = np.linspace(0, 10, 100)
y = np.sin(x)

# Crear un objeto Axes
fig, ax = plt.subplots()

# Graficar los datos
ax.plot(x, y)

# Establecer los valores y etiquetas en el eje x
valores_x = [1, 4, 7, 10]
etiquetas_x = ['A', 'B', 'C', 'D']
ax.set_xticks(valores_x)
ax.set_xticklabels(etiquetas_x)

# Establecer los valores y etiquetas en el eje y
valores_y = [-1, 0, 1]
etiquetas_y = ['Min', 'Med', 'Max']
ax.set_yticks(valores_y)
ax.set_yticklabels(etiquetas_y)

# Establecer el título del Axes
ax.set_title('Gráfico con Ejes Personalizados')

# Mostrar la ventana
plt.show()
