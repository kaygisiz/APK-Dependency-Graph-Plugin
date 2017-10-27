// $ANTLR 2.7.0a11: "preproc.g" -> "PreprocessorLexer.java"$

package org.antlr.preprocessor;

import java.io.InputStream;
import org.antlr.TokenStreamException;
import org.antlr.TokenStreamIOException;
import org.antlr.TokenStreamRecognitionException;
import org.antlr.CharStreamException;
import org.antlr.CharStreamIOException;
import org.antlr.ANTLRException;
import java.io.Reader;
import java.util.Hashtable;
import org.antlr.CharScanner;
import org.antlr.InputBuffer;
import org.antlr.ByteBuffer;
import org.antlr.CharBuffer;
import org.antlr.Token;
import org.antlr.CommonToken;
import org.antlr.RecognitionException;
import org.antlr.NoViableAltForCharException;
import org.antlr.MismatchedCharException;
import org.antlr.TokenStream;
import org.antlr.ANTLRHashString;
import org.antlr.LexerSharedInputState;
import org.antlr.collections.impl.BitSet;
public class PreprocessorLexer extends org.antlr.CharScanner implements PreprocessorTokenTypes, TokenStream
 {
public PreprocessorLexer(InputStream in) {
	this(new ByteBuffer(in));
}
public PreprocessorLexer(Reader in) {
	this(new CharBuffer(in));
}
public PreprocessorLexer(InputBuffer ib) {
	this(new LexerSharedInputState(ib));
}
public PreprocessorLexer(LexerSharedInputState state) {
	super(state);
	literals = new Hashtable();
	literals.put(new ANTLRHashString("catch", this), new Integer(23));
	literals.put(new ANTLRHashString("exception", this), new Integer(22));
	literals.put(new ANTLRHashString("class", this), new Integer(7));
	literals.put(new ANTLRHashString("public", this), new Integer(17));
	literals.put(new ANTLRHashString("tokens", this), new Integer(4));
	literals.put(new ANTLRHashString("returns", this), new Integer(20));
	literals.put(new ANTLRHashString("private", this), new Integer(16));
	literals.put(new ANTLRHashString("protected", this), new Integer(15));
	literals.put(new ANTLRHashString("extends", this), new Integer(9));
caseSensitiveLiterals = true;
setCaseSensitive(true);
}

public Token nextToken() throws TokenStreamException {
	Token theRetToken=null;
tryAgain:
	for (;;) {
		Token _token = null;
		int _ttype = Token.INVALID_TYPE;
		resetText();
		try {   // for char stream error handling
			try {   // for lexical error handling
				switch ( LA(1)) {
				case ':':
				{
					mRULE_BLOCK(true);
					theRetToken=_returnToken;
					break;
				}
				case '\t':  case '\n':  case '\r':  case ' ':
				{
					mWS(true);
					theRetToken=_returnToken;
					break;
				}
				case '(':
				{
					mSUBRULE_BLOCK(true);
					theRetToken=_returnToken;
					break;
				}
				case '/':
				{
					mCOMMENT(true);
					theRetToken=_returnToken;
					break;
				}
				case '{':
				{
					mACTION(true);
					theRetToken=_returnToken;
					break;
				}
				case '"':
				{
					mSTRING_LITERAL(true);
					theRetToken=_returnToken;
					break;
				}
				case '\'':
				{
					mCHAR_LITERAL(true);
					theRetToken=_returnToken;
					break;
				}
				case '!':
				{
					mBANG(true);
					theRetToken=_returnToken;
					break;
				}
				case ';':
				{
					mSEMI(true);
					theRetToken=_returnToken;
					break;
				}
				case '}':
				{
					mRCURLY(true);
					theRetToken=_returnToken;
					break;
				}
				case 'A':  case 'B':  case 'C':  case 'D':
				case 'E':  case 'F':  case 'G':  case 'H':
				case 'I':  case 'J':  case 'K':  case 'L':
				case 'M':  case 'N':  case 'O':  case 'P':
				case 'Q':  case 'R':  case 'S':  case 'T':
				case 'U':  case 'V':  case 'W':  case 'X':
				case 'Y':  case 'Z':  case '_':  case 'a':
				case 'b':  case 'c':  case 'd':  case 'e':
				case 'f':  case 'g':  case 'h':  case 'i':
				case 'j':  case 'k':  case 'l':  case 'm':
				case 'n':  case 'o':  case 'p':  case 'q':
				case 'r':  case 's':  case 't':  case 'u':
				case 'v':  case 'w':  case 'x':  case 'y':
				case 'z':
				{
					mID_OR_KEYWORD(true);
					theRetToken=_returnToken;
					break;
				}
				case '=':
				{
					mASSIGN_RHS(true);
					theRetToken=_returnToken;
					break;
				}
				case '[':
				{
					mARG_ACTION(true);
					theRetToken=_returnToken;
					break;
				}
				default:
				{
					if (LA(1)==EOF_CHAR) {uponEOF(); _returnToken = makeToken(Token.EOF_TYPE);}
				else {throw new NoViableAltForCharException(LA(1), getFilename(), getLine());}
				}
				}
				if ( _returnToken==null ) continue tryAgain; // found SKIP token
				_ttype = _returnToken.getType();
				_ttype = testLiteralsTable(_ttype);
				_returnToken.setType(_ttype);
				return _returnToken;
			}
			catch (RecognitionException e) {
				reportError(e);
				consume();
			}
		}
		catch (CharStreamException cse) {
			if ( cse instanceof CharStreamIOException ) {
				throw new TokenStreamIOException(((CharStreamIOException)cse).io);
			}
			else {
				throw new TokenStreamException(cse.getMessage());
			}
		}
	}
}

	public final void mRULE_BLOCK(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = RULE_BLOCK;
		int _saveIndex;
		
		match(':');
		{
		if ((_tokenSet_0.member(LA(1))) && (_tokenSet_1.member(LA(2)))) {
			_saveIndex=text.length();
			mWS(false);
			text.setLength(_saveIndex);
		}
		else if ((_tokenSet_1.member(LA(1))) && (true)) {
		}
		else {
			throw new NoViableAltForCharException(LA(1), getFilename(), getLine());
		}
		
		}
		mALT(false);
		{
		switch ( LA(1)) {
		case '\t':  case '\n':  case '\r':  case ' ':
		{
			_saveIndex=text.length();
			mWS(false);
			text.setLength(_saveIndex);
			break;
		}
		case ';':  case '|':
		{
			break;
		}
		default:
		{
			throw new NoViableAltForCharException(LA(1), getFilename(), getLine());
		}
		}
		}
		{
		_loop38:
		do {
			if ((LA(1)=='|')) {
				match('|');
				{
				if ((_tokenSet_0.member(LA(1))) && (_tokenSet_1.member(LA(2)))) {
					_saveIndex=text.length();
					mWS(false);
					text.setLength(_saveIndex);
				}
				else if ((_tokenSet_1.member(LA(1))) && (true)) {
				}
				else {
					throw new NoViableAltForCharException(LA(1), getFilename(), getLine());
				}
				
				}
				mALT(false);
				{
				switch ( LA(1)) {
				case '\t':  case '\n':  case '\r':  case ' ':
				{
					_saveIndex=text.length();
					mWS(false);
					text.setLength(_saveIndex);
					break;
				}
				case ';':  case '|':
				{
					break;
				}
				default:
				{
					throw new NoViableAltForCharException(LA(1), getFilename(), getLine());
				}
				}
				}
			}
			else {
				break _loop38;
			}
			
		} while (true);
		}
		match(';');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mWS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = WS;
		int _saveIndex;
		
		{
		int _cnt74=0;
		_loop74:
		do {
			if ((LA(1)==' ') && (true)) {
				match(' ');
			}
			else if ((LA(1)=='\t') && (true)) {
				match('\t');
			}
			else if ((LA(1)=='\n'||LA(1)=='\r') && (true)) {
				mNEWLINE(false);
			}
			else {
				if ( _cnt74>=1 ) { break _loop74; } else {throw new NoViableAltForCharException(LA(1), getFilename(), getLine());}
			}
			
			_cnt74++;
		} while (true);
		}
		_ttype = Token.SKIP;
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mALT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = ALT;
		int _saveIndex;
		
		{
		_loop49:
		do {
			if ((_tokenSet_2.member(LA(1))) && ((LA(2) >= '\3' && LA(2) <= '~'))) {
				mELEMENT(false);
			}
			else {
				break _loop49;
			}
			
		} while (true);
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mSUBRULE_BLOCK(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = SUBRULE_BLOCK;
		int _saveIndex;
		
		match('(');
		{
		if ((_tokenSet_0.member(LA(1))) && (_tokenSet_3.member(LA(2)))) {
			mWS(false);
		}
		else if ((_tokenSet_3.member(LA(1))) && (true)) {
		}
		else {
			throw new NoViableAltForCharException(LA(1), getFilename(), getLine());
		}
		
		}
		mALT(false);
		{
		_loop44:
		do {
			if ((_tokenSet_4.member(LA(1))) && (_tokenSet_3.member(LA(2)))) {
				{
				switch ( LA(1)) {
				case '\t':  case '\n':  case '\r':  case ' ':
				{
					mWS(false);
					break;
				}
				case '|':
				{
					break;
				}
				default:
				{
					throw new NoViableAltForCharException(LA(1), getFilename(), getLine());
				}
				}
				}
				match('|');
				{
				if ((_tokenSet_0.member(LA(1))) && (_tokenSet_3.member(LA(2)))) {
					mWS(false);
				}
				else if ((_tokenSet_3.member(LA(1))) && (true)) {
				}
				else {
					throw new NoViableAltForCharException(LA(1), getFilename(), getLine());
				}
				
				}
				mALT(false);
			}
			else {
				break _loop44;
			}
			
		} while (true);
		}
		{
		switch ( LA(1)) {
		case '\t':  case '\n':  case '\r':  case ' ':
		{
			mWS(false);
			break;
		}
		case ')':
		{
			break;
		}
		default:
		{
			throw new NoViableAltForCharException(LA(1), getFilename(), getLine());
		}
		}
		}
		match(')');
		{
		if ((LA(1)=='=') && (LA(2)=='>')) {
			match("=>");
		}
		else if ((LA(1)=='*') && (true)) {
			match('*');
		}
		else if ((LA(1)=='+') && (true)) {
			match('+');
		}
		else if ((LA(1)=='?') && (true)) {
			match('?');
		}
		else {
		}
		
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mELEMENT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = ELEMENT;
		int _saveIndex;
		
		switch ( LA(1)) {
		case '/':
		{
			mCOMMENT(false);
			break;
		}
		case '{':
		{
			mACTION(false);
			break;
		}
		case '"':
		{
			mSTRING_LITERAL(false);
			break;
		}
		case '\'':
		{
			mCHAR_LITERAL(false);
			break;
		}
		case '(':
		{
			mSUBRULE_BLOCK(false);
			break;
		}
		case '\n':  case '\r':
		{
			mNEWLINE(false);
			break;
		}
		case '\3':  case '\4':  case '\5':  case '\6':
		case '\7':  case '\10':  case '\t':  case '\13':
		case '\14':  case '\16':  case '\17':  case '\20':
		case '\21':  case '\22':  case '\23':  case '\24':
		case '\25':  case '\26':  case '\27':  case '\30':
		case '\31':  case '\32':  case '\33':  case '\34':
		case '\35':  case '\36':  case '\37':  case ' ':
		case '!':  case '#':  case '$':  case '%':
		case '&':  case '*':  case '+':  case ',':
		case '-':  case '.':  case '0':  case '1':
		case '2':  case '3':  case '4':  case '5':
		case '6':  case '7':  case '8':  case '9':
		case ':':  case '<':  case '=':  case '>':
		case '?':  case '@':  case 'A':  case 'B':
		case 'C':  case 'D':  case 'E':  case 'F':
		case 'G':  case 'H':  case 'I':  case 'J':
		case 'K':  case 'L':  case 'M':  case 'N':
		case 'O':  case 'P':  case 'Q':  case 'R':
		case 'S':  case 'T':  case 'U':  case 'V':
		case 'W':  case 'X':  case 'Y':  case 'Z':
		case '[':  case '\\':  case ']':  case '^':
		case '_':  case '`':  case 'a':  case 'b':
		case 'c':  case 'd':  case 'e':  case 'f':
		case 'g':  case 'h':  case 'i':  case 'j':
		case 'k':  case 'l':  case 'm':  case 'n':
		case 'o':  case 'p':  case 'q':  case 'r':
		case 's':  case 't':  case 'u':  case 'v':
		case 'w':  case 'x':  case 'y':  case 'z':
		case '|':  case '}':  case '~':
		{
			{
			match(_tokenSet_5);
			}
			break;
		}
		default:
		{
			throw new NoViableAltForCharException(LA(1), getFilename(), getLine());
		}
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mCOMMENT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = COMMENT;
		int _saveIndex;
		
		{
		if ((LA(1)=='/') && (LA(2)=='/')) {
			mSL_COMMENT(false);
		}
		else if ((LA(1)=='/') && (LA(2)=='*')) {
			mML_COMMENT(false);
		}
		else {
			throw new NoViableAltForCharException(LA(1), getFilename(), getLine());
		}
		
		}
		_ttype = Token.SKIP;
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mACTION(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = ACTION;
		int _saveIndex;
		
		match('{');
		{
		_loop104:
		do {
			// nongreedy exit test
			if ((LA(1)=='}') && (true)) break _loop104;
			if ((LA(1)=='\n'||LA(1)=='\r') && ((LA(2) >= '\3' && LA(2) <= '~'))) {
				mNEWLINE(false);
			}
			else if ((LA(1)=='{') && ((LA(2) >= '\3' && LA(2) <= '~'))) {
				mACTION(false);
			}
			else if ((LA(1)=='\'') && (_tokenSet_6.member(LA(2)))) {
				mCHAR_LITERAL(false);
			}
			else if ((LA(1)=='/') && (LA(2)=='*'||LA(2)=='/')) {
				mCOMMENT(false);
			}
			else if ((LA(1)=='"') && ((LA(2) >= '\3' && LA(2) <= '~'))) {
				mSTRING_LITERAL(false);
			}
			else if (((LA(1) >= '\3' && LA(1) <= '~')) && ((LA(2) >= '\3' && LA(2) <= '~'))) {
				matchNot(EOF_CHAR);
			}
			else {
				break _loop104;
			}
			
		} while (true);
		}
		match('}');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mSTRING_LITERAL(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = STRING_LITERAL;
		int _saveIndex;
		
		match('"');
		{
		_loop89:
		do {
			switch ( LA(1)) {
			case '\\':
			{
				mESC(false);
				break;
			}
			case '\3':  case '\4':  case '\5':  case '\6':
			case '\7':  case '\10':  case '\t':  case '\n':
			case '\13':  case '\14':  case '\r':  case '\16':
			case '\17':  case '\20':  case '\21':  case '\22':
			case '\23':  case '\24':  case '\25':  case '\26':
			case '\27':  case '\30':  case '\31':  case '\32':
			case '\33':  case '\34':  case '\35':  case '\36':
			case '\37':  case ' ':  case '!':  case '#':
			case '$':  case '%':  case '&':  case '\'':
			case '(':  case ')':  case '*':  case '+':
			case ',':  case '-':  case '.':  case '/':
			case '0':  case '1':  case '2':  case '3':
			case '4':  case '5':  case '6':  case '7':
			case '8':  case '9':  case ':':  case ';':
			case '<':  case '=':  case '>':  case '?':
			case '@':  case 'A':  case 'B':  case 'C':
			case 'D':  case 'E':  case 'F':  case 'G':
			case 'H':  case 'I':  case 'J':  case 'K':
			case 'L':  case 'M':  case 'N':  case 'O':
			case 'P':  case 'Q':  case 'R':  case 'S':
			case 'T':  case 'U':  case 'V':  case 'W':
			case 'X':  case 'Y':  case 'Z':  case '[':
			case ']':  case '^':  case '_':  case '`':
			case 'a':  case 'b':  case 'c':  case 'd':
			case 'e':  case 'f':  case 'g':  case 'h':
			case 'i':  case 'j':  case 'k':  case 'l':
			case 'm':  case 'n':  case 'o':  case 'p':
			case 'q':  case 'r':  case 's':  case 't':
			case 'u':  case 'v':  case 'w':  case 'x':
			case 'y':  case 'z':  case '{':  case '|':
			case '}':  case '~':
			{
				matchNot('"');
				break;
			}
			default:
			{
				break _loop89;
			}
			}
		} while (true);
		}
		match('"');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mCHAR_LITERAL(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = CHAR_LITERAL;
		int _saveIndex;
		
		match('\'');
		{
		switch ( LA(1)) {
		case '\\':
		{
			mESC(false);
			break;
		}
		case '\3':  case '\4':  case '\5':  case '\6':
		case '\7':  case '\10':  case '\t':  case '\n':
		case '\13':  case '\14':  case '\r':  case '\16':
		case '\17':  case '\20':  case '\21':  case '\22':
		case '\23':  case '\24':  case '\25':  case '\26':
		case '\27':  case '\30':  case '\31':  case '\32':
		case '\33':  case '\34':  case '\35':  case '\36':
		case '\37':  case ' ':  case '!':  case '"':
		case '#':  case '$':  case '%':  case '&':
		case '(':  case ')':  case '*':  case '+':
		case ',':  case '-':  case '.':  case '/':
		case '0':  case '1':  case '2':  case '3':
		case '4':  case '5':  case '6':  case '7':
		case '8':  case '9':  case ':':  case ';':
		case '<':  case '=':  case '>':  case '?':
		case '@':  case 'A':  case 'B':  case 'C':
		case 'D':  case 'E':  case 'F':  case 'G':
		case 'H':  case 'I':  case 'J':  case 'K':
		case 'L':  case 'M':  case 'N':  case 'O':
		case 'P':  case 'Q':  case 'R':  case 'S':
		case 'T':  case 'U':  case 'V':  case 'W':
		case 'X':  case 'Y':  case 'Z':  case '[':
		case ']':  case '^':  case '_':  case '`':
		case 'a':  case 'b':  case 'c':  case 'd':
		case 'e':  case 'f':  case 'g':  case 'h':
		case 'i':  case 'j':  case 'k':  case 'l':
		case 'm':  case 'n':  case 'o':  case 'p':
		case 'q':  case 'r':  case 's':  case 't':
		case 'u':  case 'v':  case 'w':  case 'x':
		case 'y':  case 'z':  case '{':  case '|':
		case '}':  case '~':
		{
			matchNot('\'');
			break;
		}
		default:
		{
			throw new NoViableAltForCharException(LA(1), getFilename(), getLine());
		}
		}
		}
		match('\'');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mNEWLINE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = NEWLINE;
		int _saveIndex;
		
		{
		if ((LA(1)=='\r') && (LA(2)=='\n')) {
			match('\r');
			match('\n');
			newline();
		}
		else if ((LA(1)=='\r') && (true)) {
			match('\r');
			newline();
		}
		else if ((LA(1)=='\n')) {
			match('\n');
			newline();
		}
		else {
			throw new NoViableAltForCharException(LA(1), getFilename(), getLine());
		}
		
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mBANG(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = BANG;
		int _saveIndex;
		
		match('!');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mSEMI(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = SEMI;
		int _saveIndex;
		
		match(';');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mRCURLY(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = RCURLY;
		int _saveIndex;
		
		match('}');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
/** This rule picks off keywords in the lexer that need to be
 *  handled specially.  For example, "header" is the start
 *  of the header action (used to distinguish between options
 *  block and an action).  We do not want "header" to go back
 *  to the parser as a simple keyword...it must pick off
 *  the action afterwards.
 */
	public final void mID_OR_KEYWORD(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = ID_OR_KEYWORD;
		int _saveIndex;
		Token id=null;
		
		mID(true);
		id=_returnToken;
		_ttype = id.getType();
		{
		if (((_tokenSet_7.member(LA(1))) && ((LA(2) >= '\3' && LA(2) <= '~')))&&(id.getText().equals("header"))) {
			{
			if ((_tokenSet_0.member(LA(1))) && (_tokenSet_7.member(LA(2)))) {
				mWS(false);
			}
			else if ((_tokenSet_7.member(LA(1))) && ((LA(2) >= '\3' && LA(2) <= '~'))) {
			}
			else {
				throw new NoViableAltForCharException(LA(1), getFilename(), getLine());
			}
			
			}
			{
			switch ( LA(1)) {
			case '"':
			{
				mSTRING_LITERAL(false);
				break;
			}
			case '\t':  case '\n':  case '\r':  case ' ':
			case '{':
			{
				break;
			}
			default:
			{
				throw new NoViableAltForCharException(LA(1), getFilename(), getLine());
			}
			}
			}
			{
			switch ( LA(1)) {
			case '\t':  case '\n':  case '\r':  case ' ':
			{
				mWS(false);
				break;
			}
			case '{':
			{
				break;
			}
			default:
			{
				throw new NoViableAltForCharException(LA(1), getFilename(), getLine());
			}
			}
			}
			mACTION(false);
			_ttype = HEADER_ACTION;
		}
		else if (((_tokenSet_8.member(LA(1))) && ((LA(2) >= '\3' && LA(2) <= '~')))&&(id.getText().equals("tokens"))) {
			{
			switch ( LA(1)) {
			case '\t':  case '\n':  case '\r':  case ' ':
			{
				mWS(false);
				break;
			}
			case '{':
			{
				break;
			}
			default:
			{
				throw new NoViableAltForCharException(LA(1), getFilename(), getLine());
			}
			}
			}
			mCURLY_BLOCK_SCARF(false);
			_ttype = TOKENS_SPEC;
		}
		else if (((_tokenSet_8.member(LA(1))) && (true))&&(id.getText().equals("options"))) {
			{
			switch ( LA(1)) {
			case '\t':  case '\n':  case '\r':  case ' ':
			{
				mWS(false);
				break;
			}
			case '{':
			{
				break;
			}
			default:
			{
				throw new NoViableAltForCharException(LA(1), getFilename(), getLine());
			}
			}
			}
			match('{');
			_ttype = OPTIONS_START;
		}
		else {
		}
		
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mID(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = ID;
		int _saveIndex;
		
		{
		switch ( LA(1)) {
		case 'a':  case 'b':  case 'c':  case 'd':
		case 'e':  case 'f':  case 'g':  case 'h':
		case 'i':  case 'j':  case 'k':  case 'l':
		case 'm':  case 'n':  case 'o':  case 'p':
		case 'q':  case 'r':  case 's':  case 't':
		case 'u':  case 'v':  case 'w':  case 'x':
		case 'y':  case 'z':
		{
			matchRange('a','z');
			break;
		}
		case 'A':  case 'B':  case 'C':  case 'D':
		case 'E':  case 'F':  case 'G':  case 'H':
		case 'I':  case 'J':  case 'K':  case 'L':
		case 'M':  case 'N':  case 'O':  case 'P':
		case 'Q':  case 'R':  case 'S':  case 'T':
		case 'U':  case 'V':  case 'W':  case 'X':
		case 'Y':  case 'Z':
		{
			matchRange('A','Z');
			break;
		}
		case '_':
		{
			match('_');
			break;
		}
		default:
		{
			throw new NoViableAltForCharException(LA(1), getFilename(), getLine());
		}
		}
		}
		{
		_loop68:
		do {
			switch ( LA(1)) {
			case 'a':  case 'b':  case 'c':  case 'd':
			case 'e':  case 'f':  case 'g':  case 'h':
			case 'i':  case 'j':  case 'k':  case 'l':
			case 'm':  case 'n':  case 'o':  case 'p':
			case 'q':  case 'r':  case 's':  case 't':
			case 'u':  case 'v':  case 'w':  case 'x':
			case 'y':  case 'z':
			{
				matchRange('a','z');
				break;
			}
			case 'A':  case 'B':  case 'C':  case 'D':
			case 'E':  case 'F':  case 'G':  case 'H':
			case 'I':  case 'J':  case 'K':  case 'L':
			case 'M':  case 'N':  case 'O':  case 'P':
			case 'Q':  case 'R':  case 'S':  case 'T':
			case 'U':  case 'V':  case 'W':  case 'X':
			case 'Y':  case 'Z':
			{
				matchRange('A','Z');
				break;
			}
			case '_':
			{
				match('_');
				break;
			}
			case '0':  case '1':  case '2':  case '3':
			case '4':  case '5':  case '6':  case '7':
			case '8':  case '9':
			{
				matchRange('0','9');
				break;
			}
			default:
			{
				break _loop68;
			}
			}
		} while (true);
		}
		_ttype = testLiteralsTable(new String(text.getBuffer(),_begin,text.length()-_begin),_ttype);
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mCURLY_BLOCK_SCARF(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = CURLY_BLOCK_SCARF;
		int _saveIndex;
		
		match('{');
		{
		_loop64:
		do {
			// nongreedy exit test
			if ((LA(1)=='}') && (true)) break _loop64;
			if ((LA(1)=='\n'||LA(1)=='\r') && ((LA(2) >= '\3' && LA(2) <= '~'))) {
				mNEWLINE(false);
			}
			else if (((LA(1) >= '\3' && LA(1) <= '~')) && ((LA(2) >= '\3' && LA(2) <= '~'))) {
				matchNot(EOF_CHAR);
			}
			else {
				break _loop64;
			}
			
		} while (true);
		}
		match('}');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mASSIGN_RHS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = ASSIGN_RHS;
		int _saveIndex;
		
		_saveIndex=text.length();
		match('=');
		text.setLength(_saveIndex);
		{
		_loop71:
		do {
			// nongreedy exit test
			if ((LA(1)==';') && (true)) break _loop71;
			if ((LA(1)=='"') && ((LA(2) >= '\3' && LA(2) <= '~'))) {
				mSTRING_LITERAL(false);
			}
			else if ((LA(1)=='\'') && (_tokenSet_6.member(LA(2)))) {
				mCHAR_LITERAL(false);
			}
			else if ((LA(1)=='\n'||LA(1)=='\r') && ((LA(2) >= '\3' && LA(2) <= '~'))) {
				mNEWLINE(false);
			}
			else if (((LA(1) >= '\3' && LA(1) <= '~')) && ((LA(2) >= '\3' && LA(2) <= '~'))) {
				matchNot(EOF_CHAR);
			}
			else {
				break _loop71;
			}
			
		} while (true);
		}
		match(';');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mSL_COMMENT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = SL_COMMENT;
		int _saveIndex;
		
		match("//");
		{
		_loop81:
		do {
			// nongreedy exit test
			if ((LA(1)=='\n'||LA(1)=='\r') && (true)) break _loop81;
			if (((LA(1) >= '\3' && LA(1) <= '~')) && ((LA(2) >= '\3' && LA(2) <= '~'))) {
				matchNot(EOF_CHAR);
			}
			else {
				break _loop81;
			}
			
		} while (true);
		}
		mNEWLINE(false);
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mML_COMMENT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = ML_COMMENT;
		int _saveIndex;
		
		match("/*");
		{
		_loop84:
		do {
			// nongreedy exit test
			if ((LA(1)=='*') && (LA(2)=='/')) break _loop84;
			if ((LA(1)=='\n'||LA(1)=='\r') && ((LA(2) >= '\3' && LA(2) <= '~'))) {
				mNEWLINE(false);
			}
			else if (((LA(1) >= '\3' && LA(1) <= '~')) && ((LA(2) >= '\3' && LA(2) <= '~'))) {
				matchNot(EOF_CHAR);
			}
			else {
				break _loop84;
			}
			
		} while (true);
		}
		match("*/");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mESC(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = ESC;
		int _saveIndex;
		
		match('\\');
		{
		switch ( LA(1)) {
		case 'n':
		{
			match('n');
			break;
		}
		case 'r':
		{
			match('r');
			break;
		}
		case 't':
		{
			match('t');
			break;
		}
		case 'b':
		{
			match('b');
			break;
		}
		case 'f':
		{
			match('f');
			break;
		}
		case 'w':
		{
			match('w');
			break;
		}
		case 'a':
		{
			match('a');
			break;
		}
		case '"':
		{
			match('"');
			break;
		}
		case '\'':
		{
			match('\'');
			break;
		}
		case '\\':
		{
			match('\\');
			break;
		}
		case '0':  case '1':  case '2':  case '3':
		{
			{
			matchRange('0','3');
			}
			{
			if (((LA(1) >= '0' && LA(1) <= '9')) && ((LA(2) >= '\3' && LA(2) <= '~'))) {
				mDIGIT(false);
				{
				if (((LA(1) >= '0' && LA(1) <= '9')) && ((LA(2) >= '\3' && LA(2) <= '~'))) {
					mDIGIT(false);
				}
				else if (((LA(1) >= '\3' && LA(1) <= '~')) && (true)) {
				}
				else {
					throw new NoViableAltForCharException(LA(1), getFilename(), getLine());
				}
				
				}
			}
			else if (((LA(1) >= '\3' && LA(1) <= '~')) && (true)) {
			}
			else {
				throw new NoViableAltForCharException(LA(1), getFilename(), getLine());
			}
			
			}
			break;
		}
		case '4':  case '5':  case '6':  case '7':
		{
			{
			matchRange('4','7');
			}
			{
			if (((LA(1) >= '0' && LA(1) <= '9')) && ((LA(2) >= '\3' && LA(2) <= '~'))) {
				mDIGIT(false);
			}
			else if (((LA(1) >= '\3' && LA(1) <= '~')) && (true)) {
			}
			else {
				throw new NoViableAltForCharException(LA(1), getFilename(), getLine());
			}
			
			}
			break;
		}
		case 'u':
		{
			match('u');
			mXDIGIT(false);
			mXDIGIT(false);
			mXDIGIT(false);
			mXDIGIT(false);
			break;
		}
		default:
		{
			throw new NoViableAltForCharException(LA(1), getFilename(), getLine());
		}
		}
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mDIGIT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = DIGIT;
		int _saveIndex;
		
		matchRange('0','9');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mXDIGIT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = XDIGIT;
		int _saveIndex;
		
		switch ( LA(1)) {
		case '0':  case '1':  case '2':  case '3':
		case '4':  case '5':  case '6':  case '7':
		case '8':  case '9':
		{
			matchRange('0','9');
			break;
		}
		case 'a':  case 'b':  case 'c':  case 'd':
		case 'e':  case 'f':
		{
			matchRange('a','f');
			break;
		}
		case 'A':  case 'B':  case 'C':  case 'D':
		case 'E':  case 'F':
		{
			matchRange('A','F');
			break;
		}
		default:
		{
			throw new NoViableAltForCharException(LA(1), getFilename(), getLine());
		}
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mARG_ACTION(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = ARG_ACTION;
		int _saveIndex;
		
		match('[');
		{
		_loop101:
		do {
			// nongreedy exit test
			if ((LA(1)==']') && (true)) break _loop101;
			if ((LA(1)=='[') && ((LA(2) >= '\3' && LA(2) <= '~'))) {
				mARG_ACTION(false);
			}
			else if ((LA(1)=='\n'||LA(1)=='\r') && ((LA(2) >= '\3' && LA(2) <= '~'))) {
				mNEWLINE(false);
			}
			else if ((LA(1)=='\'') && (_tokenSet_6.member(LA(2)))) {
				mCHAR_LITERAL(false);
			}
			else if ((LA(1)=='"') && ((LA(2) >= '\3' && LA(2) <= '~'))) {
				mSTRING_LITERAL(false);
			}
			else if (((LA(1) >= '\3' && LA(1) <= '~')) && ((LA(2) >= '\3' && LA(2) <= '~'))) {
				matchNot(EOF_CHAR);
			}
			else {
				break _loop101;
			}
			
		} while (true);
		}
		match(']');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	
	private static final long _tokenSet_0_data_[] = { 4294977024L, 0L, 0L };
	public static final BitSet _tokenSet_0 = new BitSet(_tokenSet_0_data_);
	private static final long _tokenSet_1_data_[] = { -2199023255560L, 9223372036854775807L, 0L, 0L };
	public static final BitSet _tokenSet_1 = new BitSet(_tokenSet_1_data_);
	private static final long _tokenSet_2_data_[] = { -576462951326679048L, 9223372036854775807L, 0L, 0L };
	public static final BitSet _tokenSet_2 = new BitSet(_tokenSet_2_data_);
	private static final long _tokenSet_3_data_[] = { -576460752303423496L, 9223372036854775807L, 0L, 0L };
	public static final BitSet _tokenSet_3 = new BitSet(_tokenSet_3_data_);
	private static final long _tokenSet_4_data_[] = { 4294977024L, 1152921504606846976L, 0L, 0L };
	public static final BitSet _tokenSet_4 = new BitSet(_tokenSet_4_data_);
	private static final long _tokenSet_5_data_[] = { -576605355262354440L, 8646911284551352319L, 0L, 0L };
	public static final BitSet _tokenSet_5 = new BitSet(_tokenSet_5_data_);
	private static final long _tokenSet_6_data_[] = { -549755813896L, 9223372036854775807L, 0L, 0L };
	public static final BitSet _tokenSet_6 = new BitSet(_tokenSet_6_data_);
	private static final long _tokenSet_7_data_[] = { 21474846208L, 576460752303423488L, 0L, 0L };
	public static final BitSet _tokenSet_7 = new BitSet(_tokenSet_7_data_);
	private static final long _tokenSet_8_data_[] = { 4294977024L, 576460752303423488L, 0L, 0L };
	public static final BitSet _tokenSet_8 = new BitSet(_tokenSet_8_data_);
	
	}
