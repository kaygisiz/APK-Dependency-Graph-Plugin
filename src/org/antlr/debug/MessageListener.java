package org.antlr.debug;

public interface MessageListener extends ListenerBase {


	void reportError(MessageEvent e);
	void reportWarning(MessageEvent e);
}