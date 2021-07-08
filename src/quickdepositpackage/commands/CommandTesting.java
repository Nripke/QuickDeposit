package quickdepositpackage.commands;

import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import quickdepositpackage.Main;

public class CommandTesting implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!(sender instanceof Player)) // If person executing command is not a player, stop method
        {
            return true;
        }

        Player plr = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("heal")) //If player runs /heal command in chat
        {
            double maxHealth = plr.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue(); //Finds max health possible

            plr.setHealth(maxHealth); //Sets health to that max health
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("feed")) //If player runs /feed command in chat
        {
            plr.setFoodLevel(20); //Gives max food
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("jester")) //If player runs /jester command in chat
        {
            Main.setRank(plr, Main.Rank.JESTER);
            plr.sendMessage(ChatColor.GREEN + "Your rank is now Jester!");
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("squire")) //If player runs /squire command in chat
        {
            Main.setRank(plr, Main.Rank.SQUIRE);
            plr.sendMessage(ChatColor.GREEN + "Your rank is now Squire!");
            return true;
        }
        return false;
    }

}
