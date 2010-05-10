package org.emftext.sdk.codegen.newproject.creators;

import org.emftext.sdk.IPluginDescriptor;
import org.emftext.sdk.codegen.IArtifactCreator;
import org.emftext.sdk.codegen.creators.AbstractGenerationComponent;
import org.emftext.sdk.codegen.newproject.NewProjectGenerationContext;
import org.emftext.sdk.codegen.util.GenModelUtil;

public class GenerateCodeCreator extends AbstractGenerationComponent implements
		IArtifactCreator<NewProjectGenerationContext> {

	public void createArtifacts(IPluginDescriptor plugin, NewProjectGenerationContext context) {
		new GenModelUtil().generateMetaModelCode(context.getGenPackage(), context.getMonitor());
	}

	public String getArtifactDescription() {
		return "metamodel code";
	}
}