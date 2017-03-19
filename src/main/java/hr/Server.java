package hr;

import java.io.File;
import hr.util.GList;
import hr.util.ServerConnection;

public class Server
{
	private ServerConnection connection;
	private File root;
	private File jar;
	private long mem;
	private GList<String> args;
	
	public Server(File root, String jar, long mem, GList<String> args)
	{
		this.root = root;
		this.jar = new File(root, jar);
		this.mem = mem;
		this.args = args;
		connection = new ServerConnection(this.jar, root, new GList<String>().qadd("-Xmx" + (mem / 1024 / 1024) + "M").qadd("-Xms1M"), args);
	}
	
	public Server(File root, String jar, long mem)
	{
		this(root, jar, mem, new GList<String>().qadd("-XX:+UseG1GC"));
	}
	
	public void startServer()
	{
		connection.start();
	}
	
	public void stopServer()
	{
		command("stop");
		
		try
		{
			connection.getP().waitFor();
		}
		
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		
		connection.getP().destroy();
	}
	
	public void forceStopServer()
	{
		connection.forceStop();
	}
	
	public boolean running()
	{
		return connection.getP().isAlive();
	}
	
	public void command(String string)
	{
		connection.command(string);
	}
	
	public ServerConnection getConnection()
	{
		return connection;
	}
	
	public File getRoot()
	{
		return root;
	}
	
	public File getJar()
	{
		return jar;
	}
	
	public long getMem()
	{
		return mem;
	}
	
	public GList<String> getArgs()
	{
		return args;
	}
}
