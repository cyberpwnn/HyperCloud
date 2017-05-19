package hr.server;

import java.io.File;
import hr.HyperCloud;
import hr.util.ServerConnection;

public class BungeecordInstance extends BaseServerInstance
{
	public BungeecordInstance()
	{
		super(new ServerConnection(new File(HyperCloud.fm.a("HyperCloud", "servers", "bungeecord"), "Bungeecord.jar"), HyperCloud.fm.a("HyperCloud", "servers", "bungeecord"), HyperCloud.bungeeArgs, ""), "bungeecord");
	}
}
