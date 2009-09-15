package org.emftext.sdk.codegen.generators.ui;

import static org.emftext.sdk.codegen.generators.IClassNameConstants.*;
import org.emftext.sdk.codegen.generators.BaseGenerator;
import org.emftext.sdk.codegen.EArtifact;
import org.emftext.sdk.codegen.GenerationContext;
import java.io.PrintWriter;

public class CodeFoldingManagerGenerator extends BaseGenerator {

	private String editorClassName;

	public CodeFoldingManagerGenerator(GenerationContext context) {
		super(context, EArtifact.CODE_FOLDING_MANAGER);
		this.editorClassName = context.getQualifiedClassName(EArtifact.EDITOR);
	}

	public boolean generate(PrintWriter out) {
		org.emftext.sdk.codegen.composites.StringComposite sc = new org.emftext.sdk.codegen.composites.JavaComposite();
		sc.add("package " + getResourcePackageName() + ";");
		sc.addLineBreak();
		sc.add("// This manager adds new projection annotations for the code folding and deletes");
		sc.add("// old projection annotations with lines < 3. It is needed to hold the toggle");
		sc.add("// states. It provides the ability to restore the toggle states between Eclipse");
		sc.add("// sessions and after closing, opening as well.");
		sc.add("public class " + getResourceClassName() + " {");
		sc.addLineBreak();
		
		addFields(sc);
		addConstructor(sc);
		
		sc.add("private class FoldingUpdateListener implements " + I_BACKGROUND_PARSING_LISTENER + " {");
		sc.add("public void parsingCompleted(" + RESOURCE + " resource) {");
		sc.add("calculatePositions();");
		sc.add("}");
		sc.add("}");
		sc.addLineBreak();
		sc.add("private class EditorOnCloseListener implements " + I_PART_LISTENER2 + " {");
		sc.addLineBreak();
		sc.add("private String uri;");
		sc.addLineBreak();
		sc.add("public EditorOnCloseListener(String uri) {");
		sc.add("this.uri = uri;");
		sc.add("}");
		sc.addLineBreak();
		sc.add("public void partActivated(" + I_WORKBENCH_PART_REFERENCE + " partRef) {");
		sc.add("}");
		sc.addLineBreak();
		sc.add("public void partBroughtToTop(" + I_WORKBENCH_PART_REFERENCE + " partRef) {");
		sc.add("}");
		sc.addLineBreak();
		sc.add("public void partClosed(" + I_WORKBENCH_PART_REFERENCE + " partRef) {");
		sc.add("if (partRef.isDirty()) {");
		sc.add("return;");
		sc.add("}");
		sc.add(I_WORKBENCH_PART + " workbenchPart = partRef.getPart(false);");
		sc.add("if (workbenchPart instanceof " + editorClassName + ") {");
		sc.add(editorClassName + " editor = (" + editorClassName + ") workbenchPart;");
		sc.add("String uri = editor.getResource().getURI().toString();");
		sc.add("if (uri.equals(this.uri)) {");
		sc.add("saveCodeFoldingStateFile(uri);");
		sc.add("editor.getSite().getPage().removePartListener(this);");
		sc.add("}");
		sc.add("}");
		sc.add("}");
		sc.addLineBreak();
		sc.add("public void partDeactivated(" + I_WORKBENCH_PART_REFERENCE + " partRef) {");
		sc.add("}");
		sc.addLineBreak();
		sc.add("public void partHidden(" + I_WORKBENCH_PART_REFERENCE + " partRef) {");
		sc.add("}");
		sc.addLineBreak();
		sc.add("public void partInputChanged(" + I_WORKBENCH_PART_REFERENCE + " partRef) {");
		sc.add("}");
		sc.addLineBreak();
		sc.add("public void partOpened(" + I_WORKBENCH_PART_REFERENCE + " partRef) {");
		sc.add("}");
		sc.addLineBreak();
		sc.add("public void partVisible(" + I_WORKBENCH_PART_REFERENCE + " partRef) {");
		sc.add("}");
		sc.addLineBreak();
		sc.add("}");
		sc.addLineBreak();
		sc.add("private void addCloseListener(final " + editorClassName + " emfTextEditor) {");
		sc.add("String uri = emfTextEditor.getResource().getURI().toString();");
		sc.add("emfTextEditor.getSite().getPage().addPartListener(new EditorOnCloseListener(uri));");
		sc.add("emfTextEditor.addBackgroundParsingListener(new FoldingUpdateListener());");
		sc.add("}");
		sc.addLineBreak();
		
		sc.add("// Checks whether it is in the <code>" + PROJECTION_ANNOTATION_MODEL + "</code> or in");
		sc.add("// the addition set. If not it tries to add into <code>additions</code>.");
		sc.add("// Deletes old " + PROJECTION_ANNOTATION + " with line count < 2.");
		sc.add("//");
		sc.add("// @param positions a list of available foldable positions");
		sc.add("public void updateCodefolding(" + LIST + "<" + POSITION + "> positions) {");
		sc.add(I_DOCUMENT + " document = sourceViewer.getDocument();");
		sc.add("if (document == null) {");
		sc.add("return;");
		sc.add("}");
		sc.add("oldAnnotations.clear();");
		sc.add(ITERATOR + "<?> annotationIterator = projectionAnnotationModel.getAnnotationIterator();");
		sc.add("while (annotationIterator.hasNext()) {");
		sc.add("oldAnnotations.add((" + PROJECTION_ANNOTATION + ") annotationIterator.next());");
		sc.add("}");
		sc.add("// Add new Position with a unique line offset");
		sc.add("for (" + POSITION + " position : positions) {");
		sc.add("if (!isInAdditions(position)) {");
		sc.add("addPosition(position);");
		sc.add("}");
		sc.add("}");
		sc.add("projectionAnnotationModel.modifyAnnotations(oldAnnotations.toArray(new " + ANNOTATION + "[0]), additions, null);");
		sc.add("additions.clear();");
		sc.add("}");
		sc.addLineBreak();
		
		sc.add("// Checks the offset of this <code>" + POSITION + "</code> with the");
		sc.add("// <code>" + POSITION + "</code>s in <code>additions</code> to determine the");
		sc.add("// existence.");
		sc.add("//");
		sc.add("// @param position the position to check");
		sc.add("//");
		sc.add("// @return <code>true</code> if it is in the <code>additions</code>");
		sc.add("private boolean isInAdditions(" + POSITION + " position) {");
		sc.add("for (" + ANNOTATION + " addition : additions.keySet()) {");
		sc.add(POSITION + " additionPosition = additions.get(addition);");
		sc.add("if (position.offset == additionPosition.offset && position.length == additionPosition.length) {");
		sc.add("return true;");
		sc.add("}");
		sc.add("}");
		sc.add("return false;");
		sc.add("}");
		sc.addLineBreak();
		
		sc.add("//");
		sc.add("// Tries to add this position into the model. Only positions with more than");
		sc.add("// 3 lines can be taken in. If more positions exist on the same line, the");
		sc.add("// longer one will be chosen. The shorter one in additions will be deleted.");
		sc.add("//");
		sc.add("// @param position the position to be added.");
		sc.add("private void addPosition(" + POSITION + " position) {");
		sc.add(I_DOCUMENT + " document = sourceViewer.getDocument();");
		sc.add("int lines = 0;");
		sc.add("try {");
		sc.add("lines = document.getNumberOfLines(position.offset, position.length);");
		sc.add("} catch (" + BAD_LOCATION_EXCEPTION + " e) {");
		sc.add("e.printStackTrace();");
		sc.add("return;");
		sc.add("}");
		sc.add("if (lines < 3) {");
		sc.add("return;");
		sc.add("}");
		sc.addLineBreak();
		sc.add("// if a position to add existed on the same line, the longer one will be");
		sc.add("// chosen");
		sc.add("try {");
		sc.add("for (" + PROJECTION_ANNOTATION + " annotationToAdd : additions.keySet()) {");
		sc.add(POSITION + " positionToAdd = additions.get(annotationToAdd);");
		sc.add("if (document.getLineOfOffset(position.offset) == document.getLineOfOffset(positionToAdd.offset)) {");
		sc.add("if (positionToAdd.length < position.length) {");
		sc.add("additions.remove(annotationToAdd);");
		sc.add("} else {");
		sc.add("return;");
		sc.add("}");
		sc.add("}");
		sc.add("}");
		sc.add("} catch (" + BAD_LOCATION_EXCEPTION + " e) {");
		sc.add("return;");
		sc.add("}");
		sc.add("for (" + PROJECTION_ANNOTATION + " annotationInModel : oldAnnotations) {");
		sc.add(POSITION + " positionInModel = projectionAnnotationModel.getPosition(annotationInModel);");
		sc.add("if (position.offset == positionInModel.offset && position.length == positionInModel.length) {");
		sc.add("oldAnnotations.remove(annotationInModel);");
		sc.add("return;");
		sc.add("}");
		sc.add("}");
		sc.addLineBreak();
		sc.add("additions.put(new " + PROJECTION_ANNOTATION + "(), position);");
		sc.add("}");
		sc.addLineBreak();
		sc.add("// Saves the code folding state into the given memento.");
		sc.add("public void saveCodeFolding(" + I_MEMENTO + " memento) {");
		sc.add(ITERATOR + "<?> annotationIt = projectionAnnotationModel.getAnnotationIterator();");
		sc.add("while (annotationIt.hasNext()) {");
		sc.add(PROJECTION_ANNOTATION + " annotation = (" + PROJECTION_ANNOTATION + ") annotationIt.next();");
		sc.add(I_MEMENTO + " annotationMemento = memento.createChild(ANNOTATION);");
		sc.add(POSITION + " position = projectionAnnotationModel.getPosition(annotation);");
		sc.add("annotationMemento.putBoolean(IS_COLLAPSED, annotation.isCollapsed());");
		sc.add("annotationMemento.putInteger(OFFSET, position.offset);");
		sc.add("annotationMemento.putInteger(LENGTH, position.length);");
		sc.add("}");
		sc.add("}");
		sc.addLineBreak();

		sc.add("// Restore the code folding state information from the given memento.");
		sc.add("public void restoreCodeFolding(" + I_MEMENTO + " memento) {");
		sc.add("if (memento == null) {");
		sc.add("return;");
		sc.add("}");
		sc.add(I_MEMENTO + "[] annotationMementos = memento.getChildren(ANNOTATION);");
		sc.add("if (annotationMementos == null) {");
		sc.add("return;");
		sc.add("}");
		sc.add(MAP + "<" + PROJECTION_ANNOTATION + ", Boolean> collapsedStates = new " + LINKED_HASH_MAP + "<" + PROJECTION_ANNOTATION + ", Boolean>();");
		sc.add("for (" + I_MEMENTO + " annotationMemento : annotationMementos) {");
		sc.add(PROJECTION_ANNOTATION + " annotation = new " + PROJECTION_ANNOTATION + "();");
		sc.add("collapsedStates.put(annotation, annotationMemento.getBoolean(IS_COLLAPSED));");
		sc.add("int offset = annotationMemento.getInteger(OFFSET);");
		sc.add("int length = annotationMemento.getInteger(LENGTH);");
		sc.add(POSITION + " position = new " + POSITION + "(offset, length);");
		sc.add("projectionAnnotationModel.addAnnotation(annotation, position);");
		sc.add("}");
		sc.add("// postset collapse state to prevent wrong displaying folding code.");
		sc.add("for (" + PROJECTION_ANNOTATION + " annotation : collapsedStates.keySet()) {");
		sc.add("if (collapsedStates.get(annotation)) {");
		sc.add("projectionAnnotationModel.collapse(annotation);");
		sc.add("}");
		sc.add("}");
		sc.add("}");
		sc.addLineBreak();
		
		sc.add("// Restores the code folding state from a XML file in the state location.");
		sc.add("//");
		sc.add("// @param uriString the key to determine the file name");
		sc.add("public void restoreCodeFoldingStateFromFile(String uriString) {");
		sc.add("final " + FILE + " stateFile = getCodeFoldingStateFile(uriString);");
		sc.add("if (stateFile == null || !stateFile.exists()) {");
		sc.add("calculatePositions();");
		sc.add("return;");
		sc.add("}");
		sc.add(SAFE_RUNNER + ".run(new " + SAFE_RUNNABLE + "(\"Unable to read code folding state. The state will be reset.\") {");
		sc.add("public void run() throws " + EXCEPTION + " {");
		sc.add(FILE_INPUT_STREAM + " input = new " + FILE_INPUT_STREAM + "(stateFile);");
		sc.add(BUFFERED_READER + " reader = new " + BUFFERED_READER + "(new " + INPUT_STREAM_READER + "(input, \"utf-8\"));");
		sc.add(I_MEMENTO + " memento = " + XML_MEMENTO + ".createReadRoot(reader);");
		sc.add("reader.close();");
		sc.add("String sourceText = sourceViewer.getDocument().get();");
		sc.add("if (memento.getString(VERIFY_KEY).equals(makeMD5(sourceText))) {");
		sc.add("restoreCodeFolding(memento);");
		sc.add("} else {");
		sc.add("calculatePositions();");
		sc.add("}");
		sc.add("}");
		sc.add("});");
		sc.add("}");
		sc.addLineBreak();
		
		sc.add("// Saves the code folding state to a XML file in the state location.");
		sc.add("//");
		sc.add("// @param uriString the key to determine the file name to save");
		sc.add("public void saveCodeFoldingStateFile(String uriString) {");
		sc.add(I_DOCUMENT + " document = sourceViewer.getDocument();");
		sc.add("if (document == null) {");
		sc.add("return;");
		sc.add("}");
		sc.add(XML_MEMENTO + " codeFoldingMemento = " + XML_MEMENTO + ".createWriteRoot(MODEL);");
		sc.add("codeFoldingMemento.putString(VERIFY_KEY, makeMD5(document.get()));");
		sc.add("saveCodeFolding(codeFoldingMemento);");
		sc.add(FILE + " stateFile = getCodeFoldingStateFile(uriString);");
		sc.add("if (stateFile == null) {");
		sc.add("return;");
		sc.add("}");
		sc.add("try {");
		sc.add(FILE_OUTPUT_STREAM + " stream = new " + FILE_OUTPUT_STREAM + "(stateFile);");
		sc.add(OUTPUT_STREAM_WRITER + " writer = new " + OUTPUT_STREAM_WRITER + "(stream, \"utf-8\");");
		sc.add("codeFoldingMemento.save(writer);");
		sc.add("writer.close();");
		sc.add("} catch (" + IO_EXCEPTION + " e) {");
		sc.add("stateFile.delete();");
		sc.add(MESSAGE_DIALOG + ".openError((" + SHELL + ") null, \"Saving Problems\", \"Unable to save code folding state.\");");
		sc.add("}");
		sc.add("}");
		sc.addLineBreak();
		
		sc.add("private " + FILE + " getCodeFoldingStateFile(String uriString) {");
		sc.add(BUNDLE + " bundle = " + PLATFORM + ".getBundle(PLUGIN_ID);");
		sc.add(I_PATH + " path = " + PLATFORM + ".getStateLocation(bundle);");
		sc.add("if (path == null) {");
		sc.add("return null;");
		sc.add("}");
		sc.add("path = path.append(makeMD5(uriString) + \".xml\");");
		sc.add("return path.toFile();");
		sc.add("}");
		sc.addLineBreak();
		
		sc.add("private String makeMD5(String text) {");
		sc.add(MESSAGE_DIGEST + " md = null;");
		sc.add("byte[] encryptMsg = null;");
		sc.add("try {");
		sc.add("md = " + MESSAGE_DIGEST + ".getInstance(\"MD5\");");
		sc.add("encryptMsg = md.digest(text.getBytes());");
		sc.add("} catch (" + NO_SUCH_ALGORITHM_EXCEPTION + " e) {");
		sc.add("// TODO write this to the error log");
		sc.add("System.out.println(\"No Such Algorithm " + EXCEPTION + "!\");");
		sc.add("}");
		sc.add("String swap = \"\";");
		sc.add("String byteStr = \"\";");
		sc.add("StringBuffer strBuf = new StringBuffer();");
		sc.add("for (int i = 0; i <= encryptMsg.length - 1; i++) {");
		sc.add("byteStr = Integer.toHexString(encryptMsg[i]);");
		sc.add("switch (byteStr.length()) {");
		sc.add("case 1:");
		sc.add("// if hex-number length is 1, add a '0' before");
		sc.add("swap = \"0\" + Integer.toHexString(encryptMsg[i]);");
		sc.add("break;");
		sc.add("case 2:");
		sc.add("// correct hex-letter");
		sc.add("swap = Integer.toHexString(encryptMsg[i]);");
		sc.add("break;");
		sc.add("case 8:");
		sc.add("// get the correct substring");
		sc.add("swap = (Integer.toHexString(encryptMsg[i])).substring(6, 8);");
		sc.add("break;");
		sc.add("}");
		sc.add("strBuf.append(swap);");
		sc.add("// appending swap to get complete hash-key");
		sc.add("}");
		sc.add("return strBuf.toString();");
		sc.add("}");
		sc.addLineBreak();
		
		sc.add("protected void calculatePositions() {");
		sc.add(I_TEXT_RESOURCE + " textResource = (" + I_TEXT_RESOURCE + ") editor.getResource();");
		sc.add(I_DOCUMENT + " document = sourceViewer.getDocument();");
		sc.add("if (textResource == null || document == null) {");
		sc.add("return;");
		sc.add("}");
		sc.add(E_LIST + "<?> errorList = textResource.getErrors();");
		sc.add("if (errorList != null && errorList.size() > 0) {");
		sc.add("return;");
		sc.add("}");
		sc.add("final " + LIST + "<" + POSITION + "> positions = new " + ARRAY_LIST + "<" + POSITION + ">();");
		sc.add(I_LOCATION_MAP + " locationMap = textResource.getLocationMap();");
		sc.add(E_CLASS + "[] foldableClasses = textResource.getMetaInformation().getFoldableClasses();");
		sc.add("if (foldableClasses == null) {");
		sc.add("return;");
		sc.add("}");
		sc.add("if (foldableClasses.length < 1) {");
		sc.add("return;");
		sc.add("}");
		sc.add(LIST + "<" + E_OBJECT + "> contents = textResource.getContents();");
		sc.add(E_OBJECT + "[] contentArray = contents.toArray(new " + E_OBJECT + "[0]);");
		sc.add(LIST + "<" + E_OBJECT + "> allContents = getAllContents(contentArray);");
		sc.add("for (" + E_OBJECT + " nextObject : allContents) {");
		sc.add("boolean isFoldable = false;");
		sc.add("for (" + E_CLASS + " eClass : foldableClasses) {");
		sc.add("if (nextObject.eClass().equals(eClass)) {");
		sc.add("isFoldable = true;");
		sc.add("break;");
		sc.add("}");
		sc.add("}");
		sc.add("if (!isFoldable) {");
		sc.add("continue;");
		sc.add("}");
		sc.add("int offset = locationMap.getCharStart(nextObject);");
		sc.add("int length = locationMap.getCharEnd(nextObject) + 1 - offset;");
		sc.add("try {");
		sc.add("int lines = document.getNumberOfLines(offset, length);");
		sc.add("if (lines < 2) {");
		sc.add("continue;");
		sc.add("}");
		sc.add("} catch (" + BAD_LOCATION_EXCEPTION + " e) {");
		sc.add("continue;");
		sc.add("}");
		sc.add("length = getOffsetOfNextLine(document, length + offset) - offset;");
		sc.add("if (offset >= 0 && length > 0) {");
		sc.add("positions.add(new " + POSITION + "(offset, length));");
		sc.add("}");
		sc.add("}");
		sc.add(DISPLAY + ".getDefault().asyncExec(new " + RUNNABLE + "() {");
		sc.add("public void run() {");
		sc.add("updateCodefolding(positions);");
		sc.add("}");
		sc.add("});");
		sc.add("}");
		sc.addLineBreak();
		
		sc.add("private " + LIST + "<" + E_OBJECT + "> getAllContents(" + E_OBJECT + "[] contentArray) {");
		sc.add(LIST + "<" + E_OBJECT + "> result = new " + ARRAY_LIST + "<" + E_OBJECT + ">();");
		sc.add("for (" + E_OBJECT + " eObject : contentArray) {");
		sc.add("result.add(eObject);");
		sc.add("result.addAll(getAllContents(eObject.eContents().toArray(new " + E_OBJECT + "[0])));");
		sc.add("}");
		sc.add("return result;");
		sc.add("}");
		sc.addLineBreak();
		
		sc.add("private int getOffsetOfNextLine(" + I_DOCUMENT + " document, int offset) {");
		sc.add("int end = document.getLength();");
		sc.add("int nextLineOffset = offset;");
		sc.add("if (offset < 0 || offset > end) {");
		sc.add("return -1;");
		sc.add("}");
		sc.add("while (nextLineOffset < end) {");
		sc.add("String charAtOffset = \"\";");
		sc.add("try {");
		sc.add("charAtOffset += document.getChar(nextLineOffset);");
		sc.add("} catch (" + BAD_LOCATION_EXCEPTION + " e) {");
		sc.add("return -1;");
		sc.add("}");
		sc.add("if (charAtOffset.matches(\"\\\\S\")) {");
		sc.add("return nextLineOffset;");
		sc.add("}");
		sc.add("if (charAtOffset.equals(\"\\n\")) {");
		sc.add("return nextLineOffset + 1;");
		sc.add("}");
		sc.add("nextLineOffset++;");
		sc.add("}");
		sc.add("return offset;");
		sc.add("}");
		sc.addLineBreak();
		sc.add("}");
		out.print(sc.toString());
		return true;
	}

	private void addFields(org.emftext.sdk.codegen.composites.StringComposite sc) {
		sc.add("// TODO replace this with the id of the generated plug-in");
		sc.add("private static final String PLUGIN_ID = \"org.emftext.runtime.ui\";");
		sc.add("private static final String VERIFY_KEY = \"verify_key\";");
		sc.add("private static final String ANNOTATION = \"ANNOTATION\";");
		sc.add("private static final String IS_COLLAPSED = \"IS_COLLAPED\";");
		sc.add("private static final String OFFSET = \"OFFSET\";");
		sc.add("private static final String LENGTH = \"LENGTH\";");
		sc.add("private static final String MODEL = \"MODEL\";");
		sc.add("protected " + LIST + "<" + PROJECTION_ANNOTATION + "> oldAnnotations = new " + ARRAY_LIST + "<" + PROJECTION_ANNOTATION + ">();");
		sc.add("protected " + MAP + "<" + PROJECTION_ANNOTATION + ", " + POSITION + "> additions = new " + HASH_MAP + "<" + PROJECTION_ANNOTATION + ", " + POSITION + ">();");
		sc.add("protected " + PROJECTION_ANNOTATION_MODEL + " projectionAnnotationModel;");
		sc.add("protected " + PROJECTION_VIEWER + " sourceViewer;");
		sc.add("protected " + editorClassName + " editor;");
		sc.addLineBreak();
	}

	private void addConstructor(
			org.emftext.sdk.codegen.composites.StringComposite sc) {
		sc.add("// Creates a code folding manager to handle the");
		sc.add("// <code>" + PROJECTION_ANNOTATION + "</code>.");
		sc.add("//");
		sc.add("// @param sourceViewer");
		sc.add("//            the source viewer to calculate the element lines");
		sc.add("public " + getResourceClassName() + "(" + PROJECTION_VIEWER + " sourceViewer," + editorClassName + " textEditor) {");
		sc.add("this.projectionAnnotationModel = sourceViewer.getProjectionAnnotationModel();");
		sc.add("this.sourceViewer = sourceViewer;");
		sc.add("this.editor = textEditor;");
		sc.add("addCloseListener(textEditor);");
		sc.add("try {");
		sc.add("restoreCodeFoldingStateFromFile(editor.getResource().getURI().toString());");
		sc.add("} catch (" + EXCEPTION + " e) {");
		sc.add("calculatePositions();");
		sc.add("}");
		sc.add("}");
		sc.addLineBreak();
	}
}
