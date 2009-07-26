package org.emftext.sdk.codegen.generators;

import static org.emftext.sdk.codegen.generators.IClassNameConstants.*;
import org.emftext.sdk.codegen.generators.BaseGenerator;
import org.emftext.sdk.codegen.EArtifact;
import org.emftext.sdk.codegen.GenerationContext;
import java.io.PrintWriter;

public class URIMappingGenerator extends BaseGenerator {

	public URIMappingGenerator(GenerationContext context) {
		super(context.getPackageName(), context.getClassName(EArtifact.URI_MAPPING));
	}

	public boolean generate(PrintWriter out) {
		org.emftext.sdk.codegen.composites.StringComposite sc = new org.emftext.sdk.codegen.composites.JavaComposite();
		sc.add("package " + getResourcePackageName() + ";");
		sc.addLineBreak();
		sc.add("// A basic implementation of the IURIMapping interface that can");
		sc.add("// map identifiers to URIs.");
		sc.add("//");
		sc.add("// @param <ReferenceType> unused type parameter which is needed to implement IURIMapping.");
		sc.add("//");
		sc.add("public class " + getResourceClassName() + "<ReferenceType> extends " + ABSTRACT_REFERENCE_MAPPING + "<ReferenceType> implements " + I_URI_MAPPING + "<ReferenceType> {");
		sc.addLineBreak();
		sc.add("private " + URI + " uri;");
		sc.addLineBreak();
		sc.add("public " + getResourceClassName() + "(String identifier, " + URI + " newIdentifier, String warning) {");
		sc.add("super(identifier, warning);");
		sc.add("this.uri = newIdentifier;");
		sc.add("}");
		sc.addLineBreak();
		sc.add("public " + URI + " getTargetIdentifier() {");
		sc.add("return uri;");
		sc.add("}");
		sc.add("}");
		out.print(sc.toString());
		return true;
	}
}
