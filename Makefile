##########################################
####
# Makefile for project 3
# Modified 2/2011 Raphael Finkel
#
# All classes are compiled into the ./classes directory
#
# The default is to make all .class files.
#
#    clean    -- removes all generated files
#
#    test     -- Tests proj4 by running P4.class with inputs test[123].lite
#
###

DESTPATH = ./classes
CUPPATH = ./cup
CLASSPATH = $(DESTPATH):$(CUPPATH)/java-cup-10k.jar
JASMINPATH = $(CUPPATH)/cup.jar:$(CUPPATH)/jasmin-sable.jar
VPATH = $(DESTPATH)
JCFLAGS = -Xlint:all,-auxiliaryclass,-rawtypes -deprecation -classpath $(CLASSPATH) -d $(DESTPATH)
JFLAGS = -classpath $(CLASSPATH)

%.class: %.java
	javac $(JCFLAGS) $<

.PHONY: all 
all: P5.class Yylex.class parser.class 

# don't use CUP 0.11a; I couldn't get it to work right.  -- Raphael 2/2011
sym.java parser.java: csx_go.cup 
	java $(JFLAGS) java_cup/Main < csx_go.cup

Yylex.java: csx_go.jlex
	jflex csx_go.jlex
parser.class: ASTNode.class Scanner.class SyntaxErrorException.class
Yylex.class: sym.class 
SymbolTable.class: Symb.class EmptySTException.class DuplicateException.class
Scanner.class: Yylex.class Types.class
ArgPairs.class: Kinds.class Types.class 
SymbolInfo.class: Kinds.class ArgPairs.class
ASTNode.class: ast.java sym.class SymbolTable.class ArgPairs.class SymbolInfo.class
	javac $(JCFLAGS) ast.java
P5.class: Yylex.class Scanner.class parser.class ASTNode.class

test: all CSXLib.class
	java $(JFLAGS) P5 tests/test-38.csx_go
	java -classpath $(JASMINPATH) jasmin.Main tester.j
	java -classpath .:./classes   p38csx
	@echo

###
# style check
###

HOW = basic,braces,clone,design,finalizers,migrating,unusedcode,imports,optimizations,strictexception,strings,sunsecure,typeresolution

style: 
	t=`pwd`; cd ~raphael/source/pmd-4.2.5/bin; \
		./pmd.sh $$t text $(HOW)

###
# lint check
###

lint: all
	jlint +all classes

###
# clean
###

clean: 
	rm -f $(DESTPATH)/*.class  sym.java parser.java test.j *.class
