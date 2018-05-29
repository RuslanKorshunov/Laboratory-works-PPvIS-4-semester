import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class DeleteKeyListener extends KeyAdapter
{
    Main main;
    TextField textField;
    TextFieldOperations textFieldOperations;

    public DeleteKeyListener(Main main)
    {
        this.main=main;
        textField=main.getTextField();
        textFieldOperations=textField.getTextFieldOperations();
    }
    @Override
    public void keyPressed(KeyEvent e)
    {
        if(e.getKeyCode()==KeyEvent.VK_BACK_SPACE)
            if(!textFieldOperations.getSelectedChars().isEmpty())
                textFieldOperations.backSpaceSelection();
            else if(textFieldOperations.getIndexOfActiveCh()==-1)
                textFieldOperations.backSpaceLine();
            else
                textFieldOperations.backSpaceChar();
        if(e.getKeyCode()==KeyEvent.VK_DELETE)
            if(!textFieldOperations.getSelectedChars().isEmpty())
                textFieldOperations.deleteSelection();
            else
                textFieldOperations.deleteChar();
        main.updateWindow();
    }
}
