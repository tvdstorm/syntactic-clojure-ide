/*******************************************************************************
 * Copyright (c) 2009-2011 CWI
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   * Jurgen J. Vinju - Jurgen.Vinju@cwi.nl - CWI
 *   * Tijs van der Storm - Tijs.van.der.Storm@cwi.nl
 *   * Arnold Lankamp - Arnold.Lankamp@cwi.nl
*******************************************************************************/
package synclj.lang.editor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.imp.parser.IParseController;
import org.eclipse.imp.services.ITokenColorer;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public class TokenColorer implements ITokenColorer {
	public static final String NORMAL = "Normal";
	public static final String TYPE = "Type";
	public static final String IDENTIFIER = "Identifier";
	public static final String VARIABLE = "Variable";
	public static final String CONSTANT = "Constant";
	public static final String COMMENT = "Comment";
	public static final String TODO = "Todo";
	public static final String QUOTE = "Quote";
	public static final String META_AMBIGUITY = "MetaAmbiguity";
	public static final String META_VARIABLE = "MetaVariable";
	public static final String META_KEYWORD = "MetaKeyword";
	public static final String META_SKIPPED = "MetaSkipped";
	
	public static final String STRING = "String";
	public static final String NUMBER = "Number";
	public static final String SYMBOL = "Symbol";
	public static final String REGEXP = "RegExp";
	public static final String CHAR = "Char";
	
	

	private final Map<String,TextAttribute> map = new HashMap<String,TextAttribute>();

	public TokenColorer() {
		super();
		map.put(NORMAL, new TextAttribute(null, null, SWT.NONE));
		map.put(META_KEYWORD, new TextAttribute(new Color(Display.getDefault(), 123, 0, 82), null, SWT.BOLD));
		map.put(META_VARIABLE, new TextAttribute(new Color(Display.getDefault(), 0x29,0x5F,0x94), null, SWT.ITALIC));
		map.put(META_AMBIGUITY,  new TextAttribute(new Color(Display.getDefault(), 186, 29, 29), null, SWT.BOLD));
		map.put(META_SKIPPED,  new TextAttribute(null, new Color(Display.getDefault(), 82, 141, 115), SWT.ITALIC));
		map.put(TODO,new TextAttribute(new Color(Display.getDefault(), 123, 157, 198), null, SWT.BOLD));
		map.put(COMMENT,new TextAttribute(new Color(Display.getDefault(), 82, 141, 115), null, SWT.ITALIC));
		map.put(CONSTANT,new TextAttribute(new Color(Display.getDefault(), 139, 0, 139), null, SWT.NONE));
		map.put(VARIABLE,new TextAttribute(new Color(Display.getDefault(), 0x55,0xaa,0x55), null, SWT.NONE));
		map.put(IDENTIFIER,new TextAttribute(new Color(Display.getDefault(), 0x2C,0x57,0x7C), null, SWT.NONE));
		map.put(QUOTE,new TextAttribute(new Color(Display.getDefault(), 255, 69, 0), new Color(Display.getDefault(), 32,178,170), SWT.NONE));
		map.put(TYPE,new TextAttribute(new Color(Display.getDefault(), 0xAB,0x25,0x25), null, SWT.NONE));

		// same as constant
		map.put(NUMBER, new TextAttribute(new Color(Display.getDefault(), 139, 0, 139), null, SWT.NONE));
		map.put(STRING, new TextAttribute(new Color(Display.getDefault(), 139, 0, 139), null, SWT.NONE));
		map.put(REGEXP, new TextAttribute(new Color(Display.getDefault(), 139, 0, 139), null, SWT.NONE));
		map.put(CHAR, new TextAttribute(new Color(Display.getDefault(), 139, 0, 139), null, SWT.NONE));
	} 

	
	
	public IRegion calculateDamageExtent(IRegion seed, IParseController ctlr) {
		return seed;
	}

	public TextAttribute getColoring(IParseController controller, Object token) {
		return map.get(((Token) token).getCategory());
	}

}
