package org.antlr.preprocessor;

/* ANTLR Translator Generator
 * Project led by Terence Parr at http://www.jGuru.com
 * Software rights: http://www.antlr.org/RIGHTS.html
 *
 * $Id: //depot/code/org.antlr/release/antlr-2.7.0/antlr/preprocessor/Tool.java#1 $
 */

/*
 * Tester for the preprocessor
 * 
 */
import java.io.*;
import org.antlr.collections.impl.Vector;
import java.util.Enumeration;

public class Tool {
    protected Hierarchy theHierarchy;
    protected String grammarFileName;
    protected String[] args;
    protected int nargs;		// how many args in new args list
    protected Vector grammars;
    protected org.antlr.Tool antlrTool;
    public Tool(org.antlr.Tool t, String[] args) {
	antlrTool = t;
	processArguments(args);
    }
    public static void main(String[] args) {
	org.antlr.Tool aTool = new org.antlr.Tool();
	Tool theTool = new Tool(aTool, args);
	theTool.preprocess();
	String[] a = theTool.preprocessedArgList();
	for (int i=0; i<a.length; i++) {
	    System.out.print(" "+a[i]);
	}
	System.out.println();	
    }
    public boolean preprocess() {
	if (grammarFileName == null) {
	    org.antlr.Tool.toolError("no grammar file specified");
	    return false;
	}
	if (grammars != null) {
	    theHierarchy = new Hierarchy();
	    for (Enumeration e = grammars.elements(); e.hasMoreElements();) {
		String f = (String) e.nextElement();
		try {
		    theHierarchy.readGrammarFile(f);
		} catch (FileNotFoundException fe) {
		    org.antlr.Tool.toolError("file " + f + " not found");
		    return false;
		}
	    }
	}


	// do the actual inheritance stuff
	boolean complete = theHierarchy.verifyThatHierarchyIsComplete();
	if (!complete)
	    return false;
	theHierarchy.expandGrammarsInFile(grammarFileName);
	GrammarFile gf = theHierarchy.getFile(grammarFileName);
	String expandedFileName = gf.nameForExpandedGrammarFile(grammarFileName);

	// generate the output file if necessary
	if (expandedFileName.equals(grammarFileName)) {
	    args[nargs++] = grammarFileName;			// add to argument list
	} else {
	    try {
		gf.generateExpandedFile(); 				// generate file to feed ANTLR
		args[nargs++] = org.antlr.Tool.getOutputDirectory() +
		    System.getProperty("file.separator") +
		    expandedFileName;		// add to argument list
	    } catch (IOException io) {
		org.antlr.Tool.toolError("cannot write expanded grammar file " + expandedFileName);
		return false;
	    }
	}
	return true;
    }
    /** create new arg list with correct length to pass to ANTLR */
    public String[] preprocessedArgList() {
	String[] a = new String[nargs];
	System.arraycopy(args, 0, a, 0, nargs);
	args = a;
	return args;
    }
    /** Process -glib options and grammar file.  Create a new args list
	 *  that does not contain the -glib option.  The grammar file name
	 *  might be modified and, hence, is not added yet to args list.
	 */
    private void processArguments(String[] incomingArgs)
    {
	this.nargs = 0;
	this.args = new String[incomingArgs.length];
	for (int i=0; i<incomingArgs.length; i++) {
	    if ( incomingArgs[i].equals("-glib") ) {
				// if on a pc and they use a '/', warn them
		if ( File.separator.equals("\\") && 
		     incomingArgs[i].indexOf('/') != -1 ) {
		    org.antlr.Tool.warning("-glib cannot deal with '/' on a PC: use '\\'; ignoring...");
		}
		else {
		    grammars = org.antlr.Tool.parseSeparatedList(incomingArgs[i+1],';');
		    i++;
		}
	    }
	    else if ( incomingArgs[i].equals("-o") ) {
		args[this.nargs++] = incomingArgs[i];
		if (i + 1 >= incomingArgs.length) {
		    antlrTool.error("missing output directory with -o option; ignoring");
		} else {
		    i++;
		    args[this.nargs++] = incomingArgs[i];
		    org.antlr.Tool.setOutputDirectory(incomingArgs[i]);
		}
	    }
	    else if (incomingArgs[i].charAt(0) == '-') {
		args[this.nargs++] = incomingArgs[i];
	    }	
	    else {
		// Must be the grammar file
		grammarFileName = incomingArgs[i];
		if ( grammars==null ) {
		    grammars = new Vector(10);
		}	
		grammars.appendElement(grammarFileName);	// process it too
		if ( (i+1)<incomingArgs.length ) {
		    org.antlr.Tool.warning("grammar file must be last; ignoring other arguments...");
		    break;
		}
	    }
	}
    }
}
