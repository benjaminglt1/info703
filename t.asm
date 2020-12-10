DATA SEGMENT
	prixHt DD
	prixTtc DD
DATA ENDS
CODE SEGMENT
    mov eax,200
    mov prixHt, eax
    mov eax,(/ (* prixHt 119) 100)
    mov prixTtc, eax
CODE ENDS
