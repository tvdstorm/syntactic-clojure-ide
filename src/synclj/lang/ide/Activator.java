package synclj.lang.ide;

import org.eclipse.imp.runtime.PluginBase;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends PluginBase {

	// The plug-in ID
	public static final String PLUGIN_ID = "syntactic-clojure-ide"; //$NON-NLS-1$
	public static final String kLanguageName = "Rascal";
	
	// The shared instance
	private static Activator plugin;
	
	/**
	 * The constructor
	 */
	public Activator() {
		super();
		Activator.getInstance();
	}
	
	
	@Override
	public void start(BundleContext context) throws Exception {
		plugin = this;
		super.start(context);
	}
	
	public static Activator getInstance() {
		return plugin;
	}


	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	@Override
	public String getID() {
		return PLUGIN_ID;
	}

	@Override
	public String getLanguageID() {
		return kLanguageName;
	}

}
