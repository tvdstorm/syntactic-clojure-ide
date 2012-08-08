package synclj.lang.parser;

import java.net.URI;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.imp.parser.IMessageHandler;
import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.ISourceLocation;
import org.eclipse.imp.pdb.facts.IValue;
import org.rascalmpl.interpreter.control_exceptions.Throw;
import org.rascalmpl.interpreter.utils.RuntimeExceptionFactory;
import org.rascalmpl.parser.gtd.IGTD;
import org.rascalmpl.parser.gtd.exception.ParseError;
import org.rascalmpl.parser.gtd.result.out.DefaultNodeFlattener;
import org.rascalmpl.parser.uptr.UPTRNodeFactory;

import clojure.lang.ClojureParser;
import clojure.lang.Compiler;

import synclj.lang.ide.Activator;

public class ParseJob extends Job {

	private final IMessageHandler handler;

	private String input;
	public IConstructor parseTree = null;
	private final IPath path;

	public ParseJob(IPath path, IMessageHandler handler) {
		super("Clojure parsing");
		this.path = path;
		this.handler = handler;
	}

	public void initialize(String input) {
		this.input = input;
	}

	
	private static final String START_SORT = "start__File";

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		monitor.beginTask("Parsing", 105);

		try {
			handler.clearMessages();
			
			IGTD<IConstructor, IConstructor, ISourceLocation> gtd = new ClojureParser();
			parseTree = (IConstructor) gtd
					.parse(START_SORT,
							URI.create(path.toString()),
							input.toCharArray(),
							new DefaultNodeFlattener<IConstructor, IConstructor, ISourceLocation>(),
							new UPTRNodeFactory());
			
		} catch (ParseError pe) {
			int offset = pe.getOffset();
			if (offset == input.length())
				--offset;

			handler.handleSimpleMessage("parse error", offset,
					offset + pe.getLength(), pe.getBeginColumn(),
					pe.getEndColumn(), pe.getBeginLine() + 1,
					pe.getEndLine() + 1);
		} catch (Throw e) {
			IValue exc = e.getException();

			if (exc.getType() == RuntimeExceptionFactory.Exception) {
				if (((IConstructor) exc).getConstructorType() == RuntimeExceptionFactory.ParseError) {
					ISourceLocation loc = (ISourceLocation) ((IConstructor) e
							.getException()).get(0);
					handler.handleSimpleMessage("parse error: " + loc,
							loc.getOffset(), loc.getOffset() + loc.getLength(),
							loc.getBeginColumn(), loc.getEndColumn(),
							loc.getBeginLine(), loc.getEndLine());
				} else {
					Activator.getInstance().logException(e.getMessage(), e);
				}
			}
		} catch (Throwable e) {
			Activator.getInstance().logException(
					"parsing failed: "
							+ e.getMessage(), e);
		} finally {
			monitor.done();
		}

		return Status.OK_STATUS;
	}
}
