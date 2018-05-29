import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CtrlKeyListener extends KeyAdapter
{
    Main main;
    TextField textField;
    TextFieldOperations textFieldOperations;

    public CtrlKeyListener(Main main)
    {
        this.main=main;
        textField=main.getTextField();
        textFieldOperations=textField.getTextFieldOperations();
    }

    public void keyPressed(KeyEvent e)
    {
        if(e.isControlDown() && e.getKeyCode()==KeyEvent.VK_C)
            textFieldOperations.copyText();
        if(e.isControlDown() && e.getKeyCode()==KeyEvent.VK_V)
            textFieldOperations.pasteText();
        if(e.isControlDown() && e.getKeyCode()==KeyEvent.VK_X)
            textFieldOperations.cutText();
        main.updateWindow();
    }
}
