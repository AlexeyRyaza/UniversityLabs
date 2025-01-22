.model small
.stack 100h
.data
    input_string_message db "Input your string:", 0Dh, 0Ah,'$'
    input_word_message db "Input your word:", 0Dh, 0Ah,'$'
    bad_word_message db "Word must contain no spaces and not be empty!", 0Dh, 0Ah,'$'
    end_message db "Result:", 0Dh, 0Ah,'$'
    bad_string_message db "String must be not empty!", 0Dh, 0Ah,'$'
    
    buffer_size equ 200
    string_buffer db buffer_size
                 db 0
                 db buffer_size dup(0)
    word_buffer db buffer_size
                 db 0
                 db buffer_size dup(0)
    new_line db 0Dh, 0Ah, '$'
    
.code

print_str:
   mov ah,9
   int 21h
ret  


print_buffer_str:    
    mov cx, 0
    mov cl, [bx+1] 
    inc cl
    mov si, bx
    add si, 2
    mov ah, 02h
output_loop:
    mov dl,[si]
    inc si
    int 21h 
    loop output_loop    
    
    jmp End    
ret


input_str:
    mov ah,0Ah
    int 21h
    mov ah,9
    mov dx,offset new_line
    int 21h
ret    


check_spaces:
    push ax
    push cx
    push si
    
    cmp word_buffer+1,0
    je set_cf_0
    
    mov si, offset word_buffer+2
    mov cx,0 
    mov cl, [word_buffer+1] 
    clc
check_loop:
    lodsb
    cmp al, ' '
    je set_cf_0
    loop check_loop
    stc
    jmp end_check
set_cf_0:
    clc
end_check:
    pop si
    pop cx
    pop ax
ret

cmps:
    mov bx, di
    mov bx, [bx]
    inc si
    inc di
    cmp bl, [si-1]
ret

word_in_string_is_on_pos:
    push ax
    push cx
    push si
    push di
    push bx
    mov ax, cx
    
    mov cx,0 
    mov cl, [word_buffer+1]
    mov bx, offset word_buffer+2
    mov di, bx
    
    mov bx, offset string_buffer+2
    add bx, ax
    mov si, bx
    clc
word_check:
    call cmps
    jne not_presented
    loop word_check
    
    
    
    mov bx, ax
    add bx, offset string_buffer+2
    dec bx
    cmp bx, offset string_buffer+1
    je check_aftter_space
    cmp [bx], ' '
    jne not_presented
check_aftter_space:    
    mov bx, si
    sub bl, [string_buffer+1]
    cmp bx, offset string_buffer+2
    je not_presented
    cmp [si],' '
    jne not_presented
    stc
    jmp is_presented
not_presented:
    clc
is_presented:
    pop bx
    pop di
    pop si
    pop cx
    pop ax 
ret



first_appear:
    push ax
    push cx
    
    cmp cl,[word_buffer+1] 
    jl no_word
    sub cl, [word_buffer+1]
    
    
    mov bx, 0
    mov bl, [string_buffer+1]
    cmp bl,[word_buffer+1]
    jl no_word
search_loop:
    call word_in_string_is_on_pos
    jc found_word
not_word:
    cmp cx, 0
    je one_letter
    loop search_loop
one_letter:
    call word_in_string_is_on_pos
    jnc no_word
found_word:
    mov bx,cx    
    jmp is_word   
no_word:
    mov bx, 201 
is_word:    
    pop cx
    pop ax
ret

    

first_word_after_pos_pos:
    push ax
    push cx   
    mov ax, bx   
first_space_search:
    cmp al,[string_buffer+1]
    jge no_word_after
    mov bx, offset string_buffer+2
    add bx, ax 
    cmp [bx], ' '  
    je second_not_space_search
    inc ax
    jmp first_space_search
second_not_space_search:
    cmp al,[string_buffer+1]
    jge no_word_after
    mov bx, offset string_buffer+2
    add bx, ax 
    cmp [bx], ' '  
    jne found_word_after
    inc ax
    jmp second_not_space_search    
found_word_after:
    mov bx,ax    
    jmp is_word_after    
no_word_after:    
    mov bx, 201
is_word_after:    
    pop cx
    pop ax
ret    



delete_letter_on_pos:
    push ax
    push cx
    push bx
    mov cx, 0 
    mov cl, [string_buffer+1]
    sub cx, bx
    inc cx
    dec [string_buffer+1]
    mov ax, bx
change_loop:
    mov bx, offset string_buffer+3
    add bx, ax
    mov dx, [bx]
    mov bx, offset string_buffer+2
    add bx, ax
    mov [bx], dl
    inc ax
    loop change_loop
    pop bx
    pop cx              ; here
    pop ax
ret



delete_word_on_pos:
deletion_loop:
    call delete_letter_on_pos
    cmp [string_buffer+bx+2], ' '
    je end_deletion
    cmp [string_buffer+1], bl
    jl end_deletion
    jmp deletion_loop
end_deletion:
jmp deleteSpace ; added
ret

deleteSpace:                 ;added
    dec bx                   ;added
    jmp delete_letter_on_pos ;added

delete_all_word_after_word:
    mov cx, 0
    mov cl, [string_buffer+1]
delete:
    call first_appear 
    cmp bx, 201
    je full_end_deletion
    mov cl, bl 
    call first_word_after_pos_pos
    cmp bx, 201
    je full_end_deletion 
    call delete_word_on_pos
    cmp cx, 0
    je full_end_deletion
    dec cl
    cmp cx, 0
    je full_end_deletion
    jmp delete
full_end_deletion:
ret


start:
    mov ax, @data 
    mov ds, ax
    mov es, ax
    jmp first_string
bad_string:
    lea dx, bad_string_message
    call print_str
first_string:
    mov dx, offset input_string_message
    call print_str
    mov dx, offset string_buffer
    call input_str
    cmp [string_buffer+1], 0
    je bad_string
    jmp input_word
bad_word:
    mov dx, offset bad_word_message
    call print_str
input_word:    
    mov dx, offset input_word_message
    call print_str
    
    mov dx, offset word_buffer
    call input_str
    call check_spaces
    jnc bad_word
    
    call delete_all_word_after_word
    
    mov dx, offset end_message
    call print_str
    mov bx, offset string_buffer 
    call print_buffer_str


End:
    mov ax, 4c00h 
    int 21h    
 
end start
