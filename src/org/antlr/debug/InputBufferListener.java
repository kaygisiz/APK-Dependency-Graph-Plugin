package org.antlr.debug;

public interface InputBufferListener extends ListenerBase {


	void inputBufferConsume(InputBufferEvent e);
	void inputBufferLA(InputBufferEvent e);
	void inputBufferMark(InputBufferEvent e);
	void inputBufferRewind(InputBufferEvent e);
}