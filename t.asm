DATA SEGMENT
	prixHt DD
	prixTtc DD
DATA ENDS
CODE SEGMENT
    mov eax, 200
    mov prixHt, eax
    push eax
    mov eax, prixHt
    mov ebx, 119
    mul eax, ebx
    push eax
    mov ebx, 100
    pop eax
    div ebx, eax
    mov eax, ebx
    push eax
    pop eax
    mov prixTtc, eax
    push eax
CODE ENDS
