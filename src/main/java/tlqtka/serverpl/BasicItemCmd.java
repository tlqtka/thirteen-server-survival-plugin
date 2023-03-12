package tlqtka.serverpl;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class BasicItemCmd implements CommandExecutor {

    private Set<Player> executedPlayers = new HashSet<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (label.equalsIgnoreCase("기본템")) {
            if (executedPlayers.contains(sender)) {
                sender.sendMessage(ChatColor.RED + "이미 기본템을 받으셨습니다.");
            } else {
                Player player = (Player) sender;
                ItemStack item = new ItemStack(Material.BREAD, 32);
                player.getInventory().addItem(item);
                executedPlayers.add(player);
            }
        }
        if (label.equalsIgnoreCase("trade")) {
            if (sender instanceof ConsoleCommandSender) {
                Main trade = new Main();
                trade.Trade = true;
                Bukkit.broadcastMessage(ChatColor.AQUA + "주민 거래가 활성화되었습니다.\n주민과의 거래로 다양한 품목의 아이템을 얻어보세요!");
            } else {
                sender.sendMessage(ChatColor.RED + "해당 명령어는 서버 관리자만 사용할 수 있습니다.");
            }
        }
        if (label.equalsIgnoreCase("wither")) {
            if (sender instanceof ConsoleCommandSender) {
                Main wither = new Main();
                wither.Wither = true;
                Bukkit.broadcastMessage(ChatColor.AQUA + "이제부터 위더 스켈레톤이 지옥 유적에 소환됩니다.\n위더를 잡아서 다양한 전리품을 얻어보세요!");
            } else {
                sender.sendMessage(ChatColor.RED + "해당 명령어는 서버 관리자만 사용할 수 있습니다.");
            }
        }
        if (label.equalsIgnoreCase("warden")) {
            if (sender instanceof ConsoleCommandSender) {
                Main warden = new Main();
                warden.Warden = true;
                Bukkit.broadcastMessage(ChatColor.AQUA + "이제부터 워든이 고대 도시에 소환됩니다..\n워든을 소환하고  " + ChatColor.GREEN + "[워든 처치하기]" + ChatColor.AQUA + " 발전 과제를 달성해보세요!");
            } else {
                sender.sendMessage(ChatColor.RED + "해당 명령어는 서버 관리자만 사용할 수 있습니다.");
            }
        }
        return true;
    }
}
