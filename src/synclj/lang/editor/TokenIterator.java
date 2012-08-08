/*******************************************************************************
 * Copyright (c) 2009-2011 CWI
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   * Jurgen J. Vinju - Jurgen.Vinju@cwi.nl - CWI
 *   * Arnold Lankamp - Arnold.Lankamp@cwi.nl
*******************************************************************************/
package synclj.lang.editor;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.ISourceLocation;
import org.eclipse.imp.pdb.facts.IString;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.visitors.VisitorException;
import org.rascalmpl.values.uptr.ProductionAdapter;
import org.rascalmpl.values.uptr.TreeAdapter;
import org.rascalmpl.values.uptr.visitors.TreeVisitor;

public class TokenIterator implements Iterator<Token>{
	private final List<Token> tokenList;
	private final Iterator<Token> tokenIterator;
	private boolean showAmb;
	
	private final List<String> CLJ_KEYWORDS = Arrays.asList(
			"defn", "defn-", "def", "def-", "defonce",
			"defmulti", "defmethod", "defmacro",
			"defstruct", "deftype", "defprotocol",
			"defrecord", "deftest",
			"slice",
			"defalias", "defhinted", "defmacro-",
			"defn-memo", "defnk", "defonce-",
			"defstruct-", "defunbound", "defunbound-",
			"defvar", "defvar-", "let", "letfn", "do",
			"case", "cond", "condp",
			"for", "loop", "recur",
			"when", "when-not", "when-let", "when-first",
			"if", "if-let", "if-not",
			".", "..", "->", "->>", "doto",
			"and", "or",
			"dosync", "doseq", "dotimes", "dorun", "doall",
			"load", "import", "unimport", "ns", "in-ns", "refer",
			"try", "catch", "finally", "throw",
			"with-open", "with-local-vars", "binding",
			"gen-class", "gen-and-load-class", "gen-and-save-class",
			"handler-case", "handle");

	public TokenIterator(boolean showAmb, IConstructor parseTree){
		this.tokenList = new LinkedList<Token>();
		this.showAmb = showAmb;
		
		if(parseTree != null){
			try{
				parseTree.accept(new LexicalCollector());
			}catch(VisitorException e){
				// is not thrown
			}
		}
		tokenIterator = tokenList.iterator();
	}

	public boolean hasNext(){
		return tokenIterator.hasNext();
	}

	public Token next(){
		return tokenIterator.next();
	}

	public void remove(){
		throw new UnsupportedOperationException();
	}

	private class LexicalCollector extends TreeVisitor{
		private int location;
		
		public LexicalCollector(){
			super();
			
			location = 0;
		}
		
		public IConstructor visitTreeAmb(IConstructor arg) throws VisitorException {
			if (showAmb) {
				int offset = location;
				ISourceLocation ambLoc = TreeAdapter.getLocation(arg);
				int length = ambLoc != null ? ambLoc.getLength() : TreeAdapter.yield(arg).length();

				location += length;
				tokenList.add(new Token(TokenColorer.META_AMBIGUITY, offset, length));
			}
			else {
				TreeAdapter.getAlternatives(arg).iterator().next().accept(this);
			}
			return arg;
			
		}
		
		public IConstructor visitTreeErrorAmb(IConstructor arg) throws VisitorException{
			if (showAmb) {
				int offset = location;
				ISourceLocation ambLoc = TreeAdapter.getLocation(arg);
				int length = ambLoc != null ? ambLoc.getLength() : TreeAdapter.yield(arg).length();

				location += length;
				tokenList.add(new Token(TokenColorer.META_AMBIGUITY, offset, length));
			}
			else {
				TreeAdapter.getAlternatives(arg).iterator().next().accept(this);
			}
			return arg;
		}
		
		public IConstructor visitTreeAppl(IConstructor arg) throws VisitorException{
			IValue catAnno = arg.getAnnotation("category");
			String category = null;
			
			
			if (catAnno != null) {
				category = ((IString) catAnno).getValue();
			}
			
			IConstructor prod = TreeAdapter.getProduction(arg);
			if (category == null && ProductionAdapter.isDefault(prod)) {
				category = ProductionAdapter.getCategory(prod);
				if (category != null && category.equals(TokenColorer.SYMBOL) && 
						CLJ_KEYWORDS.contains(TreeAdapter.yield(arg))) {
					category = TokenColorer.META_KEYWORD;
				}
			}
			
			int offset = location;
			
			for (IValue child : TreeAdapter.getArgs(arg)){
				child.accept(this);
			}

			if (ProductionAdapter.isDefault(prod) && (TreeAdapter.isLiteral(arg) || TreeAdapter.isCILiteral(arg))) {
				if (category == null){
					category = TokenColorer.META_KEYWORD;

					for (IValue child : TreeAdapter.getArgs(arg)) {
						int c = TreeAdapter.getCharacter((IConstructor) child);
						if (c != '-' && !Character.isJavaIdentifierPart(c)){
							category = null;
						}
					}
				}
			}
			
			if (ProductionAdapter.isSkipped(prod)) {
				category = TokenColorer.META_SKIPPED;
			}
			
			if (category != null) {
				tokenList.add(new Token(category, offset, location - offset));
			}

			return arg;
		}
		
		public IConstructor visitTreeChar(IConstructor arg) throws VisitorException{
			++location;
			
			return arg;
		}
		
		public IConstructor visitTreeCycle(IConstructor arg) throws VisitorException{
			return arg;
		}
		
		public IConstructor visitTreeErrorCycle(IConstructor arg) throws VisitorException{
			return arg;
		}
		
		public IConstructor visitTreeError(IConstructor arg) throws VisitorException{
			String category = ProductionAdapter.getCategory(TreeAdapter.getProduction(arg));
			
			int offset = location;
			
			for (IValue child : TreeAdapter.getArgs(arg)){
				child.accept(this);
			}
			
			if (TreeAdapter.isLiteral(arg) || TreeAdapter.isCILiteral(arg)){
				if (category == null){
					category = TokenColorer.META_KEYWORD;
					
					for (IValue child : TreeAdapter.getArgs(arg)) {
						int c = TreeAdapter.getCharacter((IConstructor) child);
						if (c != '-' && !Character.isJavaIdentifierPart(c)){
							category = null;
						}
					}
				}
			}

			if (category != null) {
				tokenList.add(new Token(category, offset, location - offset));
			}

			return arg;
		}
		
		public IConstructor visitTreeExpected(IConstructor arg) throws VisitorException{
			return arg;
		}
	}
}
