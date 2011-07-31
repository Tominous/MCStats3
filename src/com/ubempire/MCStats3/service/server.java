package com.ubempire.MCStats3.service;

// Bukkit Imports
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServerListener;
import org.bukkit.plugin.java.JavaPlugin;

import com.ubempire.MCStats3.StatsPlugin;

public class server extends ServerListener {
	// Change "MyPlugin" to the name of your MAIN class file.
	// Let's say my plugins MAIN class is: Register.java
	// I would change "MyPlugin" to "Register"
	private StatsPlugin plugin;
	private Methods Methods = null;

	public server(StatsPlugin plugin) {
		this.plugin = plugin;
		this.Methods = new Methods();
	}

	@Override
	public void onPluginDisable(PluginDisableEvent event) {
		// Check to see if the plugin thats being disabled is the one we are
		// using
		if (this.Methods != null && this.Methods.hasMethod()) {
			Boolean check = this.Methods.checkDisabled(event.getPlugin());

			if (check) {
				StatsPlugin.Method = null;
				System.out
						.println("["
								+ plugin.info.getName()
								+ "] Payment method was disabled. No longer accepting payments.");
			}
		}
	}

	@Override
	public void onPluginEnable(PluginEnableEvent event) {
		// Check to see if we need a payment method
		if (!this.Methods.hasMethod()) {
			if (this.Methods.setMethod(event.getPlugin())) {
				// You might want to make this a public variable inside your
				// MAIN class public Method Method = null;
				// then reference it through this.plugin.Method so that way you
				// can use it in the rest of your plugin ;)
				StatsPlugin.Method = this.Methods.getMethod();
				System.out.println("[" + plugin.info.getName()
						+ "] Payment method found (" + StatsPlugin.Method.getName()
						+ " version: " + StatsPlugin.Method.getVersion() + ")");
			}
		}
	}
}