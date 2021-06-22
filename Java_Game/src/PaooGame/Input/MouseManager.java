package PaooGame.Input;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/*! \class public class MouseManager extends MoudeAdapter
    \brief Gestioneaza intrarea (input-ul) de mouse.

    Clasa citeste daca a fost apasat un click si unde se afla mouse-ul(coordonate x, y), stabiliteste ce click a fost actionat si seteaza corespunzator un flag.
    In program trebuie sa se tina cont de flagul aferent click-ului de interes. Daca flagul respectiv este true inseamna
    ca click-ul respectiva a fost apasata si false nu a fost apasata.
 */
public class MouseManager extends MouseAdapter {
    private static int mouseX = -1, mouseY = -1;        /*!< Pozitii relative x, y.*/
    public boolean left_pressed;        /*!< Flag pentru click stanga apasat.*/
    public boolean right_pressed;       /*!< Flag pentru click dreapta apasat.*/

    /*! \fn public void mousePressed(MouseEvent e)
        \brief Seteaza flagul daca click-ul a fost apasat

        \param e este obiectul eveniment de mouse
     */
    @Override
    public void mousePressed(MouseEvent e){

        if(e.getButton() == MouseEvent.BUTTON1)
            left_pressed = true;

        if(e.getButton() == MouseEvent.BUTTON3)
            right_pressed = true;

    }

    /*! \fn public void mouseReleased(MouseEvent e)
        \brief Seteaza flagul daca click-ul a fost eliberat

        \param e este obiectul eveniment de mouse
     */
    @Override
    public void mouseReleased(MouseEvent e){
        if(e.getButton() == MouseEvent.BUTTON1)
            left_pressed = false;

        if(e.getButton() == MouseEvent.BUTTON3)
            right_pressed = false;
    }

    /*! \fn public void mouseMoved(MouseEvent e)
        \brief Actualizeaza pozitia mouse-ului.

        \param e este obiectul eveniment de mouse
     */
    @Override
    public void mouseMoved(MouseEvent e){
        mouseX = e.getX();
        mouseY = e.getY();
    }

     /*! \fn public int getX()
        \brief Returneaza coordonata x a mouseului.
      */
    public  int getX(){ return mouseX; }

    /*! \fn public int getY()
        \brief Returneaza coordonata y a mouseului.
      */
    public  int getY(){ return mouseY; }
}
