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
package org.emftext.sdk.codegen.creators;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.emftext.sdk.EMFTextSDKPlugin;
import org.emftext.sdk.IPluginDescriptor;
import org.emftext.sdk.codegen.GenerationProblem;
import org.emftext.sdk.codegen.IArtifactCreator;
import org.emftext.sdk.util.StreamUtil;

/**
 * Copies files.
 */
public class FileCopier<ContextType> extends AbstractGenerationComponent implements IArtifactCreator<ContextType> {

	private InputStream inputStream;
	private File targetFile;

	public FileCopier(InputStream inputStream, File targetFile) {
		this.inputStream = inputStream;
		this.targetFile = targetFile;
	}

	public void createArtifacts(IPluginDescriptor plugin, ContextType context) {
		try {
			StreamUtil.storeContentIfChanged(targetFile, inputStream);
		} catch (IOException e) {
			addError(context, e);
		} catch (NullPointerException e) {
			addError(context, e);
		}
	}

	private void addError(ContextType context, Exception e) {
		getProblemCollector().addProblem(new GenerationProblem("Exception while copying " + targetFile.getName() + ".", null, GenerationProblem.Severity.ERROR, e));
		EMFTextSDKPlugin.logError("Error while copying " + targetFile.getName() + ".", e);
	}

	public String getArtifactDescription() {
		return targetFile.getName();
	}
}
