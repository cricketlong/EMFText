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
package org.emftext.sdk.codegen.generators;

import java.io.PrintWriter;

import org.eclipse.emf.ecore.EObject;
import org.emftext.runtime.resource.ITextPrinter;
import org.emftext.runtime.resource.ITextResource;
import org.emftext.sdk.codegen.EArtifact;
import org.emftext.sdk.codegen.GenerationContext;
import org.emftext.sdk.codegen.composites.JavaComposite;
import org.emftext.sdk.codegen.composites.StringComposite;

/**
 * A generator that create a printer class. This class can be
 * subject to modifications.
 * 
 * @author Sven Karol (Sven.Karol@tu-dresden.de)
 */
public class TextPrinterGenerator extends BaseGenerator {
	
	private String basePrinterClassName; 
	
	public TextPrinterGenerator(GenerationContext context, boolean printerBaseExists){
		super(context, EArtifact.PRINTER);
		if (printerBaseExists) { 
			basePrinterClassName = context.getClassName(EArtifact.PRINTER_BASE);
		} else {
			basePrinterClassName = null;
		}
	}
	
	@Override
	public boolean generate(PrintWriter out) {
		boolean noBaseClass = basePrinterClassName == null;

		StringComposite sc = new JavaComposite();
		
		sc.add("package " + getResourcePackageName() + ";");
		sc.addLineBreak();
	    sc.add("/**").addLineBreak();
	    sc.add("* This is the printer class used by EMFText.").addLineBreak();
		if (!noBaseClass) {
			sc.add("* Users may implement own behavior by overriding printing methods in the printer base.").addLineBreak();
			sc.add("* The baseclass contains a pretty printer implementation generated by EMFText which").addLineBreak();
			sc.add("* is not granted to work in all cases, but should work in most cases.").addLineBreak();
		}
		sc.add("*/");
		sc.add("public class " + getResourceClassName() + (noBaseClass ? " implements " + ITextPrinter.class.getName() : " extends " + basePrinterClassName)+ " {");
		sc.addLineBreak();
		sc.add("public " + getResourceClassName() + "(" + java.io.OutputStream.class.getName() + " o, " + ITextResource.class.getName() + " resource) {");
	    if (!noBaseClass) {
	    	sc.add("super(o, resource);");
	    }
	    sc.add("}");
	    
	    if (noBaseClass) {
	    	sc.addLineBreak();
	    	sc.add("public void print(" + EObject.class.getName() + " arg0) throws " + java.io.IOException.class.getName() + " {");
	    	sc.add("// TODO insert printer implementation here.").addLineBreak();
	    	sc.add("}");
	    }
	    sc.add("}");
	    
	    out.print(sc.toString());
		return true;
	}
}
