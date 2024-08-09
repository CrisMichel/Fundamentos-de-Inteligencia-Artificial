%Base de conocimientos basada en marcos

%reino
frame(animal, subclase_de(objeto), propiedades([puede(sentir), puede(respirar)])).

%tipos
frame(mamifero, subclase_de(animal), propiedades([puede(mamar), respira(aire)])).
frame(ave, subclase_de(animal), propiedades([poseen(alas), tienen(pico)])).
frame(reptil, subclase_de(animal), propiedades([tienen(sangre_fria), piel(seca),cuerpo(alargado)])).
frame(anfibio, subclase_de(animal), propiedades([son(ectotermos),pasan_por(metamorfosis)])).
frame(pez, subclase_de(animal), propiedades([son(oviparos),tienen(aletas),poseen(escamas)])).

%Orden_mamifero
frame(artiodactilo, subclase_de(mamifero), propiedades([tiene(pesugnas_pares), comen(plantas)])).
frame(carnivora, subclase_de(mamifero), propiedades([comen(carne)])).
frame(primates, subclase_de(mamifero), propiedades([tiene(cerebro_desarrollado)])).
frame(proboscidea, subclase_de(mamifero), propiedades([es(grande)])).
frame(perissodactyla, subclase_de(mamifero), propiedades([tiene(pesugnas_impares)])).
frame(chiroptera, subclase_de(mamifero), propiedades([tien(alas), es(roedor)])).

%Orden_ave
frame(passeriformes, subclase_de(ave), propiedades([son(pequenos),suelen(cantar)])).

%Orden_reptil
frame(crocodilia, subclase_de(reptil), propiedades([son(nadadores),se_mueven_en(tierra)])).

%Orden_anfibio
frame(caudata, subclase_de(anfibio), propiedades([tienen(cola)])).

%Orden_pez
frame(perciforme, subclase_de(pez), propiedades([son(coloridos),cuerpos(fusiformes)])).

%Familia_mamiferos
frame(camelidos, subclase_de(artiodactilo), propiedades([familia_de(camellos)])).
frame(canidae, subclase_de(carnivora), propiedades([puede(comer_vegetales)])).
frame(suidae, subclase_de(artiodactilo), propiedades([son(inteligentes)])).
frame(hominidae, subclase_de(primates), propiedades([son(grandes_simios)])).
frame(felidae, subclase_de(carnivora), propiedades([son(felinos)])).
frame(elephantidae, subclase_de(proboscidea), propiedades([son(elefantes)])).
frame(equidae, subclase_de(perissodactyla), propiedades([son(compatibles)])).
frame(rhinocerotidae, subclase_de(perissodactyla), propiedades([tiene(cuerno)])).
frame(ursidae, subclase_de(carnivora), propiedades([son(osos)])).
frame(noctilionoidea, subclase_de(chiroptera), propiedades([es(pescador)])).
frame(hyaenidae,subclase_de(carnivora),propiedades([son(torpes),cuello(largo)])).

%Familia_aves
frame(hirundinidae,subclase_de(passeriformes),propiedades([comen(insectos),pico(corto)])).

%Familia_reptiles
frame(alligatoridae,subclase_de(crocodilia),propiedades([comen(poco)])).

%Familia_anfibios
frame(ambystomatidae,subclase_de(caudata),propiedades([pueden(regenerarse)])).

%Familia_pez
frame(pomacentridae,subclase_de(perciforme),propiedades([son(sedentarios)])).


%Ejemplares_mamiferos
frame(vicugna_vicugna, subclase_de(camelidos), propiedades([nombre_comun(vicugna), imagen('vicugna.jpg')])).
frame(lama_guanicoe, subclase_de(camelidos), propiedades([nombre_comun(guanaco), imagen('guanaco.jpg')])).
frame(lama_pacos, subclase_de(camelidos), propiedades([nombre_comun(llama), imagen('llama.jpg')])).
frame(canis_lupus_familiaris, subclase_de(canidae), propiedades([nombre_comun(perro_domestico), imagen('husky.jpg')])).
frame(canis_rufus, subclase_de(canidae), propiedades([nombre_comun(lobo_rojo), imagen('lobo_rojo.jpg')])).
frame(canis_latrans, subclase_de(canidae), propiedades([nombre_comun(coyote), imagen('coyote.jpg')])).
frame(babyrousa_babyrussa, subclase_de(suidae), propiedades([nombre_comun(babirusa), imagen('babirusa.jpg')])).
frame(sus_scrofa, subclase_de(suidae), propiedades([nombre_comun(jabali), imagen('jabali.jpg')])).
frame(pan_troglodytes, subclase_de(hominidae), propiedades([nombre_comun(chimpance), vive_en(selvas), imagen('chimpance.jpg')])).
frame(gorilla_gorilla, subclase_de(hominidae), propiedades([nombre_comun(gorila), vive_en(bosques_costeros), imagen('gorila.jpg')])).
frame(pongo_pygmaeus, subclase_de(hominidae), propiedades([nombre_comun(orangutan), vive_en(selvas), imagen('orangutan.jpg')])).
frame(puma_concolor, subclase_de(felidae), propiedades([nombre_comun(puma), es(pequegna), emite(maullidos), imagen('puma.jpg')])).
frame(panthera_pardus, subclase_de(felidae), propiedades([nombre_comun(leopardo), es(rapido), imagen('leopardo.jpg')])).
frame(leopardus_geoffroyi, subclase_de(felidae), propiedades([nombre_comun(gato_montes), es(chiquito), imagen('montes.jpg')])).
frame(elephas_maximus, subclase_de(elephantidae), propiedades([nombre_comun(elefante_asiatico), imagen('elefante.jpg')])).
frame(equus_caballus, subclase_de(equidae), propiedades([nombre_comun(caballo), ruido(relincha), imagen('caballo.jpg')])).
frame(ceratotherium_simum, subclase_de(rhinocerotidae), propiedades([nombre_comun(rinoceronte_blanco), imagen('rino.jpg')])).
frame(ursus_arctos, subclase_de(ursidae), propiedades([nombre_comun(oso_pardo), pelaje(marron), imagen('pardo.jpg')])).
frame(ursus_maritimus, subclase_de(ursidae), propiedades([nombre_comun(oso_polar), pelaje(blanco), imagen('polar.jpg')])).
frame(ailuropoda_melanoleuca, subclase_de(ursidae), propiedades([nombre_comun(oso_panda), pelaje(blanco_y_negro), imagen('panda.jpg')])).
frame(noctilio_albiventris, subclase_de(noctilionoidea), propiedades([nombre_comun(murcielago_bulldog), imagen('murcielago.jpg')])).
frame(hyaena_hyaena,subclase_de(hyaenidae),propiedades([nombre_comun(hiena_rayada),imagen('hiena.jpg')])).

%Ejemplares_ave
frame(hirundo_rustica,subclase_de(hirundinidae),propiedades([nombre_comun(golondrina_comun),cola(ahorquillada),alas(largas),imagen('golondrina.jpg')])).

%Ejemplares_reptil
frame(caiman_crocodilus,subclase_de(alligatoridae),propiedades([nombre_comun(caiman_de_antojos),color(verde),son(inofensivos),imagen('caiman.jpg')])).

%Ejemplares_anfibios
frame(ambystoma_mexicanum,subclase_de(ambystomatidae),propiedades([nombre_comun(ajolote_mexicano),tienen(branquias),cuerpo(gelatinoso),posee(cuernos),imagen('ajolote.jpg')])).

%Ejemplares_pez
frame(amphiprion_ocellaris,subclase_de(pomacentridae),propiedades([nombre_comun(pez_payaso),tienen(franjas),son(hermafroditas),imagen('payaso.jpg')])).

%Reglas de inferencia
subc(C1,C2):- frame(C1,subclase_de(C2),_).
subc(C1,C2):- frame(C1,subclase_de(C3),_),subc(C3,C2).
subclase(X):-frame(X,subclase_de(_),_).

%Para consultar propiedades use: propiedadesc(luciernaga, L).
propiedadesc(objeto,_):-!.
propiedadesc(X,Z):-frame(X,subclase_de(Y),propiedades(P)),propiedadesc(Y,P1), append(P,P1,Z).

%Para consultar todas las clases representadas en los frames
clases(L):-findall(X,frame(X,subclase_de(_),propiedades(_)),L).

%Para consultar todas las subclases de una una clase
subclases_de(X,L):-findall(C1,subc(C1,X),L).

%Para consultar todas las superclases de una clase
superclases_de(X,L):-findall(C1,subc(X,C1),L).

%Para consultar qué objetos tienen UNA propiedad determinada
tiene_propiedad(P,Objs):-frame(X,_,propiedades(L)),member(P,L),subclases_de(X,S),select(X,Objs,S),!.

%Obtiene todas las propiedades de todos los objetos
todas_propiedades(L):-findall(P,frame(_,_,propiedades(P)),NL), flatten(NL,L).

% Obtiene una lista de clases con los objetos que tienen la propiedades
% de la lista de entrada en P
consulta_por_propiedades(P,C):-consulta(P,C1),list_to_set(C1,C2),sort(C2,C).

consulta([],[]).
consulta([H|T],C):-consulta(T,C1), tiene_propiedad(H,C2),append(C1,C2,C).

%Es hoja (regresa verdadero si la clase no tiene subclases)
%
es_hoja(Clase):-subclases_de(Clase,L),L = [].