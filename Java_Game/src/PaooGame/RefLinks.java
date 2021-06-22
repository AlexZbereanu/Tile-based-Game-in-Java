package PaooGame;

import PaooGame.Graphics.GameCamera;
import PaooGame.Input.MouseManager;
import PaooGame.Maps.Map;
import PaooGame.Input.KeyManager;

/*! \class public class RefLinks
    \brief Clasa ce retine o serie de referinte ale unor elemente pentru a fi usor
    accesibile.

    Altfel ar trebui ca functiile respective sa aibe o serie intreaga de parametri si
    ar ingreuna programarea.
 */
public class RefLinks
{
    private Game game;      /*!< Referinta catre obiectul Game.*/
    private Map map;        /*!< Referinta catre harta curenta.*/

    /*! \fn public RefLinks(Game game)
        \brief Constructorul de initializare al clasei.

        \param game Refeinta catre obiectul game.
     */
    public RefLinks(Game game)
    {
        this.game = game;
    }

    /*! \fn public KeyManager GetManager()
        \brief Returneaza referinta catre managerul evenimentelor de la tastatura.
     */

    public KeyManager GetKeyManager()
    {
        return game.GetKeyManager();
    }

    /*! \fn public MouseManager GetMouseManager()
        \brief Returneaza referinta catre managerul evenimentelor de la mouse.
     */
    public MouseManager GetMouseManager(){
        return game.GetMouseManager();
    }

    /*! \fn public int GetWidth()
        \brief Returneaza latimea ferestrei jocului.
     */
    public int GetWidth()
    {
        return game.GetWidth();
    }

    /*! \fn public int GetHeight()
        \brief Returneaza inaltimea ferestrei jocului.
     */
    public int GetHeight()
    {
        return game.GetHeight();
    }

    /*! \fn public Game GetGame()
        \brief Returneaza referinta catre obiectul Game.
     */
    public Game GetGame()
    {
        return game;
    }

    /*! \fn public Map GetMap()
        \brief Returneaza referinta catrea harta curenta.
     */
    public Map GetMap()
    {
        return map;
    }

    /*! \fn public void SetMap(Map map)
        \brief Seteaza referinta catre harta curenta.

        \param map Referinta catre harta curenta.
     */
    public void SetMap(Map map)
    {
        this.map = map;
    }

    /*! fn public GameCamera GetGameCamera()
        /brief Returneaza referinta catre camera jocului.
     */
    public GameCamera GetGameCamera(){ return game.GetCamera(); }
}
