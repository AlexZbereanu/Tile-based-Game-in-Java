package PaooGame;

import PaooGame.GameWindow.GameWindow;
import PaooGame.Graphics.Assets;
import PaooGame.Graphics.GameCamera;
import PaooGame.Input.*;
import PaooGame.States.*;
import PaooGame.Tiles.Tile;
import java.awt.*;
import java.awt.image.BufferStrategy;


/*! \class Game
    \brief Clasa principala a intregului proiect. Implementeaza Game - Loop (Update -> Draw)

                ------------
                |           |
                |     ------------
    60 times/s  |     |  Update  |  -->{ actualizeaza variabile, stari, pozitii ale elementelor grafice etc.
        =       |     ------------
     16.7 ms    |           |
                |     ------------
                |     |   Draw   |  -->{ deseneaza totul pe ecran
                |     ------------
                |           |
                -------------
    Implementeaza interfata Runnable:

        public interface Runnable {
            public void run();
        }

    Interfata este utilizata pentru a crea un nou fir de executie avand ca argument clasa Game.
    Clasa Game trebuie sa aiba definita metoda "public void run()", metoda ce va fi apelata
    in noul thread(fir de executie). Mai multe explicatii veti primi la curs.

    In mod obisnuit aceasta clasa trebuie sa contina urmatoarele:
        - public Game();            //constructor
        - private void init();      //metoda privata de initializare
        - private void update();    //metoda privata de actualizare a elementelor jocului
        - private void draw();      //metoda privata de desenare a tablei de joc
        - public run();             //metoda publica ce va fi apelata de noul fir de executie
        - public synchronized void start(); //metoda publica de pornire a jocului
        - public synchronized void stop()   //metoda publica de oprire a jocului
 */
public class Game implements Runnable
{
    private static Game game_instance = new Game("Nero's assault", 960, 580); /*!< Referinta catre instanta
                                                                                                singleton a jocului.*/
    private GameWindow      wnd;        /*!< Fereastra in care se va desena tabla jocului*/
    private boolean         runState;   /*!< Flag ce salveaza starea firului de executie.*/
    private Thread          gameThread; /*!< Referinta catre thread-ul de update si draw al ferestrei*/
    private BufferStrategy  bs;         /*!< Referinta catre un mecanism cu care se organizeaza memoria complexa pentru un canvas.*/
    /// Sunt cateva tipuri de "complex buffer strategies", scopul fiind acela de a elimina fenomenul de
    /// flickering (palpaire) a ferestrei.
    /// Modul in care va fi implementata aceasta strategie in cadrul proiectului curent va fi triplu buffer-at

    ///                         |------------------------------------------------>|
    ///                         |                                                 |
    ///                 ****************          *****************        ***************
    ///                 *              *   Show   *               *        *             *
    /// [ Ecran ] <---- * Front Buffer *  <------ * Middle Buffer * <----- * Back Buffer * <---- Draw()
    ///                 *              *          *               *        *             *
    ///                 ****************          *****************        ***************
    private Graphics        g;          /*!< Referinta catre un context grafic.*/
    ///Available states
    private State playState;        /*!< Referinta catre joc.*/
    private State menuState;        /*!< Referinta catre meniu.*/
    private State settingsState;    /*!< Referinta catre setari.*/
    private State gameOver;       /*!< Referinta catre gameOver.*/
    private KeyManager keyManager;      /*!< Referinta catre managerul evenimentelor tastaturii.*/
    private MouseManager mouseManager;      /*!< Referinta catre managerul evenimentelor mouse-ului.*/
    private GameCamera gameCamera;      /*!< Referinta catre camera jocului.*/
    private RefLinks refLink;            /*!< Referinta catre un obiect a carui sarcina este doar de a retine diverse referinte pentru a fi usor accesibile.*/

    /*! \fn public Game(String title, int width, int height)
       \brief Constructor de initializare al clasei Game.

       Acest constructor primeste ca parametri titlul ferestrei, latimea si inaltimea
       acesteia avand in vedere ca fereastra va fi construita/creata in cadrul clasei Game.

       \param title Titlul ferestrei.
       \param width Latimea ferestrei in pixeli.
       \param height Inaltimea ferestrei in pixeli.
    */
    private Game(String title, int width, int height)
    {
        /// Obiectul GameWindow este creat insa fereastra nu este construita
        /// Acest lucru va fi realizat in metoda init() prin apelul
        /// functiei BuildGameWindow();
        wnd = new GameWindow(title, width, height);
        /// Resetarea flagului runState ce indica starea firului de executie (started/stoped)
        runState = false;
        /// Se construieste obiectul de tip shortcut ce va retine o serie de referinte catre elementele importante din program.
        refLink = new RefLinks(this);
        /// Se contruieste obiectul de gestiune al evenimentelor de tastatura
        keyManager = new KeyManager();
        /// Se contruieste obiectul de gestiune al evenimentelor de mouse
        mouseManager = new MouseManager();
        /// Se contruieste camera jocului
        gameCamera   = new GameCamera(refLink, 0, 0);
    }

    /*! \fn private void init()
        \brief  Metoda construieste fereastra jocului, initializeaza aseturile, listenerul de tastatura etc.

        Fereastra jocului va fi construita prin apelul functiei BuildGameWindow();
        Sunt construite elementele grafice (assets): dale, player, elemente active si pasive.

     */
    private void InitGame() {
        /// Este construita fereastra grafica.
        wnd.BuildGameWindow();
        /// Se ataseaza ferestrei, managerul de tastatura pentru a primi evenimentele furnizate de fereastra.
        wnd.GetWndFrame().addKeyListener(keyManager);
        /// Idem si pentru panza jocului.
        wnd.GetCanvas().addKeyListener(keyManager);
        /// Se ataseaza ferestrei, managerul de mouse pentru a primi evenimentele furnizate de fereastra.
        wnd.GetWndFrame().addMouseListener(mouseManager);
        wnd.GetWndFrame().addMouseMotionListener(mouseManager);
        /// Idem si pentru panza jocului.
        wnd.GetCanvas().addMouseListener(mouseManager);
        wnd.GetCanvas().addMouseMotionListener(mouseManager);
        /// Se incarca toate elementele grafice
        Assets.Init();
        /// Definirea starilor programului
        playState       = new PlayState(refLink);
        menuState       = new MenuState(refLink);
        gameOver        = new GameOverState(refLink);
        /// Seteaza starea implicita prin care va fi lansat programul in executie.
        State.SetState(menuState);
    }

    /*! \fn public void run()
        \brief Functia ce va rula in thread-ul creat.

        Aceasta functie va actualiza starea jocului si va redesena tabla de joc (va actualiza fereastra grafica)
     */
    public void run() {
        ///Initializeaza obiectul game.
        InitGame();
        long oldTime = System.nanoTime();  /*!< Retine timpul in nanosecunde aferent frame-ului anterior.*/
        long currentTime;       /*!< Retine timpul curent de executie.*/
        /// Apelul functiilor Update() & Draw() trebuie realizat la fiecare 16.7 ms
        /// sau mai bine spus de 60 ori pe secunda.
        final int framesPerSecond   = 60; /*!< Numarul de frame-uri pe secunda.*/
        final double timeFrame      = 1000000000 / framesPerSecond;     /*!< Durata unui frame in ns.*/
        ///Atat timp cat thread-ul este pornit Update() si Draw()
        while (runState) {
            /// Se obtine timpul curent.
            currentTime = System.nanoTime();
            /// Daca diferenta dintre currentTime si oldTime este mai mare de 16.6 ms.
            if((currentTime - oldTime) > timeFrame) {
                /// Actualizeaza pozitiile elementelor.
                Update();
                /// Deseneaza elementele grafice in fereastra.
                Draw();
                oldTime = currentTime;
            }
        }
        /// Daca runState devine Fals, oprim thread-ul jocului.
        StopGame();
    }

    /*! \fn public synchronized void StartGame()
       \brief Creaza si starteaza firul separat de executie (thread).

       Metoda trebuie sa fie declarata synchronized pentru ca apelul acesteia sa fie semaforizat.
    */
    public synchronized void StartGame() {
        if(runState == false) {
            /// Se actualizeaza flagul de stare al thread-ului
            runState = true;
            /// Se construieste thread-ul avand ca parametru obiectul game.
            gameThread = new Thread(this);
            /// Thread-ul creat este lansat in executie( va executa metoda run).
            gameThread.start();
        }
        else {
            /// Thread-ul este creat si pornit deja.
            return;
        }
    }

    /*! \fn public synchronized void stop()
        \brief Opreste executie thread-ului.

        Metoda trebuie sa fie declarata synchronized pentru ca apelul acesteia sa fie semaforizat.
     */
    public synchronized void StopGame() {
        if(runState == true) {
            /// Actualizez starea thread-ului.
            runState = false;
            /// Metoda join() arunca exceptii motiv pentru care trebuie incadrata intr-un block try - catch.
            try {
                /// Metoda join() pune un thread in asteptare panca cand un altul isi termina executie.
                /// Totusi, in situatia de fata efectul apelului este de oprire a threadului.
                gameThread.join();
            }
            catch(InterruptedException ex)
            {
                /// In situatia in care apare o exceptie pe ecran vor fi afisate informatii utile pentru depanare.
                ex.printStackTrace();
            }
        }
        else {
            /// Thread-ul este oprit deja.
            return;
        }
    }

    /*! \fn private void Update()
        \brief Actualizeaza starea elementelor din joc.

        Metoda este declarata privat deoarece trebuie apelata doar in metoda run()
     */
    private void Update() {
        /// Determina starea tastelor.
        keyManager.Update();
        ///Trebuie obtinuta starea curenta pentru care urmeaza a se actualiza starea, atentie trebuie sa fie diferita de null.
        if(State.GetState() != null)
        {
            ///Actualizez starea curenta a jocului daca exista.
            State.GetState().Update();
        }
    }

    /*! \fn private void Draw()
       \brief Deseneaza elementele grafice in fereastra coresponzator starilor actualizate ale elementelor.

       Metoda este declarata privat deoarece trebuie apelata doar in metoda run()
    */
    private void Draw() {
        /// Returnez bufferStrategy pentru canvasul existent
        bs = wnd.GetCanvas().getBufferStrategy();
        /// Verific daca buffer strategy a fost construit sau nu
        if(bs == null) {
            /// Se executa doar la primul apel al metodei Draw()
            try {
                /// Se construieste tripul buffer
                wnd.GetCanvas().createBufferStrategy(3);
                return;
            }
            catch (Exception e) {
                /// Afisez informatii despre problema aparuta pentru depanare.
                e.printStackTrace();
            }
        }
        /// Se obtine contextul grafic curent in care se poate desena.
        g = bs.getDrawGraphics();
        /// Se sterge ce era
        g.clearRect(0, 0, wnd.GetWndWidth(), wnd.GetWndHeight());
        /// operatie de desenare
        ///Trebuie obtinuta starea curenta pentru care urmeaza a se actualiza starea, atentie trebuie sa fie diferita de null.
        if(State.GetState() != null) {
            ///Actualizez starea curenta a jocului daca exista.
            State.GetState().Draw(g);
        }
        /// end operatie de desenare
        /// Se afiseaza pe ecran
        bs.show();
        /// Elibereaza resursele de memorie aferente contextului grafic curent (zonele de memorie ocupate de
        /// elementele grafice ce au fost desenate pe canvas).
        g.dispose();
    }

    /*! \fn public int GetWidth()
        \brief Returneaza latimea ferestrei
     */
    public int GetWidth()
    {
        return wnd.GetWndWidth();
    }

    /*! \fn public int GetHeight()
        \brief Returneaza inaltimea ferestrei
     */
    public int GetHeight()
    {
        return wnd.GetWndHeight();
    }

    /*! \fn public KeyManager()
        \brief Returneaza managerul evenimentelor tastaturii.
     */
    public KeyManager GetKeyManager()
    {
        return keyManager;
    }

    /*! \fn public MouseManager GetMouseManager()
        \brief Returneaza managerul evenimentelor mouse-ului.
     */
    public MouseManager GetMouseManager() { return mouseManager; }

    /*! \fn public GameCamera GetCamera()
        \brief Returneaza camera jocului.
     */
    public GameCamera GetCamera(){ return gameCamera; }

    /*! \fn public PlayState GetPlayState()
        \brief Returneaza starea de joc.
     */
    public PlayState GetPlayState(){ return (PlayState) playState; }
    /*! \fn public State GetMenuState()
        \brief Returneaza starea de meniu.
     */
    public State GetMenuState(){ return menuState; }

    /*! \fn public State GetOverState()
        \brief Returneaza starea de gameOver.
     */
    public State GetGameOverState(){ return gameOver; }

    /*! \fn public static Game GetGame()
        \brief Returneaza o instanta statica a jocului.
     */
    public static Game GetGame(){
        return game_instance;
    }

}

