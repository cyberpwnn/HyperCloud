package hr;

import java.io.File;
import hr.util.GList;
import hr.util.ServerConnection;

public class HyperCloud
{
	public static void main(String[] args) throws InterruptedException
	{
		File f = new File("server-foler");
		f.mkdirs();
		
		File jar = new File(f, "spigot-1.11.2.jar");
		
		ServerConnection sc = new ServerConnection(jar, f, new GList<String>().qadd("-Xmx4G").qadd("-Xms1M"), new GList<String>().qadd("-XX:+UseG1GC"));
		sc.start();
		sc.join();
		
		Runtime.getRuntime().addShutdownHook(new Thread()
		{
			@Override
			public void run()
			{
				
			}
		});
	}
}
