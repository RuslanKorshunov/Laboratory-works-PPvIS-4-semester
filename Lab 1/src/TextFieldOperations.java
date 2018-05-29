//13.04 - функция вставить
//14.04 - бэкспэйс и удаление выделенного фрагмента, замена его на символ или новую строку, вырезание
//21.04 - проблема с вставкой
import java.awt.geom.Point2D;
import java.util.*;

public class TextFieldOperations
{
    private TextField textField;
    private List<Line> lines;
    private List<Line> copiedLines;
    private List<Char> selectedChars;
    private int indexOfActiveLine=0;
    private int indexOfActiveCh=-1;
    private final int START_Y_COORDINATE=6;
    private Carriage carriage;

    public TextFieldOperations(TextField textField)
    {
        this.textField=textField;
        lines=new ArrayList<>();
        copiedLines=new ArrayList<>();
        selectedChars=new LinkedList<>();
        lines.add(new Line(START_Y_COORDINATE));
        carriage=new Carriage();
        carriageTimer();
    }

    private void carriageTimer()
    {
        Timer timer=new Timer();
        timer.schedule(new TimerTask()
        {
            public void run()
            {
                textField.drawCarriage();
            }
        }, 500, 1000);
    }

    public void clearLines()
    {
        lines.clear();
    }

    public List<Line> getLines()
    {
        return lines;
    }

    public List<Char> getSelectedChars()
    {
        return selectedChars;
    }

    public Line getActiveLine()
    {
        return lines.get(indexOfActiveLine);
    }

    public Char getActiveChar()
    {
        return getActiveLine().getSingleChar(indexOfActiveCh);
    }

    public void addNewLine()
    {
        int lineSize=getActiveLine().size();
        int mH=getActiveLine().getMaxHeight();
        Line line = new Line();
        if(indexOfActiveCh!=-1)
        {
            mH=getActiveChar().getFontSize();
            if(indexOfActiveCh!=lineSize-1)
            {
                for (int i = indexOfActiveCh + 1; i < lineSize; i++)
                {
                    if(getActiveLine().getSingleChar(i).getFontSize()>=mH)
                    {
                        mH=getActiveLine().getSingleChar(i).getFontSize();
                        line.setMaxHeight(mH);
                    }
                    line.add(getActiveLine().getSingleChar(i));
                }
                getActiveLine().removeAll(line.getChars());
                int mHOfOldLine=getActiveLine().getSingleChar(0).getFontSize();
                for (int i=0; i<=indexOfActiveCh; i++)
                    if(getActiveLine().getSingleChar(i).getFontSize()>=mHOfOldLine)
                    {
                        mHOfOldLine=getActiveLine().getSingleChar(i).getFontSize();
                        getActiveLine().setMaxHeight(mHOfOldLine);
                    }
            }
        }
        else
        {
            if(lineSize!=0)
                for(Char ch:getActiveLine().getChars())
                    line.add(ch);
            getActiveLine().getChars().clear();
            if(line.size()!=0)
                getActiveLine().setMaxHeight(line.getSingleChar(0).getFontSize());
        }
        line.setMaxHeight(mH);
        if(indexOfActiveLine==lines.size()-1)
            lines.add(line);
        else
            lines.add(indexOfActiveLine+1, line);
        indexOfActiveLine++;
        indexOfActiveCh=-1;
    }

    public void addNewChar(Char ch)
    {
        if(indexOfActiveCh!=-1)
        {
            ch.setFontStyle(getActiveChar().getFontStyle());
            ch.setFontSize(getActiveChar().getFontSize());
            ch.setFontName(getActiveChar().getFontName());
            getActiveLine().add(indexOfActiveCh+1, ch);
            indexOfActiveCh++;
        }
        else
        {
            if(indexOfActiveLine!=0)
                for(int i=indexOfActiveLine;i>=0;i--)
                {
                    int lineSize=lines.get(i).size();
                    if(lineSize!=0)
                    {
                        int fontStyle=lines.get(i).getSingleChar(lineSize-1).getFontStyle();
                        int fontSize=getActiveLine().getMaxHeight();
                        String fontName=lines.get(i).getSingleChar(lineSize-1).getFontName();
                        ch.setFontStyle(fontStyle);
                        ch.setFontSize(fontSize);
                        ch.setFontName(fontName);
                        break;
                    }
                }
            else
                ch.setFontSize(getActiveLine().getMaxHeight());
            getActiveLine().add(0, ch);
            indexOfActiveCh++;
        }
    }

    public void backSpaceChar()
    {
        getActiveLine().remove(indexOfActiveCh);
        if(indexOfActiveCh!=0)
        {
            indexOfActiveCh--;
        }
        else
        {
            indexOfActiveCh=-1;
        }
        getActiveLine().setMaxHeight();
    }

    public void backSpaceLine()
    {
        int lineSize=getActiveLine().size();
        if(indexOfActiveLine!=0)
        {
            int mH=getActiveLine().getMaxHeight();
            indexOfActiveLine--;
            if(getActiveLine().size()!=0)
            {
                indexOfActiveCh=getActiveLine().size()-1;
            }
            else
                indexOfActiveCh=-1;
            if(lineSize!=0)
                for(Char ch:lines.get(indexOfActiveLine+1).getChars())
                    lines.get(indexOfActiveLine).add(ch);
            if(getActiveLine().getMaxHeight()<mH)
                getActiveLine().setMaxHeight(mH);
            lines.remove(indexOfActiveLine+1);
        }
    }

    public void deleteChar()
    {
        int lineSize=getActiveLine().size();
        if(indexOfActiveCh!=-1)
        {
            if(indexOfActiveCh!=lineSize-1)
                getActiveLine().remove(indexOfActiveCh+1);
            else
                deleteLine();
            getActiveLine().setMaxHeight();
        }
        else
            if(lineSize!=0)
            {
                getActiveLine().remove(0);
                getActiveLine().setMaxHeight();
            }
            else
                deleteLine();
    }

    public void deleteLine()
    {
        if(indexOfActiveLine!=lines.size()-1)
        {
            getActiveLine().addAll(lines.get(indexOfActiveLine + 1).getChars());
            lines.remove(indexOfActiveLine + 1);
            getActiveLine().setMaxHeight();
        }
    }

    public Carriage getCarriage()
    {
        return carriage;
    }

    public void click(Point2D point)
    {
        int coorX=(int)point.getX();
        int coorY=(int)point.getY();
        int previousCoorY=0;
        for(Line line:lines)
        {
            if(coorY>previousCoorY && coorY<=line.getCoordinateY())
            {
                indexOfActiveLine=lines.indexOf(line);
                indexOfActiveCh=-1;
                int previousCoorX=0;
                for(Char ch:line.getChars())
                {
                    if(coorX>previousCoorX && coorX<=ch.getCharCoordinateX()+ch.getWidth())
                    {
                        indexOfActiveCh=line.indexOf(ch);
                        break;
                    }
                    previousCoorX=ch.getCharCoordinateX()+ch.getWidth();
                }
                break;
            }
        }
    }

    public void moveCarriageToLeft()
    {
        if(indexOfActiveCh!=-1)
        {
            indexOfActiveCh--;
        }
        else
        {
            if(indexOfActiveLine!=0)
            {
                indexOfActiveLine--;
                if(getActiveLine().size()!=0)
                {
                    indexOfActiveCh=getActiveLine().size()-1;
                }
                else
                    indexOfActiveCh=-1;
            }
        }
    }

    public void moveCarriageToRight()
    {
        if(indexOfActiveCh==-1)
        {
            boolean editorInActiveLine = getActiveLine().size() != 0;
            if(editorInActiveLine)
            {
                indexOfActiveCh=0;
            }

            else
            {
                if(indexOfActiveLine!=lines.size()-1)
                {
                    indexOfActiveLine++;
                }
            }
        }
        else
        {
            if(indexOfActiveCh!=getActiveLine().size()-1)
            {
                indexOfActiveCh++;
            }
            else if(indexOfActiveLine!=lines.size()-1)
            {
                indexOfActiveLine++;
                indexOfActiveCh=-1;
            }
        }
    }

    public void moveCarriageToUp()
    {
        if(indexOfActiveLine!=0)
        {
            indexOfActiveLine--;
            if(indexOfActiveCh>getActiveLine().size()-1)
                indexOfActiveCh=getActiveLine().size()-1;
        }
        else
        {
            if(getActiveLine().size() != 0)
                indexOfActiveCh=-1;
        }
    }

    public void moveCarriageToDown()
    {
        if(indexOfActiveLine!=lines.size()-1)
        {
            indexOfActiveLine++;
            if(indexOfActiveCh>getActiveLine().size()-1)
                indexOfActiveCh=getActiveLine().size()-1;
        }
        else
        {
            if(getActiveLine().size() != 0)
                indexOfActiveCh=getActiveLine().size()-1;
        }
    }

    public void selectToLeft()
    {
        if(indexOfActiveCh!=-1)
            if(!selectedChars.contains(getActiveChar()))
                selectedChars.add(getActiveChar());
            else
                selectedChars.remove(getActiveChar());
        moveCarriageToLeft();
    }

    public void selectToRight()
    {
        moveCarriageToRight();
        if(indexOfActiveCh!=-1)
            if(!selectedChars.contains(getActiveChar()))
                selectedChars.add(getActiveChar());
            else
                selectedChars.remove(getActiveChar());
    }

    public void selectToUp()
    {
        int index=indexOfActiveCh;
        if(indexOfActiveCh!=-1)
        {
            for(int i=indexOfActiveCh;i>=0;i--)
                selectToLeft();
            moveCarriageToLeft();
            if(index<=getActiveLine().size()-1)
                for(int i=getActiveLine().size()-1;i>=index+1;i--)
                    selectToLeft();
        }
        else
        {
            selectToLeft();
            if(getActiveLine().size()!=0)
                for(int i=getActiveLine().size()-1;i>=0;i--)
                    selectToLeft();
        }
    }

    public void selectToDown()
    {
        int index=indexOfActiveCh;
        int lineSize=getActiveLine().size();
        if (indexOfActiveCh!=-1)
        {
            for(int i=indexOfActiveCh+1;i<=lineSize-1;i++)
                selectToRight();
            moveCarriageToRight();
            if(index>getActiveLine().size()-1)
                index=getActiveLine().size()-1;
            for(int i=0;i<=index;i++)
                selectToRight();
        }
        else
        {
            if(lineSize!=0)
                for(int i=0;i<=lineSize-1;i++)
                    selectToRight();
            moveCarriageToRight();
        }
    }

    public void removeSelection()
    {
        selectedChars.clear();
    }

    public void backSpaceSelection()
    {
        int indexOfFirstLine=indexOfLine(selectedChars.get(0));
        int indexOfFirstCh=lines.get(indexOfFirstLine).indexOf(selectedChars.get(0));
        int indexOfLastLine=indexOfLine(selectedChars.get(selectedChars.size()-1));
        int indexOfLastCh=lines.get(indexOfLastLine).indexOf(selectedChars.get(selectedChars.size()-1));
        if(indexOfFirstLine>indexOfLastLine || indexOfFirstLine==indexOfLastLine && indexOfFirstCh>indexOfLastCh)
        {
            int buffer=indexOfLastLine;
            indexOfLastLine=indexOfFirstLine;
            indexOfFirstLine=buffer;
            buffer=indexOfLastCh;
            indexOfLastCh=indexOfFirstCh;
            indexOfFirstCh=buffer;
        }
        List<Line> deletedLines=new ArrayList<>();
        indexOfActiveLine=indexOfFirstLine;
        indexOfActiveCh=indexOfFirstCh-1;

        for(int i=indexOfFirstLine;i<=indexOfLastLine;i++)
        {
            List<Char> deletedChars=new LinkedList<>();
            for(Char ch:lines.get(i).getChars())
                if(selectedChars.contains(ch))
                    deletedChars.add(ch);
            lines.get(i).removeAll(deletedChars);
            if(lines.get(i).size()==0 && i!=indexOfFirstLine && i!=indexOfLastLine)
                deletedLines.add(lines.get(i));
        }
        lines.removeAll(deletedLines);
        if(getActiveLine().size()==0)
            indexOfActiveCh=-1;
        removeSelection();
    }

    public void deleteSelection()
    {
        backSpaceSelection();
    }

    public void changeStyle(int style)
    {
        for(Char ch:selectedChars)
            if(style==1)
                if(!ch.getFont().isBold())
                    ch.setFontStyle(ch.getFontStyle()+style);
                else
                    ch.setFontStyle(ch.getFontStyle()-style);
            else if(style==2)
                if(!ch.getFont().isItalic())
                    ch.setFontStyle(ch.getFontStyle()+style);
                else
                    ch.setFontStyle(ch.getFontStyle()-style);
    }

    public void changeFontName(String fontName)
    {
        for(Char ch:selectedChars)
        {
            ch.setFontName(fontName);
        }
    }

    private int indexOfLine(Char ch)
    {
        for(int i=indexOfActiveLine;i>=0;i--)
            if(lines.get(i).getChars().contains(ch))
                return i;
        for(int i=indexOfActiveLine;i<lines.size();i++)
            if(lines.get(i).getChars().contains(ch))
                return i;
        return indexOfActiveLine;
    }

    public void changeFontSize(int fontSize)
    {
        int indexOfFirstLine=indexOfLine(selectedChars.get(0));
        int indexOfLastLine=indexOfLine(selectedChars.get(selectedChars.size()-1));
        if(indexOfFirstLine>indexOfLastLine)
        {
            int buffer=indexOfLastLine;
            indexOfLastLine=indexOfFirstLine;
            indexOfFirstLine=buffer;
        }
        for(Char ch:selectedChars)
        {
            ch.setFontSize(fontSize);
        }
        for(int i=indexOfFirstLine;i<=indexOfLastLine;i++)
        {
            if(lines.get(i).getChars().isEmpty())
                lines.get(i).setMaxHeight(fontSize);
            else
                lines.get(i).setMaxHeight();
        }
    }

    public void copyText()
    {
        int indexOfFirstLine=indexOfLine(selectedChars.get(0));
        int indexOfLastLine=indexOfLine(selectedChars.get(selectedChars.size()-1));
        if(indexOfFirstLine>indexOfLastLine)
        {
            int buffer=indexOfLastLine;
            indexOfLastLine=indexOfFirstLine;
            indexOfFirstLine=buffer;
        }
        for(int i=indexOfFirstLine;i<=indexOfLastLine;i++)
        {
            Line line=new Line();
            for(Char ch:lines.get(i).getChars())
                if(selectedChars.contains(ch))
                    line.add(new Char(ch));
            if(line.size()!=0)
                line.setMaxHeight();
            else
                line.setMaxHeight(lines.get(i).getMaxHeight());
            copiedLines.add(line);
        }
    }

    public void pasteText()
    {
        Line remainder=new Line();
        if(getActiveLine().size()!=0)
        {
            int sizeOfLine=getActiveLine().size();
            Line buffer=new Line();
            for(int i=indexOfActiveCh+1;i<sizeOfLine;i++)
            {
                buffer.add(getActiveLine().getSingleChar(i));
                remainder.add(new Char(getActiveLine().getSingleChar(i)));
            }
            getActiveLine().removeAll(buffer.getChars());
        }
        getActiveLine().addAll(new Line(copiedLines.get(0)).getChars());
        getActiveLine().setMaxHeight();
        indexOfActiveCh=getActiveLine().size()-1;
        for(int i=1;i<copiedLines.size();i++)
        {
            lines.add(indexOfActiveLine+1, new Line(copiedLines.get(i)));
            indexOfActiveLine++;
            indexOfActiveCh=getActiveLine().size()-1;
        }
        getActiveLine().addAll(remainder.getChars());
        getActiveLine().setMaxHeight();
   }

    public void cutText()
    {
       copyText();
       backSpaceSelection();
    }

    public int getIndexOfActiveCh()
    {
        return indexOfActiveCh;
    }

    public void setIndexOfActiveLine(int index)
    {
        indexOfActiveLine=index;
    }

    public void setIndexOfActiveCh(int index)
    {
        indexOfActiveCh=index;
    }
}