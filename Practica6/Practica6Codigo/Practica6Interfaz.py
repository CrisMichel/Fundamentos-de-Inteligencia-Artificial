from tkinter import *
from tkinter.ttk import Combobox
from pyswip import Prolog
from PIL import Image, ImageTk

def consult_specie():
    global seleccionCombo, textResult

    if seleccionCombo == '':
        return

    prolog = Prolog()
    prolog.consult("marcos-animalia.pl")

    textResult.delete("1.0","end")

    propiedades_query = list(prolog.query(f"propiedadesc({seleccionCombo}, P)"))

    if propiedades_query:
        propiedades = propiedades_query[0]['P']

        if propiedades:
            
            for propiedad in propiedades:

                if "imagen" in propiedad:
                    a = propiedad.split("(")[1]
                    src = a.split(")")[0]
                    image2=Image.open(f"Imagenes/{src}")
                    image2 = image2.resize((150, 150),Image.Resampling.LANCZOS)
                    img_tk = ImageTk.PhotoImage(image2)
                    labelImg2 = Label(miFrame,image=img_tk)
                    labelImg2.grid(row = 4, column = 3, columnspan = 2)
                    labelImg2.image = img_tk
                
                else:
                    textResult.insert(END, propiedad+'\n')
        else:
            print("Advertencia", f"No se encontró información para {seleccionCombo}")
    else:
        print("Advertencia", f"No se encontró información para {seleccionCombo}")


def list_species():
    global animals

    textResult.delete("1.0", "end")

    for animal in animals:
        textResult.insert(END, animal + "\n")

def display_image(src):
    try:
        image2 = Image.open(f"Imagenes/{src}")
        image2 = image2.resize((100, 100), Image.Resampling.LANCZOS)
        img_tk = ImageTk.PhotoImage(image2)
        labelImg2 = Label(frame, image=img_tk)
        labelImg2.place(x=350, y=80)
        labelImg2.image = img_tk
    except FileNotFoundError:
        print(f"Imagen no encontrada: {src}")

def mostrar_seleccion(event):
    global seleccionCombo, especieCombo
    seleccionCombo = especieCombo.get()

#-----------------------------------------------
#               V A R I A B L E S
#-----------------------------------------------

animals = ["vicugna_vicugna", "lama_guanicoe", "lama_pacos", "canis_lupus_familiaris", "canis_rufus",
               "canis_latrans", "babyrousa_babyrussa", "sus_scrofa", "pan_troglodytes", "gorilla_gorilla",
               "pongo_pygmaeus", "puma_concolor", "panthera_pardus", "leopardus_geoffroyi", "elephas_maximus",
               "equus_caballus", "ceratotherium_simum", "ursus_arctos", "ursus_maritimus", "ailuropoda_melanoleuca",
               "noctilio_albiventris", "hyaena_hyaena", "ambystoma_mexicanum", "hirundo_rustica", "amphiprion_ocellaris",
               "caiman_crocodilus"]
seleccionCombo = ''

#-----------------------------------------------
#               I N T E R F A Z
#-----------------------------------------------

root = Tk()
root.title('Taxonomia')

miFrame = Frame(root, width = 500, height = 500)
miFrame.pack(fill="both",expand=True)

bg=PhotoImage(file="Imagenes/salvaje.png")
my_label=Label(miFrame,image=bg)
my_label.place(x=0,y=0,relwidth=1,relheight=1)

tituloLabel = Label(miFrame, text = 'Taxonomia de animales', fg = "red", font = ("Arial", 29))

specieLabel = Label(miFrame, text="Introduzca la especie:",font=("Trebuchet MS",10))

especieCombo = Combobox(miFrame, values=animals)
especieCombo.bind("<<ComboboxSelected>>", mostrar_seleccion)

consultar = Button(miFrame, text="Consultar",width=17,command=consult_specie, bg = 'lime')

textResult = Text(miFrame, width=45,height=10)

image=Image.open("Imagenes/question.png")
image=image.resize((150,150),Image.Resampling.LANCZOS)

img=ImageTk.PhotoImage(image)
lbl_img=Label(miFrame,image=img)


#----------------------------------------------------------
#                   E M P A Q U E T A D O
#----------------------------------------------------------
tituloLabel.grid(row = 1, column = 1, columnspan = 3, pady = 10)

specieLabel.grid(row = 2, column = 1, sticky = 'e', pady = 10)
# especieInput.grid(row = 2, column = 2)
especieCombo.grid(row = 2, column = 2)

consultar.grid(row = 3, column = 1, columnspan = 2, pady = 10)

textResult.grid(row = 4, column = 1, columnspan = 2, pady = 10, padx = 20)

lbl_img.grid(row = 4, column = 3, columnspan = 2, padx = 20)

miFrame.pack()
root.mainloop()