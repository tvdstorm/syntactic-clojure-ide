package synclj.lang.ide;

public interface ISyncljResources {

	public static final String SYNCLJ_DEFAULT_IMAGE = "rascal_default_image";
	public static final String SYNCLJ_DEFAULT_OUTLINE_ITEM = "rascal_default_outline_item";
	public static final String SYNCLJ_FILE = "rascal_file";
	public static final String SYNCLJ_FILE_WARNING = "rascal_file_warning";
	public static final String SYNCLJ_FILE_ERROR = "rascal_file_error";
	public static final String AMBIDEXTER = "ambidexter";
	public static final String COPY_TO_CONSOLE = "copy_to_console";

	public static final String ID_SYNCLJ_DEBUG_MODEL = "rascal.debugModel";
	public static final String ID_SYNCLJ_NATURE = "rascal_eclipse.rascal_nature";
	public static final String ID_SYNCLJ_BUILDER = "rascal_eclipse.rascal_builder";

	/**
	 * Source folder name within an Eclipse project.
	 */
	public static final String SYNCLJ_SRC = "src";
	
	/**
	 * Rascal source file name extension.
	 */	
	public static final String SYNCLJ_EXT = "clj";
		
	public static final String ID_SYNCLJ_MARKER = "rascal_eclipse.rascal_markers";
	public static final String ID_SYNCLJ_MARKER_TYPE_TEST_RESULTS = "rascal.markerType.testResult";
	public static final String ID_SYNCLJ_MARKER_TYPE_FOCUS = "rascal.focusMarker";
	
	/**
	 * Name of the string substitution variable that resolves to the
	 * location of a local Rascal executable (value <code>rascalExecutable</code>).
	 */
	public static final String VARIABLE_SYNCLJ_EXECUTABLE = "rascalExecutable";
	
	/**
	 * Launch configuration attribute key. Value is a path to a rascal
	 * program. The path is a string representing a full path
	 * to a Rascal program in the workspace. 
	 */
	public static final String ATTR_SYNCLJ_PROGRAM = ID_SYNCLJ_DEBUG_MODEL + ".ATTR_SYNCLJ_PROGRAM";

	/**
	 * Launch configuration attribute key. Value is an Eclipse project (instance of IProject).
	 * The module path is set relatively to this project and its referenced projects
	 * in the workspace. 
	 */
	public static final String ATTR_SYNCLJ_PROJECT = ID_SYNCLJ_DEBUG_MODEL + ".ATTR_SYNCLJ_PROJECT";
	
	/**
	 * Identifier for the RASCAL launch configuration type
	 * (value <code>rascal.launchType</code>)
	 */
	public static final String ID_SYNCLJ_LAUNCH_CONFIGURATION_TYPE = "rascal.launchType";
	public static final String ID_SYNCLJ_EDITOR = "rascal.editor";

	public static final String SYNCLJ_EDITOR_MESSAGES = "rascal.editor.messages";
	


}
