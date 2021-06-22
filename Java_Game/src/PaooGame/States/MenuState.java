package PaooGame.States;

import PaooGame.Menu.Menu;
import PaooGame.RefLinks;

import java.awt.*;

/*! \class public class MenuState extends State
    \brief Implementeaza notiunea de menu pentru joc.
 */
public class MenuState extends State {
    private Menu item1, item2, item3, item4;

    /*! \fn public MenuState(RefLinks refLink)
        \brief Constructorul de initializare al clasei.

        \param refLink O referinta catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
     */
    public MenuState(RefLinks refLink) {
        super(refLink);
        item1 = new Menu(refLink, "arial", "New game", 350, 100);
        item2 = new Menu(refLink, "arial", "Load game", 350, 200);
        item3 = new Menu(refLink, "arial", "Options", 350, 300);
        item4 = new Menu(refLink, "arial", "Exit", 350, 400);

    }

    /*! \fn public void Update()
        \brief Actualizeaza starea curenta a meniului.
     */
    @Override
    public void Update() {
        item1.Update();
        item2.Update();
        item3.Update();
        item4.Update();
        if(refLink.GetMouseManager().left_pressed)
            if(refLink.GetMouseManager().getX() >= 350 && refLink.GetMouseManager().getX() <= 600)
                if(refLink.GetMouseManager().getY() >= 50 && refLink.GetMouseManager().getY() <= 100)
                    State.SetState(refLink.GetGame().GetPlayState());
        if(refLink.GetMouseManager().getX() >= 350 && refLink.GetMouseManager().getX() <= 600 &&
                refLink.GetMouseManager().getY() >= 50 && refLink.GetMouseManager().getY() <= 100){
                item1.setX(355);
                item1.setY(105);
                item1.setColor(Color.RED);
            }
        else{
               item1.setX(350);
               item1.setY(100);
               item1.setColor(Color.GREEN);
            }
        if(refLink.GetMouseManager().getX() >= 350 && refLink.GetMouseManager().getX() <= 600 &&
                refLink.GetMouseManager().getY() >= 150 && refLink.GetMouseManager().getY() <= 200){
            item2.setX(355);
            item2.setY(205);
            item2.setColor(Color.RED);
        }
        else{
            item2.setX(350);
            item2.setY(200);
            item2.setColor(Color.GREEN);
        }
        if(refLink.GetMouseManager().getX() >= 350 && refLink.GetMouseManager().getX() <= 550 &&
                refLink.GetMouseManager().getY() >= 250 && refLink.GetMouseManager().getY() <= 300){
            item3.setX(355);
            item3.setY(305);
            item3.setColor(Color.RED);
        }
        else{
            item3.setX(350);
            item3.setY(300);
            item3.setColor(Color.GREEN);
        }
        if(refLink.GetMouseManager().getX() >= 350 && refLink.GetMouseManager().getX() <= 450 &&
                refLink.GetMouseManager().getY() >= 350 && refLink.GetMouseManager().getY() <= 400){
            item4.setX(355);
            item4.setY(405);
            item4.setColor(Color.RED);
        }
        else{
            item4.setX(350);
            item4.setY(400);
            item4.setColor(Color.GREEN);
        }

        if(refLink.GetMouseManager().left_pressed)
            if(refLink.GetMouseManager().getX() >= 350 && refLink.GetMouseManager().getX() <= 450)
                if(refLink.GetMouseManager().getY() >= 350 && refLink.GetMouseManager().getY() <= 400)
                    System.exit(0);

    }

    /*! \fn public void Draw(Graphics g)
        \brief Deseneaza (randeaza) pe ecran starea curenta a meniului.

            \param g Contextul grafic in care trebuie sa deseneze starea jocului pe ecran.
     */
    @Override
    public void Draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0,0, refLink.GetWidth(), refLink.GetHeight());
        item1.Draw(g);
        item2.Draw(g);
        item3.Draw(g);
        item4.Draw(g);
    }
}
