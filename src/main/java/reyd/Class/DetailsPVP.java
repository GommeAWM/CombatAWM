package reyd.Class;

import cn.nukkit.Player;
import cn.nukkit.utils.DummyBossBar;
import reyd.CombatMain;

public class DetailsPVP {

    private int time;
    private Player thisPerson;
    private Player oppositePlayer;

    private DummyBossBar dummyBossBar;

    public DetailsPVP(Player thisPerson, Player oppositePlayer){

        time = 20;
        this.thisPerson = thisPerson;
        this.oppositePlayer = oppositePlayer;


        StringBuilder builder = new StringBuilder()
                .append(CombatMain.firstText)
                .append(time)
                .append("\n\n")
                .append(CombatMain.secondText)
                .append(oppositePlayer.getName());


        dummyBossBar = new DummyBossBar.Builder(thisPerson)
                .text(builder.toString())
                .build();
        thisPerson.createBossBar(dummyBossBar);


    }

    public void reloadCooldown(Player newOppositePlayer){

        time = 20;
        this.oppositePlayer = newOppositePlayer;

        StringBuilder builder = new StringBuilder()
                .append(CombatMain.firstText)
                .append(time)
                .append("\n\n")
                .append(CombatMain.secondText)
                .append(oppositePlayer.getName());

        dummyBossBar.setText(builder.toString());
        dummyBossBar.setLength(20 * 5);


    }

    public int decreaseCooldown(){

        time--;

        if (time == 0){
            dummyBossBar.destroy();
            return 0;
        }


        StringBuilder builder = new StringBuilder()
                .append(CombatMain.firstText)
                .append(time)
                .append("\n\n")
                .append(CombatMain.secondText)
                .append(oppositePlayer.getName());

        dummyBossBar.setText(builder.toString());
        dummyBossBar.setLength(time * 5);
        return 1;
    }

    public void removePlayer(){

        dummyBossBar.destroy();
        CombatMain.getInstance().cooldownH.remove(thisPerson.getName());

    }

    public void quitGame(){

        dummyBossBar.destroy();
        thisPerson.kill();
        CombatMain.getInstance().cooldownH.remove(thisPerson.getName());

    }

}
