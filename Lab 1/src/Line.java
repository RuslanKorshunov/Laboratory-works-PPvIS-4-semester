import java.util.LinkedList;
import java.util.List;

public class Line
{
    private List<Char> chars;
    private boolean activity;
    private int coordinateY;
    private int maxHeight;
    private boolean selected;

    public Line(int coordinateY)
    {
        chars=new LinkedList<Char>();
        this.coordinateY=coordinateY;
        activity=true;
        maxHeight=14;
    }

    public Line()
    {
        chars=new LinkedList<Char>();
        coordinateY=0;
        activity=true;
    }

    public Line(Line oldLine)
    {
        this.chars=new LinkedList<>();
        for(Char ch:oldLine.chars)
            this.chars.add(new Char(ch));
        this.maxHeight=oldLine.maxHeight;
    }

    public void add(Char ch)
    {
        chars.add(ch);
    }

    public void add(int index, Char ch)
    {
        chars.add(index, ch);
    }

    public void addAll(List<Char> List)
    {
        chars.addAll(List);
    }

    public void remove(int index)
    {
        chars.remove(index);
    }

    public void removeAll(List<Char> List)
    {
        chars.removeAll(List);
    }

    public int size()
    {
        return chars.size();
    }

    public int indexOf(Char ch)
    {
        return chars.indexOf(ch);
    }

    public List<Char> getChars()
    {
        return chars;
    }

    public Char getSingleChar(int index)
    {
        return chars.get(index);
    }

    public void setCoordinateY(int coordinateY)
    {
        this.coordinateY=coordinateY;
    }

    public int getCoordinateY()
    {
        return coordinateY;
    }

    public void setMaxHeight(int maxHeight)
    {
        this.maxHeight=maxHeight;
    }

    public void setMaxHeight()
    {
        int mH=0;
        if(chars.size()!=0)
        {
            for(int i=0;i<chars.size();i++)
                if(getSingleChar(i).getFontSize()>mH)
                    mH=getSingleChar(i).getFontSize();
            maxHeight=mH;
        }
    }

    public int getMaxHeight()
    {
        return maxHeight;
    }
}