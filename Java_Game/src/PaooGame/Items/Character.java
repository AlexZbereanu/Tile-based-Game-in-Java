package PaooGame.Items;

import PaooGame.RefLinks;
import PaooGame.Tiles.Tile;
import java.awt.*;

/*! \class public abstract class Character extends Entity
    \brief Defineste notiunea abstracta de caracter/individ/fiinta din joc.

    Notiunea este definita doar de viata, viteza de deplasare si distanta cu care trebuie sa se
    miste/deplaseze in urma calculelor.
 */
public abstract class Character extends Entity {
    public static final float DEFAULT_SPEED         = 3.0f;     /*!< Viteza implicita a unu caracter.*/
    public static final int DEFAULT_CREATURE_WIDTH  = 48;       /*!< Latimea implicita a imaginii caracterului.*/
    public static final int DEFAULT_CREATURE_HEIGHT = 48;       /*!< Inaltimea implicita a imaginii caracterului.*/
    public static final int DEFAULT_HEALTH  = 600;
    protected Rectangle normalBounds;       /*!< Colisiune normala. */
    protected Rectangle attackBounds;       /*!< Colisiune de atac. */
    protected float speed;      /*!< Retine viteza de deplasare caracterului.*/
    protected float xMove;      /*!< Retine noua pozitie a caracterului pe axa X.*/
    protected float yMove;      /*!< Retine noua pozitie a caracterului pe axa Y.*/
    protected float healthBar;      /*!< Bara de viata.*/

    /*! \fn public Character(RefLinks refLink, float x, float y, int width, int height)
        \brief Constructor de initializare al clasei Character

        \param refLink Referinta catre obiectul shortcut (care retine alte referinte utile/necesare in joc).
        \param x Pozitia de start pa axa X a caracterului.
        \param y Pozitia de start pa axa Y a caracterului.
        \param width Latimea imaginii caracterului.
        \param height Inaltimea imaginii caracterului.
        \param health Viata caracterului.
     */
    public Character(RefLinks refLink, float x, float y, int width, int height, int health) {
        ///Apel constructor la clasei de baza
        super(refLink, x,y, width, height, health);
        //Seteaza pe valorile implicite pentru viata, viteza si distantele de deplasare
        speed   = DEFAULT_SPEED;
        xMove   = 0;
        yMove   = 0;
        normalBounds = new Rectangle(0, 0, width, height);
        attackBounds = new Rectangle(0, 0, width, height);
    }

    /*! \fn public void Move()
        \brief Modifica pozitia caracterului
     */
    public void Move() {
        ///Modifica pozitia caracterului pe axa X daca nu exista colisiuni cu alte entitati.
        if(!checkEntityCollisions(xMove, 0f))
            MoveX();
        ///Modifica pozitia caracterului pe axa X daca nu exista colisiuni cu alte entitati.
        if(!checkEntityCollisions(0f, yMove))
            MoveY();
    }

    /*! \fn public void MoveX()
        \brief Modifica pozitia caracterului pe axa X.
     */
    public void MoveX() {

        if(xMove > 0) {
            int tx = (int) (x + xMove + bounds.x + bounds.width) / Tile.TILE_WIDTH;
            if (!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILE_HEIGHT) &&
                    !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILE_HEIGHT)) {
                x += xMove;
            } else {
                x = tx * Tile.TILE_WIDTH - bounds.x - bounds.width - 1;
            }
        }else if (xMove < 0) {
            int tx = (int) (x + xMove + bounds.x ) / Tile.TILE_WIDTH;
            if (!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILE_HEIGHT) &&
                    !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILE_HEIGHT))
                x += xMove;
            else
                x = tx * Tile.TILE_WIDTH + Tile.TILE_WIDTH - bounds.x;
        }
    }

    /*! \fn public void MoveY()
        \brief Modifica pozitia caracterului pe axa Y.
     */
    public void MoveY()
    {
        if(yMove > 0) {
            int ty = (int) (y + yMove + bounds.y + bounds.height) / Tile.TILE_HEIGHT;
            if(! collisionWithTile((int)(x + bounds.x) / Tile.TILE_WIDTH, ty) &&
                    !collisionWithTile((int)(x + bounds.x + bounds.width) / Tile.TILE_WIDTH, ty)) {
                y += yMove;
            }else
                y = ty * Tile.TILE_HEIGHT - bounds.y - bounds.height - 1;
        }
        else if (yMove < 0) {
            int ty = (int) (y + yMove + bounds.y ) / Tile.TILE_HEIGHT;
            if (!collisionWithTile((int) (x + bounds.x) / Tile.TILE_WIDTH, ty) &&
                    !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILE_WIDTH, ty))
                y += yMove;
            else{
                y = ty * Tile.TILE_HEIGHT + Tile.TILE_HEIGHT - bounds.y;
            }
        }

    }

    /*! \fn public abstract void die()
        \brief Functie abstracta care are ca scop eliminarea unui jucator/inamic.
     */
    public abstract void die();

    /*! \fn protected boolean collisionWithTile(int x, int y)
        \brief Returneaza un flag care indica daca tile-ul este solid sau nu.
     */
    protected boolean collisionWithTile(int x, int y){
        return refLink.GetMap().GetTile(x, y).IsSolid();
    }

}

