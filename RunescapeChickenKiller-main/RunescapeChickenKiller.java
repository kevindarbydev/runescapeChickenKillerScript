
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.dialogues.Dialogues;
import org.dreambot.api.methods.input.Camera;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.item.GroundItems;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.items.GroundItem;
import org.dreambot.api.wrappers.widgets.message.Message;

// first ever script I wrote
@ScriptManifest(name = "First Script", description = "My script description!", author = "Developer Name",
        version = 2.0, category = Category.COMBAT, image = "")
public class chickenKillerInProgress extends AbstractScript {
    State state;
    String s;
    int killed;

    boolean isFighting;
    boolean isLoot;

    Area chimkenArea = new Area(3171, 3300, 3184, 3290);
    NPC npc = NPCs.closest( chim -> chim != null && chim.getName().equals("Chicken") && !chim.isInCombat() && chimkenArea.contains(chim));

    @Override // Infinite loop
    public int onLoop() {
    switch(getState()){


        case FIGHTING:
            log("FIGHTING");




            if(npc != null) {
                if(!npc.exists()  || npc.getHealthPercent() == 0 ){
                    killed++;
                    npc=null;
                }
            }

            Dialogues d = getDialogues();
while (d.inDialogue()) {
    if (d.canContinue()){
        d.spaceToContinue();
    }
}
break;





        case LOOKINGFORCHICKEN:
            log("LOOKINGFORCHICKEN");
           if (!npc.isOnScreen()){
               Camera.keyboardRotateToEntity(npc);
               sleep(Calculations.random(500,1000));
           }
           npc.interact("Attack");
            sleep(Calculations.random(500,1000));
            break;



    }
    return 0;




    }

    //State names
    private enum State{
        FIGHTING, LOOKINGFORCHICKEN

    }
    //Checks if a certain condition is met and then returns that state
    //Define all states the bot should be in
    private State getState() {


        if (chimkenArea.contains(getLocalPlayer()) && getLocalPlayer().isInCombat()){
            state = State.FIGHTING;
        }  else if (chimkenArea.contains(getLocalPlayer()) && !getLocalPlayer().isInCombat()){
            state = State.LOOKINGFORCHICKEN;
        }

        return state;
    }
    public void onStart() {
        log("Bot started");
    }
    public void onExit(){
        log("Bot ended!");
    }
    public int randomNum(int i, int k){
        int number = (int)(Math.random() * (k - i)) + i;
        return number;
    }

}
