package Model;

public class Sportsman
{
    private String surname;
    private String name;
    private String patronymic;
    private String cast;
    private String position;
    private int titles;
    private String view;
    private String discharge;

    public Sportsman(String snp, String cast, String position, int titles, String view, String discharge)
    {
        this.surname=snp.split(" ")[0];
        this.name=snp.split(" ")[1];
        this.patronymic=snp.split(" ")[2];
        this.cast=cast;
        this.position=position;
        this.titles=titles;
        this.view=view;
        this.discharge=discharge;
    }

    public Sportsman()
    {
        surname="";
        name="";
        patronymic="";
        cast="";
        position="";
        titles=0;
        view="";
        discharge="";
    }

    public String getSNP()
    {
        return new String(surname+" "+name+" "+patronymic);
    }

    public String getCast() {
        return cast;
    }

    public String getPosition() {
        return position;
    }

    public String getTitles()
    {
        return Integer.toString(titles);
    }

    public String getView() {
        return view;
    }

    public String getDischarge() {
        return discharge;
    }

    public void setSNP(String snp)
    {
        this.surname=snp.split(" ")[0];
        this.name=snp.split(" ")[1];
        this.patronymic=snp.split(" ")[2];
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setDischarge(String discharge) {
        this.discharge = discharge;
    }

    public void setTitles(int titles) {
        this.titles = titles;
    }

    public void setView(String view) {
        this.view = view;
    }
}
