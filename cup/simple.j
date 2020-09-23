.class public simple

.super java/lang/Object


.method public static main([Ljava/lang/String;)V
invokestatic simple/main()V

return

.limit stack 2

.end method

.method public static main()V

.limit locals 1
invokestatic CSXLib/readInt()I

istore 0

ldc "Answer = "


invokestatic CSXLib/printString(Ljava/lang/String;)V
ldc 2

iload 0
; Push local 0 (a) onto stack
imul
; Multiply top two stack values
ldc 1
; Push 1 onto stack
iadd
; Add top two stack values
invokestatic CSXLib/printInt(I)V
; Call CSXLib.printInt(int)
ldc 10
; Push 10 ('\n') onto stack
invokestatic CSXLib/printChar(C)V
; Call CSXLib.printChar(char)
return
; return from main()
.limit stack 25
; Max stack depth needed(overestimate)
.end method
; End of body of main()
