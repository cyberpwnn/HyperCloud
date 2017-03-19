package hr.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

public class ServerConnection extends Thread
{
	private File jar;
	private File wd;
	private Process p;
	private ProcessBuilder builder;
	private OutputStream os;
	private GList<String> args;
	
	public ServerConnection(File jar, File wd, List<String> mem, List<String> custom)
	{
		this.jar = jar;
		this.wd = wd;
		
		args = new GList<String>().qadd("java").qadd(mem).qadd(custom).qadd("-jar").qadd(jar.getName());
		builder = new ProcessBuilder(args);
		builder.directory(wd);
		os = new ByteArrayOutputStream();
		L.l("Preparing Server Startup: " + jar.getPath());
		
		try
		{
			agreeEULA();
		}
		
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void forceStop()
	{
		p.destroyForcibly();
	}
	
	public void agreeEULA() throws IOException
	{
		L.l("Rewriting EULA");
		File w = new File(wd, "eula.txt");
		w.delete();
		w.createNewFile();
		FileWriter fw = new FileWriter(w);
		fw.write("# HyperCloud was here\neula=true");
		fw.close();
	}
	
	@Override
	public void run()
	{
		try
		{
			L.l("Starting Process: " + jar.getPath());
			L.l("Args: " + args.toString(" "));
			p = builder.start();
			
			while(p.isAlive())
			{
				InputStream is = p.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				String line = null;
				
				try
				{
					while((line = br.readLine()) != null)
					{
						System.out.println("[Server-" + jar.getName() + "]: " + line);
					}
				}
				
				catch(IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void command(String cmd)
	{
		PrintWriter pw = new PrintWriter(p.getOutputStream());
		pw.println(cmd);
		pw.flush();
	}
	
	public File getJar()
	{
		return jar;
	}
	
	public File getWd()
	{
		return wd;
	}
	
	public Process getP()
	{
		return p;
	}
	
	public ProcessBuilder getBuilder()
	{
		return builder;
	}
	
	public OutputStream getOs()
	{
		return os;
	}
}
