package reyd;

import cn.nukkit.Player;
import cn.nukkit.plugin.PluginBase;
import reyd.Class.DetailsPVP;
import reyd.Listener.PVPListener;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CombatMain extends PluginBase {

    public HashMap<String, DetailsPVP> cooldownH = new HashMap<>();     // CountDown (CoolDown) to save players that are in Combat (ct)
    private static CombatMain instance;
    public static String firstText;
    public static String secondText;

    @Override
    public void onLoad() {
        instance = this;
        this.saveDefaultConfig();
    }

    @Override
    public void onEnable() {

        // text
        firstText = getConfig().getString("DetailsPVP.firstText");
        secondText = getConfig().getString("DetailsPVP.secondText");

        // timer
        startPVPTimer();

        // register Listener
        getServer().getPluginManager().registerEvents(new PVPListener(), instance);

        // Standard
        super.onEnable();
    }

    private void startPVPTimer() {
        try {
            this.getServer().getScheduler().scheduleRepeatingTask(instance, new Runnable() {
                public void run() {

                    if (cooldownH.isEmpty()){
                        return;
                    }

                    Iterator<Map.Entry<String, DetailsPVP>> itr = cooldownH.entrySet().iterator();


                    while (itr.hasNext()) {
                        DetailsPVP value = itr.next().getValue();
                        if (value.decreaseCooldown() == 0) {
                            itr.remove();
                        }
                    }
                }
            }, 20);
        } catch (ConcurrentModificationException var2) {
        }

    }

    public void startPVP(String name, Player newOppositePlayer, Player thisPlayer) {
        if (cooldownH.containsKey(name)){
            cooldownH.get(name).reloadCooldown(newOppositePlayer);
        } else {
            cooldownH.put(name, new DetailsPVP(thisPlayer, newOppositePlayer));
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public static CombatMain getInstance() {
        return instance;
    }
}
