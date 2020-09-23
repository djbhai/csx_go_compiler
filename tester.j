	.class	public  p38csx
	.super	java/lang/Object
	.field static	dim  I
	.field static	a1  I
	.field static	b1  I
	.field static	c1  I
	.field static	d1  I
	.field static	e1  I
	.field static	f1  I
	.field static	g1  I
	.field static	h1  I
	.field static	a2  I
	.field static	b2  I
	.field static	c2  I
	.field static	d2  I
	.field static	e2  I
	.field static	f2  I
	.field static	g2  I
	.field static	h2  I
	.field static	count  I
	.field static	flag1  Z
	.field static	flag2  Z
	.field static	flag3  Z
	.field static	flag4  Z
	.field static	flag5  Z
	.field static	flag6  Z
	.field static	flag7  Z
	.method	public static main([Ljava/lang/String;)V
	ldc 8
	putstatic	p38csx/dim  I
	ldc 1
	putstatic	p38csx/a1  I
	ldc 2
	putstatic	p38csx/b1  I
	ldc 3
	putstatic	p38csx/c1  I
	ldc 4
	putstatic	p38csx/d1  I
	ldc 5
	putstatic	p38csx/e1  I
	ldc 6
	putstatic	p38csx/f1  I
	ldc 7
	putstatic	p38csx/g1  I
	ldc 8
	putstatic	p38csx/h1  I
	ldc 0
	putstatic	p38csx/count  I
	.limit locals	1
	invokestatic p38csx/main()V
	return
	.limit stack 51
	.end	method
	.method	public static compatible(IIII)Z
	.limit	locals 5
	iload	1
	i2l
	iload	3
	i2l
	lcmp
	ifeq	#3
	iconst_0
	goto	#4
	#3:
	iconst_1
	#4:
	ifeq	#2
	iconst_0
	ireturn
	#2:
	iload	3
	iload	1
	isub
	i2l
	iload	2
	iload	0
	isub
	i2l
	lcmp
	ifeq	#7
	iconst_0
	goto	#8
	#7:
	iconst_1
	#8:
	iload	1
	iload	3
	isub
	i2l
	iload	2
	iload	0
	isub
	i2l
	lcmp
	ifeq	#9
	iconst_0
	goto	#10
	#9:
	iconst_1
	#10:
	ior
	ifeq	#5
	iconst_0
	ireturn
	goto	#6
	#5:
	iconst_1
	ireturn
	#6:
	return
	.limit stack 30
	.end method
	.method	public static main()V
	.limit	locals 2
	ldc "Testing Program p38csx (8 queens problem)"
	invokestatic CSXLib/printString(Ljava/lang/String;)V
	ldc "\n"
	invokestatic CSXLib/printString(Ljava/lang/String;)V
	ldc 1
	putstatic	p38csx/a2  I
	ldc 1
	putstatic	p38csx/b2  I
	ldc 1
	putstatic	p38csx/c2  I
	ldc 1
	putstatic	p38csx/d2  I
	ldc 1
	putstatic	p38csx/e2  I
	ldc 1
	putstatic	p38csx/f2  I
	ldc 1
	putstatic	p38csx/g2  I
	ldc 1
	putstatic	p38csx/h2  I
	#11	:
	getstatic	p38csx/a2  I
	i2l
	ldc 8
	i2l
	lcmp
	ifle	#13
	iconst_0
	goto	#14
	#13:
	iconst_1
	#14:
	ifeq	#12
	#15	:
	getstatic	p38csx/b2  I
	i2l
	ldc 8
	i2l
	lcmp
	ifle	#17
	iconst_0
	goto	#18
	#17:
	iconst_1
	#18:
	ifeq	#16
	getstatic	p38csx/a1  I
	getstatic	p38csx/a2  I
	getstatic	p38csx/b1  I
	getstatic	p38csx/b2  I
	invokestatic	p38csx/compatible(IIII)Z
	putstatic	p38csx/flag1  Z
	getstatic	p38csx/flag1  Z
	ifeq	#20
	#21	:
	getstatic	p38csx/c2  I
	i2l
	ldc 8
	i2l
	lcmp
	ifle	#23
	iconst_0
	goto	#24
	#23:
	iconst_1
	#24:
	ifeq	#22
	getstatic	p38csx/a1  I
	getstatic	p38csx/a2  I
	getstatic	p38csx/c1  I
	getstatic	p38csx/c2  I
	invokestatic	p38csx/compatible(IIII)Z
	putstatic	p38csx/flag1  Z
	getstatic	p38csx/b1  I
	getstatic	p38csx/b2  I
	getstatic	p38csx/c1  I
	getstatic	p38csx/c2  I
	invokestatic	p38csx/compatible(IIII)Z
	putstatic	p38csx/flag2  Z
	getstatic	p38csx/flag1  Z
	getstatic	p38csx/flag2  Z
	iand
	ifeq	#26
	#27	:
	getstatic	p38csx/d2  I
	i2l
	ldc 8
	i2l
	lcmp
	ifle	#29
	iconst_0
	goto	#30
	#29:
	iconst_1
	#30:
	ifeq	#28
	getstatic	p38csx/a1  I
	getstatic	p38csx/a2  I
	getstatic	p38csx/d1  I
	getstatic	p38csx/d2  I
	invokestatic	p38csx/compatible(IIII)Z
	putstatic	p38csx/flag1  Z
	getstatic	p38csx/b1  I
	getstatic	p38csx/b2  I
	getstatic	p38csx/d1  I
	getstatic	p38csx/d2  I
	invokestatic	p38csx/compatible(IIII)Z
	putstatic	p38csx/flag2  Z
	getstatic	p38csx/c1  I
	getstatic	p38csx/c2  I
	getstatic	p38csx/d1  I
	getstatic	p38csx/d2  I
	invokestatic	p38csx/compatible(IIII)Z
	putstatic	p38csx/flag3  Z
	getstatic	p38csx/flag1  Z
	getstatic	p38csx/flag2  Z
	iand
	getstatic	p38csx/flag3  Z
	iand
	ifeq	#32
	#33	:
	getstatic	p38csx/e2  I
	i2l
	ldc 8
	i2l
	lcmp
	ifle	#35
	iconst_0
	goto	#36
	#35:
	iconst_1
	#36:
	ifeq	#34
	getstatic	p38csx/a1  I
	getstatic	p38csx/a2  I
	getstatic	p38csx/e1  I
	getstatic	p38csx/e2  I
	invokestatic	p38csx/compatible(IIII)Z
	putstatic	p38csx/flag1  Z
	getstatic	p38csx/b1  I
	getstatic	p38csx/b2  I
	getstatic	p38csx/e1  I
	getstatic	p38csx/e2  I
	invokestatic	p38csx/compatible(IIII)Z
	putstatic	p38csx/flag2  Z
	getstatic	p38csx/c1  I
	getstatic	p38csx/c2  I
	getstatic	p38csx/e1  I
	getstatic	p38csx/e2  I
	invokestatic	p38csx/compatible(IIII)Z
	putstatic	p38csx/flag3  Z
	getstatic	p38csx/d1  I
	getstatic	p38csx/d2  I
	getstatic	p38csx/e1  I
	getstatic	p38csx/e2  I
	invokestatic	p38csx/compatible(IIII)Z
	putstatic	p38csx/flag4  Z
	getstatic	p38csx/flag1  Z
	getstatic	p38csx/flag2  Z
	iand
	getstatic	p38csx/flag3  Z
	iand
	getstatic	p38csx/flag4  Z
	iand
	ifeq	#38
	#39	:
	getstatic	p38csx/f2  I
	i2l
	ldc 8
	i2l
	lcmp
	ifle	#41
	iconst_0
	goto	#42
	#41:
	iconst_1
	#42:
	ifeq	#40
	getstatic	p38csx/a1  I
	getstatic	p38csx/a2  I
	getstatic	p38csx/f1  I
	getstatic	p38csx/f2  I
	invokestatic	p38csx/compatible(IIII)Z
	putstatic	p38csx/flag1  Z
	getstatic	p38csx/b1  I
	getstatic	p38csx/b2  I
	getstatic	p38csx/f1  I
	getstatic	p38csx/f2  I
	invokestatic	p38csx/compatible(IIII)Z
	putstatic	p38csx/flag2  Z
	getstatic	p38csx/c1  I
	getstatic	p38csx/c2  I
	getstatic	p38csx/f1  I
	getstatic	p38csx/f2  I
	invokestatic	p38csx/compatible(IIII)Z
	putstatic	p38csx/flag3  Z
	getstatic	p38csx/d1  I
	getstatic	p38csx/d2  I
	getstatic	p38csx/f1  I
	getstatic	p38csx/f2  I
	invokestatic	p38csx/compatible(IIII)Z
	putstatic	p38csx/flag4  Z
	getstatic	p38csx/e1  I
	getstatic	p38csx/e2  I
	getstatic	p38csx/f1  I
	getstatic	p38csx/f2  I
	invokestatic	p38csx/compatible(IIII)Z
	putstatic	p38csx/flag5  Z
	getstatic	p38csx/flag1  Z
	getstatic	p38csx/flag2  Z
	iand
	getstatic	p38csx/flag3  Z
	iand
	getstatic	p38csx/flag4  Z
	iand
	getstatic	p38csx/flag5  Z
	iand
	ifeq	#44
	#45	:
	getstatic	p38csx/g2  I
	i2l
	ldc 8
	i2l
	lcmp
	ifle	#47
	iconst_0
	goto	#48
	#47:
	iconst_1
	#48:
	ifeq	#46
	getstatic	p38csx/a1  I
	getstatic	p38csx/a2  I
	getstatic	p38csx/g1  I
	getstatic	p38csx/g2  I
	invokestatic	p38csx/compatible(IIII)Z
	putstatic	p38csx/flag1  Z
	getstatic	p38csx/b1  I
	getstatic	p38csx/b2  I
	getstatic	p38csx/g1  I
	getstatic	p38csx/g2  I
	invokestatic	p38csx/compatible(IIII)Z
	putstatic	p38csx/flag2  Z
	getstatic	p38csx/c1  I
	getstatic	p38csx/c2  I
	getstatic	p38csx/g1  I
	getstatic	p38csx/g2  I
	invokestatic	p38csx/compatible(IIII)Z
	putstatic	p38csx/flag3  Z
	getstatic	p38csx/d1  I
	getstatic	p38csx/d2  I
	getstatic	p38csx/g1  I
	getstatic	p38csx/g2  I
	invokestatic	p38csx/compatible(IIII)Z
	putstatic	p38csx/flag4  Z
	getstatic	p38csx/e1  I
	getstatic	p38csx/e2  I
	getstatic	p38csx/g1  I
	getstatic	p38csx/g2  I
	invokestatic	p38csx/compatible(IIII)Z
	putstatic	p38csx/flag5  Z
	getstatic	p38csx/f1  I
	getstatic	p38csx/f2  I
	getstatic	p38csx/g1  I
	getstatic	p38csx/g2  I
	invokestatic	p38csx/compatible(IIII)Z
	putstatic	p38csx/flag6  Z
	getstatic	p38csx/flag1  Z
	getstatic	p38csx/flag2  Z
	iand
	getstatic	p38csx/flag3  Z
	iand
	getstatic	p38csx/flag4  Z
	iand
	getstatic	p38csx/flag5  Z
	iand
	getstatic	p38csx/flag6  Z
	iand
	ifeq	#50
	#51	:
	getstatic	p38csx/h2  I
	i2l
	ldc 8
	i2l
	lcmp
	ifle	#53
	iconst_0
	goto	#54
	#53:
	iconst_1
	#54:
	ifeq	#52
	getstatic	p38csx/a1  I
	getstatic	p38csx/a2  I
	getstatic	p38csx/h1  I
	getstatic	p38csx/h2  I
	invokestatic	p38csx/compatible(IIII)Z
	putstatic	p38csx/flag1  Z
	getstatic	p38csx/b1  I
	getstatic	p38csx/b2  I
	getstatic	p38csx/h1  I
	getstatic	p38csx/h2  I
	invokestatic	p38csx/compatible(IIII)Z
	putstatic	p38csx/flag2  Z
	getstatic	p38csx/c1  I
	getstatic	p38csx/c2  I
	getstatic	p38csx/h1  I
	getstatic	p38csx/h2  I
	invokestatic	p38csx/compatible(IIII)Z
	putstatic	p38csx/flag3  Z
	getstatic	p38csx/d1  I
	getstatic	p38csx/d2  I
	getstatic	p38csx/h1  I
	getstatic	p38csx/h2  I
	invokestatic	p38csx/compatible(IIII)Z
	putstatic	p38csx/flag4  Z
	getstatic	p38csx/e1  I
	getstatic	p38csx/e2  I
	getstatic	p38csx/h1  I
	getstatic	p38csx/h2  I
	invokestatic	p38csx/compatible(IIII)Z
	putstatic	p38csx/flag5  Z
	getstatic	p38csx/f1  I
	getstatic	p38csx/f2  I
	getstatic	p38csx/h1  I
	getstatic	p38csx/h2  I
	invokestatic	p38csx/compatible(IIII)Z
	putstatic	p38csx/flag6  Z
	getstatic	p38csx/g1  I
	getstatic	p38csx/g2  I
	getstatic	p38csx/h1  I
	getstatic	p38csx/h2  I
	invokestatic	p38csx/compatible(IIII)Z
	putstatic	p38csx/flag7  Z
	getstatic	p38csx/flag1  Z
	getstatic	p38csx/flag2  Z
	iand
	getstatic	p38csx/flag3  Z
	iand
	getstatic	p38csx/flag4  Z
	iand
	getstatic	p38csx/flag5  Z
	iand
	getstatic	p38csx/flag6  Z
	iand
	getstatic	p38csx/flag7  Z
	iand
	ifeq	#56
	getstatic	p38csx/count  I
	ldc 1
	iadd
	putstatic	p38csx/count  I
	ldc "\n"
	invokestatic CSXLib/printString(Ljava/lang/String;)V
	ldc "Solution#"
	invokestatic CSXLib/printString(Ljava/lang/String;)V
	getstatic	p38csx/count  I
	invokestatic CSXLib/printInt(I)V
	ldc " is:"
	invokestatic CSXLib/printString(Ljava/lang/String;)V
	ldc "\n"
	invokestatic CSXLib/printString(Ljava/lang/String;)V
	ldc " "
	invokestatic CSXLib/printString(Ljava/lang/String;)V
	getstatic	p38csx/a2  I
	invokestatic CSXLib/printInt(I)V
	ldc " "
	invokestatic CSXLib/printString(Ljava/lang/String;)V
	getstatic	p38csx/b2  I
	invokestatic CSXLib/printInt(I)V
	ldc " "
	invokestatic CSXLib/printString(Ljava/lang/String;)V
	getstatic	p38csx/c2  I
	invokestatic CSXLib/printInt(I)V
	ldc " "
	invokestatic CSXLib/printString(Ljava/lang/String;)V
	getstatic	p38csx/d2  I
	invokestatic CSXLib/printInt(I)V
	ldc " "
	invokestatic CSXLib/printString(Ljava/lang/String;)V
	getstatic	p38csx/e2  I
	invokestatic CSXLib/printInt(I)V
	ldc " "
	invokestatic CSXLib/printString(Ljava/lang/String;)V
	getstatic	p38csx/f2  I
	invokestatic CSXLib/printInt(I)V
	ldc " "
	invokestatic CSXLib/printString(Ljava/lang/String;)V
	getstatic	p38csx/g2  I
	invokestatic CSXLib/printInt(I)V
	ldc " "
	invokestatic CSXLib/printString(Ljava/lang/String;)V
	getstatic	p38csx/h2  I
	invokestatic CSXLib/printInt(I)V
	ldc "\n"
	invokestatic CSXLib/printString(Ljava/lang/String;)V
	#56:
	getstatic	p38csx/h2  I
	ldc 1
	iadd
	putstatic	p38csx/h2  I
	goto	#51
	#52	:
	#50:
	getstatic	p38csx/g2  I
	ldc 1
	iadd
	putstatic	p38csx/g2  I
	ldc 1
	putstatic	p38csx/h2  I
	goto	#45
	#46	:
	#44:
	getstatic	p38csx/f2  I
	ldc 1
	iadd
	putstatic	p38csx/f2  I
	ldc 1
	putstatic	p38csx/g2  I
	ldc 1
	putstatic	p38csx/h2  I
	goto	#39
	#40	:
	#38:
	getstatic	p38csx/e2  I
	ldc 1
	iadd
	putstatic	p38csx/e2  I
	ldc 1
	putstatic	p38csx/f2  I
	ldc 1
	putstatic	p38csx/g2  I
	ldc 1
	putstatic	p38csx/h2  I
	goto	#33
	#34	:
	#32:
	getstatic	p38csx/d2  I
	ldc 1
	iadd
	putstatic	p38csx/d2  I
	ldc 1
	putstatic	p38csx/e2  I
	ldc 1
	putstatic	p38csx/f2  I
	ldc 1
	putstatic	p38csx/g2  I
	ldc 1
	putstatic	p38csx/h2  I
	goto	#27
	#28	:
	#26:
	getstatic	p38csx/c2  I
	ldc 1
	iadd
	putstatic	p38csx/c2  I
	ldc 1
	putstatic	p38csx/d2  I
	ldc 1
	putstatic	p38csx/e2  I
	ldc 1
	putstatic	p38csx/f2  I
	ldc 1
	putstatic	p38csx/g2  I
	ldc 1
	putstatic	p38csx/h2  I
	goto	#21
	#22	:
	#20:
	getstatic	p38csx/b2  I
	ldc 1
	iadd
	putstatic	p38csx/b2  I
	ldc 1
	putstatic	p38csx/c2  I
	ldc 1
	putstatic	p38csx/d2  I
	ldc 1
	putstatic	p38csx/e2  I
	ldc 1
	putstatic	p38csx/f2  I
	ldc 1
	putstatic	p38csx/g2  I
	ldc 1
	putstatic	p38csx/h2  I
	goto	#15
	#16	:
	getstatic	p38csx/a2  I
	ldc 1
	iadd
	putstatic	p38csx/a2  I
	ldc 1
	putstatic	p38csx/b2  I
	ldc 1
	putstatic	p38csx/c2  I
	ldc 1
	putstatic	p38csx/d2  I
	ldc 1
	putstatic	p38csx/e2  I
	ldc 1
	putstatic	p38csx/f2  I
	ldc 1
	putstatic	p38csx/g2  I
	ldc 1
	putstatic	p38csx/h2  I
	goto	#11
	#12	:
	return
	.limit stack 27
	.end method
