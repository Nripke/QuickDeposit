package quickdepositpackage.chestclick;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import quickdepositpackage.Main;

public class ClickChest implements Listener
{
    boolean isShifting = false;

    @EventHandler
    public void onPlayerSneak(PlayerToggleSneakEvent event)
    {
        if (event.isSneaking())
        {
            isShifting = true;
        }else {
            isShifting = false;
        }
    }

    @EventHandler
    public void onPlayerClick(PlayerInteractEvent event)
    {
        Action ac = event.getAction();
        if (ac == Action.LEFT_CLICK_BLOCK && isShifting)
        {
            Block blockClicked = event.getClickedBlock();
            if (blockClicked.getType() == Material.CHEST) {
                Player plr = event.getPlayer();
                ItemStack items = event.getItem();
                Chest chest = (Chest) blockClicked.getState();
                Block blockAbove = blockClicked.getRelative(0, 1, 0);

                double size1 = blockAbove.getBoundingBox().getWidthX();
                double size2 = blockAbove.getBoundingBox().getHeight();
                double size3 = blockAbove.getBoundingBox().getWidthZ();

                if (Main.getRank(plr) == Main.Rank.JESTER)
                {
                    if (size1 != 1 && size2 != 1 && size3 != 1) {
                        if (items == null) {
                            plr.sendMessage(ChatColor.RED + "You have nothing to deposit!");
                        } else if (!isChestFull(chest, items)) {
                            plr.sendMessage(ChatColor.GREEN + "You have deposited your ItemStack!");
                            chest.getInventory().addItem(items);
                            plr.getInventory().setItemInMainHand(null); //You cannot simply remove the itemstack, because the player might have the exact same itemstack object in a different location
                        } else {
                            plr.sendMessage(ChatColor.RED + "There is not enough space inside the chest to deposit your items!");
                        }
                    } else {
                        plr.sendMessage(ChatColor.RED + "There is a solid block above this chest!");
                    }
                }else {
                    plr.sendMessage(ChatColor.RED + "The JESTER rank is required to do that!");
                }
            }
        }

    }

    public boolean isChestFull(Chest chest, ItemStack items)
    {
        int extraSpace = 0;

        for (ItemStack i : chest.getInventory().getContents())
        {
            if (i == null)
            {
                return false;
            }
            if (i.getType() == items.getType())
            {
                int max = i.getMaxStackSize();
                if (i.getAmount() + items.getAmount() <= max)
                {
                    return false;
                }else {
                    extraSpace += (max - i.getAmount());
                }
            }
        }
        if (extraSpace >= items.getAmount()) //Checks if the items could be distributed throughout multiple stacks
        {
            return false;
        }
        return true;
    }

}

