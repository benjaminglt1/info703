KITSCHMINYOF Stephan

GALLET Benjamin

M1-INFO     GRP-2

# TP Compilation

## Compte-rendu TP 3-4

## Exercice 1 :

### Programme source :

~~~
let prixHt = input;
let prixTtc =  prixHt * 119 / 100 .
~~~

### Code généré avec notre version du générateur pour la machine à registre :

#### Fichier Exercice1.asm
~~~
DATA SEGMENT
	prixHt DD
	prixTtc DD
DATA ENDS
CODE SEGMENT
    in eax
    mov prixHt, eax
    push eax
    mov eax, prixHt
    mov ebx, 119
    mul eax, ebx
    push eax
    mov eax, 100
    pop ebx
    div ebx, eax
    push ebx
    pop eax
    mov prixTtc, eax
    push eax
    mov eax, prixTtc
    out eax
CODE ENDS
~~~

#### Résultats :
On peut donc tester le programme produit ett verfifier que l'on obtient bien les bon résultats.

Sur cette première capture on rentre donc le nombre "200" on obtient donc en résultat 238 -> 200 * 119 / 100
![Capture 1](./img/Capture1.png)

Sur la deuxième capture on obtient 1850 -> 1555 * 119 / 100 
![Capture 2](./img/Capture2.png)

## Exercice 2 :

### Programme source :

~~~
let a = input;
let b = input;
while (0 < b)
do (let aux=(a mod b); let a=b; let b=aux );
output a
.
~~~

### Code généré :

#### Fichier Exercice2.asm
~~~
DATA SEGMENT
	a DD
	b DD
	aux DD
	a DD
	b DD
DATA ENDS
CODE SEGMENT
    in eax
    mov a, eax
    push eax
    in eax
    mov b, eax
    push eax
debut_while_1:
    mov eax, 0
    mov ebx, b
    sub eax, ebx
    push eax
    pop eax
    jle faux_gt_1
    mov eax,1
    jmp sortie_gt_1
faux_gt_1:
sortie_gt_1:
    jz sortie_while_1
    mov eax, b
    push eax
    mov eax, a
    pop ebx
    mov ecx,eax
    div ecx,ebx
    mul ecx,ebx
    sub eax,ecx
    push eax
    pop eax
    mov aux, eax
    push eax
    mov eax, b
    mov a, eax
    push eax
    mov eax, aux
    mov b, eax
    push eax
    jmp debut_while_1
sortie_while_1:
    mov eax, a
    out eax
CODE ENDS
~~~

### Résultats :

Ce programme permetant de calculer le PGCD de deux nombre qui sont entrés dans la console on remarque sur les deux capture suivantes que le résultat corespond bien au résuktats trouvé par la machine à registre.

PGCD(60,36) -> 12
![Capture 3](./img/Capture3.png)

PGCD(2322,654) -> 6
![Capture 4](./img/Capture4.png)
## Tests compémentaires :
