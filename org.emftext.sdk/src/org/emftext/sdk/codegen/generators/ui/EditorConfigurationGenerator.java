package org.emftext.sdk.codegen.generators.ui;

import static org.emftext.sdk.codegen.generators.IClassNameConstants.*;
import org.emftext.sdk.codegen.generators.BaseGenerator;
import org.emftext.sdk.codegen.GenerationContext;
import org.emftext.sdk.codegen.IGenerator;

import java.io.PrintWriter;
import org.emftext.sdk.codegen.EArtifact;

public class EditorConfigurationGenerator extends BaseGenerator {

	private String editorClassName;
	private String colorManagerClassName;
	private String completionProcessorClassName;
	private String textTokenScannerClassName;
	private String textHoverClassName;
	private String hyperlinkDetectorClassName;

	public EditorConfigurationGenerator() {
		super();
	}

	private EditorConfigurationGenerator(GenerationContext context) {
		super(context, EArtifact.EDITOR_CONFIGURATION);
		editorClassName = getContext().getQualifiedClassName(EArtifact.EDITOR);
		colorManagerClassName = getContext().getQualifiedClassName(EArtifact.COLOR_MANAGER);
		completionProcessorClassName = getContext().getQualifiedClassName(EArtifact.COMPLETION_PROCESSOR);
		textTokenScannerClassName = getContext().getQualifiedClassName(EArtifact.TOKEN_SCANNER);
		textHoverClassName = getContext().getQualifiedClassName(EArtifact.TEXT_HOVER);
		hyperlinkDetectorClassName = getContext().getQualifiedClassName(EArtifact.HYPERLINK_DETECTOR);
	}

	public boolean generate(PrintWriter out) {
		org.emftext.sdk.codegen.composites.StringComposite sc = new org.emftext.sdk.codegen.composites.JavaComposite();
		sc.add("package " + getResourcePackageName() + ";");
		sc.addLineBreak();
		sc.add("// This class provides the configuration for all EMFText editors. It registers");
		sc.add("// content assistance and syntax highlighting.");
		sc.add("public class " + getResourceClassName() + " extends " + SOURCE_VIEWER_CONFIGURATION + " {");
		sc.addLineBreak();
		addFields(sc);
		addConstructor(sc);
		addMethods(sc);
		
		sc.add("}");
		out.print(sc.toString());
		return true;
	}

	private void addMethods(
			org.emftext.sdk.codegen.composites.StringComposite sc) {
		addGetContentAssistantMethod(sc);
		addGetConfiguredContentTypesMethod(sc);
		addGetScannerMethod(sc);
		addGetPresentationReconcilerMethod(sc);
		addGetAnnotationHoverMethod(sc);
		addGetTextHoverMethod(sc);
		addGetHyperlinkDetectorsMethod(sc);
	}

	private void addGetHyperlinkDetectorsMethod(
			org.emftext.sdk.codegen.composites.StringComposite sc) {
		sc.add("public " + I_HYPERLINK_DETECTOR + "[] getHyperlinkDetectors(" + I_SOURCE_VIEWER + " sourceViewer) {");
		sc.add("if (sourceViewer == null) {");
		sc.add("return null;");
		sc.add("}");
		sc.add("return new " + I_HYPERLINK_DETECTOR + "[] { new " + hyperlinkDetectorClassName + "(theEditor.getResource()) };");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addGetTextHoverMethod(
			org.emftext.sdk.codegen.composites.StringComposite sc) {
		sc.add("public " + I_TEXT_HOVER + " getTextHover(" + I_SOURCE_VIEWER + " sourceViewer, String contentType) {");
		sc.add("return new " + textHoverClassName + "(theEditor);");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addGetAnnotationHoverMethod(
			org.emftext.sdk.codegen.composites.StringComposite sc) {
		sc.add("public " + I_ANNOTATION_HOVER + " getAnnotationHover(" + I_SOURCE_VIEWER + " sourceViewer) {");
		sc.add("return new " + DEFAULT_ANNOTATION_HOVER + "();");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addGetPresentationReconcilerMethod(
			org.emftext.sdk.codegen.composites.StringComposite sc) {
		sc.add("public " + I_PRESENTATION_RECONCILER + " getPresentationReconciler(" + I_SOURCE_VIEWER + " sourceViewer) {");
		sc.addLineBreak();
		sc.add(PRESENTATION_RECONCILER + " reconciler = new " + PRESENTATION_RECONCILER + "();");
		sc.add("String fileName = theEditor.getEditorInput().getName();");
		sc.addLineBreak();
		sc.add(DEFAULT_DAMAGER_REPAIRER + " repairer = new " + DEFAULT_DAMAGER_REPAIRER + "(getScanner(fileName));");
		sc.add("reconciler.setDamager(repairer, " + I_DOCUMENT + ".DEFAULT_CONTENT_TYPE);");
		sc.add("reconciler.setRepairer(repairer, " + I_DOCUMENT + ".DEFAULT_CONTENT_TYPE);");
		sc.addLineBreak();
		sc.add("return reconciler;");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addGetScannerMethod(
			org.emftext.sdk.codegen.composites.StringComposite sc) {
		sc.add("// @param fileExtension");
		sc.add("// @return");
		sc.add("///");
		sc.add("protected " + I_TOKEN_SCANNER + " getScanner(String fileName) {");
		sc.add(FILE_EDITOR_INPUT + " input = (" + FILE_EDITOR_INPUT + ") theEditor.getEditorInput();");
		sc.add("String path = input.getFile().getFullPath().toString();");
		sc.add(I_TEXT_RESOURCE + " thisResource = (" + I_TEXT_RESOURCE + ") theEditor.getResourceSet().getResource(" + URI + ".createPlatformResourceURI(path, true), true);");
		sc.addLineBreak();
		sc.add("return new " + textTokenScannerClassName + "(thisResource, colorManager);");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addGetConfiguredContentTypesMethod(
			org.emftext.sdk.codegen.composites.StringComposite sc) {
		sc.add("public String[] getConfiguredContentTypes(" + I_SOURCE_VIEWER + " sourceViewer) {");
		sc.add("return new String[] {");
		sc.add(I_DOCUMENT + ".DEFAULT_CONTENT_TYPE,");
		sc.add("};");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addGetContentAssistantMethod(
			org.emftext.sdk.codegen.composites.StringComposite sc) {
		sc.add("public " + I_CONTENT_ASSISTANT + " getContentAssistant(" + I_SOURCE_VIEWER + " sourceViewer) {");
		sc.addLineBreak();
		sc.add(CONTENT_ASSISTANT + " assistant = new " + CONTENT_ASSISTANT + "();");
		sc.add("assistant.setContentAssistProcessor(new " + completionProcessorClassName + "(theEditor), " + I_DOCUMENT + ".DEFAULT_CONTENT_TYPE);");
		sc.add("assistant.enableAutoActivation(true);");
		sc.add("assistant.setAutoActivationDelay(500);");
		sc.add("assistant.setProposalPopupOrientation(" + I_CONTENT_ASSISTANT + ".PROPOSAL_OVERLAY);");
		sc.add("assistant.setContextInformationPopupOrientation(" + I_CONTENT_ASSISTANT + ".CONTEXT_INFO_ABOVE);");
		sc.addLineBreak();
		sc.add("return assistant;");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addConstructor(
			org.emftext.sdk.codegen.composites.StringComposite sc) {
		sc.add("// Create a new editor configuration.");
		sc.add("//");
		sc.add("// @param editor");
		sc.add("// @param colorManager");
		sc.add("///");
		sc.add("public " + getResourceClassName() + "(" + editorClassName + " editor, " + colorManagerClassName + " colorManager) {");
		sc.add("this.theEditor = editor;");
		sc.add("this.colorManager = colorManager;");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addFields(org.emftext.sdk.codegen.composites.StringComposite sc) {
		sc.add("private " + colorManagerClassName + " colorManager;");
		sc.add("private " + editorClassName + " theEditor;");
		sc.addLineBreak();
	}

	public IGenerator newInstance(GenerationContext context) {
		return new EditorConfigurationGenerator(context);
	}
}
