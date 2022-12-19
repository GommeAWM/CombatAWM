package reyd.Listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.player.PlayerDeathEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import reyd.CombatMain;

public class PVPListener implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {

        // PVP STUFF
        if (!event.isCancelled()) {
            if (event.getEntity() instanceof Player) {
                if (event.getDamager() instanceof Player) {

                    // add to cooldown
                    if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                        CombatMain.getInstance().startPVP(event.getEntity().getName(), (Player) event.getDamager(), ((Player) event.getEntity()).getPlayer());
                        CombatMain.getInstance().startPVP(event.getDamager().getName(), (Player) event.getEntity(), ((Player) event.getDamager()).getPlayer());
                    }

                    // add to cooldown
                    if (event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE){
                        CombatMain.getInstance().startPVP(event.getEntity().getName(), (Player) event.getDamager(), ((Player) event.getEntity()).getPlayer());
                        CombatMain.getInstance().startPVP(event.getDamager().getName(), (Player) event.getEntity(), ((Player) event.getDamager()).getPlayer());
                    }

                }
            }
        }
    }

    @EventHandler
    public void onQuitEvent(PlayerQuitEvent event) {

        // If player quit the Game he will die
        if (CombatMain.getInstance().cooldownH.containsKey(event.getPlayer().getName())){
            CombatMain.getInstance().cooldownH.get(event.getPlayer().getName()).quitGame();
        }

    }

    @EventHandler
    public void onDeathEvent(PlayerDeathEvent event) {

        // After the Death of Entity remove the BossBar
        if (CombatMain.getInstance().cooldownH.containsKey(event.getEntity().getName())){
            CombatMain.getInstance().cooldownH.get(event.getEntity().getName()).removePlayer();
        }

    }

}
