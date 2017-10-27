package org.antlr.debug;

public interface ParserMatchListener extends ListenerBase {


	void parserMatch(ParserMatchEvent e);
	void parserMatchNot(ParserMatchEvent e);
	void parserMismatch(ParserMatchEvent e);
	void parserMismatchNot(ParserMatchEvent e);
}