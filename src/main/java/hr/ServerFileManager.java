package hr;

import java.io.File;

public class ServerFileManager
{
	private File templates;
	private File servers;
	private File configs;
	private File repository;
	
	public ServerFileManager()
	{
		templates = new File("templates");
		servers = new File("serve");
		configs = new File("config");
		repository = new File("temp");
		
		templates.mkdirs();
		servers.mkdirs();
		configs.mkdirs();
		repository.mkdirs();
	}
}
