package hr;

public class ServerHandle extends Thread
{
	private Server server;
	
	public ServerHandle(Server server)
	{
		this.server = server;
	}
	
	@Override
	public void run()
	{
		while(server.running())
		{
			try
			{
				Thread.sleep(50);
			}
			
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}
