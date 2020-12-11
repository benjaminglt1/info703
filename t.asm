DATA SEGMENT
	aux DD
DATA ENDS
CODE SEGMENT
    pop eax
    mov aux, eax
    push eax
    mov eax, aux
    out eax
CODE ENDS
