import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CarriageKeyListener extends KeyAdapter
{
    Main main;
    TextField textField;
    TextFieldOperations textFieldOperations;
    public CarriageKeyListener(Main main)
    {
        this.main=main;
        textField=main.getTextField();
        textFieldOperations=textField.getTextFieldOperations();
    }
    @Override
    public void keyPressed(KeyEvent e)
    {
        if(e.getKeyCode()==KeyEvent.VK_LEFT && e.isShiftDown())
            textFieldOperations.selectToLeft();
        if(e.getKeyCode()==KeyEvent.VK_RIGHT && e.isShiftDown())
            textFieldOperations.selectToRight();
        if(e.getKeyCode()==KeyEvent.VK_UP && e.isShiftDown())
            textFieldOperations.selectToUp();
        if(e.getKeyCode()==KeyEvent.VK_DOWN && e.isShiftDown())
            textFieldOperations.selectToDown();

        if(e.getKeyCode()==KeyEvent.VK_LEFT && !e.isShiftDown())
        {
            textFieldOperations.removeSelection();
            textFieldOperations.moveCarriageToLeft();
        }
        if(e.getKeyCode()==KeyEvent.VK_RIGHT && !e.isShiftDown())
        {
            textFieldOperations.removeSelection();
            textFieldOperations.moveCarriageToRight();
        }
        if(e.getKeyCode()==KeyEvent.VK_UP && !e.isShiftDown())
        {
            textFieldOperations.removeSelection();
            textFieldOperations.moveCarriageToUp();
        }
        if(e.getKeyCode()==KeyEvent.VK_DOWN && !e.isShiftDown())
        {
            textFieldOperations.removeSelection();
            textFieldOperations.moveCarriageToDown();
        }
        main.updateWindow();
    }
}