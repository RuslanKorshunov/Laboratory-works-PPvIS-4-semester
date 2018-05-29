import javax.swing.*;
import java.awt.*;

public class TextField extends JPanel
{
    private final int START_X_COORDINATE=20;
    private final int START_Y_COORDINATE=6;
    private TextFieldOperations textFieldOperations;
    private Graphics2D graphics2D;
    private int mWidth;

    public TextField()
    {
        textFieldOperations=new TextFieldOperations(this);
    }

    public void paintComponent(Graphics graphics)
    {
        graphics2D=(Graphics2D) graphics;
        int y=START_Y_COORDINATE;
        for(Line line: textFieldOperations.getLines())
        {
            y+=line.getMaxHeight();
            line.setCoordinateY(y);
            int x=START_X_COORDINATE;
            for(Char ch:line.getChars())
            {
                graphics2D.setColor(Color.BLACK);
                graphics2D.setFont(ch.getFont());
                FontMetrics fm=graphics2D.getFontMetrics();
                if(textFieldOperations.getSelectedChars().contains(ch))
                {
                    graphics2D.setColor(new Color(44,137,99));
                    graphics2D.fillRect(x, y-line.getMaxHeight(), fm.stringWidth(ch.getStringCh()), line.getMaxHeight());
                    graphics2D.setColor(Color.WHITE);
                }
                graphics2D.drawString(ch.getStringCh(),x,y);
                ch.setWidth(fm.stringWidth(ch.getStringCh()));
                ch.setCharCoordinateX(x);
                x+=fm.stringWidth(ch.getStringCh());
            }

            mWidth= x > mWidth ? x:mWidth;
        }
        setPreferredSize(new Dimension(mWidth+20, y+20));
    }

    public TextFieldOperations getTextFieldOperations()
    {
        return textFieldOperations;
    }

    public void drawCarriage()
    {
        int carCoordinateY=textFieldOperations.getActiveLine().getCoordinateY();
        textFieldOperations.getCarriage().setCarCoordinateY(carCoordinateY);
        int carCoordinateX=START_X_COORDINATE;
        if(textFieldOperations.getIndexOfActiveCh()!=-1)
            carCoordinateX=textFieldOperations.getActiveChar().getCharCoordinateX()+textFieldOperations.getActiveChar().getWidth();
        textFieldOperations.getCarriage().setCarCoordinateX(carCoordinateX);
        int carCoordinateYH=carCoordinateY-textFieldOperations.getActiveLine().getMaxHeight();
        if(textFieldOperations.getActiveLine().size()!=0)
            if(textFieldOperations.getIndexOfActiveCh()!=-1)
                carCoordinateYH=carCoordinateY-textFieldOperations.getActiveChar().getFontSize();
            else
                carCoordinateYH=carCoordinateY-textFieldOperations.getActiveLine().getSingleChar(0).getFontSize();

        graphics2D=(Graphics2D)this.getGraphics();
        graphics2D.drawLine(carCoordinateX, carCoordinateY, carCoordinateX, carCoordinateYH);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        graphics2D.setColor(this.getBackground());
        graphics2D.drawLine(carCoordinateX, carCoordinateY, carCoordinateX, carCoordinateYH);
    }
}