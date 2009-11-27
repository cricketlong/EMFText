/*******************************************************************************
 * Copyright (c) 2006-2009 
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

package org.emftext.sdk.concretesyntax.resource.cs.mopp;

// A CodeCompletionHelper can be used to derive completion proposals for partial
// documents. It runs the parser generated by EMFText in a special mode (i.e., the
// rememberExpectedElements mode). Based on the elements that are expected by the
// parser for different regions in the document, valid proposals are computed.
public class CsCodeCompletionHelper {
	
	// Computes a set of proposals for the given document assuming the cursor is
	// at 'cursorOffset'. The proposals are derived using the meta information, i.e.,
	// the generated language plug-in.
	//
	// @param metaInformation
	// @param content the documents content
	// @param cursorOffset
	// @return
	public java.util.Collection<org.emftext.sdk.concretesyntax.resource.cs.mopp.CsCompletionProposal> computeCompletionProposals(org.emftext.sdk.concretesyntax.resource.cs.ICsTextResource originalResource, String content, int cursorOffset) {
		org.eclipse.emf.ecore.resource.ResourceSet resourceSet = new org.eclipse.emf.ecore.resource.impl.ResourceSetImpl();
		// the shadow resource needs the same URI because reference resolvers may use the URI to resolve external references
		org.emftext.sdk.concretesyntax.resource.cs.ICsTextResource resource = (org.emftext.sdk.concretesyntax.resource.cs.ICsTextResource) resourceSet.createResource(originalResource.getURI());
		java.io.ByteArrayInputStream inputStream = new java.io.ByteArrayInputStream(content.getBytes());
		org.emftext.sdk.concretesyntax.resource.cs.mopp.CsMetaInformation metaInformation = resource.getMetaInformation();
		org.emftext.sdk.concretesyntax.resource.cs.ICsTextParser parser = metaInformation.createParser(inputStream, null);
		org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal[] expectedElements = parseToExpectedElements(parser, resource);
		if (expectedElements == null) {
			return java.util.Collections.emptyList();
		}
		if (expectedElements.length == 0) {
			return java.util.Collections.emptyList();
		}
		java.util.List<org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal> expectedAfterCursor = java.util.Arrays.asList(getElementsExpectedAt(expectedElements, cursorOffset));
		java.util.List<org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal> expectedBeforeCursor = java.util.Arrays.asList(getElementsExpectedAt(expectedElements, cursorOffset - 1));
		System.out.println("parseToCursor(" + cursorOffset + ") BEFORE CURSOR " + expectedBeforeCursor);
		System.out.println("parseToCursor(" + cursorOffset + ") AFTER CURSOR  " + expectedAfterCursor);
		setPrefixes(expectedAfterCursor, content, cursorOffset);
		setPrefixes(expectedBeforeCursor, content, cursorOffset);
		// first we derive all possible proposals from the set of elements that are expected at the cursor position
		java.util.Collection<org.emftext.sdk.concretesyntax.resource.cs.mopp.CsCompletionProposal> allProposals = new java.util.LinkedHashSet<org.emftext.sdk.concretesyntax.resource.cs.mopp.CsCompletionProposal>();
		java.util.Collection<org.emftext.sdk.concretesyntax.resource.cs.mopp.CsCompletionProposal> rightProposals = deriveProposals(expectedAfterCursor, content, resource, cursorOffset);
		java.util.Collection<org.emftext.sdk.concretesyntax.resource.cs.mopp.CsCompletionProposal> leftProposals = deriveProposals(expectedBeforeCursor, content, resource, cursorOffset);
		// second, the set of left proposals (i.e., the ones before the cursor) is
		// checked for emptiness. if the set is empty, the right proposals (i.e., 
		// the ones after the cursor are removed, because it does not make sense to
		// propose them until the element before the cursor was completed
		allProposals.addAll(leftProposals);
		if (leftProposals.isEmpty()) {
			allProposals.addAll(rightProposals);
		}
		// third, the proposals are sorted according to their relevance
		// proposals that matched the prefix are preferred over ones that did not
		// afterward proposals are sorted alphabetically
		final java.util.List<org.emftext.sdk.concretesyntax.resource.cs.mopp.CsCompletionProposal> sortedProposals = new java.util.ArrayList<org.emftext.sdk.concretesyntax.resource.cs.mopp.CsCompletionProposal>(allProposals);
		java.util.Collections.sort(sortedProposals);
		return sortedProposals;
	}
	
	public org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal[] parseToExpectedElements(org.emftext.sdk.concretesyntax.resource.cs.ICsTextParser parser, org.emftext.sdk.concretesyntax.resource.cs.ICsTextResource resource) {
		final java.util.List<org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal> expectedElements = parser.parseToExpectedElements(null, resource);
		if (expectedElements == null) {
			return new org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal[0];
		}
		removeDuplicateEntries(expectedElements);
		removeInvalidEntriesAtEnd(expectedElements);
		return expectedElements.toArray(new org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal[expectedElements.size()]);
	}
	
	private void removeDuplicateEntries(java.util.List<org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal> expectedElements) {
		for (int i = 0; i < expectedElements.size() - 1; i++) {
			org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal elementAtIndex = expectedElements.get(i);
			for (int j = i + 1; j < expectedElements.size();) {
				org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal elementAtNext = expectedElements.get(j);
				if (elementAtIndex.equals(elementAtNext) && elementAtIndex.getStartExcludingHiddenTokens() == elementAtNext.getStartExcludingHiddenTokens()) {
					expectedElements.remove(j);
				} else {
					j++;
				}
			}
		}
	}
	
	private void removeInvalidEntriesAtEnd(java.util.List<org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal> expectedElements) {
		for (int i = 0; i < expectedElements.size() - 1;) {
			org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal elementAtIndex = expectedElements.get(i);
			org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal elementAtNext = expectedElements.get(i + 1);
			if (elementAtIndex.getStartExcludingHiddenTokens() == elementAtNext.getStartExcludingHiddenTokens() && shouldRemove(elementAtIndex.getFollowSetID(), elementAtNext.getFollowSetID())) {
				expectedElements.remove(i + 1);
			} else {
				i++;
			}
		}
	}
	
	public boolean shouldRemove(int followSetID1, int followSetID2) {
		return followSetID1 != followSetID2;
	}
	
	private String findPrefix(java.util.List<org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal> expectedElements, org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal expectedAtCursor, String content, int cursorOffset) {
		if (cursorOffset < 0) {
			return "";
		}
		int end = 0;
		for (org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal expectedElement : expectedElements) {
			if (expectedElement == expectedAtCursor) {
				final int start = expectedElement.getStartExcludingHiddenTokens();
				if (start >= 0  && start < Integer.MAX_VALUE) {
					end = start;
				}
				break;
			}
		}
		end = Math.min(end, cursorOffset);
		final String prefix = content.substring(end, Math.min(content.length(), cursorOffset));
		System.out.println("Found prefix '" + prefix + "'");
		return prefix;
	}
	
	private java.util.Collection<org.emftext.sdk.concretesyntax.resource.cs.mopp.CsCompletionProposal> deriveProposals(java.util.List<org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal> expectedElements, String content, org.emftext.sdk.concretesyntax.resource.cs.ICsTextResource resource, int cursorOffset) {
		java.util.Collection<org.emftext.sdk.concretesyntax.resource.cs.mopp.CsCompletionProposal> resultSet = new java.util.HashSet<org.emftext.sdk.concretesyntax.resource.cs.mopp.CsCompletionProposal>();
		for (org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal expectedElement : expectedElements) {
			resultSet.addAll(deriveProposals(expectedElement, content, resource, cursorOffset));
		}
		return resultSet;
	}
	
	private java.util.Collection<org.emftext.sdk.concretesyntax.resource.cs.mopp.CsCompletionProposal> deriveProposals(org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal expectedTerminal, String content, org.emftext.sdk.concretesyntax.resource.cs.ICsTextResource resource, int cursorOffset) {
		org.emftext.sdk.concretesyntax.resource.cs.mopp.CsMetaInformation metaInformation = resource.getMetaInformation();
		org.emftext.sdk.concretesyntax.resource.cs.ICsLocationMap locationMap = resource.getLocationMap();
		org.emftext.sdk.concretesyntax.resource.cs.ICsExpectedElement expectedElement = (org.emftext.sdk.concretesyntax.resource.cs.ICsExpectedElement) expectedTerminal.getTerminal();
		if (expectedElement instanceof org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedCsString) {
			org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedCsString csString = (org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedCsString) expectedElement;
			return deriveProposal(csString, content, expectedTerminal.getPrefix(), cursorOffset);
		} else if (expectedElement instanceof org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedStructuralFeature) {
			org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedStructuralFeature expectedFeature = (org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedStructuralFeature) expectedElement;
			org.eclipse.emf.ecore.EStructuralFeature feature = expectedFeature.getFeature();
			org.eclipse.emf.ecore.EClassifier featureType = feature.getEType();
			java.util.List<org.eclipse.emf.ecore.EObject> elementsAtCursor = locationMap.getElementsAt(cursorOffset);
			org.eclipse.emf.ecore.EObject container = null;
			// we need to skip the proxy elements at the cursor, because they are not the container for the reference we try to complete
			for (int i = 0; i < elementsAtCursor.size(); i++) {
				container = elementsAtCursor.get(i);
				if (!container.eIsProxy()) {
					break;
				}
			}
			if (feature instanceof org.eclipse.emf.ecore.EReference) {
				org.eclipse.emf.ecore.EReference reference = (org.eclipse.emf.ecore.EReference) feature;
				if (featureType instanceof org.eclipse.emf.ecore.EClass) {
					if (reference.isContainment()) {
						// the FOLLOW set should contain only non-containment references
						assert false;
					} else {
						return handleNCReference(metaInformation, container, reference, expectedTerminal.getPrefix());
					}
				}
			} else if (feature instanceof org.eclipse.emf.ecore.EAttribute) {
				org.eclipse.emf.ecore.EAttribute attribute = (org.eclipse.emf.ecore.EAttribute) feature;
				if (featureType instanceof org.eclipse.emf.ecore.EEnum) {
					org.eclipse.emf.ecore.EEnum enumType = (org.eclipse.emf.ecore.EEnum) featureType;
					return deriveProposals(expectedTerminal, enumType, content, cursorOffset);
				} else {
					// handle EAttributes (derive default value depending on
					// the type of the attribute, figure out token resolver, and
					// call deResolve())
					return handleAttribute(metaInformation, expectedFeature, container, attribute, expectedTerminal.getPrefix());
				}
			} else {
				// there should be no other subclass of EStructuralFeature
				assert false;
			}
		} else {
			// there should be no other class implementing IExpectedElement
			assert false;
		}
		return java.util.Collections.emptyList();
	}
	
	private java.util.Collection<org.emftext.sdk.concretesyntax.resource.cs.mopp.CsCompletionProposal> deriveProposals(org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal expectedElement, org.eclipse.emf.ecore.EEnum enumType, String content, int cursorOffset) {
		java.util.Collection<org.eclipse.emf.ecore.EEnumLiteral> enumLiterals = enumType.getELiterals();
		java.util.Collection<org.emftext.sdk.concretesyntax.resource.cs.mopp.CsCompletionProposal> result = new java.util.HashSet<org.emftext.sdk.concretesyntax.resource.cs.mopp.CsCompletionProposal>();
		for (org.eclipse.emf.ecore.EEnumLiteral literal : enumLiterals) {
			String proposal = literal.getLiteral();
			String prefix = expectedElement.getPrefix();
			if (matches(proposal, prefix)) {
				result.add(new org.emftext.sdk.concretesyntax.resource.cs.mopp.CsCompletionProposal(proposal, prefix, !"".equals(prefix), true));
			}
		}
		return result;
	}
	
	private java.util.Collection<org.emftext.sdk.concretesyntax.resource.cs.mopp.CsCompletionProposal> handleNCReference(org.emftext.sdk.concretesyntax.resource.cs.mopp.CsMetaInformation metaInformation, org.eclipse.emf.ecore.EObject container, org.eclipse.emf.ecore.EReference reference, java.lang.String prefix) {
		// proposals for non-containment references are derived by calling the
		// reference resolver switch in fuzzy mode.
		org.emftext.sdk.concretesyntax.resource.cs.ICsReferenceResolverSwitch resolverSwitch = metaInformation.getReferenceResolverSwitch();
		org.emftext.sdk.concretesyntax.resource.cs.ICsReferenceResolveResult<org.eclipse.emf.ecore.EObject> result = new org.emftext.sdk.concretesyntax.resource.cs.mopp.CsReferenceResolveResult<org.eclipse.emf.ecore.EObject>(true);
		resolverSwitch.resolveFuzzy(prefix, container, reference, 0, result);
		java.util.Collection<org.emftext.sdk.concretesyntax.resource.cs.ICsReferenceMapping<org.eclipse.emf.ecore.EObject>> mappings = result.getMappings();
		if (mappings != null) {
			java.util.Collection<org.emftext.sdk.concretesyntax.resource.cs.mopp.CsCompletionProposal> resultSet = new java.util.HashSet<org.emftext.sdk.concretesyntax.resource.cs.mopp.CsCompletionProposal>();
			for (org.emftext.sdk.concretesyntax.resource.cs.ICsReferenceMapping<org.eclipse.emf.ecore.EObject> mapping : mappings) {
				final String identifier = mapping.getIdentifier();
				// the proposal can be added without checking the prefix because this is
				// performed by the reference resolvers
				resultSet.add(new org.emftext.sdk.concretesyntax.resource.cs.mopp.CsCompletionProposal(identifier, prefix, true, true));
			}
			return resultSet;
		}
		return java.util.Collections.emptyList();
	}
	
	private java.util.Collection<org.emftext.sdk.concretesyntax.resource.cs.mopp.CsCompletionProposal> handleAttribute(org.emftext.sdk.concretesyntax.resource.cs.mopp.CsMetaInformation metaInformation, org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedStructuralFeature expectedFeature, org.eclipse.emf.ecore.EObject container, org.eclipse.emf.ecore.EAttribute attribute, java.lang.String prefix) {
		java.lang.Object defaultValue = getDefaultValue(attribute);
		if (defaultValue != null) {
			org.emftext.sdk.concretesyntax.resource.cs.ICsTokenResolverFactory tokenResolverFactory = metaInformation.getTokenResolverFactory();
			String tokenName = expectedFeature.getTokenName();
			if (tokenName != null) {
				org.emftext.sdk.concretesyntax.resource.cs.ICsTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver(tokenName);
				if (tokenResolver != null) {
					String defaultValueAsString = tokenResolver.deResolve(defaultValue, attribute, container);
					java.util.Collection<org.emftext.sdk.concretesyntax.resource.cs.mopp.CsCompletionProposal> resultSet = new java.util.HashSet<org.emftext.sdk.concretesyntax.resource.cs.mopp.CsCompletionProposal>();
					if (matches(defaultValueAsString, prefix)) {
						resultSet.add(new org.emftext.sdk.concretesyntax.resource.cs.mopp.CsCompletionProposal(defaultValueAsString, prefix, !"".equals(prefix), true));
					}
					return resultSet;
				}
			}
		}
		return java.util.Collections.emptyList();
	}
	
	private java.lang.Object getDefaultValue(org.eclipse.emf.ecore.EAttribute attribute) {
		String typeName = attribute.getEType().getName();
		if ("EString".equals(typeName)) {
			return "some" + org.emftext.sdk.concretesyntax.resource.cs.util.CsStringUtil.capitalize(attribute.getName());
		}
		System.out.println("CodeCompletionHelper.getDefaultValue() unknown type " + typeName);
		return attribute.getDefaultValue();
	}
	
	private java.util.Collection<org.emftext.sdk.concretesyntax.resource.cs.mopp.CsCompletionProposal> deriveProposal(org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedCsString csString, String content, String prefix, int cursorOffset) {
		String proposal = csString.getValue();
		java.util.Collection<org.emftext.sdk.concretesyntax.resource.cs.mopp.CsCompletionProposal> result = new java.util.HashSet<org.emftext.sdk.concretesyntax.resource.cs.mopp.CsCompletionProposal>();
		if (matches(proposal, prefix)) {
			result.add(new org.emftext.sdk.concretesyntax.resource.cs.mopp.CsCompletionProposal(proposal, prefix, !"".equals(prefix), false));
		}
		return result;
	}
	
	// for each given expected elements the prefix is calculated
	// the prefix depends on the current document content, the cursor position, and
	// the position where the element is expected
	private void setPrefixes(java.util.List<org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal> expectedElements, String content, int cursorOffset) {
		if (cursorOffset < 0) {
			return;
		}
		for (org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal expectedElement : expectedElements) {
			java.lang.String prefix = findPrefix(expectedElements, expectedElement, content, cursorOffset);
			expectedElement.setPrefix(prefix);
		}
	}
	
	public org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal[] getElementsExpectedAt(org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal[] allExpectedElements, int cursorOffset) {
		java.util.List<org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal> expectedAtCursor = new java.util.ArrayList<org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal>();
		for (int i = 0; i < allExpectedElements.length; i++) {
			org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal expectedElement = allExpectedElements[i];
			int startIncludingHidden = expectedElement.getStartIncludingHiddenTokens();
			int end = getEnd(allExpectedElements, i);
			if (cursorOffset >= startIncludingHidden && cursorOffset <= end) {
				expectedAtCursor.add(expectedElement);
			}
		}
		return expectedAtCursor.toArray(new org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal[expectedAtCursor.size()]);
	}
	
	private int getEnd(org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal[] allExpectedElements, int indexInList) {
		org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal elementAtIndex = allExpectedElements[indexInList];
		int startIncludingHidden = elementAtIndex.getStartIncludingHiddenTokens();
		int startExcludingHidden = elementAtIndex.getStartExcludingHiddenTokens();
		for (int i = indexInList + 1; i < allExpectedElements.length; i++) {
			org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal elementAtI = allExpectedElements[i];
			int startIncludingHiddenForI = elementAtI.getStartIncludingHiddenTokens();
			int startExcludingHiddenForI = elementAtI.getStartExcludingHiddenTokens();
			if (startIncludingHidden != startIncludingHiddenForI || startExcludingHidden != startExcludingHiddenForI) {
				return startIncludingHiddenForI - 1;
			}
		}
		return Integer.MAX_VALUE;
	}
	
	private boolean matches(java.lang.String proposal, java.lang.String prefix) {
		return (proposal.startsWith(prefix) || org.emftext.sdk.concretesyntax.resource.cs.util.CsStringUtil.matchCamelCase(prefix, proposal) != null) && !proposal.equals(prefix);
	}
	
}
