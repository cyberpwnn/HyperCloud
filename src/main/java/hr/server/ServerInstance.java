package hr.server;

import java.io.File;
import hr.util.ServerConnection;

public interface ServerInstance
{
	public String getType();
	
	public int getInstanceId();
	
	public String getName();
	
	public File getWorkingDirectory();
	
	public File getExecutable();
	
	public ServerConnection getConnection();
	
	public int getPort();
}
