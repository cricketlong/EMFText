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
/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.emftext.language.petrinets.resource.petrinets.analysis;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.petrinets.ConsumingArc;
import org.emftext.language.petrinets.Function;
import org.emftext.language.petrinets.InitialisedVariable;
import org.emftext.language.petrinets.Parameter;
import org.emftext.language.petrinets.ProducingArc;
import org.emftext.language.petrinets.Statement;
import org.emftext.language.petrinets.Transition;
import org.emftext.language.petrinets.Variable;
import org.emftext.language.petrinets.VariableCall;
import org.emftext.language.petrinets.impl.PetrinetsFactoryImpl;
import org.emftext.language.petrinets.resource.petrinets.IPetrinetsReferenceResolveResult;

public class VariableCallVariableReferenceResolver
		implements
		org.emftext.language.petrinets.resource.petrinets.IPetrinetsReferenceResolver<org.emftext.language.petrinets.VariableCall, org.emftext.language.petrinets.Variable> {

	private org.emftext.language.petrinets.resource.petrinets.analysis.PetrinetsDefaultResolverDelegate<org.emftext.language.petrinets.VariableCall, org.emftext.language.petrinets.Variable> delegate = new org.emftext.language.petrinets.resource.petrinets.analysis.PetrinetsDefaultResolverDelegate<org.emftext.language.petrinets.VariableCall, org.emftext.language.petrinets.Variable>();

	public void resolve(
			String identifier,
			org.emftext.language.petrinets.VariableCall container,
			org.eclipse.emf.ecore.EReference reference,
			int position,
			boolean resolveFuzzy,
			final org.emftext.language.petrinets.resource.petrinets.IPetrinetsReferenceResolveResult<org.emftext.language.petrinets.Variable> result) {
		
		VariableCallAnalysisHelper vca = new VariableCallAnalysisHelper();
		List<Variable> candidates = vca.collectFilteredCandidates(container, resolveFuzzy, identifier);
		for (Variable variable : candidates) {
			result.addMapping(variable.getName(), variable);
			if (!resolveFuzzy) {
				container.setType(FunctionCallAnalysisHelper.getInstance().getType(variable));
				return;
			}
		}
	}

	

	public String deResolve(org.emftext.language.petrinets.Variable element,
			org.emftext.language.petrinets.VariableCall container,
			org.eclipse.emf.ecore.EReference reference) {
		return delegate.deResolve(element, container, reference);
	}

	public void setOptions(java.util.Map<?, ?> options) {
		// save options in a field or leave method empty if this resolver does
		// not depend
		// on any option
	}

}
