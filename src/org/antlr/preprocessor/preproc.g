header {
package org.antlr.preprocessor;
}

/* ANTLR Translator Generator
 * Project led by Terence Parr at http://www.jGuru.com
 * Software rights: http://www.antlr.org/RIGHTS.html
 *
 * $Id: //depot/code/org.antlr/release/antlr-2.7.0/antlr/preprocessor/preproc.g#1 $
 */

{
import org.antlr.collections.impl.IndexedVector;
import java.util.Hashtable;
import org.antlr.preprocessor.Grammar;
}

class Preprocessor extends Parser;
options {
	k=1;
	interactive=true;
}

tokens {
	"tokens";
}

grammarFile[Hierarchy hier, String file]
{
	Grammar gr;
	IndexedVector opt=null;
}
	:	( hdr:HEADER_ACTION { hier.getFile(file).addHeaderAction(hdr.getText()); } )*
		( opt=optionSpec[null] )?
		(	gr=class_def[hier]
			{
			if ( opt!=null ) {
				hier.getFile(file).setOptions(opt);
			}
			if ( gr!=null ) {
				gr.setFileName(file);
				hier.addGrammar(gr);
			}
			}
		)*
		EOF
	;

class_def[Hierarchy hier] returns [Grammar gr]
{
	gr=null;
	IndexedVector rules = new IndexedVector(100);
	IndexedVector classOptions = null;
}
	:	( preamble:ACTION )?
		"class" sub:ID "extends" sup:ID SEMI
		{
			gr = (Grammar)hier.getGrammar(sub.getText());
			if ( gr!=null ) {
				org.antlr.Tool.toolError("redefinition of grammar "+gr.getName()+" ignored");
				gr=null;
			}
			else {
				gr = new Grammar(sub.getText(), sup.getText(), rules);
				if ( preamble!=null ) {
					gr.setPreambleAction(preamble.getText());
				}
			}
		}
		( classOptions = optionSpec[gr] )?
		{
		if ( gr!=null ) {
			gr.setOptions(classOptions);
		}
		}
		( tk:TOKENS_SPEC {gr.setTokenSection(tk.getText());} )?
		( memberA:ACTION {gr.setMemberAction(memberA.getText());} )?
		( rule[gr] )+
	;

optionSpec[Grammar gr] returns [IndexedVector options]
{
	options = new IndexedVector();
}
	:	OPTIONS_START
			(	op:ID rhs:ASSIGN_RHS
				{
				Option newOp = new Option(op.getText(),rhs.getText(),gr);
				options.appendElement(newOp.getName(),newOp);
				if ( gr!=null && op.getText().equals("importVocab") ) {
					gr.specifiedVocabulary = true;
					gr.importVocab = rhs.getText();
				}
				else if ( gr!=null && op.getText().equals("exportVocab") ) {
					// don't want ';' included in outputVocab.
					// This is heinously inconsistent!  Ugh.
					gr.exportVocab = rhs.getText().substring(0,rhs.getText().length()-1);
				}
				}
			)*
//		{gr.fixupVocabOptionsForInheritance();}
		RCURLY
	;

rule[Grammar gr]
{
	IndexedVector o = null;	// options for rule
	String vis = null;
	boolean bang=false;
	String eg=null;
}
	:	(	"protected"	{vis="protected";}
		|	"private"	{vis="private";}
		|	"public"	{vis="public";}
		)?
		r:ID
		( BANG {bang=true;} )?
		( arg:ARG_ACTION )?
		( "returns" ret:ARG_ACTION )?
		( o = optionSpec[null] )?
		( init:ACTION )?
		blk:RULE_BLOCK
		eg=exceptionGroup
		{
		String rtext = blk.getText()+eg;
		Rule ppr = new Rule(r.getText(),rtext,o,gr);
		if ( arg!=null ) {
			ppr.setArgs(arg.getText());
		}
		if ( ret!=null ) {
			ppr.setReturnValue(ret.getText());
		}
		if ( init!=null ) {
			ppr.setInitAction(init.getText());
		}
		if ( bang ) {
			ppr.setBang();
		}
		ppr.setVisibility(vis);
		if ( gr!=null ) {
			gr.addRule(ppr);
		}
		}
	;

exceptionGroup returns [String g]
{String e=null; g="";}
	:	( e=exceptionSpec {g += e;} )*
	;

exceptionSpec returns [String es]
{ String h=null;
  es = System.getProperty("line.separator")+"exception ";
}
	:	"exception"
		( aa:ARG_ACTION {es += aa.getText();} )?
		( h=exceptionHandler {es += h;} )*
	;

exceptionHandler returns [String h]
{h=null;}
	:	"catch" a1:ARG_ACTION a2:ACTION
		{h = System.getProperty("line.separator")+
			 "catch "+a1.getText()+" "+a2.getText();}
	;

class PreprocessorLexer extends Lexer;
options {
	k=2;
	charVocabulary = '\3'..'\176';	// common ASCII
	interactive=true;
}

RULE_BLOCK
    :   ':' (options {greedy=true;}:WS!)?
		ALT (options {greedy=true;}:WS!)?
		( '|' (options {greedy=true;}:WS!)? ALT (options {greedy=true;}:WS!)? )* ';'
    ;

SUBRULE_BLOCK
	:	'(' (options {greedy=true;}:WS)? ALT
		(	options {greedy=true;}
		:	(WS)? '|' (options {greedy=true;}:WS)? ALT
		)*
		(WS)?
		')'
		(	options {greedy=true;}
		:	'*'
		|	'+'
		|	'?'
		|	"=>"
		)?
	;

protected
ALT	:	(options {greedy=true;} : ELEMENT)*
	;

protected
ELEMENT
	:	COMMENT
	|	ACTION	
	|	STRING_LITERAL
	|	CHAR_LITERAL
	|	SUBRULE_BLOCK
	|	NEWLINE
	|	~('\n' | '\r' | '(' | ')' | '/' | '{' | '"' | '\'' | ';')
	;

BANG:	'!'
	;

SEMI:	';'
	;

RCURLY
	:	'}'
	;

/** This rule picks off keywords in the lexer that need to be
 *  handled specially.  For example, "header" is the start
 *  of the header action (used to distinguish between options
 *  block and an action).  We do not want "header" to go back
 *  to the parser as a simple keyword...it must pick off
 *  the action afterwards.
 */
ID_OR_KEYWORD
	:	id:ID	{$setType(id.getType());}
		(	{id.getText().equals("header")}? (options {greedy=true;}:WS)?
			(STRING_LITERAL)? (WS)? ACTION
			{$setType(HEADER_ACTION);}
		|	{id.getText().equals("tokens")}? (WS)? CURLY_BLOCK_SCARF
			{$setType(TOKENS_SPEC);}
		|	{id.getText().equals("options")}? (WS)? '{'
			{$setType(OPTIONS_START);}
		)?
	;


protected
CURLY_BLOCK_SCARF
	:	'{'
		(	options {greedy=false;}
		:	NEWLINE
		|	.
		)*
		'}'
	;

protected
ID
options {
	testLiterals=true;
}
	:	('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')*
	;

ASSIGN_RHS
	:	'='!
		(	options {greedy=false;}
		:	STRING_LITERAL 
		|	CHAR_LITERAL 
		|	NEWLINE
		|	.
		)*
		';'
	;

WS	:	(	options {greedy=true;}
		: 	' '
		|	'\t'
		|	NEWLINE
		)+
		{$setType(Token.SKIP);}
	;

protected
NEWLINE
	:	(	options {
				generateAmbigWarnings=false;
			}
		:	'\r' '\n'	{newline();}
		|	'\r'		{newline();}
		|	'\n'		{newline();}
		)
	;

COMMENT
	:	( SL_COMMENT | ML_COMMENT )
		{$setType(Token.SKIP);}
	;

protected
SL_COMMENT
	:	"//" (options {greedy=false;}:.)* NEWLINE
	;

protected
ML_COMMENT :
	"/*"
	(	options {greedy=false;}
	:	NEWLINE
	|	.
	)*
	"*/"
	;

CHAR_LITERAL
	:	'\'' (ESC|~'\'') '\''
	;

STRING_LITERAL
	:	'"' (ESC|~'"')* '"'
	;

protected
ESC	:	'\\'
		(	'n'
		|	'r'
		|	't'
		|	'b'
		|	'f'
		|	'w'
		|	'a'
		|	'"'
		|	'\''
		|	'\\'
		|	('0'..'3')
			(	options {greedy=true;}
			:	DIGIT
				(	options {greedy=true;}
				:	DIGIT
				)?
			)?
		|	('4'..'7') (options {greedy=true;}:DIGIT)?
		|	'u' XDIGIT XDIGIT XDIGIT XDIGIT
		)
	;

protected
DIGIT
	:	'0'..'9'
	;

protected
XDIGIT
	:	'0' .. '9'
	|	'a' .. 'f'
	|	'A' .. 'F'
	;

ARG_ACTION
	:	'['
		(
			options {
				greedy=false;
			}
		:	ARG_ACTION
		|	NEWLINE
		|	CHAR_LITERAL 
		|	STRING_LITERAL 
		|	.
		)* 
		']'
	;

ACTION
	:	'{'
		(
			options {
				greedy=false;
			}
		:	NEWLINE
		|	ACTION
		|	CHAR_LITERAL
		|	COMMENT
		|	STRING_LITERAL
		|	.
		)*
		'}'
   ;
