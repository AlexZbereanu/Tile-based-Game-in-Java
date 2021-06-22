package PaooGame.Items;

import PaooGame.RefLinks;

import java.awt.*;

public abstract class Entity {

    protected float x, y;
    protected int width, height;
    protected Rectangle bounds;
    protected RefLinks refLink;
    protected int health;
    protected boolean isActive = true;


    public Entity(RefLinks refLink, float x, float y, int width, int height, int health) {
        this.refLink = refLink;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.health = health;
        bounds = new Rectangle(0, 0, width, height);
    }
    /*! \colisiune intre entitati */
    public boolean checkEntityCollisions(float x1, float y1){
        for(Entity e: refLink.GetGame().GetPlayState().GetManager().GetEntities()){
            if(e.equals(this))
                continue;
             if(e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(x1, y1)))
                 return true;
        }
        return false;
    }

    public Rectangle getCollisionBounds(float x1, float y1){
        return new Rectangle((int)(x + bounds.x + x1), (int)(y + bounds.y + y1), bounds.width, bounds.height);
    }

    /*! \fn public abstract void Update()
        \brief Functie abstracta ce actualizeaza obiectului entitate.
     */
    public abstract void Update();

    /*! \fn public abstract void Draw()
        \brief Functie abstracta ce deseneaza obiectului entitate.
     */
    public abstract void Draw(Graphics g);

    /*! \fn public abstract void die()
        \brief Functie abstracta ce elimina obiectului entitate.
     */
    public abstract void die();

    /*! \fn public void hurt(int hit)
        \brief Actualizeaza viata entitati atunci cand aceasta este lovita.

        \param hit Retine valoarea cu cat scade la fiecare atac viata.
     */
    public void hurt(int hit){
        /// Actualizare viata entitate
        health -= hit;
        if( health <= 0) {
            /// Daca viata devine <=0 , entitatea devine inactiva si dispare.
            isActive = false;
            die();
        }
    }

    /*! \fn public float GetX()
        \brief Returneaza pozitia pe axa x a entitatii.
     */
    public float GetX()
    {
        return x;
    }

    /*! \fn public float GetX()
        \brief Returneaza pozitia pe axa y a entitatii.
     */
    public float GetY()
    {
        return y;
    }

    public void SetX(float x) {
        this.x = x;
    }

    public void SetY(float y) {
        this.y = y;
    }
    /*! \fn public float GetX()
        \brief Returneaza latimea entitatii.
     */
    public int GetWidth()
    {
        return width;
    }

    /*! \fn public float GetX()
        \brief Returneaza inaltimea entitatii.
     */
    public int GetHeight()
    {
        return height;
    }

    /*! \fn public float GetX()
        \brief Returneaza existenta entitatii.
     */
    public boolean isActive() {
        return isActive;
    }

    public void SetHealth(int heal){
        health = heal;
    }

    public int GetHealth(){
        return health;
    }
}
