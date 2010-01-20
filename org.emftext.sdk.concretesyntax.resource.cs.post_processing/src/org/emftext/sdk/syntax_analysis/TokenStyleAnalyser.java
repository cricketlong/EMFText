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
package org.emftext.sdk.syntax_analysis;

import java.util.ArrayList;
import java.util.Collection;

import org.emftext.sdk.AbstractPostProcessor;
import org.emftext.sdk.concretesyntax.ConcreteSyntax;
import org.emftext.sdk.concretesyntax.ConcretesyntaxPackage;
import org.emftext.sdk.concretesyntax.CsString;
import org.emftext.sdk.concretesyntax.Rule;
import org.emftext.sdk.concretesyntax.CompleteTokenDefinition;
import org.emftext.sdk.concretesyntax.TokenStyle;
import org.emftext.sdk.concretesyntax.resource.cs.mopp.CsResource;
import org.emftext.sdk.concretesyntax.resource.cs.mopp.ECsProblemType;
import org.emftext.sdk.concretesyntax.resource.cs.util.CsEObjectUtil;

/**
 * A analyser that checks that all token styles refer either to
 * a token definition or a CsString.
 */
public class TokenStyleAnalyser extends AbstractPostProcessor {

	@Override
	public void analyse(CsResource resource, ConcreteSyntax syntax) {
		// first we collect all CsStrings from all rules
		Collection<CsString> csStrings = new ArrayList<CsString>();
		// to do so, we must iterate over the rules. Calling eAllContents()
		// on the syntax is not sufficient, because imported rules are not
		// contained in eAllContents().
		for (Rule rule : syntax.getAllRules()) {
			Collection<CsString> csStringsInRule = CsEObjectUtil.getObjectsByType(rule.eAllContents(), ConcretesyntaxPackage.eINSTANCE.getCsString());
			csStrings.addAll(csStringsInRule);
		}

		// for each token style we search for a matching token
		// definition or CsString
		Collection<TokenStyle> styles = syntax.getAllTokenStyles();
		for (TokenStyle tokenStyle : styles) {
			if (!refersToExistingToken(syntax, csStrings, tokenStyle)) {
				addProblem(resource, ECsProblemType.STYLE_REFERENCE_TO_NON_EXISTING_TOKEN, "Token style refers to non-existing token.", tokenStyle);
			}
		}
	}

	private boolean refersToExistingToken(ConcreteSyntax syntax, Collection<CsString> csStrings, TokenStyle tokenStyle) {
		String name = tokenStyle.getTokenName();
		for (CompleteTokenDefinition tokenDefinition : syntax.getActiveTokens()) {
			if (name.equals(tokenDefinition.getName())) {
				return true;
			}
		}
		for (CsString csString : csStrings) {
			if (name.equals(csString.getValue())) {
				return true;
			}
		}
		return false;
	}
}
