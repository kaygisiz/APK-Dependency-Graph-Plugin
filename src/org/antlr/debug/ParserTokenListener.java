package org.antlr.debug;

public interface ParserTokenListener extends ListenerBase {


	void parserConsume(ParserTokenEvent e);
	void parserLA(ParserTokenEvent e);
}