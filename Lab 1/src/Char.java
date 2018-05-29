import java.awt.*;

public class Char
{
    private char ch;
    private int fontStyle;
    private int fontSize;
    private int charCoordinateX;
    private int width;
    private String fontName;

    public Char(char ch)
    {
        this.ch=ch;
        fontName="Verdana";
        fontSize=14;
        fontStyle = Font.PLAIN;
    }

    public Char(Char oldChar)
    {
        ch=oldChar.getCharCh();
        fontName=oldChar.getFontName();
        fontSize=oldChar.getFontSize();
        fontStyle=oldChar.getFontStyle();
    }

    public String getStringCh()
    {
        return Character.toString(ch);
    }

    private char getCharCh()
    {
        return ch;
    }

    public void setFontStyle(int fontStyle)
    {
        this.fontStyle=fontStyle;
    }

    public int getFontStyle()
    {
        return fontStyle;
    }

    public void setFontSize(int fontSize)
    {
        this.fontSize=fontSize;
    }

    public int getFontSize()
    {
        return fontSize;
    }

    public void setFontName(String fontName)
    {
        this.fontName=fontName;
    }

    public String getFontName()
    {
        return fontName;
    }

    public Font getFont()
    {
        return new Font(fontName, fontStyle, fontSize);
    }

    public void setCharCoordinateX(int x)
    {
        charCoordinateX=x;
    }

    public int getCharCoordinateX()
    {
        return charCoordinateX;
    }

    public void setWidth(int w)
    {
        width=w;
    }

    public int getWidth()
    {
        return width;
    }
}