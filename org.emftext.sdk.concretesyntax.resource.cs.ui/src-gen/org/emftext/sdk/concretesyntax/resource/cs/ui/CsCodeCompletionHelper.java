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

package org.emftext.sdk.concretesyntax.resource.cs.ui;

/**
 * A CodeCompletionHelper can be used to derive completion proposals for partial
 * documents. It runs the parser generated by EMFText in a special mode (i.e., the
 * rememberExpectedElements mode). Based on the elements that are expected by the
 * parser for different regions in the document, valid proposals are computed.
 */
public class CsCodeCompletionHelper {
	
	private org.emftext.sdk.concretesyntax.resource.cs.mopp.CsAttributeValueProvider attributeValueProvider = new org.emftext.sdk.concretesyntax.resource.cs.mopp.CsAttributeValueProvider();
	
	private org.emftext.sdk.concretesyntax.resource.cs.ICsMetaInformation metaInformation = new org.emftext.sdk.concretesyntax.resource.cs.mopp.CsMetaInformation();
	
	/**
	 * Computes a set of proposals for the given document assuming the cursor is at
	 * 'cursorOffset'. The proposals are derived using the meta information, i.e., the
	 * generated language plug-in.
	 * 
	 * @param originalResource
	 * @param content the documents content
	 * @param cursorOffset
	 * 
	 * @return
	 */
	public org.emftext.sdk.concretesyntax.resource.cs.ui.CsCompletionProposal[] computeCompletionProposals(org.emftext.sdk.concretesyntax.resource.cs.ICsTextResource originalResource, String content, int cursorOffset) {
		org.eclipse.emf.ecore.resource.ResourceSet resourceSet = new org.eclipse.emf.ecore.resource.impl.ResourceSetImpl();
		// the shadow resource needs the same URI because reference resolvers may use the
		// URI to resolve external references
		org.emftext.sdk.concretesyntax.resource.cs.ICsTextResource resource = (org.emftext.sdk.concretesyntax.resource.cs.ICsTextResource) resourceSet.createResource(originalResource.getURI());
		java.io.ByteArrayInputStream inputStream = new java.io.ByteArrayInputStream(content.getBytes());
		org.emftext.sdk.concretesyntax.resource.cs.ICsMetaInformation metaInformation = resource.getMetaInformation();
		org.emftext.sdk.concretesyntax.resource.cs.ICsTextParser parser = metaInformation.createParser(inputStream, null);
		org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal[] expectedElements = parseToExpectedElements(parser, resource, cursorOffset);
		if (expectedElements == null) {
			return new org.emftext.sdk.concretesyntax.resource.cs.ui.CsCompletionProposal[0];
		}
		if (expectedElements.length == 0) {
			return new org.emftext.sdk.concretesyntax.resource.cs.ui.CsCompletionProposal[0];
		}
		java.util.List<org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal> expectedAfterCursor = java.util.Arrays.asList(getElementsExpectedAt(expectedElements, cursorOffset));
		java.util.List<org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal> expectedBeforeCursor = java.util.Arrays.asList(getElementsExpectedAt(expectedElements, cursorOffset - 1));
		setPrefixes(expectedAfterCursor, content, cursorOffset);
		setPrefixes(expectedBeforeCursor, content, cursorOffset);
		// First, we derive all possible proposals from the set of elements that are
		// expected at the cursor position.
		java.util.Collection<org.emftext.sdk.concretesyntax.resource.cs.ui.CsCompletionProposal> allProposals = new java.util.LinkedHashSet<org.emftext.sdk.concretesyntax.resource.cs.ui.CsCompletionProposal>();
		java.util.Collection<org.emftext.sdk.concretesyntax.resource.cs.ui.CsCompletionProposal> rightProposals = deriveProposals(expectedAfterCursor, content, resource, cursorOffset);
		java.util.Collection<org.emftext.sdk.concretesyntax.resource.cs.ui.CsCompletionProposal> leftProposals = deriveProposals(expectedBeforeCursor, content, resource, cursorOffset - 1);
		removeKeywordsEndingBeforeIndex(leftProposals, cursorOffset);
		// Second, the set of left proposals (i.e., the ones before the cursor) is checked
		// for emptiness. If the set is empty, the right proposals (i.e., the ones after
		// the cursor) are also considered. If the set is not empty, the right proposal
		// are discarded, because it does not make sense to propose them until the element
		// before the cursor was completed.
		allProposals.addAll(leftProposals);
		// Count the proposals before the cursor that match the prefix
		int leftMatchingProposals = 0;
		for (org.emftext.sdk.concretesyntax.resource.cs.ui.CsCompletionProposal leftProposal : leftProposals) {
			if (leftProposal.getMatchesPrefix()) {
				leftMatchingProposals++;
			}
		}
		if (leftMatchingProposals == 0) {
			allProposals.addAll(rightProposals);
		}
		// Third, the proposals are sorted according to their relevance. Proposals that
		// matched the prefix are preferred over ones that did not. Finally, proposals are
		// sorted alphabetically.
		final java.util.List<org.emftext.sdk.concretesyntax.resource.cs.ui.CsCompletionProposal> sortedProposals = new java.util.ArrayList<org.emftext.sdk.concretesyntax.resource.cs.ui.CsCompletionProposal>(allProposals);
		java.util.Collections.sort(sortedProposals);
		org.eclipse.emf.ecore.EObject root = null;
		if (!resource.getContents().isEmpty()) {
			root = resource.getContents().get(0);
		}
		for (org.emftext.sdk.concretesyntax.resource.cs.ui.CsCompletionProposal proposal : sortedProposals) {
			proposal.setRoot(root);
		}
		return sortedProposals.toArray(new org.emftext.sdk.concretesyntax.resource.cs.ui.CsCompletionProposal[sortedProposals.size()]);
	}
	
	public org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal[] parseToExpectedElements(org.emftext.sdk.concretesyntax.resource.cs.ICsTextParser parser, org.emftext.sdk.concretesyntax.resource.cs.ICsTextResource resource, int cursorOffset) {
		final java.util.List<org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal> expectedElements = parser.parseToExpectedElements(null, resource, cursorOffset);
		if (expectedElements == null) {
			return new org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal[0];
		}
		removeDuplicateEntries(expectedElements);
		removeInvalidEntriesAtEnd(expectedElements);
		return expectedElements.toArray(new org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal[expectedElements.size()]);
	}
	
	/**
	 * Removes all expected elements that refer to the same terminal and that start at
	 * the same position.
	 */
	protected void removeDuplicateEntries(java.util.List<org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal> expectedElements) {
		int size = expectedElements.size();
		// We split the list of expected elements into buckets where each bucket contains
		// the elements that start at the same position.
		java.util.Map<Integer, java.util.List<org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal>> map = new java.util.LinkedHashMap<Integer, java.util.List<org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal>>();
		for (int i = 0; i < size; i++) {
			org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal elementAtIndex = expectedElements.get(i);
			int start1 = elementAtIndex.getStartExcludingHiddenTokens();
			java.util.List<org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal> list = map.get(start1);
			if (list == null) {
				list = new java.util.ArrayList<org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal>();
				map.put(start1, list);
			}
			list.add(elementAtIndex);
		}
		
		// Then, we remove all duplicate elements from each bucket individually.
		for (int position : map.keySet()) {
			java.util.List<org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal> list = map.get(position);
			removeDuplicateEntriesFromBucket(list);
		}
		
		// After removing all duplicates, we merge the buckets.
		expectedElements.clear();
		for (int position : map.keySet()) {
			java.util.List<org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal> list = map.get(position);
			expectedElements.addAll(list);
		}
	}
	
	/**
	 * Removes all expected elements that refer to the same terminal. Attention: This
	 * method assumes that the given list of expected terminals contains only elements
	 * that start at the same position.
	 */
	protected void removeDuplicateEntriesFromBucket(java.util.List<org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal> expectedElements) {
		int size = expectedElements.size();
		for (int i = 0; i < size - 1; i++) {
			org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal elementAtIndex = expectedElements.get(i);
			org.emftext.sdk.concretesyntax.resource.cs.ICsExpectedElement terminal = elementAtIndex.getTerminal();
			for (int j = i + 1; j < size;) {
				org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal elementAtNext = expectedElements.get(j);
				if (terminal.equals(elementAtNext.getTerminal())) {
					expectedElements.remove(j);
					size--;
				} else {
					j++;
				}
			}
		}
	}
	
	protected void removeInvalidEntriesAtEnd(java.util.List<org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal> expectedElements) {
		for (int i = 0; i < expectedElements.size() - 1;) {
			org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal elementAtIndex = expectedElements.get(i);
			org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal elementAtNext = expectedElements.get(i + 1);
			
			// If the two expected elements have a different parent in the syntax definition,
			// we must not discard the second element, because is probably stems from a parent
			// rule.
			org.emftext.sdk.concretesyntax.resource.cs.grammar.CsSyntaxElement symtaxElementOfThis = elementAtIndex.getTerminal().getSymtaxElement();
			org.emftext.sdk.concretesyntax.resource.cs.grammar.CsSyntaxElement symtaxElementOfNext = elementAtNext.getTerminal().getSymtaxElement();
			boolean differentParent = symtaxElementOfNext.getParent() != symtaxElementOfThis.getParent();
			
			boolean sameStartExcludingHiddenTokens = elementAtIndex.getStartExcludingHiddenTokens() == elementAtNext.getStartExcludingHiddenTokens();
			boolean differentFollowSet = elementAtIndex.getFollowSetID() != elementAtNext.getFollowSetID();
			if (sameStartExcludingHiddenTokens && differentFollowSet && !differentParent) {
				expectedElements.remove(i + 1);
			} else {
				i++;
			}
		}
	}
	
	/**
	 * Removes all proposals for keywords that end before the given index.
	 */
	protected void removeKeywordsEndingBeforeIndex(java.util.Collection<org.emftext.sdk.concretesyntax.resource.cs.ui.CsCompletionProposal> proposals, int index) {
		java.util.List<org.emftext.sdk.concretesyntax.resource.cs.ui.CsCompletionProposal> toRemove = new java.util.ArrayList<org.emftext.sdk.concretesyntax.resource.cs.ui.CsCompletionProposal>();
		for (org.emftext.sdk.concretesyntax.resource.cs.ui.CsCompletionProposal proposal : proposals) {
			org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal expectedTerminal = proposal.getExpectedTerminal();
			org.emftext.sdk.concretesyntax.resource.cs.ICsExpectedElement terminal = expectedTerminal.getTerminal();
			if (terminal instanceof org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedCsString) {
				org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedCsString csString = (org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedCsString) terminal;
				int startExcludingHiddenTokens = expectedTerminal.getStartExcludingHiddenTokens();
				if (startExcludingHiddenTokens + csString.getValue().length() - 1 < index) {
					toRemove.add(proposal);
				}
			}
		}
		proposals.removeAll(toRemove);
	}
	
	protected String findPrefix(java.util.List<org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal> expectedElements, org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal expectedAtCursor, String content, int cursorOffset) {
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
		return prefix;
	}
	
	protected java.util.Collection<org.emftext.sdk.concretesyntax.resource.cs.ui.CsCompletionProposal> deriveProposals(java.util.List<org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal> expectedElements, String content, org.emftext.sdk.concretesyntax.resource.cs.ICsTextResource resource, int cursorOffset) {
		java.util.Collection<org.emftext.sdk.concretesyntax.resource.cs.ui.CsCompletionProposal> resultSet = new java.util.LinkedHashSet<org.emftext.sdk.concretesyntax.resource.cs.ui.CsCompletionProposal>();
		for (org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal expectedElement : expectedElements) {
			resultSet.addAll(deriveProposals(expectedElement, content, resource, cursorOffset));
		}
		return resultSet;
	}
	
	protected java.util.Collection<org.emftext.sdk.concretesyntax.resource.cs.ui.CsCompletionProposal> deriveProposals(final org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal expectedTerminal, String content, org.emftext.sdk.concretesyntax.resource.cs.ICsTextResource resource, int cursorOffset) {
		org.emftext.sdk.concretesyntax.resource.cs.ICsExpectedElement expectedElement = (org.emftext.sdk.concretesyntax.resource.cs.ICsExpectedElement) expectedTerminal.getTerminal();
		if (expectedElement instanceof org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedCsString) {
			org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedCsString csString = (org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedCsString) expectedElement;
			return handleKeyword(expectedTerminal, csString, expectedTerminal.getPrefix());
		} else if (expectedElement instanceof org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedBooleanTerminal) {
			org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedBooleanTerminal expectedBooleanTerminal = (org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedBooleanTerminal) expectedElement;
			return handleBooleanTerminal(expectedTerminal, expectedBooleanTerminal, expectedTerminal.getPrefix());
		} else if (expectedElement instanceof org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedEnumerationTerminal) {
			org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedEnumerationTerminal expectedEnumerationTerminal = (org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedEnumerationTerminal) expectedElement;
			return handleEnumerationTerminal(expectedTerminal, expectedEnumerationTerminal, expectedTerminal.getPrefix());
		} else if (expectedElement instanceof org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedStructuralFeature) {
			final org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedStructuralFeature expectedFeature = (org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedStructuralFeature) expectedElement;
			final org.eclipse.emf.ecore.EStructuralFeature feature = expectedFeature.getFeature();
			final org.eclipse.emf.ecore.EClassifier featureType = feature.getEType();
			final org.eclipse.emf.ecore.EObject container = findCorrectContainer(expectedTerminal);
			
			// Here it gets really crazy. We need to modify the model in a way that reflects
			// the state the model would be in, if the expected terminal were present. After
			// computing the corresponding completion proposals, the original state of the
			// model is restored. This procedure is required, because different models can be
			// required for different completion situations. This can be particularly observed
			// when the user has not yet typed a character that starts an element to be
			// completed.
			final java.util.Collection<org.emftext.sdk.concretesyntax.resource.cs.ui.CsCompletionProposal> proposals = new java.util.ArrayList<org.emftext.sdk.concretesyntax.resource.cs.ui.CsCompletionProposal>();
			expectedTerminal.materialize(new Runnable() {
				
				public void run() {
					if (feature instanceof org.eclipse.emf.ecore.EReference) {
						org.eclipse.emf.ecore.EReference reference = (org.eclipse.emf.ecore.EReference) feature;
						if (featureType instanceof org.eclipse.emf.ecore.EClass) {
							if (reference.isContainment()) {
								// the FOLLOW set should contain only non-containment references
								assert false;
							} else {
								proposals.addAll(handleNCReference(expectedTerminal, container, reference, expectedTerminal.getPrefix(), expectedFeature.getTokenName()));
							}
						}
					} else if (feature instanceof org.eclipse.emf.ecore.EAttribute) {
						org.eclipse.emf.ecore.EAttribute attribute = (org.eclipse.emf.ecore.EAttribute) feature;
						if (featureType instanceof org.eclipse.emf.ecore.EEnum) {
							org.eclipse.emf.ecore.EEnum enumType = (org.eclipse.emf.ecore.EEnum) featureType;
							proposals.addAll(handleEnumAttribute(expectedTerminal, expectedFeature, enumType, expectedTerminal.getPrefix(), container));
						} else {
							// handle EAttributes (derive default value depending on the type of the
							// attribute, figure out token resolver, and call deResolve())
							proposals.addAll(handleAttribute(expectedTerminal, expectedFeature, container, attribute, expectedTerminal.getPrefix()));
						}
					} else {
						// there should be no other subclass of EStructuralFeature
						assert false;
					}
				}
			});
			// Return the proposals that were computed in the closure call.
			return proposals;
		} else {
			// there should be no other class implementing IExpectedElement
			assert false;
		}
		return java.util.Collections.emptyList();
	}
	
	protected java.util.Collection<org.emftext.sdk.concretesyntax.resource.cs.ui.CsCompletionProposal> handleEnumAttribute(org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal expectedTerminal, org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedStructuralFeature expectedFeature, org.eclipse.emf.ecore.EEnum enumType, String prefix, org.eclipse.emf.ecore.EObject container) {
		java.util.Collection<org.eclipse.emf.ecore.EEnumLiteral> enumLiterals = enumType.getELiterals();
		java.util.Collection<org.emftext.sdk.concretesyntax.resource.cs.ui.CsCompletionProposal> result = new java.util.LinkedHashSet<org.emftext.sdk.concretesyntax.resource.cs.ui.CsCompletionProposal>();
		for (org.eclipse.emf.ecore.EEnumLiteral literal : enumLiterals) {
			String unResolvedLiteral = literal.getLiteral();
			// use token resolver to get de-resolved value of the literal
			org.emftext.sdk.concretesyntax.resource.cs.ICsTokenResolverFactory tokenResolverFactory = metaInformation.getTokenResolverFactory();
			org.emftext.sdk.concretesyntax.resource.cs.ICsTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver(expectedFeature.getTokenName());
			String resolvedLiteral = tokenResolver.deResolve(unResolvedLiteral, expectedFeature.getFeature(), container);
			boolean matchesPrefix = matches(resolvedLiteral, prefix);
			result.add(new org.emftext.sdk.concretesyntax.resource.cs.ui.CsCompletionProposal(expectedTerminal, resolvedLiteral, prefix, matchesPrefix, expectedFeature.getFeature(), container));
		}
		return result;
	}
	
	protected java.util.Collection<org.emftext.sdk.concretesyntax.resource.cs.ui.CsCompletionProposal> handleNCReference(org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal expectedTerminal, org.eclipse.emf.ecore.EObject container, org.eclipse.emf.ecore.EReference reference, String prefix, String tokenName) {
		// proposals for non-containment references are derived by calling the reference
		// resolver switch in fuzzy mode.
		org.emftext.sdk.concretesyntax.resource.cs.ICsReferenceResolverSwitch resolverSwitch = metaInformation.getReferenceResolverSwitch();
		org.emftext.sdk.concretesyntax.resource.cs.ICsTokenResolverFactory tokenResolverFactory = metaInformation.getTokenResolverFactory();
		org.emftext.sdk.concretesyntax.resource.cs.ICsReferenceResolveResult<org.eclipse.emf.ecore.EObject> result = new org.emftext.sdk.concretesyntax.resource.cs.mopp.CsReferenceResolveResult<org.eclipse.emf.ecore.EObject>(true);
		resolverSwitch.resolveFuzzy(prefix, container, reference, 0, result);
		java.util.Collection<org.emftext.sdk.concretesyntax.resource.cs.ICsReferenceMapping<org.eclipse.emf.ecore.EObject>> mappings = result.getMappings();
		if (mappings != null) {
			java.util.Collection<org.emftext.sdk.concretesyntax.resource.cs.ui.CsCompletionProposal> resultSet = new java.util.LinkedHashSet<org.emftext.sdk.concretesyntax.resource.cs.ui.CsCompletionProposal>();
			for (org.emftext.sdk.concretesyntax.resource.cs.ICsReferenceMapping<org.eclipse.emf.ecore.EObject> mapping : mappings) {
				org.eclipse.swt.graphics.Image image = null;
				if (mapping instanceof org.emftext.sdk.concretesyntax.resource.cs.mopp.CsElementMapping<?>) {
					org.emftext.sdk.concretesyntax.resource.cs.mopp.CsElementMapping<?> elementMapping = (org.emftext.sdk.concretesyntax.resource.cs.mopp.CsElementMapping<?>) mapping;
					Object target = elementMapping.getTargetElement();
					// de-resolve reference to obtain correct identifier
					org.emftext.sdk.concretesyntax.resource.cs.ICsTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver(tokenName);
					final String identifier = tokenResolver.deResolve(elementMapping.getIdentifier(), reference, container);
					if (target instanceof org.eclipse.emf.ecore.EObject) {
						image = getImage((org.eclipse.emf.ecore.EObject) target);
					}
					boolean matchesPrefix = matches(identifier, prefix);
					resultSet.add(new org.emftext.sdk.concretesyntax.resource.cs.ui.CsCompletionProposal(expectedTerminal, identifier, prefix, matchesPrefix, reference, container, image));
				}
			}
			return resultSet;
		}
		return java.util.Collections.emptyList();
	}
	
	protected java.util.Collection<org.emftext.sdk.concretesyntax.resource.cs.ui.CsCompletionProposal> handleAttribute(org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal expectedTerminal, org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedStructuralFeature expectedFeature, org.eclipse.emf.ecore.EObject container, org.eclipse.emf.ecore.EAttribute attribute, String prefix) {
		java.util.Collection<org.emftext.sdk.concretesyntax.resource.cs.ui.CsCompletionProposal> resultSet = new java.util.LinkedHashSet<org.emftext.sdk.concretesyntax.resource.cs.ui.CsCompletionProposal>();
		Object[] defaultValues = attributeValueProvider.getDefaultValues(attribute);
		if (defaultValues != null) {
			for (Object defaultValue : defaultValues) {
				if (defaultValue != null) {
					org.emftext.sdk.concretesyntax.resource.cs.ICsTokenResolverFactory tokenResolverFactory = metaInformation.getTokenResolverFactory();
					String tokenName = expectedFeature.getTokenName();
					if (tokenName != null) {
						org.emftext.sdk.concretesyntax.resource.cs.ICsTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver(tokenName);
						if (tokenResolver != null) {
							String defaultValueAsString = tokenResolver.deResolve(defaultValue, attribute, container);
							boolean matchesPrefix = matches(defaultValueAsString, prefix);
							resultSet.add(new org.emftext.sdk.concretesyntax.resource.cs.ui.CsCompletionProposal(expectedTerminal, defaultValueAsString, prefix, matchesPrefix, expectedFeature.getFeature(), container));
						}
					}
				}
			}
		}
		return resultSet;
	}
	
	/**
	 * Creates a set of completion proposals from the given keyword.
	 */
	protected java.util.Collection<org.emftext.sdk.concretesyntax.resource.cs.ui.CsCompletionProposal> handleKeyword(org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal expectedTerminal, org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedCsString csString, String prefix) {
		String proposal = csString.getValue();
		boolean matchesPrefix = matches(proposal, prefix);
		return java.util.Collections.singleton(new org.emftext.sdk.concretesyntax.resource.cs.ui.CsCompletionProposal(expectedTerminal, proposal, prefix, matchesPrefix, null, null));
	}
	
	/**
	 * Creates a set of (two) completion proposals from the given boolean terminal.
	 */
	protected java.util.Collection<org.emftext.sdk.concretesyntax.resource.cs.ui.CsCompletionProposal> handleBooleanTerminal(org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal expectedTerminal, org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedBooleanTerminal expectedBooleanTerminal, String prefix) {
		java.util.Collection<org.emftext.sdk.concretesyntax.resource.cs.ui.CsCompletionProposal> result = new java.util.LinkedHashSet<org.emftext.sdk.concretesyntax.resource.cs.ui.CsCompletionProposal>(2);
		org.emftext.sdk.concretesyntax.resource.cs.grammar.CsBooleanTerminal booleanTerminal = expectedBooleanTerminal.getBooleanTerminal();
		result.addAll(handleLiteral(expectedTerminal, booleanTerminal.getAttribute(), prefix, booleanTerminal.getTrueLiteral()));
		result.addAll(handleLiteral(expectedTerminal, booleanTerminal.getAttribute(), prefix, booleanTerminal.getFalseLiteral()));
		return result;
	}
	
	/**
	 * Creates a set of completion proposals from the given enumeration terminal. For
	 * each enumeration literal one proposal is created.
	 */
	protected java.util.Collection<org.emftext.sdk.concretesyntax.resource.cs.ui.CsCompletionProposal> handleEnumerationTerminal(org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal expectedTerminal, org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedEnumerationTerminal expectedEnumerationTerminal, String prefix) {
		java.util.Collection<org.emftext.sdk.concretesyntax.resource.cs.ui.CsCompletionProposal> result = new java.util.LinkedHashSet<org.emftext.sdk.concretesyntax.resource.cs.ui.CsCompletionProposal>(2);
		org.emftext.sdk.concretesyntax.resource.cs.grammar.CsEnumerationTerminal enumerationTerminal = expectedEnumerationTerminal.getEnumerationTerminal();
		java.util.Map<String, String> literalMapping = enumerationTerminal.getLiteralMapping();
		for (String literalName : literalMapping.keySet()) {
			result.addAll(handleLiteral(expectedTerminal, enumerationTerminal.getAttribute(), prefix, literalMapping.get(literalName)));
		}
		return result;
	}
	
	protected java.util.Collection<org.emftext.sdk.concretesyntax.resource.cs.ui.CsCompletionProposal> handleLiteral(org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal expectedTerminal, org.eclipse.emf.ecore.EAttribute attribute, String prefix, String literal) {
		if ("".equals(literal)) {
			return java.util.Collections.emptySet();
		}
		boolean matchesPrefix = matches(literal, prefix);
		return java.util.Collections.singleton(new org.emftext.sdk.concretesyntax.resource.cs.ui.CsCompletionProposal(expectedTerminal, literal, prefix, matchesPrefix, null, null));
	}
	
	/**
	 * Calculates the prefix for each given expected element. The prefix depends on
	 * the current document content, the cursor position, and the position where the
	 * element is expected.
	 */
	protected void setPrefixes(java.util.List<org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal> expectedElements, String content, int cursorOffset) {
		if (cursorOffset < 0) {
			return;
		}
		for (org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal expectedElement : expectedElements) {
			String prefix = findPrefix(expectedElements, expectedElement, content, cursorOffset);
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
	
	/**
	 * Calculates the end index of the expected element at allExpectedElements[index].
	 * To determine the end, the subsequent expected elements from the array of all
	 * expected elements are used. An element is considered to end one character
	 * before the next elements starts.
	 */
	protected int getEnd(org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal[] allExpectedElements, int indexInList) {
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
	
	/**
	 * Checks whether the given proposed string matches the prefix. The two strings
	 * are compared ignoring the case. The prefix is also considered to match if is a
	 * camel case representation of the proposal.
	 */
	protected boolean matches(String proposal, String prefix) {
		if (proposal == null || prefix == null) {
			return false;
		}
		return (proposal.toLowerCase().startsWith(prefix.toLowerCase()) || org.emftext.sdk.concretesyntax.resource.cs.util.CsStringUtil.matchCamelCase(prefix, proposal) != null) && !proposal.equals(prefix);
	}
	
	protected org.eclipse.swt.graphics.Image getImage(org.eclipse.emf.ecore.EObject element) {
		if (!org.eclipse.core.runtime.Platform.isRunning()) {
			return null;
		}
		org.eclipse.emf.edit.provider.ComposedAdapterFactory adapterFactory = new org.eclipse.emf.edit.provider.ComposedAdapterFactory(org.eclipse.emf.edit.provider.ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		adapterFactory.addAdapterFactory(new org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory());
		org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider labelProvider = new org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider(adapterFactory);
		return labelProvider.getImage(element);
	}
	
	protected org.eclipse.emf.ecore.EObject findCorrectContainer(org.emftext.sdk.concretesyntax.resource.cs.mopp.CsExpectedTerminal expectedTerminal) {
		org.eclipse.emf.ecore.EObject container = expectedTerminal.getContainer();
		org.eclipse.emf.ecore.EClass ruleMetaclass = expectedTerminal.getTerminal().getRuleMetaclass();
		if (ruleMetaclass.isInstance(container)) {
			// container is correct for expected terminal
			return container;
		}
		// the container is wrong
		org.eclipse.emf.ecore.EObject parent = null;
		org.eclipse.emf.ecore.EObject previousParent = null;
		org.eclipse.emf.ecore.EObject correctContainer = null;
		org.eclipse.emf.ecore.EObject hookableParent = null;
		org.emftext.sdk.concretesyntax.resource.cs.grammar.CsContainmentTrace containmentTrace = expectedTerminal.getContainmentTrace();
		org.eclipse.emf.ecore.EClass startClass = containmentTrace.getStartClass();
		org.emftext.sdk.concretesyntax.resource.cs.mopp.CsContainedFeature currentLink = null;
		org.emftext.sdk.concretesyntax.resource.cs.mopp.CsContainedFeature previousLink = null;
		org.emftext.sdk.concretesyntax.resource.cs.mopp.CsContainedFeature[] containedFeatures = containmentTrace.getPath();
		for (int i = 0; i < containedFeatures.length; i++) {
			currentLink = containedFeatures[i];
			if (i > 0) {
				previousLink = containedFeatures[i - 1];
			}
			org.eclipse.emf.ecore.EClass containerClass = currentLink.getContainerClass();
			hookableParent = findHookParent(container, startClass, currentLink, parent);
			if (hookableParent != null) {
				// we found the correct parent
				break;
			} else {
				previousParent = parent;
				parent = containerClass.getEPackage().getEFactoryInstance().create(containerClass);
				if (parent != null) {
					if (previousParent == null) {
						// replace container for expectedTerminal with correctContainer
						correctContainer = parent;
					} else {
						org.emftext.sdk.concretesyntax.resource.cs.util.CsEObjectUtil.setFeature(parent, previousLink.getFeature(), previousParent, false);
					}
				}
			}
		}
		
		if (correctContainer == null) {
			correctContainer = container;
		}
		
		if (currentLink == null) {
			return correctContainer;
		}
		
		hookableParent = findHookParent(container, startClass, currentLink, parent);
		
		final org.eclipse.emf.ecore.EObject finalHookableParent = hookableParent;
		final org.eclipse.emf.ecore.EStructuralFeature finalFeature = currentLink.getFeature();
		final org.eclipse.emf.ecore.EObject finalParent = parent;
		if (parent != null && hookableParent != null) {
			expectedTerminal.setAttachmentCode(new Runnable() {
				
				public void run() {
					org.emftext.sdk.concretesyntax.resource.cs.util.CsEObjectUtil.setFeature(finalHookableParent, finalFeature, finalParent, false);
				}
			});
		}
		return correctContainer;
	}
	
	/**
	 * Walks up the containment hierarchy to find an EObject that is able to hold
	 * (contain) the given object.
	 */
	protected org.eclipse.emf.ecore.EObject findHookParent(org.eclipse.emf.ecore.EObject container, org.eclipse.emf.ecore.EClass startClass, org.emftext.sdk.concretesyntax.resource.cs.mopp.CsContainedFeature currentLink, org.eclipse.emf.ecore.EObject object) {
		org.eclipse.emf.ecore.EClass containerClass = currentLink.getContainerClass();
		while (container != null) {
			if (containerClass.isInstance(object)) {
				if (startClass.equals(container.eClass())) {
					return container;
				}
			}
			container = container.eContainer();
		}
		return null;
	}
	
}
