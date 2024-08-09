% Crear el objeto FIS (Fuzzy Inference System)
fis = mamfis('Name', 'Calificacion');

% Variables de entrada
fis = addInput(fis, 'Name', 'Porcentaje_creditos', 'MembershipFunction', 'trapmf', 'Params', [0 0 25 50]);
fis = addInput(fis, 'Name', 'Promedio', 'MembershipFunction', 'trapmf', 'Params', [6.0 6.0 10.0 10.0]);

% Variable de salida
fis = addOutput(fis, 'Name', 'Calificacion', 'MembershipFunction', 'trapmf', 'Params', [0 0 10 10]);

% Reglas de inferencia
rule1 = [1 1 3 1 1];
rule2 = [1 2 4 2 1];
rule3 = [1 3 5 3 1];
rule4 = [2 3 4 2 1];
rule5 = [2 2 5 3 1];
rule6 = [2 1 5 3 1];
rule7 = [3 3 4 2 1];
rule8 = [3 2 4 2 1];
rule9 = [3 1 5 3 1];

rules = [rule1; rule2; rule3; rule4; rule5; rule6; rule7; rule8; rule9];

% Añadir reglas al sistema
fis = addRule(fis, rules);