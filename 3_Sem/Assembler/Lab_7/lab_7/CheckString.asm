global checkstring

section .data
    error_msg db "Invalid number format", 10, 0
    negative_flag dq 0x8000000000000000 ;
    result resq 1

section .text
checkstring:
    push rbp
    mov rbp, rsp
    sub rsp, 64

    xor rax, rax
    xor rcx, rcx
    mov rsi, rdi


    movzx rbx, byte [rsi]
    cmp rbx, '-'
    jne check_digit
    inc rsi
    mov byte [rsp], 1
    jmp check_digit

    check_digit:
        movzx rbx, byte [rsi]
        cmp rbx, 0
        je error
        cmp rbx, '0'
        jl error
        cmp rbx, '9'
        jg check_dot


        call parse_integer_part
        jmp check_fractional_part

    check_dot:
        cmp rbx, '.'
        jne error
        inc rsi
        jmp check_fractional_part

    check_fractional_part:
        call parse_fractional_part

    build_result:
        movq xmm0, [result]
        cmp byte [rsp], 1
        jne exit
        movq rax, xmm0
        xor rax, qword [negative_flag]
        movq xmm0, rax

    exit:
        add rsp, 32
        pop rbp
        xor rax, rax
        ret

    error:
        add rsp, 32
        pop rbp
        mov rax, 1
        ret


parse_integer_part:
    xor rax, rax
loop_integer:
    movzx rbx, byte [rsi]
    cmp rbx, 0
    je done1
    cmp rbx, '.'
    je done1
    cmp rbx, '0'
    jl error
    cmp rbx, '9'
    jg error

    sub rbx, '0'
    imul rax, rax, 10
    add rax, rbx
    inc rsi
    jmp loop_integer

done1:
    cvtsi2sd xmm0, rax
    movq qword [result], xmm0
    ret


parse_fractional_part:
    xor rdx, rdx
    xor rcx, rcx
    mov rax, 1

loop_fraction:
    movzx rbx, byte [rsi]
    cmp rbx, 0
    je done
    cmp rbx, '0'
    jl done
    cmp rbx, '9'
    jg done

    sub rbx, '0'
    imul rax, rax, 10
    imul rdx, rdx, 10
    add rdx, rbx
    inc rsi
    jmp loop_fraction

done:

    cvtsi2sd xmm1, rdx
    cvtsi2sd xmm2, rax
    divsd xmm1, xmm2
    movq xmm0, [result]
    addsd xmm0, xmm1
    movq qword [result], xmm0
    ret
