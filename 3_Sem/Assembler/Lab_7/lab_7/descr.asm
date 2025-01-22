global discriminant

section .text
discriminant:
    push rbp
    mov rbp, rsp

    movapd xmm3, xmm1
    mulsd xmm3, xmm1

    movsd xmm4, qword [four]
    mulsd xmm4, xmm0
    mulsd xmm4, xmm2

    subsd xmm3, xmm4

    movsd xmm5, qword [zero]
    ucomisd xmm3, xmm5
    ja positive_result

    movsd xmm0, qword [minus_one]
    jmp exit

positive_result:

    movapd xmm0, xmm3

exit:
    mov rsp, rbp
    pop rbp
    ret

section .data
    four dq 4.0
    zero dq 0.0
    minus_one dq -1.0
