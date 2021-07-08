package quickdepositpackage;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import quickdepositpackage.chestclick.ClickChest;
import quickdepositpackage.commands.CommandTesting;
import java.util.HashMap;


public class Main extends JavaPlugin
{

        public static enum Rank
        {
            SQUIRE,
            JESTER
        }

        static HashMap<Player, Rank> rank = new HashMap<Player, Rank>();
        static HashMap<Rank, String> prefix = new HashMap<Rank, String>();

        @Override
        public void onEnable()
        {
            CommandTesting command = new CommandTesting();
            getCommand("heal").setExecutor(command);
            getCommand("feed").setExecutor(command);
            getCommand("squire").setExecutor(command);
            getCommand("jester").setExecutor(command);

            getServer().getPluginManager().registerEvents(new ClickChest(), this);

            prefix.put(Rank.SQUIRE, "<Squire>");
            prefix.put(Rank.JESTER, "<JESTER>");

            getLogger().info("~QuickDeposit Plugin has been ENABLED on this world~");
        }

        @Override
        public void onDisable()
        {
            getLogger().info("~QuickDeposit Plugin has been DISABLED on this world~");
        }

        public static void setRank(Player plr, Rank r)
        {
            rank.put(plr, r);
            plr.setDisplayName(prefix.get(r) + plr.getName());
            plr.setPlayerListName(prefix.get(r) + plr.getName());
        }

        public static Rank getRank(Player plr)
        {
            Rank r = rank.get(plr);
            return r;
        }
}


