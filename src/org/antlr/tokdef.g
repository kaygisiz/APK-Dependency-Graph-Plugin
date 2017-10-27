/* ANTLR Translator Generator
 * Project led by Terence Parr at http://www.jGuru.com
 * Software rights: http://www.antlr.org/RIGHTS.html
 *
 * $Id: //depot/code/org.antlr/release/antlr-2.7.0/antlr/tokdef.g#1 $
 */

header { package org.antlr; }

/** Simple lexer/parser for reading token definition files
  in support of the import/export vocab option for grammars.
 */
class ANTLRTokdefParser extends Parser;
options {
	k=3;
	interactive=true;
}

file [ImportVocabTokenManager tm] : 
	name:ID
	(line[tm])*;

line [ImportVocabTokenManager tm]
{ Token t=null; Token s=null; }
	:	(	s1:STRING {s = s1;}
		|	lab:ID {t = lab;} ASSIGN s2:STRING {s = s2;}
		|	id:ID {t=id;} LPAREN para:STRING RPAREN
		|	id2:ID {t=id2;}
		)
		ASSIGN 
		i:INT
		{
		Integer value = Integer.valueOf(i.getText());
		// if literal found, define as a string literal
		if ( s!=null ) {
			tm.define(s.getText(), value.intValue());
			// if label, then label the string and map label to token symbol also
			if ( t!=null ) {
				StringLiteralSymbol sl =
					(StringLiteralSymbol) tm.getTokenSymbol(s.getText());
				sl.setLabel(t.getText());
				tm.mapToTokenSymbol(t.getText(), sl);
			}
		}
		// define token (not a literal)
		else if ( t!=null ) {
			tm.define(t.getText(), value.intValue());
			if ( para!=null ) {
				TokenSymbol ts = tm.getTokenSymbol(t.getText());
				ts.setParaphrase(
					para.getText()
				);
			}
		}
		}
	;

class ANTLRTokdefLexer extends Lexer;
options { 
	k=2;
	testLiterals=false;
	interactive=true;
}

WS	:	(	' '
		|	'\t'
		|	'\r' ('\n')?	{newline();}
		|	'\n'		{newline();}
		)
		{ _ttype = Token.SKIP; }
	;

SL_COMMENT :
	"//"
	(~('\n'|'\r'))* ('\n'|'\r'('\n')?)
	{ _ttype = Token.SKIP; newline(); }
	;

ML_COMMENT :
   "/*"
   (
			'\n' { newline(); }
		|	'*' ~'/'
		|	~'*'
	)*
	"*/"
	{ _ttype = Token.SKIP; }
	;

LPAREN : '(' ;
RPAREN : ')' ;

ASSIGN : '=' ;

STRING
	:	'"' (ESC|~'"')* '"'
	;

protected
ESC	:	'\\'
		(	'n'
		|	'r'
		|	't'
		|	'b'
		|	'f'
		|	'"'
		|	'\''
		|	'\\'
		|	('0'..'3') ( DIGIT (DIGIT)? )?
		|	('4'..'7') (DIGIT)?
		|	'u' XDIGIT XDIGIT XDIGIT XDIGIT
		)
	;

protected
DIGIT
	:	'0'..'9'
	;

protected
XDIGIT :
		'0' .. '9'
	|	'a' .. 'f'
	|	'A' .. 'F'
	;

protected
VOCAB
	:	'\3'..'\176'	// common ASCII
	;

ID :
	('a'..'z'|'A'..'Z') 
	('a'..'z'|'A'..'Z'|'_'|'0'..'9')*
	;

INT : (DIGIT)+
	;
