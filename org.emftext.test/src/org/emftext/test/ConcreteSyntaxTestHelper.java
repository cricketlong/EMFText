/*******************************************************************************
 * Copyright (c) 2006-2009 
 * Software Technology Group, Dresden University of Technology
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version. This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * 
 * See the GNU Lesser General Public License for more details. You should have
 * received a copy of the GNU Lesser General Public License along with this
 * program; if not, write to the Free Software Foundation, Inc., 59 Temple Place,
 * Suite 330, Boston, MA  02111-1307 USA
 * 
 * Contributors:
 *   Software Technology Group - TU Dresden, Germany 
 *   - initial API and implementation
 ******************************************************************************/
package org.emftext.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.emftext.runtime.resource.ITextResource;
import org.emftext.runtime.util.ResourceUtil;
import org.emftext.sdk.SDKOptionProvider;
import org.emftext.sdk.codegen.GenerationContext;
import org.emftext.sdk.codegen.GenerationProblem;
import org.emftext.sdk.codegen.IProblemCollector;
import org.emftext.sdk.codegen.generators.ANTLRGrammarGenerator;
import org.emftext.sdk.concretesyntax.ConcreteSyntax;

/**
 * A helper class for all EMFText tests.
 */
public class ConcreteSyntaxTestHelper {

	public static void registerResourceFactories() {
		org.emftext.sdk.concretesyntax.resource.cs.CsResourceFactory csResourceFactoryImpl = new org.emftext.sdk.concretesyntax.resource.cs.CsResourceFactory();
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(
				"ecore",
				new org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl());
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(
				"genmodel",
				new org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl());
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("cs",
				csResourceFactoryImpl);
	}

	public static ConcreteSyntax getConcreteSyntax(Resource csResource) {
		ConcreteSyntax concreteSyntax = (ConcreteSyntax) csResource
				.getContents().get(0);
		return concreteSyntax;
	}

	public static Resource getConcreteSyntaxResource(URI fileURI) {
		ResourceSet rs = new ResourceSetImpl();
		rs.getLoadOptions().putAll(new SDKOptionProvider().getOptions());
		ITextResource resource = (ITextResource) rs.getResource(fileURI, true);
		final List<EObject> proxies = ResourceUtil.findUnresolvedProxies(resource);
		for (EObject proxy : proxies) {
			System.out.println("getConcreteSyntaxResource() unresolved proxy : " + proxy);
		}
		final EList<Diagnostic> errors = resource.getErrors();
		for (Diagnostic error : errors) {
			System.out.println("getConcreteSyntaxResource() error : " + error.getMessage());
		}
		assertTrue("The CS file should not contain errors.", errors.isEmpty());
		return resource;
	}

	public static ANTLRGrammarGenerator createANTLRGenerator(ConcreteSyntax concreteSyntax) {
		IProblemCollector collector = new IProblemCollector() {
			public void addProblem(GenerationProblem problem) {
				fail();
			}
		};
		GenerationContext context = new TestGenerationContext(concreteSyntax, collector);
		ANTLRGrammarGenerator antlrGenerator = (ANTLRGrammarGenerator) new ANTLRGrammarGenerator().newInstance(context);
		return antlrGenerator;
	}

	public static File generateANTLRGrammarToTempFile(URI fileURI) throws IOException,
			FileNotFoundException {
		ConcreteSyntax concreteSyntax = getConcreteSyntax(getConcreteSyntaxResource(fileURI));
		assertNotNull("The concrete syntax should be successfully loaded.",
				concreteSyntax);
		
		ANTLRGrammarGenerator antlrGenerator = createANTLRGenerator(concreteSyntax);

		File tempGrammarFile = File.createTempFile(
				ConcreteSyntaxTestHelper.class.getSimpleName(), ".g");
		tempGrammarFile.deleteOnExit();
		boolean success = antlrGenerator.generate(new PrintWriter(
				new FileOutputStream(tempGrammarFile)));
		Collection<GenerationProblem> problems = antlrGenerator.getCollectedProblems();
		for (GenerationProblem problem : problems) {
			System.out.println("generateANTLRGrammarToTempFile() " + problem.getMessage());
		}
		assertTrue(success);
		return tempGrammarFile;
	}
	
	
	public static Collection<String> findAllGrammars(File directory) {
		Collection<String> grammarPaths = new LinkedHashSet<String>();
		File[] subDirs = directory.listFiles(new FileFilter() {

			public boolean accept(File file) {
				return file.isDirectory() && !file.getName().startsWith(".");
			}
			
		});
		File[] grammarFiles = directory.listFiles(new FileFilter() {

			public boolean accept(File file) {
				return !file.isDirectory() && file.getName().endsWith(".cs");
			}
			
		});
		for (File file : grammarFiles) {
			grammarPaths.add(file.getAbsolutePath());
		}
		for (File subDir : subDirs) {
			grammarPaths.addAll(findAllGrammars(subDir));
		}
		return grammarPaths;
	}
}
