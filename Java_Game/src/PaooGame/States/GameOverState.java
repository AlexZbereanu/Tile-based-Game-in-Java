package PaooGame.States;

import PaooGame.Menu.Menu;
import PaooGame.RefLinks;
import java.awt.*;

/*! \class public class GameOverState extends State
    \brief Implementeaza notiunea de game over pentru joc.
 */
public class GameOverState extends State {
    private Menu item1, item2, item3, gameover;

    /*! \fn public GameOverState(RefLinks refLink)
        \brief Constructorul de initializare al clasei.

        \param refLink O referinta catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
     */
    public GameOverState(RefLinks refLink) {
        super(refLink);
        gameover = new Menu(refLink, "arial", "GAME OVER", 350, 100);
        item1 = new Menu(refLink, "arial", "Score: " + refLink.GetGame().GetPlayState().GetScore(), 350, 200);
        item2 = new Menu(refLink, "arial", "New Game", 350, 300);
        item3 = new Menu(refLink, "arial", "Exit", 350, 400);
    }

    /*! \fn public void Update()
        \brief Actualizeaza starea curenta a game over-ului.
     */
    @Override
    public void Update() {
        item1.SetOption("Score: " + refLink.GetGame().GetPlayState().GetScore());
        gameover.Update();
        item1.Update();
        item2.Update();
        item3.Update();
        if(refLink.GetMouseManager().left_pressed)
            if(refLink.GetMouseManager().getX() >= 350 && refLink.GetMouseManager().getX() <= 600)
                if(refLink.GetMouseManager().getY() >= 250 && refLink.GetMouseManager().getY() <= 300) {
                    State.SetState(refLink.GetGame().GetPlayState());
                    refLink.GetGame().GetPlayState().SetScore(0);
                }
        if(refLink.GetMouseManager().getX() >= 350 && refLink.GetMouseManager().getX() <= 600 &&
                refLink.GetMouseManager().getY() >= 250 && refLink.GetMouseManager().getY() <= 300){
            item2.setX(355);
            item2.setY(305);
            item2.setColor(Color.RED);
        }
        else{
            item2.setX(350);
            item2.setY(300);
            item2.setColor(Color.GREEN);
        }

        if(refLink.GetMouseManager().left_pressed)
            if(refLink.GetMouseManager().getX() >= 350 && refLink.GetMouseManager().getX() <= 450)
                if(refLink.GetMouseManager().getY() >= 350 && refLink.GetMouseManager().getY() <= 400)
                    System.exit(0);
        if(refLink.GetMouseManager().getX() >= 350 && refLink.GetMouseManager().getX() <= 450 &&
                refLink.GetMouseManager().getY() >= 350 && refLink.GetMouseManager().getY() <= 400){
            item3.setX(355);
            item3.setY(405);
            item3.setColor(Color.RED);
        }
        else {
            item3.setX(350);
            item3.setY(400);
            item3.setColor(Color.GREEN);
        }

    }

    /*! \fn public void Draw(Graphics g)
        \brief Deseneaza (randeaza) pe ecran starea curenta a finalului de joc.

            \param g Contextul grafic in care trebuie sa deseneze starea jocului pe ecran.
     */
    @Override
    public void Draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0,0, refLink.GetWidth(), refLink.GetHeight());
        gameover.Draw(g);
        item1.Draw(g);
        item2.Draw(g);
        item3.Draw(g);
    }
}
