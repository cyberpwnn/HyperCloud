package hr.cloud;

import java.io.File;
import hr.server.ServerInstance;
import hr.util.GMap;

public interface HyperCloudServer
{
	public File getRoot();
	
	public File getInstanceDirectory();
	
	public File getRepositoryDirectory();
	
	public GMap<String, ServerInstance> getServers();
	
	public ServerInstance getServer(String server);
	
	public void createServerInstance(ServerInstance instance);
}
