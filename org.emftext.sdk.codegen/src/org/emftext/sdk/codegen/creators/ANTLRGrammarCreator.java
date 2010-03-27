/*******************************************************************************
 * Copyright (c) 2006-2010 
 * Software Technology Group, Dresden University of Technology
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Software Technology Group - TU Dresden, Germany 
 *      - initial API and implementation
 ******************************************************************************/
package org.emftext.sdk.codegen.creators;

import java.io.File;
import java.util.Collection;

import org.emftext.sdk.codegen.GenerationContext;
import org.emftext.sdk.codegen.generators.mopp.ANTLRGrammarGenerator;
import org.emftext.sdk.concretesyntax.OptionTypes;

/**
 * Creates an ANTLR .g file using the ANTLRGrammarGenerator, which
 * creates the content for the file.
 */
public class ANTLRGrammarCreator extends AbstractArtifactCreator {

	public ANTLRGrammarCreator() {
		super("ANTLR grammar");
	}

	public Collection<IArtifact> getArtifactsToCreate(GenerationContext context) {
		
	    File antlrFile = context.getANTLRGrammarFile();
	    return createArtifact(
	    		context,
	    		new ANTLRGrammarGenerator().newInstance(context),
	    		antlrFile,
	    		"Exception while generating ANTLR grammar."
	    );
	}

	public OptionTypes getOverrideOption() {
		return OptionTypes.OVERRIDE_PARSER;
	}
}
