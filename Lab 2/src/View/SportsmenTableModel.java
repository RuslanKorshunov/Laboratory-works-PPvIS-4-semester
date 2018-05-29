package View;

import javax.swing.table.AbstractTableModel;
import java.util.LinkedList;
import java.util.List;
import Model.Sportsman;

public class SportsmenTableModel extends AbstractTableModel
{
    private List<Sportsman> sportsmen;
    private int rowCount;
    private final int columnCount=6;

    public SportsmenTableModel(LinkedList<Sportsman> sportsmen, int rowCount)
    {
        this.sportsmen=new LinkedList<>(sportsmen);
        this.rowCount=rowCount;
    }

    public void setRowCount(int rowCount)
    {
        this.rowCount = rowCount;
    }

    @Override
    public int getRowCount() {
        return rowCount;
    }

    @Override
    public int getColumnCount() {
        return columnCount;
    }

    @Override
    public String getColumnName(int column)
    {
        String result="";
        switch(column)
        {
            case 0:
                result="ФИО";
                break;
            case 1:
                result="Состав";
                break;
            case 2:
                result="Позиция";
                break;
            case 3:
                result="Титулы";
                break;
            case 4:
                result="Вид спорта";
                break;
            case 5:
                result="Разряд";
                break;
        }
        return result;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        switch(columnIndex)
        {
            case 0:
            case 1:
            case 2: return String.class;
            case 3: return Integer.class;
            case 4:
            case 5: return String.class;
            default: return Object.class;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        if(rowIndex>sportsmen.size()-1)
            return "";
        switch (columnIndex)
        {
            case 0: return sportsmen.get(rowIndex).getSNP();
            case 1: return sportsmen.get(rowIndex).getCast();
            case 2: return sportsmen.get(rowIndex).getPosition();
            case 3: return sportsmen.get(rowIndex).getTitles();
            case 4: return sportsmen.get(rowIndex).getView();
            case 5: return sportsmen.get(rowIndex).getDischarge();
            default: return "";
        }
    }
}
