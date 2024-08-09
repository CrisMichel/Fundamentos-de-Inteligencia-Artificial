:- dynamic conocido/1.

% Men� principal
main :- bienvenida,
        nl, write('Menu'), nl,
        nl, write('1 Consulta bot�nica'),
        nl, write('2 Salir'),
        nl, nl, write('Indica tu opci�n: '),
        read(Opcion),
        nl,
        (Opcion = 1 -> consulta ; Opcion = 2, writeln('Gracias por no dejarme plantado, ten un plantabuloso dia ^.^\n')).

bienvenida :- writeln('\nBienvenido a mi Consultorio Bot�nico "El plantastico" :D'),
             writeln('==========================================='),
             writeln('\nPuedo ayudarte a identificar plantas, pero ten en cuenta'),
             writeln('que puedo regarla, solo soy un fanatico de las plantas :3').

consulta :- limpia_memoria_de_trabajo,
            writeln("\nResponder� algunas preguntas. Por favor, responde con 'si' o 'no'\n"),
            haz_diagnostico(X),
            escribe_diagnostico(X),
            ofrece_explicacion_diagnostico(X).

consulta :- write('No hay suficiente informaci�n para hacer una identificaci�n.').

% Realiza el diagn�stico basado en los s�ntomas ingresados
haz_diagnostico(Planta) :- obten_hipotesis_y_sintomas(Planta, ListaDeCaracteristicas),
                           prueba_presencia_de(Planta, ListaDeCaracteristicas).

obten_hipotesis_y_sintomas(Planta, ListaDeCaracteristicas) :- reglas(planta(Planta), caracteristicas(ListaDeCaracteristicas)).

prueba_presencia_de(_, []).

prueba_presencia_de(Planta, [Caracteristica | RestoCaracteristicas]) :-
    prueba_verdad_de(Planta, Caracteristica),
    prueba_presencia_de(Planta, RestoCaracteristicas).

% Verifica si la caracter�stica est� en la memoria de trabajo
prueba_verdad_de(_, Caracteristica) :- conocido(Caracteristica).

% Si no est� la caracter�stica en la memoria, pregunta sobre su presencia
prueba_verdad_de(Planta, Caracteristica) :-
    not(conocido(is_false(Caracteristica))),
    pregunta_sobre(Planta, Caracteristica, Respuesta),
    Respuesta = si.

% Realiza la pregunta al usuario
pregunta_sobre(Planta, Caracteristica, Respuesta) :-
    write('�La planta tiene '), write(Caracteristica), write('? '),
    read(Respuesta),
    process(Planta, Caracteristica, Respuesta, Respuesta).

% Procesa la respuesta del usuario
process(_, Caracteristica, si, si) :- asserta(conocido(Caracteristica)).
process(_, Caracteristica, no, no) :- asserta(conocido(is_false(Caracteristica))).
process(Planta, Caracteristica, porque, Respuesta) :- nl,
    write('Estoy investigando la planta siguiente: '), write(Planta), write('.'), nl,
    write('Para esto necesito saber si la planta tiene '), write(Caracteristica), write('.'), nl,
    pregunta_sobre(Planta, Caracteristica, Respuesta).
process(Planta, Caracteristica, Respuesta, Respuesta) :-
    Respuesta \== no,
    Respuesta \== si,
    Respuesta \== porque,
    nl, write('Debes contestar si, no o porque.'), nl,
    pregunta_sobre(Planta, Caracteristica, Respuesta).

% Muestra el diagn�stico obtenido
escribe_diagnostico(Planta) :- write('\nLa planta identificada es '),
                               write(Planta), write('.'), nl.

% Ofrece explicaciones sobre la identificaci�n
ofrece_explicacion_diagnostico(Planta) :-
    pregunta_si_necesita_explicacion(Respuesta),
    actua_consecuentemente(Planta, Respuesta).

pregunta_si_necesita_explicacion(Respuesta) :-
    write('\n�Quieres saber por qu� llegu� a esta conclusi�n? '),
    read(RespuestaUsuario),
    asegura_respuesta_si_o_no(RespuestaUsuario, Respuesta).

asegura_respuesta_si_o_no(si, si).
asegura_respuesta_si_o_no(no, no).
asegura_respuesta_si_o_no(_, Respuesta) :- write('Debes contestar si o no.'),
                                            pregunta_si_necesita_explicacion(Respuesta).

actua_consecuentemente(_, no).
actua_consecuentemente(Planta, si) :- reglas(planta(Planta), caracteristicas(ListaDeCaracteristicas)),
                                      write('\nLa identificaci�n se basa en las siguientes caracter�sticas:\n '), nl,
                                      escribe_lista_de_caracteristicas(ListaDeCaracteristicas).

escribe_lista_de_caracteristicas([]).
escribe_lista_de_caracteristicas([Caracteristica | RestoCaracteristicas]) :-
    write(Caracteristica), nl,
    escribe_lista_de_caracteristicas(RestoCaracteristicas).

% Limpia la memoria de trabajo antes de realizar una nueva consulta
limpia_memoria_de_trabajo :- retractall(conocido(_)).

% Base de conocimientos: diagn�sticos y caracter�sticas
reglas(planta('Rosa'), caracteristicas(['tiene espinas', 'tiene flores de colores'])).
reglas(planta('Orqu�dea'), caracteristicas(['tiene flores ex�ticas', 'crece en climas h�medos'])).
reglas(planta('Cactus'), caracteristicas(['almacena agua en sus hojas', 'tiene espinas gruesas'])).
reglas(planta('Pino'), caracteristicas(['tiene hojas en forma de aguja', 'produce pi�as'])).
reglas(planta('Cocotero'), caracteristicas(['da cocos', 'tiene hojas largas y flexibles'])).
reglas(planta('Lirio'), caracteristicas(['tiene flores grandes y vistosas', 'crece en zonas h�medas'])).
reglas(planta('Helecho'), caracteristicas(['tiene hojas verdes y frondosas', 'prefiere sombra y humedad'])).
reglas(planta('Tulip�n'), caracteristicas(['tiene flores en forma de copa', 'viene en varios colores', 'es muy apreciada en jardiner�a'])).
reglas(planta('Suculenta'), caracteristicas(['almacena agua en sus hojas o tallos', 'tolera condiciones �ridas', 'f�cil de cuidar'])).
reglas(planta('Bamb�'), caracteristicas(['crece r�pidamente', 'tiene tallos huecos', 'se utiliza en construcciones y mobiliario'])).
reglas(planta('Girasol'), caracteristicas(['tiene flores grandes y amarillas', 'sigue la direcci�n del sol', 'se utiliza para producir aceite comestible'])).

