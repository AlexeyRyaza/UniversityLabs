 ldx #$ffff
 ldy #$fffe

 stx $8000
 sty $8002

 ldaa $8000
 ldab $8002
 mul
 std $8004

 ldaa $8000
 ldab $8003
 mul
 std $8006
 
 ldaa $8002
 ldab $8001
 mul
 std $8008

 ldaa $8001
 ldab $8003
 mul
 std $800a

 stab $8013

 ldx $8004
 ldab $8006
 abx
 ldab $8008
 abx

 clra
 clrb
 xgdx
 std $8004
 clra
 clrb

 ldab $8007
 xgdx
 ldab $8009
 abx
 ldab $800a
 abx

 clra
 clrb
 xgdx
 stab $8012
 
 tab
 clra
 xgdx

 ldd $8004
 xgdx
 abx
 xgdx
 std $8010


 