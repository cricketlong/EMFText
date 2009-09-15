package org.emftext.sdk.codegen.generators.ui;

import static org.emftext.sdk.codegen.generators.IClassNameConstants.STRING;
import static org.emftext.sdk.codegen.generators.IClassNameConstants.TOKEN;

import java.io.PrintWriter;

import org.emftext.sdk.codegen.EArtifact;
import org.emftext.sdk.codegen.GenerationContext;
import org.emftext.sdk.codegen.generators.BaseGenerator;

public class AntlrTokenHelperGenerator extends BaseGenerator {

	public AntlrTokenHelperGenerator(GenerationContext context) {
		super(context, EArtifact.ANTLR_TOKEN_HELPER);
	}

	public boolean generate(PrintWriter out) {
		org.emftext.sdk.codegen.composites.StringComposite sc = new org.emftext.sdk.codegen.composites.JavaComposite();
		sc.add("package " + getResourcePackageName() + ";");
		sc.addLineBreak();
		
		sc.add("// A helper class that decides which tokens can be used for custom syntax highlighting.");
		sc.add("public class " + getResourceClassName() + " {");
		sc.addLineBreak();
		
		sc.add("public boolean canBeUsedForSyntaxColoring(" + TOKEN + " token) {");
		sc.add("return canBeUsedForSyntaxColoring(token.getType());");
		sc.add("}");
		sc.addLineBreak();
		
		sc.add("public boolean canBeUsedForSyntaxColoring(int tokenType) {");
		sc.add("if (tokenType == " + TOKEN + ".EOF) {");
		sc.add("return false;");
		sc.add("}");
		sc.add("if (tokenType == " + TOKEN + ".UP) {");
		sc.add("return false;");
		sc.add("}");
		sc.add("if (tokenType == " + TOKEN + ".DOWN) {");
		sc.add("return false;");
		sc.add("}");
		sc.add("if (tokenType == " + TOKEN + ".EOR_TOKEN_TYPE) {");
		sc.add("return false;");
		sc.add("}");
		sc.add("if (tokenType == " + TOKEN + ".INVALID_TOKEN_TYPE) {");
		sc.add("return false;");
		sc.add("}");
		sc.add("return true;");
		sc.add("}");
		sc.addLineBreak();
		
		sc.add("public " + STRING + " getTokenName(" + STRING + "[] tokenNames, int index) {");
		sc.add("if (tokenNames == null) {");
		sc.add("return null;");
		sc.add("}");
		sc.add(STRING + " tokenName = tokenNames[index];");
		sc.add("if (tokenName != null && tokenName.startsWith(\"'\")) {");
		sc.add("tokenName = tokenName.substring(1, tokenName.length() - 1).trim();");
		sc.add("}");
		sc.add("return tokenName;");
		sc.add("}");
		sc.addLineBreak();
		
		sc.add("}");
		out.print(sc.toString());
		return true;
	}
}
