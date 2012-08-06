/*******************************************************************************
 * Copyright (c) 2006-2012
 * Software Technology Group, Dresden University of Technology
 * DevBoost GmbH, Berlin, Amtsgericht Charlottenburg, HRB 140026
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Software Technology Group - TU Dresden, Germany;
 *   DevBoost GmbH - Berlin, Germany
 *      - initial API and implementation
 ******************************************************************************/
package org.emftext.language.templateconcepts.call.resource.templatecall.analysis;

public class TemplatecallQUOTED_34_34TokenResolver implements org.emftext.language.templateconcepts.call.resource.templatecall.ITemplatecallTokenResolver {

	private org.emftext.language.templateconcepts.call.resource.templatecall.analysis.TemplatecallDefaultTokenResolver defaultTokenResolver = new org.emftext.language.templateconcepts.call.resource.templatecall.analysis.TemplatecallDefaultTokenResolver();

	public java.lang.String deResolve(java.lang.Object value, org.eclipse.emf.ecore.EStructuralFeature feature, org.eclipse.emf.ecore.EObject container) {
		java.lang.String result = defaultTokenResolver.deResolve(value, feature, container);
		result += "\"";
		result = "\"" + result;
		return result;
	}

	public void resolve(java.lang.String lexem, org.eclipse.emf.ecore.EStructuralFeature feature, org.emftext.language.templateconcepts.call.resource.templatecall.ITemplatecallTokenResolveResult result) {
		lexem = lexem.substring(1);
		lexem = lexem.substring(0, lexem.length() - 1);
		defaultTokenResolver.resolve(lexem, feature, result);
	}

	public void setOptions(java.util.Map<?,?> options) {
		defaultTokenResolver.setOptions(options);
	}

}
