package Model;

import java.util.LinkedList;
import java.util.List;

public class Sportsmen
{
    private List<Sportsman> sportsmen;
    //private Vector<Vector<String>> sp;

    public Sportsmen()
    {
        sportsmen=new LinkedList<>();
    }

    public void addNewSportsman(Sportsman sportsman)
    {
        sportsmen.add(sportsman);
    }

    public List<Sportsman> getSportsmen()
    {
        return sportsmen;
    }

    public int getSize()
    {
        return sportsmen.size();
    }

    public Sportsman get(int i)
    {
        return sportsmen.get(i);
    }

    public void clear()
    {
        sportsmen.clear();
    }

    public void removeAll(Sportsmen sportsmenRemove)
    {
        sportsmen.removeAll(sportsmenRemove.getSportsmen());
    }
    /*public Vector<Vector<String>> getSportsmen()
    {
        Vector<Vector<String>> data=new Vector<>();
        for(int i=0;i<sportsmen.size();i++)
        {
            Vector<String> sportsman=new Vector<>(6);
            sportsman.add(0, sportsmen.get(i).getSNP());
            sportsman.add(1, sportsmen.get(i).getCast());
            sportsman.add(2, sportsmen.get(i).getPosition());
            sportsman.add(3, sportsmen.get(i).getTitles());
            sportsman.add(4, sportsmen.get(i).getView());
            sportsman.add(5, sportsmen.get(i).getDischarge());
            data.add(sportsman);
        }
        return data;
    }*/
}
