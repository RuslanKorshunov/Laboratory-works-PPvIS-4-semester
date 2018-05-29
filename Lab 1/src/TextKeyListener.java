import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TextKeyListener extends KeyAdapter
{
    Main main;
    TextField textField;
    TextFieldOperations textFieldOperations;
    public TextKeyListener(Main main)
    {
        this.main=main;
        textField=main.getTextField();
        textFieldOperations=textField.getTextFieldOperations();
    }
    @Override
    public void keyPressed(KeyEvent e)
    {
        if(e.getKeyCode()==KeyEvent.VK_ENTER)
        {
            if(!textFieldOperations.getSelectedChars().isEmpty())
                textFieldOperations.backSpaceSelection();
            textFieldOperations.addNewLine();
        }
        int width=main.getScrollPane().getWidth();
        width=textFieldOperations.getCarriage().getCarCoordinateX()>width ?
                textFieldOperations.getCarriage().getCarCoordinateX():width;
        int height=main.getScrollPane().getHeight();
        height=textFieldOperations.getCarriage().getCarCoordinateY()>height ?
                textFieldOperations.getCarriage().getCarCoordinateY():height;
        JViewport jViewport=main.getScrollPane().getViewport();
        jViewport.setViewPosition(new Point(width, height));
        main.getScrollPane().setViewport(jViewport);
        main.updateWindow();
    }
    @Override
    public void keyTyped(KeyEvent e)
    {
        if(e.getKeyChar()!=KeyEvent.VK_BACK_SPACE && e.getKeyChar()!=KeyEvent.VK_ENTER && e.getKeyChar()!=KeyEvent.VK_DELETE && !e.isControlDown())
        {
            if(!textFieldOperations.getSelectedChars().isEmpty())
                textFieldOperations.backSpaceSelection();
            textFieldOperations.addNewChar(new Char(e.getKeyChar()));
        }
        main.updateWindow();
    }
}