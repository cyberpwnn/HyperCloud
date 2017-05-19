package hr.librarymanager;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import hr.FileManager;
import hr.util.F;
import hr.util.L;

public class LibraryUtil
{
	private static final Class<?>[] parameters = new Class[] {URL.class};
	
	public static Coordinate[] getDependencies(String data)
	{
		List<Coordinate> c = new ArrayList<Coordinate>();
		
		for(String i : data.split(","))
		{
			String depend = i.trim();
			String[] gavr = depend.split(":");
			String g = gavr[0];
			String a = gavr[1];
			String v = gavr[2];
			String r = "http://" + gavr[3];
			
			if(v.equals("p"))
			{
				v = WraithAPI.GLOBAL_VERSION;
			}
			
			try
			{
				c.add(Coordinate.create(g, a, v, r));
			}
			
			catch(MalformedURLException e)
			{
				e.printStackTrace();
			}
		}
		
		return c.toArray(new Coordinate[c.size()]);
	}
	
	public static void download(FileManager fm, File file, URL url) throws IOException
	{
		L.l(F.repeat(" ", fm.distanceFromRoot(file)) + " Downloading: " + url);
		FileUtils.copyURLToFile(url, file);
	}
	
	public static void install(FileManager fm, File jar) throws IOException
	{
		L.l(F.repeat(" ", fm.distanceFromRoot(jar)) + " Installing: " + jar);
		URL u = new URL("jar:file:" + jar.toString() + "!/");
		URLClassLoader sysloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
		Class<?> sysclass = URLClassLoader.class;
		
		try
		{
			Method method = sysclass.getDeclaredMethod("addURL", parameters);
			method.setAccessible(true);
			method.invoke(sysloader, new Object[] {u});
		}
		
		catch(Throwable t)
		{
			t.printStackTrace();
			throw new IOException("Error, could not add URL to system classloader");
		}
	}
}
