package hr.librarymanager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import hr.FileManager;

public class LibraryInstaller
{
	private List<Library> libraries;
	private File repository;
	private FileManager fm;
	
	public LibraryInstaller(FileManager fm, File repository)
	{
		libraries = new ArrayList<Library>();
		this.repository = repository;
		this.fm = fm;
	}
	
	public LibraryInstaller add(Coordinate coord)
	{
		libraries.add(new WraithLibrary(coord));
		
		return this;
	}
	
	public List<Library> getLibraries()
	{
		return libraries;
	}
	
	public File getRepository()
	{
		return repository;
	}
	
	public void install()
	{
		LibraryManager.installBlocking(this, fm);
	}
	
	public void install(Runnable onCompleted)
	{
		LibraryManager.installNonBlocking(fm, this, onCompleted);
	}
}
