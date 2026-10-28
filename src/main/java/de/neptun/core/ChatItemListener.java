package de.neptun.core;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.StringJoiner;

/** Replaces [i] in chat with the player's held item, including its name and enchantments. */
public final class ChatItemListener implements Listener {
    private final JavaPlugin plugin;

    public ChatItemListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onChat(AsyncChatEvent event) {
        Player player = event.getPlayer();
        ItemStack stack = player.getInventory().getItemInMainHand();
        if (stack.getType().isAir()) {
            return;
        }

        Component item = itemComponent(stack);
        // Component is immutable; assign the replaced message back to the event.
        event.message(event.message().replaceText(net.kyori.adventure.text.TextReplacementConfig.builder()
                .matchLiteral("[i]")
                .replacement(item)
                .build()));
    }

    private Component itemComponent(ItemStack stack) {
        ItemMeta meta = stack.getItemMeta();
        Component name;
        if (meta != null && meta.hasDisplayName() && meta.displayName() != null) {
            name = meta.displayName();
        } else {
            name = Component.text(pretty(stack.getType().name()), NamedTextColor.WHITE);
        }
        Component visible = Component.text("[", NamedTextColor.DARK_GRAY)
                .append(name.decorate(TextDecoration.BOLD))
                .append(Component.text(" x" + stack.getAmount() + "]", NamedTextColor.GRAY));

        StringJoiner details = new StringJoiner("\n");
        details.add(PlainTextComponentSerializer.plainText().serialize(name));
        details.add("Menge: " + stack.getAmount());
        if (meta != null && meta.hasLore() && meta.lore() != null) {
            meta.lore().forEach(line -> details.add(PlainTextComponentSerializer.plainText().serialize(line)));
        }
        Map<Enchantment, Integer> enchants = stack.getEnchantments();
        if (!enchants.isEmpty()) {
            details.add("Verzauberungen:");
            enchants.forEach((enchantment, level) -> details.add(pretty(enchantment.getKey().getKey()) + " " + roman(level)));
        }
        return visible.hoverEvent(HoverEvent.showText(Component.text(details.toString(), NamedTextColor.GRAY)));
    }

    private String pretty(String input) {
        String[] words = input.toLowerCase(java.util.Locale.ROOT).split("_");
        StringBuilder out = new StringBuilder();
        for (String word : words) {
            if (word.isEmpty()) continue;
            if (!out.isEmpty()) out.append(' ');
            out.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1));
        }
        return out.toString();
    }

    private String roman(int level) {
        String[] numerals = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
        return level > 0 && level < numerals.length ? numerals[level] : Integer.toString(level);
    }
}
