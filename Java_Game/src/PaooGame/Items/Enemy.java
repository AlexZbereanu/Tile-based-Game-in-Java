package PaooGame.Items;

import PaooGame.Graphics.Assets;
import PaooGame.RefLinks;

import java.awt.*;

/*! \class public class Enemy extends Character
    \brief Implementeaza notiunea de inamic.

    Elementele suplimentare pe care le aduce fata de clasa de baza sunt:
        imaginea (acest atribut poate fi ridicat si in clasa de baza)
        deplasarea
        dreptunghiul de coliziune
 */
public class Enemy extends Character {
    private float distance, diffX, diffY;

    /*! \fn public Enemy(RefLinks refLink, float x, float y)
        \brief Constructorul de initializare al clasei Enemy.

        \param refLink Referinta catre obiectul shortcut (obiect ce retine o serie de referinte din program).
        \param x Pozitia initiala pe axa X a inamicului.
        \param y Pozitia initiala pe axa Y a inamicului.
     */
    public Enemy(RefLinks refLink, float x, float y) {
        super(refLink, x,y, 25, 25, 2);
        normalBounds.x = 0;
        normalBounds.y = 0;
        normalBounds.width = 23;
        normalBounds.height = 23;
        bounds = normalBounds;
    }

    /*! \fn public void Update()
       \brief Actualizeaza pozitia inamicului.
    */
    @Override
    public void Update() {
        Move();
        for(int i = 0; i<refLink.GetGame().GetPlayState().GetManager().GetEntities().size();i++){
            float dx = refLink.GetGame().GetPlayState().GetManager().GetEntities().get(0).GetX();
            float dy = refLink.GetGame().GetPlayState().GetManager().GetEntities().get(0).GetY();
            if(refLink.GetGame().GetPlayState().GetManager().GetEntities().get(i) == refLink.GetGame().GetPlayState().GetManager().GetEntities().get(0) ){
                diffX = x - dx - width;
                diffY = y - dy - height;
                distance = (float)Math.sqrt((x - dx) * (x - dx) + (y - dy) * (y - dy));
            }
        }
        if(distance < 250) {
            xMove = ((1 / distance) * diffX);
            yMove = ((1 / distance) * diffY);
        }else{
            xMove = 0;
            yMove = 0;
        }
    }

    /*! \fn public void die()
        \brief Functie ce elimina obiectului entitate.
     */
    @Override
    public void die(){
        System.out.print("Loseeer");
        refLink.GetGame().GetPlayState().SetScore(refLink.GetGame().GetPlayState().GetScore() + 100);
    }

    /*! \fn public void Draw(Graphics g)
        \brief Randeaza/deseneaza enemy in noua pozitie.

        \brief g Contextul grafic in care trebuie efectuata desenarea inamicului.
     */
    @Override
    public void Draw(Graphics g) {
        g.drawImage(Assets.enemy, (int)(x - refLink.GetGameCamera().getX()), (int)(y - refLink.GetGameCamera().getY())
                , width, height, null);
    }
}
