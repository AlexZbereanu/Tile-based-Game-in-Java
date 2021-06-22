package PaooGame.Items;

import java.awt.*;
import java.awt.image.BufferedImage;
import PaooGame.Graphics.Animation;
import PaooGame.RefLinks;
import PaooGame.Graphics.Assets;

/*! \class public class Hero extends Character
    \brief Implementeaza notiunea de erou/player (caracterul controlat de jucator).

    Elementele suplimentare pe care le aduce fata de clasa de baza sunt:
        imaginea (acest atribut poate fi ridicat si in clasa de baza)
        deplasarea
        atacul
        dreptunghiul de coliziune
 */
public class Hero extends Character {
    private final Animation animLeft, animRight, animDown, animUp;      /*!< Referinta catre animatia curenta a eroului.*/
    private final Animation attackLeft, attackRight, attackDown, attackUp;      /*!< Referinta catre animatiile de atac ale eroului.*/
    private long lastAttackTimer;       /*!< Attack time.*/
    private final long attackCooldown = 10;     /*!<Cooldown attack.*/
    private long attackTimer = attackCooldown;
    private int direction;      /*!< Referinta care indica directia inspre care este indreptat eroul.*/

    /*! \fn public Hero(RefLinks refLink, float x, float y)
        \brief Constructorul de initializare al clasei Hero.

        \param refLink Referinta catre obiectul shortcut (obiect ce retine o serie de referinte din program).
        \param x Pozitia initiala pe axa X a eroului.
        \param y Pozitia initiala pe axa Y a eroului.
     */
    public Hero(RefLinks refLink, float x, float y) {
        ///Apel al constructorului clasei de baza
        super(refLink, x,y, Character.DEFAULT_CREATURE_WIDTH, Character.DEFAULT_CREATURE_HEIGHT, Character.DEFAULT_HEALTH);
        ///Seteaza animatiile de mers si atac ale eroului.
        animLeft = new Animation(300, Assets.heroLeft);
        animRight = new Animation(300, Assets.heroRight);
        animUp = new Animation(300, Assets.heroUp);
        animDown = new Animation(300, Assets.heroDown);
        attackLeft = new Animation(100, Assets.attackLeft);
        attackRight = new Animation(100, Assets.attackRight);
        attackUp = new Animation(100, Assets.attackUp);
        attackDown = new Animation(100, Assets.attackDown);
        ///Stabilieste pozitia relativa si dimensiunea dreptunghiului de coliziune, starea implicita(normala)
        normalBounds.x = 16;
        normalBounds.y = 16;
        normalBounds.width = 16;
        normalBounds.height = 24;
        bounds = normalBounds;
        ///Stabilieste pozitia relativa si dimensiunea dreptunghiului de coliziune, starea de atac
        attackBounds.x = 10;
        attackBounds.y = 10;
        attackBounds.width = 38;
        attackBounds.height = 38;
    }

    /*! \fn public void Update()
       \brief Actualizeaza pozitia si animatia eroului.
    */
    @Override
    public void Update() {
        ///Verifica daca a fost apasata o tasta.
        GetInput();
        ///Actualizeaza pozitia.
        Move();
        ///Actualizeaza camera pe erou.
        refLink.GetGame().GetCamera().center(this);
        ///Actualizeaza animatia.
        if(refLink.GetKeyManager().left)
            animLeft.Update();
        else if(refLink.GetKeyManager().right)
            animRight.Update();
        else if(refLink.GetKeyManager().up)
            animUp.Update();
        else if(refLink.GetKeyManager().down)
            animDown.Update();

        /// Daca ataca, actualizeaza animatia de atac
        checkAttack();
    }

    /*! \fn private void checkAttack()
        \brief Actualizeaza animatia eroului cand acesta ataca.
     */
    private void checkAttack(){
        attackTimer +=System.currentTimeMillis() - lastAttackTimer;
        lastAttackTimer = System.currentTimeMillis();       /*!< Se actualizeaza lastAttack la timpul curent.*/
        if(attackTimer < attackCooldown)
            return;
        if(refLink.GetKeyManager().attack && direction == 3)
            attackDown.Update();
        if(refLink.GetKeyManager().attack && direction == 2)
            attackLeft.Update();
        if(refLink.GetKeyManager().attack && direction == 1)
            attackUp.Update();
        if(refLink.GetKeyManager().attack && direction == 4)
            attackRight.Update();


        Rectangle cb = getCollisionBounds(0, 0);

        if(refLink.GetKeyManager().attack){
            cb.width += 30;
            cb.height = cb.height + 30;
        }
        else
            return;

        attackTimer = 0;

        for(Entity e: refLink.GetGame().GetPlayState().GetManager().GetEntities()){
            if(e.equals(this))
                continue;
            if(e.getCollisionBounds(10, 10).intersects(cb)){
                e.hurt(2);
                return;
            }
        }
    }

    /*! \fn private void GetInput()
        \brief Verifica daca a fost apasata o tasta din cele stabilite pentru controlul eroului.

        Se salveaza directia inspre care este indreptat eroul.
     */
    private void GetInput() {
        ///Implicit eroul nu trebuie sa se deplaseze daca nu este apasata o tasta
        xMove = 0;
        yMove = 0;
        ///Verificare apasare tasta "sus"
        if(refLink.GetKeyManager().up) {
            yMove = -speed;
            direction = 1;
        }
        ///Verificare apasare tasta "sus"
        if(refLink.GetKeyManager().down) {
            yMove = speed;
            direction = 3;
        }
        ///Verificare apasare tasta "left"
        if(refLink.GetKeyManager().left) {
            xMove = -speed;
            direction = 2;
        }
        ///Verificare apasare tasta "dreapta"
        if(refLink.GetKeyManager().right) {
            xMove = speed;
            direction = 4;
        }
    }

    /*! \fn public void die()
        \brief Functie ce elimina obiectului entitate hero.
     */
    @Override
    public void die(){
    }

    /*! \fn public void Draw(Graphics g)
        \brief Randeaza/deseneaza eroul in noua pozitie.

        \brief g Contextul grafic in care trebuie efectuata desenarea eroului.
     */
    @Override
    public void Draw(Graphics g) {
        g.drawImage(getCurrentAnimationFrame(), (int)(x - refLink.GetGameCamera().getX()), (int)(y - refLink.GetGameCamera().getY())
                , width, height, null);
        g.setColor(Color.green);
        g.fillRect((int)(x + bounds.x - refLink.GetGameCamera().getX()), (int)(y - refLink.GetGameCamera().getY()),(int)(health / 30 + healthBar +1), 5);
    }

    /*! \fn private BufferedImage getCurrentAnimationFrame()
        \brief Returneaza frameul curent
     */
    private BufferedImage getCurrentAnimationFrame(){
        if( xMove > 0)
            return animRight.getCurrentFrame();
        else if(xMove < 0)
            return animLeft.getCurrentFrame();
        else if(yMove < 0)
            return animUp.getCurrentFrame();
        else if(yMove > 0)
            return animDown.getCurrentFrame();
        else if(direction == 1)
            if(refLink.GetKeyManager().attack)
                return attackUp.getCurrentFrame();
            else
                return animUp.getCurrentFrame();
        else if(direction == 2)
            if(refLink.GetKeyManager().attack)
                return attackLeft.getCurrentFrame();
            else
                return animLeft.getCurrentFrame();
        else if(direction == 3)
            if(refLink.GetKeyManager().attack)
                return attackDown.getCurrentFrame();
            else
                return animDown.getCurrentFrame();
        else if(direction == 4)
            if(refLink.GetKeyManager().attack)
                return attackRight.getCurrentFrame();
            else
                return animRight.getCurrentFrame();
        else
            return animDown.getCurrentFrame();
    }
}
