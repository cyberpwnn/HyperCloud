package hr;

import hr.util.GList;

public class ServerHandler extends Thread
{
	private GList<Server> servers;
	
	public ServerHandler()
	{
		servers = new GList<Server>();
	}
	
	public void addServer(Server server)
	{
		servers.add(server);
	}
	
	@Override
	public void run()
	{
		while(!interrupted())
		{
			// TODO make use of this
			
			try
			{
				Thread.sleep(100);
			}
			
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}
