package org.emftext.sdk.syntax_analysis;

import java.util.Collection;
import java.util.List;

import org.emftext.sdk.AbstractPostProcessor;
import org.emftext.sdk.concretesyntax.ConcreteSyntax;
import org.emftext.sdk.concretesyntax.ConcretesyntaxPackage;
import org.emftext.sdk.concretesyntax.PlaceholderInQuotes;
import org.emftext.sdk.concretesyntax.Rule;
import org.emftext.sdk.concretesyntax.resource.cs.mopp.CsResource;
import org.emftext.sdk.concretesyntax.resource.cs.mopp.ECsProblemType;
import org.emftext.sdk.concretesyntax.resource.cs.util.CsEObjectUtil;

public class PlaceholderInQuotesAnalyser extends AbstractPostProcessor {

	public final static String MESSAGE_1 = "The suffix for the placeholder in quotes must not be empty.";
	public final static String MESSAGE_2 = "The prefix for the placeholder in quotes must not be empty.";

	@Override
	public void analyse(CsResource resource, ConcreteSyntax syntax) {
		// we do not analyse all rule because imported ones can not be correctly
		// handled when this analyser is called
		List<Rule> rules = syntax.getRules();
		for (Rule rule : rules) {
			Collection<PlaceholderInQuotes> placeholdersInQuotes = CsEObjectUtil.getObjectsByType(rule.eAllContents(), ConcretesyntaxPackage.eINSTANCE.getPlaceholderInQuotes());
			for (PlaceholderInQuotes placeholder : placeholdersInQuotes) {
				checkEmpty(resource, placeholder, placeholder.getSuffix(), ECsProblemType.PLACEHOLDER_IN_QUOTES_WITH_EMPTY_SUFFIX, MESSAGE_1);
				checkEmpty(resource, placeholder, placeholder.getPrefix(), ECsProblemType.PLACEHOLDER_IN_QUOTES_WITH_EMPTY_PREFIX, MESSAGE_2);
			}
		}
	}

	private void checkEmpty(CsResource resource, PlaceholderInQuotes placeholder, String value, ECsProblemType problemType, String message) {
		if (value != null && "".equals(value)) {
			addProblem(resource, problemType, message, placeholder);
		}
	}
}