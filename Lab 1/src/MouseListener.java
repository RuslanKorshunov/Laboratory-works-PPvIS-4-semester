import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;


public class MouseListener extends MouseAdapter
{
    private Point2D click;
    private Main main;
    private TextField textField;
    private TextFieldOperations textFieldOperations;

    public MouseListener(Main main)
    {
        this.main=main;
        textField=main.getTextField();
        textFieldOperations=textField.getTextFieldOperations();
    }
    @Override
    public void mouseClicked(MouseEvent me)
    {
        main.getFrame().requestFocusInWindow();
        textFieldOperations.removeSelection();
        textFieldOperations.click(me.getPoint());
        main.updateWindow();
    }
    @Override
    public void mousePressed(MouseEvent me)
    {
        click=me.getPoint();
    }
}