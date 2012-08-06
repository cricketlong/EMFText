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
 * $Id$
 */
package org.emftext.language.prolog.provider;

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

import org.emftext.language.prolog.util.PrologAdapterFactory;

/**
 * This is the factory that is used to provide the interfaces needed to support Viewers.
 * The adapters generated by this factory convert EMF adapter notifications into calls to {@link #fireNotifyChanged fireNotifyChanged}.
 * The adapters also support Eclipse property sheets.
 * Note that most of the adapters are shared among multiple instances.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class PrologItemProviderAdapterFactory extends PrologAdapterFactory implements ComposeableAdapterFactory, IChangeNotifier, IDisposable {
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
	public PrologItemProviderAdapterFactory() {
		supportedTypes.add(IEditingDomainItemProvider.class);
		supportedTypes.add(IStructuredItemContentProvider.class);
		supportedTypes.add(ITreeItemContentProvider.class);
		supportedTypes.add(IItemLabelProvider.class);
		supportedTypes.add(IItemPropertySource.class);
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.emftext.language.prolog.PrologProgram} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PrologProgramItemProvider prologProgramItemProvider;

	/**
	 * This creates an adapter for a {@link org.emftext.language.prolog.PrologProgram}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createPrologProgramAdapter() {
		if (prologProgramItemProvider == null) {
			prologProgramItemProvider = new PrologProgramItemProvider(this);
		}

		return prologProgramItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.emftext.language.prolog.Clause} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ClauseItemProvider clauseItemProvider;

	/**
	 * This creates an adapter for a {@link org.emftext.language.prolog.Clause}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createClauseAdapter() {
		if (clauseItemProvider == null) {
			clauseItemProvider = new ClauseItemProvider(this);
		}

		return clauseItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.emftext.language.prolog.Numeral} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NumeralItemProvider numeralItemProvider;

	/**
	 * This creates an adapter for a {@link org.emftext.language.prolog.Numeral}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createNumeralAdapter() {
		if (numeralItemProvider == null) {
			numeralItemProvider = new NumeralItemProvider(this);
		}

		return numeralItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.emftext.language.prolog.Variable} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VariableItemProvider variableItemProvider;

	/**
	 * This creates an adapter for a {@link org.emftext.language.prolog.Variable}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createVariableAdapter() {
		if (variableItemProvider == null) {
			variableItemProvider = new VariableItemProvider(this);
		}

		return variableItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.emftext.language.prolog.SmallAtom} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SmallAtomItemProvider smallAtomItemProvider;

	/**
	 * This creates an adapter for a {@link org.emftext.language.prolog.SmallAtom}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createSmallAtomAdapter() {
		if (smallAtomItemProvider == null) {
			smallAtomItemProvider = new SmallAtomItemProvider(this);
		}

		return smallAtomItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.emftext.language.prolog.String} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StringItemProvider stringItemProvider;

	/**
	 * This creates an adapter for a {@link org.emftext.language.prolog.String}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createStringAdapter() {
		if (stringItemProvider == null) {
			stringItemProvider = new StringItemProvider(this);
		}

		return stringItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.emftext.language.prolog.List} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ListItemProvider listItemProvider;

	/**
	 * This creates an adapter for a {@link org.emftext.language.prolog.List}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createListAdapter() {
		if (listItemProvider == null) {
			listItemProvider = new ListItemProvider(this);
		}

		return listItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.emftext.language.prolog.Predicate} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PredicateItemProvider predicateItemProvider;

	/**
	 * This creates an adapter for a {@link org.emftext.language.prolog.Predicate}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createPredicateAdapter() {
		if (predicateItemProvider == null) {
			predicateItemProvider = new PredicateItemProvider(this);
		}

		return predicateItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.emftext.language.prolog.VariableReference} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VariableReferenceItemProvider variableReferenceItemProvider;

	/**
	 * This creates an adapter for a {@link org.emftext.language.prolog.VariableReference}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createVariableReferenceAdapter() {
		if (variableReferenceItemProvider == null) {
			variableReferenceItemProvider = new VariableReferenceItemProvider(this);
		}

		return variableReferenceItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.emftext.language.prolog.AnonymousVariable} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AnonymousVariableItemProvider anonymousVariableItemProvider;

	/**
	 * This creates an adapter for a {@link org.emftext.language.prolog.AnonymousVariable}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createAnonymousVariableAdapter() {
		if (anonymousVariableItemProvider == null) {
			anonymousVariableItemProvider = new AnonymousVariableItemProvider(this);
		}

		return anonymousVariableItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.emftext.language.prolog.Assignment} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AssignmentItemProvider assignmentItemProvider;

	/**
	 * This creates an adapter for a {@link org.emftext.language.prolog.Assignment}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createAssignmentAdapter() {
		if (assignmentItemProvider == null) {
			assignmentItemProvider = new AssignmentItemProvider(this);
		}

		return assignmentItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.emftext.language.prolog.Conjunction} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConjunctionItemProvider conjunctionItemProvider;

	/**
	 * This creates an adapter for a {@link org.emftext.language.prolog.Conjunction}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createConjunctionAdapter() {
		if (conjunctionItemProvider == null) {
			conjunctionItemProvider = new ConjunctionItemProvider(this);
		}

		return conjunctionItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.emftext.language.prolog.Additive} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AdditiveItemProvider additiveItemProvider;

	/**
	 * This creates an adapter for a {@link org.emftext.language.prolog.Additive}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createAdditiveAdapter() {
		if (additiveItemProvider == null) {
			additiveItemProvider = new AdditiveItemProvider(this);
		}

		return additiveItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.emftext.language.prolog.Multiplicative} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MultiplicativeItemProvider multiplicativeItemProvider;

	/**
	 * This creates an adapter for a {@link org.emftext.language.prolog.Multiplicative}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createMultiplicativeAdapter() {
		if (multiplicativeItemProvider == null) {
			multiplicativeItemProvider = new MultiplicativeItemProvider(this);
		}

		return multiplicativeItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.emftext.language.prolog.Power} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PowerItemProvider powerItemProvider;

	/**
	 * This creates an adapter for a {@link org.emftext.language.prolog.Power}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createPowerAdapter() {
		if (powerItemProvider == null) {
			powerItemProvider = new PowerItemProvider(this);
		}

		return powerItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.emftext.language.prolog.Negation} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NegationItemProvider negationItemProvider;

	/**
	 * This creates an adapter for a {@link org.emftext.language.prolog.Negation}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createNegationAdapter() {
		if (negationItemProvider == null) {
			negationItemProvider = new NegationItemProvider(this);
		}

		return negationItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.emftext.language.prolog.BracketExpression} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BracketExpressionItemProvider bracketExpressionItemProvider;

	/**
	 * This creates an adapter for a {@link org.emftext.language.prolog.BracketExpression}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createBracketExpressionAdapter() {
		if (bracketExpressionItemProvider == null) {
			bracketExpressionItemProvider = new BracketExpressionItemProvider(this);
		}

		return bracketExpressionItemProvider;
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
		if (prologProgramItemProvider != null) prologProgramItemProvider.dispose();
		if (clauseItemProvider != null) clauseItemProvider.dispose();
		if (numeralItemProvider != null) numeralItemProvider.dispose();
		if (variableItemProvider != null) variableItemProvider.dispose();
		if (smallAtomItemProvider != null) smallAtomItemProvider.dispose();
		if (stringItemProvider != null) stringItemProvider.dispose();
		if (listItemProvider != null) listItemProvider.dispose();
		if (predicateItemProvider != null) predicateItemProvider.dispose();
		if (variableReferenceItemProvider != null) variableReferenceItemProvider.dispose();
		if (anonymousVariableItemProvider != null) anonymousVariableItemProvider.dispose();
		if (assignmentItemProvider != null) assignmentItemProvider.dispose();
		if (conjunctionItemProvider != null) conjunctionItemProvider.dispose();
		if (additiveItemProvider != null) additiveItemProvider.dispose();
		if (multiplicativeItemProvider != null) multiplicativeItemProvider.dispose();
		if (powerItemProvider != null) powerItemProvider.dispose();
		if (negationItemProvider != null) negationItemProvider.dispose();
		if (bracketExpressionItemProvider != null) bracketExpressionItemProvider.dispose();
	}

}
