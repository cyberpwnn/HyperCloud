package hr.librarymanager;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import hr.FileManager;

public class WraithLibrary implements Library
{
	private LibraryState state;
	private Coordinate coordinate;
	
	public WraithLibrary(Coordinate coord)
	{
		coordinate = coord;
		state = LibraryState.NOT_INSTALLED;
	}
	
	@Override
	public LibraryState getState()
	{
		return state;
	}
	
	@Override
	public boolean isInstalled()
	{
		return state.equals(LibraryState.INSTALLED);
	}
	
	protected void install(File localRepository, FileManager fm)
	{
		state = LibraryState.NOT_INSTALLED;
		
		File localTarget = new File(localRepository, getCoordinates().getEffectivePath() + "/" + getCoordinates().getEffectiveName());
		
		fm.createDirectory(localTarget.getParentFile());
		
		if(!localTarget.exists())
		{
			fm.createFile(localTarget);
			state = LibraryState.DOWNLOADING;
			
			try
			{
				LibraryUtil.download(fm, localTarget, getEffectiveURL());
			}
			
			catch(IOException e)
			{
				e.printStackTrace();
				state = LibraryState.FAILED;
			}
		}
		
		if(!state.equals(LibraryState.FAILED))
		{
			try
			{
				installDependencies(localTarget, localRepository, fm);
				LibraryUtil.install(fm, localTarget);
				state = LibraryState.INSTALLED;
			}
			
			catch(IOException e)
			{
				state = LibraryState.FAILED;
				e.printStackTrace();
			}
		}
	}
	
	private void installDependencies(File localTarget, File localRepository, FileManager fm)
	{
		try
		{
			URL url = new URL("jar:file:" + localTarget.getAbsolutePath() + "!/init.wpa");
			InputStream is = url.openStream();
			BufferedReader bu = new BufferedReader(new InputStreamReader(is));
			String line;
			String dat = "";
			
			while((line = bu.readLine()) != null)
			{
				dat += "," + line;
			}
			
			Coordinate[] c = LibraryUtil.getDependencies(dat.substring(1));
			LibraryInstaller l = new LibraryInstaller(fm, localRepository);
			
			for(Coordinate i : c)
			{
				l.add(i);
			}
			
			l.install();
		}
		
		catch(Exception e)
		{
			
		}
	}
	
	@Override
	public Coordinate getCoordinates()
	{
		return coordinate;
	}
	
	@Override
	public String getVersion()
	{
		return getCoordinates().getVersion();
	}
	
	@Override
	public String getArtifactId()
	{
		return getCoordinates().getArtifactId();
	}
	
	@Override
	public String getGroupId()
	{
		return getCoordinates().getGroupId();
	}
	
	@Override
	public Repository getRepository()
	{
		return getCoordinates().getRepository();
	}
	
	@Override
	public URL getEffectiveURL()
	{
		return getCoordinates().getEffectiveURL();
	}
}
