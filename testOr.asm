DATA SEGMENT
	a DD
	b DD
DATA ENDS
CODE SEGMENT
    mov eax, 4
    mov a, eax
    push eax
    mov eax, 3
    mov b, eax
    push eax
debut_if_1:
    mov eax, a
    mov ebx, b
    sub eax, ebx
    push eax
    pop eax
    jle faux_gt_1
    mov eax, a
    mov ebx, 5
    sub eax, ebx
    push eax
    pop eax
    jle faux_gt_1
    mov eax,1
    jmp sortie_gt_1
sortie_gt_1:
    mov eax, a
    out eax
    jmp sortie_if_1
faux_gt_1:
    mov eax, b
    out eax
sortie_if_1:
CODE ENDS
