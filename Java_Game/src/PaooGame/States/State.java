package PaooGame.States;

import java.awt.*;

import PaooGame.Items.Entity;
import PaooGame.RefLinks;

/*! \class State
    \brief Implementeaza notiunea abstracta de stare a jocului/programului.

    Un joc odata ce este lansat in executie nu trebuie "sa arunce jucatorul direct in lupta", este nevoie de
    un meniu care sa contine optiuni: New Game, Load Game, Settings, etc. Toate aceste optiuni nu sunt altceva
    decat stari ale programului (jocului) ce trebuiesc incarcate si afisate functie de starea curenta.
 */
public abstract class State {
    private static State previousState  = null;     /*!< Referinta catre starea anterioara a jocului.*/
    private static State currentState   = null;     /*!< Referinta catre starea curenta a jocului: game, meniu, settings, about etc.*/
    protected RefLinks refLink;
    public State(RefLinks refLink)
    {
        this.refLink = refLink;
    }

    /*! \fn public static void SetState(State state)
       \brief Seteaza starea curenta a jocului.

       \param state Noua stare a programului (jocului).
    */
    public static void SetState(State state) {
        previousState = currentState;
        currentState = state;
    }

    /*! \fn public static void GetState()
       \brief Returneaza starea curenta a jocului.
    */
    public static State GetState()
    {
        return currentState;
    }

    /*! \fn public void Update()
       \brief Actualizeaza starea setarilor.
    */
    public abstract void Update();

    /*! \fn public void Draw(Graphics g)
        \brief Deseneaza (randeaza) pe ecran setarile.

        \param g Contextul grafic in care trebuie sa deseneze starea setarilor pe ecran.
     */
    public abstract void Draw(Graphics g);
}
