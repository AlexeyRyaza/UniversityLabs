 org $8000
  
 ldaa #%11001110 ; 01001010
 ldab #%10100101

 staa $80ca
 stab $80cc

 ANDA $80cc
 eora #$ff ; Invert
 ANDA $80ca




;fff6
;00f4