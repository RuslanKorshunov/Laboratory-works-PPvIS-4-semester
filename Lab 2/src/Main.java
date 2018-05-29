import Model.*;
import View.Menu;
import Controller.Operations;

public class Main
{
    public static void main(String args[])
    {
        Sportsmen sportsmen=new Sportsmen();
        Operations operations=new Operations(sportsmen);
        Menu menu=new Menu(operations);
    }
}
