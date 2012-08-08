package synclj.lang.parser;

import java.util.Iterator;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.imp.language.Language;
import org.eclipse.imp.model.ISourceProject;
import org.eclipse.imp.parser.IMessageHandler;
import org.eclipse.imp.parser.IParseController;
import org.eclipse.imp.parser.ISourcePositionLocator;
import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.services.IAnnotationTypeInfo;
import org.eclipse.imp.services.ILanguageSyntaxProperties;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;

import synclj.lang.ide.Activator;

public class ParseController implements IParseController {
	private IConstructor parseTree;
	private IDocument document;
	private IPath path;
	private ISourceProject project;
	private ParseJob job;

	
	@Override
	public IAnnotationTypeInfo getAnnotationTypeInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getCurrentAst() {
		return parseTree;
	}

	@Override
	public IDocument getDocument() {
		return document;
	}

	@Override
	public Language getLanguage() {
		return null;
	}

	@Override
	public IPath getPath() {
		return path;
	}

	@Override
	public ISourceProject getProject() {
		return project;
	}

	@Override
	public ISourcePositionLocator getSourcePositionLocator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ILanguageSyntaxProperties getSyntaxProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator getTokenIterator(IRegion arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initialize(IPath path, ISourceProject project, IMessageHandler handler) {
		this.path = path;
		this.project = project;
		this.job = new ParseJob(path, handler);
	}

	@Override
	public Object parse(String input, IProgressMonitor arg1) {
		parseTree = null;
		try {
			job.initialize(input);
			job.schedule();
			job.join();
			parseTree = job.parseTree;
			return parseTree;
		} catch (InterruptedException e) {
			Activator.getInstance().logException("parser interrupted", e);
		}
		return null;
	}

	@Override
	public Object parse(IDocument doc, IProgressMonitor monitor) {
		if (doc == null) {
			return null;
		}
		System.err.println("Parsing " + doc.get());
		this.document = doc;
		return parse(doc.get(), monitor);
	}

}
