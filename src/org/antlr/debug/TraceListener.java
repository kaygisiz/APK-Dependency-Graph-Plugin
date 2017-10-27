package org.antlr.debug;

public interface TraceListener extends ListenerBase {


	void enterRule(TraceEvent e);
	void exitRule(TraceEvent e);
}