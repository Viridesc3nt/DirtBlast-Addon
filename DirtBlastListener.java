package me.justinjaques.dirtblast;

import com.projectkorra.projectkorra.ProjectKorra;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.ability.CoreAbility;

public class DirtBlastListener implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        if(event.getAction() != Action.LEFT_CLICK_AIR || event.getAction() != Action.LEFT_CLICK_BLOCK){
            Player player = event.getPlayer();
            BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);

           // if(bPlayer.canBend(CoreAbility.getAbility(DirtBlast.class))) {
            new DirtBlast(player);
            ProjectKorra.log.info("Dirtblast Fired!");
            //}

        } else {
            return;

        }
