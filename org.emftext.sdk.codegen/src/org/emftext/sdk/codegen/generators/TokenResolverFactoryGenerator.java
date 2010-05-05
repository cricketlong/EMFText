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
package org.emftext.sdk.codegen.generators;

import static org.emftext.sdk.codegen.generators.IClassNameConstants.LINKED_HASH_MAP;
import static org.emftext.sdk.codegen.generators.IClassNameConstants.MAP;
import static org.emftext.sdk.codegen.generators.IClassNameConstants.STRING;

import org.emftext.sdk.codegen.TextResourceArtifacts;
import org.emftext.sdk.codegen.GenerationContext;
import org.emftext.sdk.codegen.IGenerator;
import org.emftext.sdk.codegen.NameUtil;
import org.emftext.sdk.codegen.composites.JavaComposite;
import org.emftext.sdk.codegen.composites.StringComposite;
import org.emftext.sdk.concretesyntax.CompleteTokenDefinition;
import org.emftext.sdk.concretesyntax.ConcreteSyntax;

/**
 * Generates a TokenResolverFactory which will contain a mapping from 
 * ANTLR token names to TokenResolver implementations. It will inherit 
 * from AbstractTokenResolverFactory to allow adaptation to API changes.
 * 
 * @see org.emftext.runtime.resource.impl.AbstractTokenResolverFactory
 * 
 * @author Sven Karol (Sven.Karol@tu-dresden.de)
 */
public class TokenResolverFactoryGenerator extends JavaBaseGenerator {
	
	private final NameUtil nameUtil = new NameUtil();
	
	public TokenResolverFactoryGenerator() {
		super();
	}

	private TokenResolverFactoryGenerator(GenerationContext context) {
		super(context, TextResourceArtifacts.TOKEN_RESOLVER_FACTORY);
	}
	
	@Override
	public boolean generateJavaContents(JavaComposite sc) {
		
		sc.add("package " + getResourcePackageName() + ";");
		sc.addLineBreak();
		sc.add("public class " + getResourceClassName() + " implements " + iTokenResolverFactoryClassName + " {");
		sc.addLineBreak();
		
		addFields(sc);
		addConstructor(sc);
		addMethods(sc);
		
		sc.add("}");
		return true;
	}

	private void addMethods(StringComposite sc) {
		addCreateTokenResolverMethod(sc);
		addCreateCollectInTokenResolverMethod(sc);
		addRegisterTokenResolverMethod(sc);
		addRegisterCollectInTokenResolverMethod(sc);
		addDeRegisterTokenResolverMethod(sc);
		addInternalCreateResolverMethod(sc);
		addInternalRegisterTokenResolverMethod(sc);
	}

	private void addInternalRegisterTokenResolverMethod(StringComposite sc) {
		sc.add("private boolean internalRegisterTokenResolver(" + MAP + "<" + STRING + ", " + iTokenResolverClassName + "> resolverMap, " + STRING + " key, " + iTokenResolverClassName + " resolver) {");
		sc.add("if (!resolverMap.containsKey(key)) {");
		sc.add("resolverMap.put(key,resolver);");
		sc.add("return true;");
		sc.add("}");
		sc.add("return false;");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addInternalCreateResolverMethod(StringComposite sc) {
		sc.add("private " + iTokenResolverClassName + " internalCreateResolver(" + MAP + "<" + STRING + ", " + iTokenResolverClassName + "> resolverMap, String key) {");
		sc.add("if (resolverMap.containsKey(key)){");
		sc.add("return resolverMap.get(key);");
		sc.add("} else {");
		sc.add("return defaultResolver;");
		sc.add("}");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addDeRegisterTokenResolverMethod(StringComposite sc) {
		sc.add("protected " + iTokenResolverClassName + " deRegisterTokenResolver(" + STRING + " tokenName){");
		sc.add("return tokenName2TokenResolver.remove(tokenName);");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addRegisterCollectInTokenResolverMethod(StringComposite sc) {
		sc.add("protected boolean registerCollectInTokenResolver(" + STRING + " featureName, " + iTokenResolverClassName + " resolver){");
		sc.add("return internalRegisterTokenResolver(featureName2CollectInTokenResolver, featureName, resolver);");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addRegisterTokenResolverMethod(StringComposite sc) {
		sc.add("protected boolean registerTokenResolver(" + STRING + " tokenName, " + iTokenResolverClassName + " resolver){");
		sc.add("return internalRegisterTokenResolver(tokenName2TokenResolver, tokenName, resolver);");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addCreateCollectInTokenResolverMethod(StringComposite sc) {
		sc.add("public " + iTokenResolverClassName + " createCollectInTokenResolver(" + STRING + " featureName) {");
		sc.add("return internalCreateResolver(featureName2CollectInTokenResolver, featureName);");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addCreateTokenResolverMethod(StringComposite sc) {
		sc.add("public " + iTokenResolverClassName + " createTokenResolver(" + STRING + " tokenName) {");
		sc.add("return internalCreateResolver(tokenName2TokenResolver, tokenName);");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addFields(StringComposite sc) {
		sc.add("private " + MAP + "<" + STRING + ", " + iTokenResolverClassName + "> tokenName2TokenResolver;");
		sc.add("private " + MAP + "<" + STRING + ", " + iTokenResolverClassName + "> featureName2CollectInTokenResolver;");
		sc.add("private static " + iTokenResolverClassName + " defaultResolver = new " + defaultTokenResolverClassName + "();");
		sc.addLineBreak();
	}

	private void addConstructor(StringComposite sc) {
		sc.add("public " + getResourceClassName() + "() {");
		sc.add("tokenName2TokenResolver = new " + LINKED_HASH_MAP + "<" + STRING + ", " + iTokenResolverClassName + ">();");
		sc.add("featureName2CollectInTokenResolver = new " + LINKED_HASH_MAP + "<" + STRING + ", " + iTokenResolverClassName + ">();");
		ConcreteSyntax concreteSyntax = getContext().getConcreteSyntax();
		for (CompleteTokenDefinition definition : concreteSyntax.getActiveTokens()) {
			if (!definition.isUsed()) {
				continue;
			}
			// user defined tokens may stem from an imported syntax
			String tokenResolverClassName = nameUtil.getQualifiedTokenResolverClassName(concreteSyntax, definition, false);
			if (definition.getAttributeName() != null) {
				String featureName = definition.getAttributeName();
				sc.add("registerCollectInTokenResolver(\"" + featureName + "\", new " + tokenResolverClassName + "());");
			} else {
				sc.add("registerTokenResolver(\"" +definition.getName()+ "\", new " + tokenResolverClassName + "());");
			}
		}
		sc.add("}");
		sc.addLineBreak();
	}

	public IGenerator<GenerationContext> newInstance(GenerationContext context) {
		return new TokenResolverFactoryGenerator(context);
	}
}
