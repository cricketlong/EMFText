package org.emftext.sdk.codegen.generators.ui;

import static org.emftext.sdk.codegen.generators.IClassNameConstants.*;
import org.emftext.sdk.codegen.generators.BaseGenerator;
import org.emftext.sdk.codegen.GenerationContext;
import java.io.PrintWriter;
import org.emftext.sdk.codegen.EArtifact;

public class PositionHelperGenerator extends BaseGenerator {

	public PositionHelperGenerator(GenerationContext context) {
		super(context, EArtifact.POSITION_HELPER);
	}

	public boolean generate(PrintWriter out) {
		org.emftext.sdk.codegen.composites.StringComposite sc = new org.emftext.sdk.codegen.composites.JavaComposite();
		sc.add("package " + getResourcePackageName() + ";");
		sc.addLineBreak();
		sc.add("// A helper class to add, get or remove positions with a specific category in a document.");
		sc.add("public class " + getResourceClassName() + " {");
		sc.addLineBreak();

		addMethods(sc);
		
		sc.add("}");
		out.print(sc.toString());
		return true;
	}

	private void addMethods(
			org.emftext.sdk.codegen.composites.StringComposite sc) {
		addAddPositionMethod(sc);
		addGetPositionsMethod(sc);
		addGetFirstPositionMethod(sc);
		addRemovePositionsMethod(sc);
	}

	private void addRemovePositionsMethod(
			org.emftext.sdk.codegen.composites.StringComposite sc) {
		sc.add("// Deletes the position category from the document.");
		sc.add("// All positions in this category are thus deleted as well.");
		sc.add("//");
		sc.add("// @param document the document contains the category");
		sc.add("// @param category the category to be removed");
		sc.add("public void removePositions(" + I_DOCUMENT + " document, String category) {");
		sc.add("try {");
		sc.add("document.removePositionCategory(category);");
		sc.add("} catch (" + BAD_POSITION_CATEGORY_EXCEPTION + " e) {");
		sc.add("//e.printStackTrace();");
		sc.add("}");
		sc.add("}");
	}

	private void addGetFirstPositionMethod(
			org.emftext.sdk.codegen.composites.StringComposite sc) {
		sc.add("// Gets the first position of a specific category of the given document.");
		sc.add("//");
		sc.add("// @param document the document to get the positions from");
		sc.add("// @param category the category of the position");
		sc.add("//");
		sc.add("// @return a position. If there is none return <code>null</code>.");
		sc.add("public " + POSITION + " getFirstPosition(" + I_DOCUMENT + " document, String category) {");
		sc.add("try {");
		sc.add(POSITION + "[] positions = document.getPositions(category);");
		sc.add("if (positions.length > 0) {");
		sc.add("return positions[0];");
		sc.add("}");
		sc.add("} catch (" + BAD_POSITION_CATEGORY_EXCEPTION + " e) {");
		sc.add("//e.printStackTrace();");
		sc.add("}");
		sc.add("return null;");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addGetPositionsMethod(
			org.emftext.sdk.codegen.composites.StringComposite sc) {
		sc.add("// Gets the positions of a specific category of the given document.");
		sc.add("//");
		sc.add("// @param document the document to get the positions from");
		sc.add("// @param category the position's category");
		sc.add("// @return an array of positions. If there is none return an array with the length = 0");
		sc.add("public " + POSITION + "[] getPositions(" + I_DOCUMENT + " document, String category) {");
		sc.add("try {");
		sc.add("return document.getPositions(category);");
		sc.add("} catch (" + BAD_POSITION_CATEGORY_EXCEPTION + " e) {");
		sc.add("//e.printStackTrace();");
		sc.add("}");
		sc.add("return new " + POSITION + "[0];");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addAddPositionMethod(
			org.emftext.sdk.codegen.composites.StringComposite sc) {
		sc.add("// Adds a position with the given offset and length into a document.");
		sc.add("//");
		sc.add("// @param document the document to add a position into");
		sc.add("// @param category the category of this position");
		sc.add("// @param offset the offset of the position");
		sc.add("// @param length the length of the position");
		sc.add("public void addPosition(" + I_DOCUMENT + " document, String category, int offset, int length) {");
		sc.add("try {");
		sc.add("document.addPositionCategory(category);");
		sc.add(POSITION + " position = new " + POSITION + "(offset, length);");
		sc.add("document.addPosition(category, position);");
		sc.add("} catch (" + BAD_LOCATION_EXCEPTION + " e) {");
		sc.add("//e.printStackTrace();");
		sc.add("} catch (" + BAD_POSITION_CATEGORY_EXCEPTION + " e) {");
		sc.add("//e.printStackTrace();");
		sc.add("}");
		sc.add("}");
		sc.addLineBreak();
	}
}
