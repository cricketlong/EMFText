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
package org.emftext.sdk.concretesyntax.provider;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.edit.provider.ChangeNotifier;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IChangeNotifier;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.emftext.sdk.concretesyntax.util.ConcretesyntaxAdapterFactory;

/**
 * This is the factory that is used to provide the interfaces needed to support Viewers.
 * The adapters generated by this factory convert EMF adapter notifications into calls to {@link #fireNotifyChanged fireNotifyChanged}.
 * The adapters also support Eclipse property sheets.
 * Note that most of the adapters are shared among multiple instances.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class ConcretesyntaxItemProviderAdapterFactory extends ConcretesyntaxAdapterFactory implements ComposeableAdapterFactory, IChangeNotifier, IDisposable {
	/**
	 * This keeps track of the root adapter factory that delegates to this adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ComposedAdapterFactory parentAdapterFactory;

	/**
	 * This is used to implement {@link org.eclipse.emf.edit.provider.IChangeNotifier}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IChangeNotifier changeNotifier = new ChangeNotifier();

	/**
	 * This keeps track of all the supported types checked by {@link #isFactoryForType isFactoryForType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Collection<Object> supportedTypes = new ArrayList<Object>();

	/**
	 * This constructs an instance.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConcretesyntaxItemProviderAdapterFactory() {
		supportedTypes.add(IEditingDomainItemProvider.class);
		supportedTypes.add(IStructuredItemContentProvider.class);
		supportedTypes.add(ITreeItemContentProvider.class);
		supportedTypes.add(IItemLabelProvider.class);
		supportedTypes.add(IItemPropertySource.class);
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.emftext.sdk.concretesyntax.ConcreteSyntax} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConcreteSyntaxItemProvider concreteSyntaxItemProvider;

	/**
	 * This creates an adapter for a {@link org.emftext.sdk.concretesyntax.ConcreteSyntax}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createConcreteSyntaxAdapter() {
		if (concreteSyntaxItemProvider == null) {
			concreteSyntaxItemProvider = new ConcreteSyntaxItemProvider(this);
		}

		return concreteSyntaxItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.emftext.sdk.concretesyntax.Import} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ImportItemProvider importItemProvider;

	/**
	 * This creates an adapter for a {@link org.emftext.sdk.concretesyntax.Import}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createImportAdapter() {
		if (importItemProvider == null) {
			importItemProvider = new ImportItemProvider(this);
		}

		return importItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.emftext.sdk.concretesyntax.Rule} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RuleItemProvider ruleItemProvider;

	/**
	 * This creates an adapter for a {@link org.emftext.sdk.concretesyntax.Rule}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createRuleAdapter() {
		if (ruleItemProvider == null) {
			ruleItemProvider = new RuleItemProvider(this);
		}

		return ruleItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.emftext.sdk.concretesyntax.Choice} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ChoiceItemProvider choiceItemProvider;

	/**
	 * This creates an adapter for a {@link org.emftext.sdk.concretesyntax.Choice}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createChoiceAdapter() {
		if (choiceItemProvider == null) {
			choiceItemProvider = new ChoiceItemProvider(this);
		}

		return choiceItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.emftext.sdk.concretesyntax.Sequence} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SequenceItemProvider sequenceItemProvider;

	/**
	 * This creates an adapter for a {@link org.emftext.sdk.concretesyntax.Sequence}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createSequenceAdapter() {
		if (sequenceItemProvider == null) {
			sequenceItemProvider = new SequenceItemProvider(this);
		}

		return sequenceItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.emftext.sdk.concretesyntax.CsString} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CsStringItemProvider csStringItemProvider;

	/**
	 * This creates an adapter for a {@link org.emftext.sdk.concretesyntax.CsString}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createCsStringAdapter() {
		if (csStringItemProvider == null) {
			csStringItemProvider = new CsStringItemProvider(this);
		}

		return csStringItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.emftext.sdk.concretesyntax.WhiteSpaces} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected WhiteSpacesItemProvider whiteSpacesItemProvider;

	/**
	 * This creates an adapter for a {@link org.emftext.sdk.concretesyntax.WhiteSpaces}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createWhiteSpacesAdapter() {
		if (whiteSpacesItemProvider == null) {
			whiteSpacesItemProvider = new WhiteSpacesItemProvider(this);
		}

		return whiteSpacesItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.emftext.sdk.concretesyntax.LineBreak} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LineBreakItemProvider lineBreakItemProvider;

	/**
	 * This creates an adapter for a {@link org.emftext.sdk.concretesyntax.LineBreak}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createLineBreakAdapter() {
		if (lineBreakItemProvider == null) {
			lineBreakItemProvider = new LineBreakItemProvider(this);
		}

		return lineBreakItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.emftext.sdk.concretesyntax.PLUS} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PLUSItemProvider plusItemProvider;

	/**
	 * This creates an adapter for a {@link org.emftext.sdk.concretesyntax.PLUS}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createPLUSAdapter() {
		if (plusItemProvider == null) {
			plusItemProvider = new PLUSItemProvider(this);
		}

		return plusItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.emftext.sdk.concretesyntax.STAR} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected STARItemProvider starItemProvider;

	/**
	 * This creates an adapter for a {@link org.emftext.sdk.concretesyntax.STAR}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createSTARAdapter() {
		if (starItemProvider == null) {
			starItemProvider = new STARItemProvider(this);
		}

		return starItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.emftext.sdk.concretesyntax.QUESTIONMARK} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected QUESTIONMARKItemProvider questionmarkItemProvider;

	/**
	 * This creates an adapter for a {@link org.emftext.sdk.concretesyntax.QUESTIONMARK}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createQUESTIONMARKAdapter() {
		if (questionmarkItemProvider == null) {
			questionmarkItemProvider = new QUESTIONMARKItemProvider(this);
		}

		return questionmarkItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.emftext.sdk.concretesyntax.CompoundDefinition} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CompoundDefinitionItemProvider compoundDefinitionItemProvider;

	/**
	 * This creates an adapter for a {@link org.emftext.sdk.concretesyntax.CompoundDefinition}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createCompoundDefinitionAdapter() {
		if (compoundDefinitionItemProvider == null) {
			compoundDefinitionItemProvider = new CompoundDefinitionItemProvider(this);
		}

		return compoundDefinitionItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.emftext.sdk.concretesyntax.RegexComposer} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RegexComposerItemProvider regexComposerItemProvider;

	/**
	 * This creates an adapter for a {@link org.emftext.sdk.concretesyntax.RegexComposer}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createRegexComposerAdapter() {
		if (regexComposerItemProvider == null) {
			regexComposerItemProvider = new RegexComposerItemProvider(this);
		}

		return regexComposerItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.emftext.sdk.concretesyntax.AtomicRegex} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AtomicRegexItemProvider atomicRegexItemProvider;

	/**
	 * This creates an adapter for a {@link org.emftext.sdk.concretesyntax.AtomicRegex}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createAtomicRegexAdapter() {
		if (atomicRegexItemProvider == null) {
			atomicRegexItemProvider = new AtomicRegexItemProvider(this);
		}

		return atomicRegexItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.emftext.sdk.concretesyntax.RegexReference} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RegexReferenceItemProvider regexReferenceItemProvider;

	/**
	 * This creates an adapter for a {@link org.emftext.sdk.concretesyntax.RegexReference}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createRegexReferenceAdapter() {
		if (regexReferenceItemProvider == null) {
			regexReferenceItemProvider = new RegexReferenceItemProvider(this);
		}

		return regexReferenceItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.emftext.sdk.concretesyntax.PartialTokenDefinition} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PartialTokenDefinitionItemProvider partialTokenDefinitionItemProvider;

	/**
	 * This creates an adapter for a {@link org.emftext.sdk.concretesyntax.PartialTokenDefinition}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createPartialTokenDefinitionAdapter() {
		if (partialTokenDefinitionItemProvider == null) {
			partialTokenDefinitionItemProvider = new PartialTokenDefinitionItemProvider(this);
		}

		return partialTokenDefinitionItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.emftext.sdk.concretesyntax.NormalTokenDefinition} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NormalTokenDefinitionItemProvider normalTokenDefinitionItemProvider;

	/**
	 * This creates an adapter for a {@link org.emftext.sdk.concretesyntax.NormalTokenDefinition}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createNormalTokenDefinitionAdapter() {
		if (normalTokenDefinitionItemProvider == null) {
			normalTokenDefinitionItemProvider = new NormalTokenDefinitionItemProvider(this);
		}

		return normalTokenDefinitionItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.emftext.sdk.concretesyntax.TokenRedefinition} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TokenRedefinitionItemProvider tokenRedefinitionItemProvider;

	/**
	 * This creates an adapter for a {@link org.emftext.sdk.concretesyntax.TokenRedefinition}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createTokenRedefinitionAdapter() {
		if (tokenRedefinitionItemProvider == null) {
			tokenRedefinitionItemProvider = new TokenRedefinitionItemProvider(this);
		}

		return tokenRedefinitionItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.emftext.sdk.concretesyntax.QuotedTokenDefinition} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected QuotedTokenDefinitionItemProvider quotedTokenDefinitionItemProvider;

	/**
	 * This creates an adapter for a {@link org.emftext.sdk.concretesyntax.QuotedTokenDefinition}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createQuotedTokenDefinitionAdapter() {
		if (quotedTokenDefinitionItemProvider == null) {
			quotedTokenDefinitionItemProvider = new QuotedTokenDefinitionItemProvider(this);
		}

		return quotedTokenDefinitionItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.emftext.sdk.concretesyntax.TokenPriorityDirective} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TokenPriorityDirectiveItemProvider tokenPriorityDirectiveItemProvider;

	/**
	 * This creates an adapter for a {@link org.emftext.sdk.concretesyntax.TokenPriorityDirective}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createTokenPriorityDirectiveAdapter() {
		if (tokenPriorityDirectiveItemProvider == null) {
			tokenPriorityDirectiveItemProvider = new TokenPriorityDirectiveItemProvider(this);
		}

		return tokenPriorityDirectiveItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.emftext.sdk.concretesyntax.Containment} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ContainmentItemProvider containmentItemProvider;

	/**
	 * This creates an adapter for a {@link org.emftext.sdk.concretesyntax.Containment}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createContainmentAdapter() {
		if (containmentItemProvider == null) {
			containmentItemProvider = new ContainmentItemProvider(this);
		}

		return containmentItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.emftext.sdk.concretesyntax.PlaceholderUsingSpecifiedToken} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PlaceholderUsingSpecifiedTokenItemProvider placeholderUsingSpecifiedTokenItemProvider;

	/**
	 * This creates an adapter for a {@link org.emftext.sdk.concretesyntax.PlaceholderUsingSpecifiedToken}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createPlaceholderUsingSpecifiedTokenAdapter() {
		if (placeholderUsingSpecifiedTokenItemProvider == null) {
			placeholderUsingSpecifiedTokenItemProvider = new PlaceholderUsingSpecifiedTokenItemProvider(this);
		}

		return placeholderUsingSpecifiedTokenItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.emftext.sdk.concretesyntax.PlaceholderUsingDefaultToken} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PlaceholderUsingDefaultTokenItemProvider placeholderUsingDefaultTokenItemProvider;

	/**
	 * This creates an adapter for a {@link org.emftext.sdk.concretesyntax.PlaceholderUsingDefaultToken}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createPlaceholderUsingDefaultTokenAdapter() {
		if (placeholderUsingDefaultTokenItemProvider == null) {
			placeholderUsingDefaultTokenItemProvider = new PlaceholderUsingDefaultTokenItemProvider(this);
		}

		return placeholderUsingDefaultTokenItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.emftext.sdk.concretesyntax.PlaceholderInQuotes} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PlaceholderInQuotesItemProvider placeholderInQuotesItemProvider;

	/**
	 * This creates an adapter for a {@link org.emftext.sdk.concretesyntax.PlaceholderInQuotes}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createPlaceholderInQuotesAdapter() {
		if (placeholderInQuotesItemProvider == null) {
			placeholderInQuotesItemProvider = new PlaceholderInQuotesItemProvider(this);
		}

		return placeholderInQuotesItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.emftext.sdk.concretesyntax.BooleanTerminal} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BooleanTerminalItemProvider booleanTerminalItemProvider;

	/**
	 * This creates an adapter for a {@link org.emftext.sdk.concretesyntax.BooleanTerminal}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createBooleanTerminalAdapter() {
		if (booleanTerminalItemProvider == null) {
			booleanTerminalItemProvider = new BooleanTerminalItemProvider(this);
		}

		return booleanTerminalItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.emftext.sdk.concretesyntax.Option} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OptionItemProvider optionItemProvider;

	/**
	 * This creates an adapter for a {@link org.emftext.sdk.concretesyntax.Option}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createOptionAdapter() {
		if (optionItemProvider == null) {
			optionItemProvider = new OptionItemProvider(this);
		}

		return optionItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.emftext.sdk.concretesyntax.Abstract} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AbstractItemProvider abstractItemProvider;

	/**
	 * This creates an adapter for a {@link org.emftext.sdk.concretesyntax.Abstract}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createAbstractAdapter() {
		if (abstractItemProvider == null) {
			abstractItemProvider = new AbstractItemProvider(this);
		}

		return abstractItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.emftext.sdk.concretesyntax.TokenStyle} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TokenStyleItemProvider tokenStyleItemProvider;

	/**
	 * This creates an adapter for a {@link org.emftext.sdk.concretesyntax.TokenStyle}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createTokenStyleAdapter() {
		if (tokenStyleItemProvider == null) {
			tokenStyleItemProvider = new TokenStyleItemProvider(this);
		}

		return tokenStyleItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.emftext.sdk.concretesyntax.Annotation} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AnnotationItemProvider annotationItemProvider;

	/**
	 * This creates an adapter for a {@link org.emftext.sdk.concretesyntax.Annotation}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createAnnotationAdapter() {
		if (annotationItemProvider == null) {
			annotationItemProvider = new AnnotationItemProvider(this);
		}

		return annotationItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.emftext.sdk.concretesyntax.KeyValuePair} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected KeyValuePairItemProvider keyValuePairItemProvider;

	/**
	 * This creates an adapter for a {@link org.emftext.sdk.concretesyntax.KeyValuePair}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createKeyValuePairAdapter() {
		if (keyValuePairItemProvider == null) {
			keyValuePairItemProvider = new KeyValuePairItemProvider(this);
		}

		return keyValuePairItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.emftext.sdk.concretesyntax.GenClassCache} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GenClassCacheItemProvider genClassCacheItemProvider;

	/**
	 * This creates an adapter for a {@link org.emftext.sdk.concretesyntax.GenClassCache}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createGenClassCacheAdapter() {
		if (genClassCacheItemProvider == null) {
			genClassCacheItemProvider = new GenClassCacheItemProvider(this);
		}

		return genClassCacheItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link java.util.Map.Entry} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GenClassCacheEntryItemProvider genClassCacheEntryItemProvider;

	/**
	 * This creates an adapter for a {@link java.util.Map.Entry}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createGenClassCacheEntryAdapter() {
		if (genClassCacheEntryItemProvider == null) {
			genClassCacheEntryItemProvider = new GenClassCacheEntryItemProvider(this);
		}

		return genClassCacheEntryItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.emftext.sdk.concretesyntax.EClassUtil} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClassUtilItemProvider eClassUtilItemProvider;

	/**
	 * This creates an adapter for a {@link org.emftext.sdk.concretesyntax.EClassUtil}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createEClassUtilAdapter() {
		if (eClassUtilItemProvider == null) {
			eClassUtilItemProvider = new EClassUtilItemProvider(this);
		}

		return eClassUtilItemProvider;
	}

	/**
	 * This returns the root adapter factory that contains this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComposeableAdapterFactory getRootAdapterFactory() {
		return parentAdapterFactory == null ? this : parentAdapterFactory.getRootAdapterFactory();
	}

	/**
	 * This sets the composed adapter factory that contains this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParentAdapterFactory(ComposedAdapterFactory parentAdapterFactory) {
		this.parentAdapterFactory = parentAdapterFactory;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object type) {
		return supportedTypes.contains(type) || super.isFactoryForType(type);
	}

	/**
	 * This implementation substitutes the factory itself as the key for the adapter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter adapt(Notifier notifier, Object type) {
		return super.adapt(notifier, this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object adapt(Object object, Object type) {
		if (isFactoryForType(type)) {
			Object adapter = super.adapt(object, type);
			if (!(type instanceof Class<?>) || (((Class<?>)type).isInstance(adapter))) {
				return adapter;
			}
		}

		return null;
	}

	/**
	 * This adds a listener.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void addListener(INotifyChangedListener notifyChangedListener) {
		changeNotifier.addListener(notifyChangedListener);
	}

	/**
	 * This removes a listener.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void removeListener(INotifyChangedListener notifyChangedListener) {
		changeNotifier.removeListener(notifyChangedListener);
	}

	/**
	 * This delegates to {@link #changeNotifier} and to {@link #parentAdapterFactory}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void fireNotifyChanged(Notification notification) {
		changeNotifier.fireNotifyChanged(notification);

		if (parentAdapterFactory != null) {
			parentAdapterFactory.fireNotifyChanged(notification);
		}
	}

	/**
	 * This disposes all of the item providers created by this factory. 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void dispose() {
		if (concreteSyntaxItemProvider != null) concreteSyntaxItemProvider.dispose();
		if (importItemProvider != null) importItemProvider.dispose();
		if (ruleItemProvider != null) ruleItemProvider.dispose();
		if (choiceItemProvider != null) choiceItemProvider.dispose();
		if (sequenceItemProvider != null) sequenceItemProvider.dispose();
		if (csStringItemProvider != null) csStringItemProvider.dispose();
		if (whiteSpacesItemProvider != null) whiteSpacesItemProvider.dispose();
		if (lineBreakItemProvider != null) lineBreakItemProvider.dispose();
		if (plusItemProvider != null) plusItemProvider.dispose();
		if (starItemProvider != null) starItemProvider.dispose();
		if (questionmarkItemProvider != null) questionmarkItemProvider.dispose();
		if (compoundDefinitionItemProvider != null) compoundDefinitionItemProvider.dispose();
		if (regexComposerItemProvider != null) regexComposerItemProvider.dispose();
		if (atomicRegexItemProvider != null) atomicRegexItemProvider.dispose();
		if (regexReferenceItemProvider != null) regexReferenceItemProvider.dispose();
		if (partialTokenDefinitionItemProvider != null) partialTokenDefinitionItemProvider.dispose();
		if (normalTokenDefinitionItemProvider != null) normalTokenDefinitionItemProvider.dispose();
		if (tokenRedefinitionItemProvider != null) tokenRedefinitionItemProvider.dispose();
		if (quotedTokenDefinitionItemProvider != null) quotedTokenDefinitionItemProvider.dispose();
		if (tokenPriorityDirectiveItemProvider != null) tokenPriorityDirectiveItemProvider.dispose();
		if (containmentItemProvider != null) containmentItemProvider.dispose();
		if (placeholderUsingSpecifiedTokenItemProvider != null) placeholderUsingSpecifiedTokenItemProvider.dispose();
		if (placeholderUsingDefaultTokenItemProvider != null) placeholderUsingDefaultTokenItemProvider.dispose();
		if (placeholderInQuotesItemProvider != null) placeholderInQuotesItemProvider.dispose();
		if (booleanTerminalItemProvider != null) booleanTerminalItemProvider.dispose();
		if (optionItemProvider != null) optionItemProvider.dispose();
		if (abstractItemProvider != null) abstractItemProvider.dispose();
		if (tokenStyleItemProvider != null) tokenStyleItemProvider.dispose();
		if (annotationItemProvider != null) annotationItemProvider.dispose();
		if (keyValuePairItemProvider != null) keyValuePairItemProvider.dispose();
		if (genClassCacheItemProvider != null) genClassCacheItemProvider.dispose();
		if (genClassCacheEntryItemProvider != null) genClassCacheEntryItemProvider.dispose();
		if (eClassUtilItemProvider != null) eClassUtilItemProvider.dispose();
	}

}
