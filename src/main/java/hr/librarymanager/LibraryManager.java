package hr.librarymanager;

import java.util.ArrayList;
import java.util.List;
import hr.FileManager;

public class LibraryManager
{
	private static final List<Library> libraries = new ArrayList<Library>();
	
	public static Library[] getInstalledLibraries()
	{
		return libraries.toArray(new Library[libraries.size()]);
	}
	
	public static void ensureInstalled(FileManager fm, Coordinate c)
	{
		if(!isInstalled(c))
		{
			new LibraryInstaller(fm, WraithAPI.REPO_LOCAL).add(c).install();
		}
	}
	
	public static boolean isInstalled(Coordinate c)
	{
		if(shouldInstall(c))
		{
			return false;
		}
		
		return true;
	}
	
	protected static boolean shouldInstall(Coordinate l)
	{
		for(Library i : libraries)
		{
			if(i.getGroupId().equals(l.getGroupId()) && i.getArtifactId().equals(l.getArtifactId()))
			{
				return false;
			}
		}
		
		return true;
	}
	
	protected static void installBlocking(LibraryInstaller installer, FileManager fm)
	{
		for(Library i : installer.getLibraries())
		{
			if(shouldInstall(i.getCoordinates()))
			{
				((WraithLibrary) i).install(installer.getRepository(), fm);
				libraries.add(i);
			}
		}
	}
	
	protected static void installNonBlocking(FileManager fm, LibraryInstaller installer, Runnable onComplete)
	{
		new Thread("Library Installer")
		{
			@Override
			public void run()
			{
				installBlocking(installer, fm);
				
				onComplete.run();
			}
		}.start();
	}
}
