/*******************************************************************************
 * Copyright (c) 2012 BestSolution.at and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Tom Schindl<tom.schindl@bestsolution.at> - initial API and implementation
 *******************************************************************************/
package at.bestsolution.efxclipse.tooling.model.internal;

import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

import at.bestsolution.efxclipse.tooling.model.IFXCtrlField;

public class FXCtrlField implements IFXCtrlField {
	private FXCtrlClass clazz;
	private IField field;
	private String erasedTypeSig;
	
	public FXCtrlField(FXCtrlClass clazz, IField field, String erasedTypeSig) {
		this.clazz = clazz;
		this.field = field;
		this.erasedTypeSig = erasedTypeSig;
	}
	
	@Override
	public IJavaElement getJavaElement() {
		return field;
	}
	
	@Override
	public String getName() {
		return field.getElementName();
	}
	
	public IType getType() {
		try {
			return clazz.getJavaProject().findType(erasedTypeSig);
		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
