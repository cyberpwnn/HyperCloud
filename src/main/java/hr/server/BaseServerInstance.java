package hr.server;

import java.io.File;
import hr.util.ServerConnection;

public class BaseServerInstance implements ServerInstance
{
	protected static int idx;
	protected String type;
	protected int id;
	protected int port;
	protected String name;
	protected File workingDirectory;
	protected ServerConnection serverConnection;
	protected File executable;
	
	public BaseServerInstance(ServerConnection connection, String name)
	{
		this.workingDirectory = connection.getWd();
		this.executable = connection.getJar();
		this.id = idx++;
		this.serverConnection = connection;
		this.name = name;
		this.type = "Unknown";
		this.port = -1;
	}
	
	@Override
	public String getType()
	{
		return type;
	}
	
	@Override
	public int getInstanceId()
	{
		return id;
	}
	
	@Override
	public String getName()
	{
		return name;
	}
	
	@Override
	public File getWorkingDirectory()
	{
		return workingDirectory;
	}
	
	@Override
	public File getExecutable()
	{
		return executable;
	}
	
	@Override
	public int getPort()
	{
		return port;
	}
	
	@Override
	public ServerConnection getConnection()
	{
		return serverConnection;
	}
}
