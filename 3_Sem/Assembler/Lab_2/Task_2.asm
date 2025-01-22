 org $8000     

 ldx #$8200
 ldy #$0020

loop:
 ldaa 0,x
 ldab 0,x

 ;ldaa #%01110001
 ;ldab #%01000000

 ANDA #%00000010
 ANDB #%00010000

 lsrb
 lsrb
 lsrb

 stab $8888
 oraa $8888

 lsla
 staa 0,x

 inx
 dey
 bne loop

