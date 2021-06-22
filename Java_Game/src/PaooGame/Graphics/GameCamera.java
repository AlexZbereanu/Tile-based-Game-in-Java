package PaooGame.Graphics;

import PaooGame.Items.Entity;
import PaooGame.RefLinks;
import PaooGame.Tiles.Tile;

/*! \class public class GameCamera
    \brief Camera principala a jocului indreptata inspre caracterul principal.

    Aceasta clasa are rolul de a mentine camera in centrul jucatorului cheie(eroul).
 */
public class GameCamera {
    private float x, y;     /*!< Referinte catre coordonatele centrului camerei.*/
    private final RefLinks refLink;     /*!< Referinta catre un obiect a carui sarcina este doar de a retine diverse referinte.*/

    /*! \fn public GameCamera(RefLinks reflink, float x, float y)
        \brief Constructorul clasei.
     */
    public GameCamera(RefLinks refLink, float x, float y){
        /// Initializare cu parametri dati.
        this.refLink = refLink;
        this.x = x;
        this.y = y;
    }

    /*! \fn public void checkBlankSpace()
        \brief Parametrizeaza camera astfel incat aceasta sa nu iasa in afara hartii.

        Centram spatiu vizibil al camerei intre punctele (0, 0) si (width, height) al hartii.
     */
    public void checkBlankSpace(){
        /// Actualizari ale camerei astfel incat aceasta sa nu treaca de spatiul vizibil al hartii.
        if(x < 0) {
            x = 0;
        }
        else if(x > refLink.GetMap().getWidth() * Tile.TILE_WIDTH - refLink.GetWidth()){
            x = refLink.GetMap().getWidth() * Tile.TILE_WIDTH - refLink.GetWidth();
        }
        if(y < 0) {
            y = 0;
        }
        else if(y > refLink.GetMap().getHeight() * Tile.TILE_HEIGHT - refLink.GetHeight()){
            y = refLink.GetMap().getHeight() * Tile.TILE_HEIGHT - refLink.GetHeight();
        }

    }

    /*! \fn public void center(Entity e)
        \brief Functie ce centreaza obiectivul pe entitate
     */
    public void center(Entity e){
        /// Centrare pe axa X.
        x = e.GetX() - refLink.GetWidth() / 2 + e.GetWidth() / 2;
        /// Centrare pe axa Y.
        y = e.GetY() - refLink.GetHeight() / 2 + e. GetHeight() / 2;
        /// Verificare spatiu vizibil al hartii.
        checkBlankSpace();
    }

    /*! \fn public float getX()
        \brief Returneaza coordonata x a camerei.
     */
    public float getX(){ return x; }

    /*! \fn public float gety()
        \brief Returneaza coordonata y a camerei.
     */
    public float getY(){ return y; }

    /*! \fn public float SetX()
        \brief Seteaza coordonata x a camerei.
     */
    public void setX(float x){ this.x = x; }

    /*! \fn public float SetY()
        \brief Seteaza coordonata y a camerei.
     */
    public void setY(float y){ this.y = y; }
}
