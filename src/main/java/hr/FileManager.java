package hr;

import java.io.File;
import java.io.IOException;
import hr.librarymanager.FileUtils;
import hr.util.F;
import hr.util.L;

public class FileManager
{
	public File root;
	
	public FileManager(File root)
	{
		this.root = root;
	}
	
	public boolean exists(File f)
	{
		return f.exists();
	}
	
	public boolean isFile(File f)
	{
		return f.isFile();
	}
	
	public boolean isDirectory(File f)
	{
		return f.isDirectory();
	}
	
	public boolean existsFile(File f)
	{
		return exists(f) && isFile(f);
	}
	
	public boolean existsDirectory(File f)
	{
		return exists(f) && isFile(f);
	}
	
	public int indices(File f)
	{
		return f.getPath().split("\\" + File.separator).length;
	}
	
	public int distanceFromRoot(File f)
	{
		return -1;
	}
	
	public void createFile(File f)
	{
		if(exists(f))
		{
			return;
		}
		
		if(!exists(f.getParentFile()))
		{
			createDirectory(f.getParentFile());
		}
		
		L.l(F.repeat(" ", distanceFromRoot(f)) + " FS Create File: " + f.getPath());
		
		try
		{
			f.createNewFile();
		}
		
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public File a(String... strings)
	{
		String path = "";
		
		for(String i : strings)
		{
			path = path + i + File.separator;
		}
		
		return new File(root, path);
	}
	
	public void copy(File f, File file)
	{
		if(f.isDirectory())
		{
			if(!file.exists())
			{
				createDirectory(file);
			}
			
			for(File i : f.listFiles())
			{
				copy(i, new File(file, f.getName()));
			}
			
			return;
		}
		
		try
		{
			if(!file.exists())
			{
				createFile(file);
			}
			
			L.l(F.repeat(" ", distanceFromRoot(f)) + " FS Copy: " + f.getPath() + " > " + file.getPath());
			FileUtils.copyFile(f, file);
		}
		
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void createDirectory(File f)
	{
		if(exists(f))
		{
			return;
		}
		
		if(!exists(f.getParentFile()))
		{
			createDirectory(f.getParentFile());
		}
		
		L.l(F.repeat(" ", distanceFromRoot(f)) + " FS Create Folder: " + f.getPath());
		f.mkdirs();
	}
	
	public void delete(File f)
	{
		if(!exists(f))
		{
			return;
		}
		
		if(isDirectory(f))
		{
			L.l(F.repeat(" ", distanceFromRoot(f)) + "FS Delete Folder: " + f.getPath());
			
			for(File i : f.listFiles())
			{
				delete(i);
			}
		}
		
		L.l(F.repeat(" ", distanceFromRoot(f)) + "FS Delete File: " + f.getPath());
		f.delete();
	}
}
