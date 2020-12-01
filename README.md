KITSCHMINYOF Stephan
GALLET Benjamin
M1-INFO     GRP-2
# TP Compilation : Génération d'arbres abstraits

## Compte-rendu TP 2

## Exercice 1 :
### Consigne :
Utiliser JFlex et CUP pour générer l'arbre abstrait correspondant à l'analyse d'expressions arithmétiques sur les nombres entiers.


L'expression

```
let prixTtc =  prixHt * 119 / 100;
prixTtc + 100
```
donne :
`(; (LET prixTtc (/ (* prixHt 119) 100)) (+ prixTtc 100))`

## Exercice 2 :
### Consigne :
Compléter la grammaire précédente en y ajoutant les opérateurs booléens, ceux de comparaison, la boucle et la conditionnelle, afin d'obtenir un sous-ensemble du langage **λ-ada** un peu plus complet.


L'expression :

```
let a = input;
let b = input;
while (0 < b)
do (let aux=(a mod b); let a=b; let b=aux );
output a .
```

Donne :
```
(; (LET a INPUT) (; (LET b INPUT) (; (WHILE (< 0 b) (DO (; (LET aux (% a b)) (; (LET a b) (LET b aux))))) (OUTPUT a))))
```

# Changements
Ajout d'une classe Arbre qui va donc permet de créer l'arbre associé aux actions que l'on rentre.
Modification du fichier cup pour qu'il génère l'arbre en fonction des éléments reconnus par la grammaire.
