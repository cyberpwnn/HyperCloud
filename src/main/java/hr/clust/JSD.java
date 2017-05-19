package hr.clust;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import hr.FileManager;
import hr.util.F;
import hr.util.GList;
import hr.util.L;

public class JSD
{
	public static void load(FileManager fm, DataCluster cc, File f)
	{
		try
		{
			if(!f.exists())
			{
				fm.createFile(f);
				new JSONDataOutput().save(cc, f);
			}
			
			L.l(F.repeat(" ", fm.distanceFromRoot(f)) + " Loading Config: " + f.getPath());
			new JSONDataInput().load(cc, f);
		}
		
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static boolean contains(FileManager fm, File f, String con) throws IOException
	{
		if(f.exists())
		{
			BufferedReader bu = new BufferedReader(new FileReader(f));
			String line = null;
			boolean has = false;
			
			while((line = bu.readLine()) != null)
			{
				if(line.contains(con))
				{
					has = true;
				}
			}
			
			bu.close();
			
			return has;
		}
		
		return false;
	}
	
	public static void rewrite(FileManager fm, File f, String from, String to) throws IOException
	{
		GList<String> lines = new GList<String>();
		
		if(f.exists())
		{
			BufferedReader bu = new BufferedReader(new FileReader(f));
			String line = null;
			
			while((line = bu.readLine()) != null)
			{
				lines.add(line.replaceAll(from, to));
			}
			
			bu.close();
			
			L.l("Rewrite " + f.getPath());
			PrintWriter pw = new PrintWriter(new FileWriter(f, false));
			
			for(String i : lines)
			{
				pw.println(i);
			}
			
			pw.close();
		}
	}
}
