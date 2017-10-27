package org.antlr.debug;

public interface SyntacticPredicateListener extends ListenerBase {


	void syntacticPredicateFailed(SyntacticPredicateEvent e);
	void syntacticPredicateStarted(SyntacticPredicateEvent e);
	void syntacticPredicateSucceeded(SyntacticPredicateEvent e);
}