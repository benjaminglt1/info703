DATA SEGMENT
	a DD
	b DD
	aux DD
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
