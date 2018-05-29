package Controller;

import java.util.List;

import Model.*;

public class Operations
{
    private Sportsmen sportsmen;

    public Operations(Sportsmen sportsmen)
    {
        this.sportsmen=sportsmen;
    }

    public void addSportsman(Sportsman sportsman)
    {
        sportsmen.addNewSportsman(sportsman);
    }

    public void searchBySNPAndKindOfSport(Sportsmen sportsmen, String surname, String name, String patronymic, String kind)
    {
        sportsmen.clear();
        for(Sportsman sp: this.sportsmen.getSportsmen())
        {
            String information=sp.getSNP()+" "+sp.getView();
            boolean result=false;
            if(information.split(" ")[0].equals(surname) &&
               information.split(" ")[1].equals(name) &&
               information.split(" ")[2].equals(patronymic) &&
               information.split(" ")[3].equals(kind))
                    result=true;
            if(result)
                sportsmen.addNewSportsman(sp);
        }
    }

    public void searchByNumberOfTitles(Sportsmen sportsmen, int lowerLevel, int topLevel)
    {
        sportsmen.clear();
        for(Sportsman sp: this.sportsmen.getSportsmen())
        {
            int titles=Integer.parseInt(sp.getTitles());
            if(titles>=lowerLevel && titles<=topLevel)
                sportsmen.addNewSportsman(sp);
        }
    }

    public void searchBySNPAndDischarge(Sportsmen sportsmen, String surname, String name, String patronymic, String discharge)
    {
        sportsmen.clear();
        for(Sportsman sp: this.sportsmen.getSportsmen())
        {
            String information=sp.getSNP()+" "+sp.getDischarge();
            boolean result=false;
            if(information.split(" ")[0].equals(surname) &&
               information.split(" ")[1].equals(name) &&
               information.split(" ")[2].equals(patronymic) &&
               information.split(" ")[3].equals(discharge))
                result=true;
            if(result)
                sportsmen.addNewSportsman(sp);
        }
    }

    public int removeBySNPAndKindOfSport(String surname, String name, String patronymic, String kind)
    {
        Sportsmen sportsmenRemove=new Sportsmen();
        searchBySNPAndKindOfSport(sportsmenRemove, surname, name, patronymic, kind);
        int size=sportsmenRemove.getSize();
        sportsmen.removeAll(sportsmenRemove);
        return size;
    }

    public int removeByNumberOfTitles(int lowerLevel, int topLevel)
    {
        Sportsmen sportsmenRemove=new Sportsmen();
        searchByNumberOfTitles(sportsmenRemove, lowerLevel, topLevel);
        int size=sportsmenRemove.getSize();
        sportsmen.removeAll(sportsmenRemove);
        return size;
    }

    public int removeBySNPAndDischarge(String surname, String name, String patronymic, String discharge)
    {
        Sportsmen sportsmenRemove=new Sportsmen();
        searchBySNPAndDischarge(sportsmenRemove, surname, name, patronymic, discharge);
        int size=sportsmenRemove.getSize();
        sportsmen.removeAll(sportsmenRemove);
        return size;
    }

    public void clearSportsmen()
    {
        sportsmen.clear();
    }

    public Sportsmen getSportsmen()
    {
        return sportsmen;
    }

    public List<Sportsman> getSportsmenList()
    {
        return sportsmen.getSportsmen();
    }
}
