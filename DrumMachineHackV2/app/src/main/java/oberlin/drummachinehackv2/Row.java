package oberlin.drummachinehackv2;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;

import io.realm.RealmObject;

/**
 * Created by Lukas on 9/23/17.
 */

public class Row {
    boolean[] buttons;
    int numButtons;
    Context context;
    int buttonSelected = -1;
    int i = -1;

    public Row(int numButtons, Context context){
        buttons = new boolean[numButtons];
        this.numButtons = numButtons;
        this.context = context;
        createButtons(numButtons);

    }

    private void createButtons(int numButtons) {
        for (int i = 0; i < numButtons; i++) {
            buttons[i] = false;
        }
    }

    public void moveMetronome(){
        buttonSelected++;
    }

    public void setButtonSelected(int index, boolean selected){
        buttons[index] = selected;
    }

    public int getSelectedColumn(){
        return buttonSelected % numButtons;
    }

    public int getNumButtons() {
        return numButtons;
    }

    public boolean isSelected(int index) {
        return buttons[index];
    }
}
