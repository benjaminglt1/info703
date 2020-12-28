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
