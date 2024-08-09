%Frutas
fruta(papaya, 45).
fruta(melon, 31).
fruta(fresas, 36).
fruta(frambuesa,40).
fruta(kiwi, 51).
fruta(mandarina, 40).
fruta(mango, 57).
fruta(mora,37).
fruta(pinia,51).
fruta(platano,90).
fruta(uva,81).
fruta(pera,61).
fruta(manzana,52).
fruta(granada,65).
fruta(durazno,52).
fruta(coco,646).

%Cereales
cereal(pan_centeno, 241).
cereal(pan_blanco, 255).
cereal(arroz_blanco,354).
cereal(avena,367).
cereal(cebada,373).
cereal(harina_maiz,349).
cereal(pan_trigo_integral,239).
cereal(yuca, 338).
cereal(polenta,358).
cereal(semola_trigo,368).
cereal(copos_maiz,350).

%jugos
jugo(zumo_naranja,42).
jugo(zumo_arandano,46).
jugo(zumo_asai,58).
jugo(zumo_ciruela,71).
jugo(zumo_limon,21).
jugo(zumo_mandarina,43).
jugo(zumo_maracuya,48).
jugo(zumo_pepino,10).
jugo(zumo_pera,60).
jugo(zumo_zanahoria,40).
jugo(zumo_tomate,17).
jugo(zumo_uva,60).

%Huevo
huevo(huevo_duro, 147).
huevo(clara,48).
huevo(huevo_entero,162).
huevo(yema,368).

%C�rnico
carnico(chuleta_cerdo, 330).
carnico(chicharron, 601).
carnico(bacon, 665).
carnico(cabrito, 127).
carnico(conejo, 162).
carnico(jabali,107).
carnico(jamon,380).
carnico(pato,200).
carnico(salchicha,315).
carnico(ternera_bistec,181).
carnico(corodniz,114).
carnico(chorizo,468).

%Pastas
pasta(pasta_huevo, 368).
pasta(pasta_semola, 361).

%Postre
postre(flan_vainilla, 102).
postre(flan_huevo, 126).
postre(helado_leche,167).
postre(helado_agua,139).
postre(pastel_queso,414).

%Lacteo
lacteo(leche_entera, 68).
lacteo(leche_semidescremada, 49).
lacteo(leche_cabra,72).
lacteo(leche_oveja,96).
lacteo(leche_polvo_entera,500).
lacteo(mousse,177).
lacteo(queso_blanco,70).
lacteo(queso_brie,263).
lacteo(queso_crema,245).
lacteo(queso_machego,376).
lacteo(queso_mozzarella,245).
lacteo(yogurth_natural,62).
lacteo(yogurth_desnatado,45).

%Colaci�n
colacion(donut, 456).
colacion(pastel_manzana, 311).
colacion(barra_chocolate,441).
colacion(yogurth_fruta,100).
colacion(gomas_fruta,172).
colacion(crema_avellanas,549).

% Ciclo principal

main:-repeat,
      pinta_menu,
      read(Opcion),
      ( (Opcion=1,doImc,fail);
	(Opcion=2,doDieta,fail);
	(Opcion=3,!)).

% Muestra el menu

pinta_menu:-nl,nl,
      writeln('===================================='),
      writeln('         DRA. MIKU HATSUNE'),
      writeln('          Medica Virtual'),
      writeln('   <<  Experta en Nutricion  >>'),
      writeln('===================================='),
      nl,writeln('       MENU PRINCIPAL'),
      nl,write('1 Calcular indice de masa corporal'),
      nl,write('2 Recomendar una dieta saludable'),
      nl,write('3 Salir'),
      nl,write('================================='),
      nl,write('Indique una opcion valida:').

% Regla para calcular IMC

doImc:-nl, write('================================='),nl,
       write('Elegiste: Calculo del Indice de Masa Corporal\n'),nl,
       write('Indique su peso en Kilogramos:'),read(Peso),
       write('Indique su estatura en Metros:'),read(Estatura),Estatura > 0,
       write('Indique su genero (1/Male, 2/Female):'),read(Sexo),
       IND is Peso/(Estatura*Estatura),
       nl,format('Su indice de masa corporal es: ~g',IND),
       nl, write('DIAGNOSTICO: '),
        (   (Sexo=1,IND<17,write('USTED PADECE DESNUTRICION!'));
	   (Sexo=1,IND>=17,IND<20,write('USTED TIENE BAJO PESO!\n'));
         (Sexo=1,IND>=20,IND<25,write('USTED TIENE PESO NORMAL!\n'));
         (Sexo=1,IND>=25,IND<30,write('USTED TIENE LIGERO SOBREPESO!\n'));
         (Sexo=1,IND>=30,IND<40,write('USTED TIENE OBESIDAD SEVERA!\n'));
         (Sexo=1,IND>=40,write('USTED TIENE OBESIDAD MÓRVIDA!\n'));
         (Sexo=2,IND<16,write('USTED PADECE DESNUTRICION!'));
         (Sexo=2,IND>=16,IND<20,write('USTED TIENE BAJO PESO!\n'));
         (Sexo=2,IND>=20,IND<24,write('USTED TIENE PESO NORMAL!\n'));
         (Sexo=2,IND>=24,IND<29,write('USTED TIENE LIGERO SOBREPESO!\n'));
         (Sexo=2,IND>=29,IND<37,write('USTED TIENE OBESIDAD SEVERA!\n'));
         (Sexo=2,IND>=37,write('USTED TIENE OBESIDAD MORVIDA!\n'))).

% Regla para recomendar dietas
doDieta :-
write('================================='), nl,
    write('Elegiste: Recomendar una dieta saludable\n'), nl,
    write('Indique su genero (1/Male, 2/Female):'), read(Sexo),
    write('Indique su peso en Kilogramos:'), read(Peso),
    write('Indique su edad: '), read(Edad),

    calcular_GAST(Peso, Edad, Sexo, GAST).

calcular_GAST(Peso, Edad, Sexo, GAST) :-
    Sexo = 1,
    Edad < 25,
    GAST is (Peso * 24) + 300.

calcular_GAST(Peso, Edad, Sexo, GAST) :-
    Sexo = 1,
    Edad >= 25, Edad =< 45,
    GAST is (Peso * 24).

calcular_GAST(Peso, Edad, Sexo, GAST) :-
    Sexo = 1,
    Edad > 45,
    Intervalo is (Edad - 45) // 10,
    GAST is (Peso * 24) - (Intervalo * 100).

calcular_GAST(Peso, Edad, Sexo, GAST) :-
    Sexo = 2,
    Edad < 25,
    GAST is (Peso * 21.6) + 300.

calcular_GAST(Peso, Edad, Sexo, GAST) :-
    Sexo = 2,
    Edad >= 25, Edad =< 45,
    GAST is (Peso * 21.6).

calcular_GAST(Peso, Edad, Sexo, GAST) :-
    Sexo = 2,
    Edad > 45,
    Intervalo is (Edad - 45) // 10,
    GAST is (Peso * 21.6) - (Intervalo * 100).

%Regla para armar el desayuno
desayuno(D1, D2, D3, D4, KCal):-fruta(D1,K1), cereal(D2, K2), jugo(D3, K3), huevo(D4, K4), KCal is K1+K2+K3+K4.

%Regla para armar la comida
comida(C1, C2, C3, KCal):-carnico(C1,K1), pasta(C2, K2), postre(C3, K3), KCal is K1+K2+K3.

%Regla para armar la merienda
merienda(M1, M2, KCal):-lacteo(M1, K1), colacion(M2, K2), KCal is K1+K2.


% Regla que recibe como entrada las KCalor�as del Gasto metab�lico,
% recupera un desayuno, comida y merienda, y muestra el men�
% si cumple con que est� en el rago Gasto-10% y Gasto+10%

dieta(GAST):-desayuno(D1,D2,D3,D4,K1),
              comida(C1,C2,C3,K2),
              merienda(M1,M2,K3),
              KCal is K1+K2+K3,
              Inferior is GAST-(GAST*0.1),
              Superior is GAST+(GAST*0.1),
              KCal >= Inferior, KCal=< Superior,
              format("\nDesayuno: ~s, ~s, ~s y ~s", [D1,D2,D3,D4]),
              format("\nComida  : ~s, ~s y ~s", [C1,C2,C3]),
              format("\nMerienda: ~s y ~s", [M1,M2]),
              format("\nTotal de KCalor�as: ~d",KCal).

