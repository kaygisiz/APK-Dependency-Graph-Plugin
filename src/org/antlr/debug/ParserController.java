package org.antlr.debug;

public interface ParserController extends ParserListener {


	void checkBreak();
	void setParserEventSupport(ParserEventSupport p);
}