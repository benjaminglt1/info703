DATA SEGMENT
	prixHt DD
	prixTtc DD
DATA ENDS
CODE SEGMENT
    mov eax,200
    mov prixHt, eax
    mov eax,prixHt
    push eax
    mov eax,119
    pop ebx
    mul eax,ebx
    mov eax,100
    push eax
    mov eax,*
    pop ebx
    div eax,ebx
    mov eax,/
    mov prixTtc, eax
CODE ENDS
