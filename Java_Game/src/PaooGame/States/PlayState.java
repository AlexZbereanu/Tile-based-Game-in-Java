package PaooGame.States;

import PaooGame.Items.EntityManager;
import PaooGame.Items.Hero;
import PaooGame.RefLinks;
import PaooGame.Maps.Map;
import java.awt.*;

/*! \class public class PlayState extends State
    \brief Implementeaza/controleaza jocul.
 */
public class PlayState extends State {
    private Map map;         /*!< Referinta catre harta curenta.*/
    private EntityManager manager;      /*!< Referinta catre obiectul manager de entitati.*/
    private int ScorePoint;         /*!< Referinta catre scor.*/
    /*! \fn public PlayState(RefLinks refLink)
        \brief Constructorul de initializare al clasei

        \param refLink O referinta catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
     */
    public PlayState(RefLinks refLink) {
        ///Apel al constructorului clasei de baza
        super(refLink);
        ///Construieste harta jocului
        map = new Map(refLink);
        ///Referinta catre harta construita este setata si in obiectul shortcut pentru a fi accesibila si in alte clase ale programului.
        refLink.SetMap(map);
        ///Construieste managerul de entitati si adauga entitatile in joc.
        manager = new EntityManager(refLink, new Hero(refLink,50, 50));
        ScorePoint = 0;
        //refLink.GetGameCamera();
    }

    /*! \fn public void Update()
       \brief Actualizeaza starea curenta a jocului.
    */
    @Override
    public void Update() {
        map.Update();
        manager.Update();
        /// Daca se apasa tasta escape in joc se intra in meniu.
        if(refLink.GetKeyManager().escape)
            State.SetState(refLink.GetGame().GetGameOverState());
    }

    /*! \fn public void Draw(Graphics g)
        \brief Deseneaza (randeaza) pe ecran starea curenta a jocului.

        \param g Contextul grafic in care trebuie sa deseneze starea jocului pe ecran.
     */
    @Override
    public void Draw(Graphics g) {
        map.Draw(g);
        manager.Draw(g);
        // Afiseaza scorul.
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        g.drawString("Score: " + ScorePoint, 30, 30);
    }

    /*! \fn public EntityManager GetManager()
        \brief Returneaza un obiect al manager-ului de entitati
     */
    public EntityManager GetManager() {
        return manager;
    }

    /*! \fn public int GetScore()
        \brief Returneaza scorul.
     */
    public int GetScore(){
        return ScorePoint;
    }

    /*! \fn public void SetScore(int score)
        \brief Seteaza scorul.
     */
    public void SetScore(int score){
        ScorePoint = score;
    }
}
