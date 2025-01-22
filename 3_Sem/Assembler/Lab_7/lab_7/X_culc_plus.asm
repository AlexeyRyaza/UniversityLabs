global calculate_x_plus

section .text
calculate_x_plus:
    push rbp
    mov rbp, rsp

    movsd xmm3, qword [zero]
    ucomisd xmm0, xmm3
    je handle_zero_a

    movapd xmm3, xmm1
    movsd xmm4, qword [minus_one]
    mulsd xmm3, xmm4

    addsd xmm3, xmm2

    movsd xmm4, qword [two]
    mulsd xmm4, xmm0

    divsd xmm3, xmm4
    jmp exit

handle_zero_a:
    movsd xmm3, qword [zero]

exit:
    movapd xmm0, xmm3
    pop rbp
    ret

section .data
    zero dq 0.0
    two dq 2.0
    minus_one dq -1.0
