DATA SEGMENT
	a DD
	b DD
	c DD
DATA ENDS
CODE SEGMENT
    in eax
    mov a, eax
    push eax
    mov eax, 1
    mov b, eax
    push eax
    mov eax, 1
    mov c, eax
    push eax
debut_if_1:
    mov eax, a
    mov ebx, 1
    sub eax, ebx
    push eax
    pop eax
    jl vrai_gt_1
    push 0
    jmp fin_gt_1
vrai_gt_1:
    push 1
fin_gt_1:
sortie_gt_1:
debut_while_2:
    mov eax, b
    mov ebx, a
    sub eax, ebx
    push eax
    pop eax
    jl vrai_gt_2
    push 0
    jmp fin_gt_2
vrai_gt_2:
    push 1
fin_gt_2:
faux_gt_2:
sortie_gt_2:
    jz sortie_while_2
    mov eax, b
    mov ebx, 1
    add eax, ebx
    push eax
    pop eax
    mov b, eax
    push eax
    mov eax, c
    mov ebx, b
    mul eax, ebx
    push eax
    pop eax
    mov c, eax
    push eax
    jmp debut_while_2
sortie_while_2:
    mov eax, c
    out eax
    jmp sortie_if_1
faux_gt_1:
    mov eax, b
    out eax
sortie_if_1:
CODE ENDS
