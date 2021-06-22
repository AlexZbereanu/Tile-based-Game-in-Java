package PaooGame.Graphics;

import java.awt.image.BufferedImage;

/*! \class public class Animation
    \brief Clasa retine o referinta catre o animatie formata din imagini.
 */
public class Animation {
    private final int speed;           /*!< Viteza de schimbare a frame-ului animatiei.*/
    private int index;                  /*!< Valoare ce retine indexul de frame.*/
    private long lastTime, timer;       /*!< Temporizeaza animatia.*/
    private final BufferedImage[] frames; /*!< Referinta catre frame de imagine.*/

    /*! \fn public Animation(int speed, BufferedImage[] frames)
       \brief Constructor, initializeaza Animatia.

       \param buffImg[] Un vector de obiect BufferedImage valid.
    */
    public Animation(int speed, BufferedImage[] frames){
        this.speed = speed;
        this.frames = frames;
        index = 0;
        timer = 0;
        lastTime = System.currentTimeMillis();
    }

    /*!< \fn public void Update()
        \brief Updateaza frame-ul curent al animatiei.
     */
    public void Update(){
        /// Diferenta de timp.
        timer += System.currentTimeMillis() - lastTime;
        /// lastTime i se atribuie timpul curent.
        lastTime = System.currentTimeMillis();

        if(timer > speed){
            index ++;
            timer  = 0;
            /// Daca a ajuns la ultimul frame al animatiei, se reia de la primul frame.
            if(index >= frames.length)
                index = 0;
        }

    }

    /*!< \fn public BufferedImage getCurrentFrame()
        \brief Returneaza frame-ul curent al animatiei.
     */
    public BufferedImage getCurrentFrame(){
        return frames[index];
    }
}
