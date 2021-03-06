package support.plugin.onyx;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import support.plugin.onyx.commands.OnyxCommand;
import support.plugin.onyx.commands.handler.CommandHandler;
import support.plugin.onyx.config.Configuration;
import support.plugin.onyx.factions.FactionManager;
import support.plugin.onyx.factions.commands.FactionCreateCommand;
import support.plugin.onyx.factions.commands.FactionDisbandCommand;
import support.plugin.onyx.factions.commands.FactionInviteCommand;
import support.plugin.onyx.factions.commands.FactionUninviteCommand;
import support.plugin.onyx.listeners.*;
import support.plugin.onyx.profiles.ProfileManager;
import support.plugin.onyx.timer.TimerManager;

import java.util.Arrays;

/*
Copyright (c) 2017 PluginManager LTD. All rights reserved.
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge and/or publish copies of the Software,
and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:
The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
Any copies of the Software shall stay private and cannot be resold.
Credit to PluginManager LTD shall be expressed in all forms of advertisement and/or endorsement.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
public class Onyx extends JavaPlugin {

    @Getter
    private static Onyx instance;

    @Getter
    private Configuration settings, locale;

    @Getter
    private FactionManager factionManager;

    @Getter
    private TimerManager timerManager;

    @Getter
    private ProfileManager profileManager;

    public void onEnable() {

        instance = this;

        loadConfiguration();
        loadCommands();
        loadListeners();

        factionManager = new FactionManager(this);
        timerManager = new TimerManager(this);
        profileManager = new ProfileManager(this);

        getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                save();
            }
        }, 10 * (60 * 20L), 10 * (60 * 20L)); // Runs every 10 minutes

    }

    public void onDisable() {

        save();

        instance = null;

    }

    private synchronized void loadConfiguration() {

        this.settings = new Configuration(this, "settings");
        this.locale = new Configuration(this, "locale");

    }

    private synchronized void loadCommands() {

        getCommand("onyx").setExecutor(new OnyxCommand());

        getCommand("factions").setExecutor(factionsCommandsHandler());

    }

    private synchronized void loadListeners() {

        PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new CombatListener(), this);
        pluginManager.registerEvents(new EnderpearlThrowListener(), this);
        pluginManager.registerEvents(new StuckListener(this), this);
        pluginManager.registerEvents(new JoinListener(this), this);
        pluginManager.registerEvents(new ChatListener(this), this);
        pluginManager.registerEvents(new FastSmeltListener(), this);
        pluginManager.registerEvents(new TntListener(), this);

    }

    private synchronized void save() {

        Bukkit.broadcastMessage(ChatColor.GREEN + "Saving all factions and player data...");
        profileManager.save();
        factionManager.save();
        timerManager.save();
        Bukkit.broadcastMessage(ChatColor.GREEN + "Done!");

    }

    private synchronized CommandHandler factionsCommandsHandler() {
        CommandHandler commandHandler = new CommandHandler("factions", "All factions commands for Onyx", "/f <subcommand> [options]", Arrays.asList("f", "t", "fac", "teams", "faction", "team"));

        commandHandler.addSubCommand("create", new FactionCreateCommand(this));
        commandHandler.addSubCommand("disband", new FactionDisbandCommand(this));
        commandHandler.addSubCommand("invite", new FactionInviteCommand(this));
        commandHandler.addSubCommand("uninvite", new FactionUninviteCommand(this));

        return commandHandler;
    }

}
