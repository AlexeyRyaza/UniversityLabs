global calculate_x_a_zero

section .text
calculate_x_a_zero:
    push rbp
    mov rbp, rsp

    movsd xmm4, qword [zero]
    ucomisd xmm0, xmm4

    movapd xmm4, xmm3
    movsd xmm5, qword [minus_one]
    mulsd xmm4, xmm5

    divsd xmm4, xmm1
    movapd xmm0, xmm4
    jmp exit

exit:
    pop rbp
    ret

section .data
    zero dq 0.0
    two dq 2.0
    minus_one dq -1.0
