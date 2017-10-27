package org.antlr.debug;

import java.util.EventListener;

public interface ListenerBase extends EventListener {


	void doneParsing(TraceEvent e);
	void refresh();
}