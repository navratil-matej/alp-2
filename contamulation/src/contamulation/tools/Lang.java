package contamulation.tools;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * static wrapper for ResourceBundle operations
 * @author des
 */
public class Lang
{
	private static ResourceBundle bundle;
	private static final String DIR = "resources/lang";
	
	public static final String string(String key)
	{
		return String.format(bundle.getString(key));
	}
	
	public static final void set(ResourceBundle bundle)
	{
		if(bundle == null)
			throw new IllegalStateException("Lang class not initialised with set(Ljava.lang.String;)");
		Lang.bundle = bundle;
	}
	
	/**
	 * Nested static class containing lang keys.
	 * @author des
	 *
	 */
	public static class Key
	{
		public static final String WELCOME = "intro.welcome";
		public static final String SELECT_FIRST = "intro.select-first";
		public static final String DIR_PATH = "intro.dir-path";
		
		public static final String EXIT = "menu.exit";
		public static final String BACK = "menu.back";
		public static final String SELECT_FILE = "menu.select-file";
		public static final String USE_QUOTES = "menu.use_quotes";
		public static final String USE_NEWLINE = "menu.use_newline";
		public static final String SELECT_ACTION = "menu.select-action";
		public static final String RUN_SIM = "menu.run-sim";
		public static final String CONFIGURE_SIM = "menu.configure-sim";
		public static final String EDIT_SIM = "menu.edit-sim";

		public static final String INVALID_PATH = "err.invalid-path";
		public static final String INVALID_FILE = "err.invalid-file";

		public static final String SELECT_OPERATION = "edit.select-operation";
		public static final String OPERATION_ADD = "edit.operation-add";
		public static final String OPERATION_REMOVE = "edit.operation-remove";
		public static final String OPERATION_LIST = "edit.operation-list";
		public static final String SELECT_ENTRY_TYPE = "edit.select-entry-type";
		public static final String SELECT_REMOVAL_ENTRY_TYPE = "edit.select-removal-entry-type";
		public static final String CITY_SIZE_POINT = "edit.city-size-point";
		public static final String JOB = "edit.job";
		public static final String JOB_DISTRIBUTION_POINT = "edit.job-distribution-point";
		public static final String BUILDING = "edit.building";
		public static final String BUILDING_DISTRIBUTION_POINT = "edit.building_distribution_point";
		public static final String BEHAVIOR = "edit.behavior";
		public static final String BEHAVIOR_TIMETABLE_POINT = "edit.behavior-timetable-point";
		public static final String DISEASE_INCUBATION_POINT = "edit.disease-incubation-point";
		public static final String DISEASE_TRANSMISSION_POINT = "edit.disease-transmission-point";
	}
	
	/**
	 * Nested static class containing resource bunles.
	 * @author des
	 *
	 */
	public static class Bundle
	{
		public static final ResourceBundle CZ_CS = // TODO DIR??
			ResourceBundle.getBundle("lang/cz-cs");
		public static final ResourceBundle EN_US =
			ResourceBundle.getBundle("lang/en-us");
	}
	
	/**
	 * Class used to generate lang files. This should not be called in the final app.
	 * @author des
	 *
	 */
	public static class Generator
	{
		/**
		 * Create lang files in directory specified by the constant DIR.
		 * @throws IOException if the files cannot be written.
		 * @throws IllegalArgumentException if I forgot to put static on a lang key.
		 * @throws IllegalAccessException if I forgot to put public on a lang key.
		 */
		public static void generate() throws IOException, IllegalArgumentException, IllegalAccessException
		{
			Field[] keys  = Key   .class.getFields();
			Field[] langs = Bundle.class.getFields();
			StringBuilder sb = new StringBuilder();
			List<String> entries;
			String pathstr;
			Path path;
			for(Field lang : langs)
			{
				if(!lang.getType().equals(ResourceBundle.class))
					return;
				sb = new StringBuilder();
				pathstr = DIR + "/" + lang.getName().toLowerCase().replace('_', '-') + ".properties";
				path = Paths.get(pathstr);
				entries = Files.exists(path) ? Files.readAllLines(path) : new ArrayList<>();
				for(Field key : keys)
				{
					if(!key.getType().equals(String.class))
						return;
					String entry = (String) key.get(null);
					String present = entries.stream()
						.filter(str -> str.startsWith(entry + "="))
						.findFirst().orElse(null);
					sb.append(present != null ? present : entry + "=").append('\n');
				}
				Files.write(path, sb.toString().getBytes());
			}
		}
	}
}
