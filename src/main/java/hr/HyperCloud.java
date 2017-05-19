package hr;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import hr.clust.Configurable;
import hr.clust.DataCluster;
import hr.clust.JSD;
import hr.librarymanager.Coordinates;
import hr.librarymanager.LibraryInstaller;
import hr.librarymanager.LibraryUtil;
import hr.util.F;
import hr.util.L;

public class HyperCloud implements Configurable
{
	public static FileManager fm;
	public static HyperCloud instance;
	public static DataCluster cc;
	public static boolean bungeecord;
	public static String bungeecordUri;
	public static int startingPort;
	public static int maxInstances;
	public static int bungeePort;
	public static String bungeeArgs;
	
	public HyperCloud()
	{
		// File Structure
		fm = new FileManager(new File("."));
		fm.createDirectory(fm.a("HyperCloud", "repository", "lib"));
		fm.createDirectory(fm.a("HyperCloud", "repository", "cache"));
		fm.createDirectory(fm.a("HyperCloud", "repository", "instance"));
		fm.createDirectory(fm.a("HyperCloud", "config"));
		fm.createDirectory(fm.a("HyperCloud", "servers"));
		fm.createDirectory(fm.a("HyperCloud", "servers", "jars", "server"));
		fm.createDirectory(fm.a("HyperCloud", "servers", "jars", "proxy"));
		fm.createDirectory(fm.a("HyperCloud", "servers", "templates"));
		fm.createDirectory(fm.a("HyperCloud", "servers", "bungeecord"));
		
		// Install Libraries
		LibraryInstaller li = new LibraryInstaller(fm, fm.a("HyperCloud", "repository", "lib"));
		li.add(Coordinates.COMMON_LANG.get());
		li.add(Coordinates.COMMON_IO.get());
		li.add(Coordinates.GUAVA.get());
		li.install();
		
		// DConfig
		cc = new DataCluster();
		onNewConfig();
		int dfr = 1 + fm.distanceFromRoot(new File(fm.a("HyperCloud", "config"), getCodeName()));
		String pref = F.repeat(" ", dfr);
		JSD.load(fm, cc, new File(fm.a("HyperCloud", "config"), getCodeName()));
		onReadConfig();
		L.l(pref + "Using Bungeecord: " + bungeecord);
		L.l(pref + "Max instances: " + maxInstances);
		L.l(pref + "Starting Port: " + startingPort);
		L.l(pref + "Port Range (computed): " + (bungeecord ? 25565 : startingPort) + " - " + (startingPort + maxInstances) + " (" + (maxInstances + (bungeecord ? 1 : 0)) + " used)");
		
		// Bungeecord Installation
		if(bungeecord)
		{
			L.l("Installing Bungeecord...");
			File f = new File(fm.a("HyperCloud", "servers", "jars", "proxy"), "Bungeecord.jar");
			
			try
			{
				LibraryUtil.download(fm, f, new URL(bungeecordUri));
			}
			
			catch(MalformedURLException e)
			{
				e.printStackTrace();
			}
			
			catch(IOException e)
			{
				e.printStackTrace();
			}
			
			File des = new File(fm.a("HyperCloud", "servers", "bungeecord"), f.getName());
			fm.copy(f, des);
			L.l("Bungeecord jar updated");
		}
	}
	
	@Override
	public void onNewConfig()
	{
		cc.set("bungeecord.port", 25565);
		cc.set("bungeecord.args", "-Xmx1g -Xms1g -XX:+UseG1GC");
		cc.set("bungeecord.enabled", true);
		cc.set("bungeecord.download", "https://ci.md-5.net/job/BungeeCord/lastSuccessfulBuild/artifact/bootstrap/target/BungeeCord.jar");
		cc.set("instances.max-instances", Runtime.getRuntime().availableProcessors() / 2);
		cc.set("instances.starting-port", 35565);
	}
	
	@Override
	public void onReadConfig()
	{
		bungeecord = cc.getBoolean("bungeecord.enabled");
		maxInstances = cc.getInt("instances.max-instances");
		startingPort = cc.getInt("instances.starting-port");
		bungeecordUri = cc.getString("bungeecord.download");
		bungeePort = cc.getInt("bungeecord.port");
		bungeeArgs = cc.getString("bungeecord.args");
	}
	
	@Override
	public DataCluster getConfiguration()
	{
		return cc;
	}
	
	@Override
	public String getCodeName()
	{
		return "config.json";
	}
	
	public static void main(String[] args)
	{
		instance = new HyperCloud();
	}
}
