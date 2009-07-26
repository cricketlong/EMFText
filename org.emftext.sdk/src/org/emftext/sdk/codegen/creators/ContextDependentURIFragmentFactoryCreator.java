package org.emftext.sdk.codegen.creators;

import java.io.File;
import java.util.Collection;

import org.emftext.sdk.codegen.EArtifact;
import org.emftext.sdk.codegen.GenerationContext;
import org.emftext.sdk.codegen.IGenerator;
import org.emftext.sdk.codegen.generators.ContextDependentURIFragmentFactoryGenerator;
import org.emftext.sdk.concretesyntax.OptionTypes;

public class ContextDependentURIFragmentFactoryCreator extends
		AbstractArtifactCreator {

	private static final String NAME = "ContextDependentURIFragmentFactory";

	public ContextDependentURIFragmentFactoryCreator() {
		super(NAME);
	}

	@Override
	public Collection<IArtifact> getArtifactsToCreate(GenerationContext context) {

		File file = context.getFile(EArtifact.CONTEXT_DEPENDENT_URI_FRAGMENT_FACTORY);
		IGenerator generator = new ContextDependentURIFragmentFactoryGenerator(context);

		return createArtifact(context, generator, file,
				"Exception while generating " + NAME + ".");
	}

	public OptionTypes getOverrideOption() {
		return OptionTypes.OVERRIDE_CONTEXT_DEPENDENT_URI_FRAGMENT_FACTORY;
	}
}
