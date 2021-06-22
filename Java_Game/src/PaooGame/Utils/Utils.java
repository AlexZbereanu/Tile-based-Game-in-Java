package PaooGame.Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/*! \class public class Utils
    \brief Va implementa incarcarea hartii dintr-un fisier.
 */
public class Utils {
    /*!< \fn public static String loadFileAsString(String path)
        \brief Returneaza un string cu elementele hartii.

        \param path Calea catre fisier.
     */
    public static String loadFileAsString(String path){
        StringBuilder builder = new StringBuilder();

        try{
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            while((line = br.readLine()) != null)
                builder.append(line + "\n");
            br.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        return builder.toString();
    }

    /*!< \fn public static int parseInt(String path)
        \brief Parseaza si returneaza o matrice cu elementele hartii.

        \param number String cu elementele hartii.
     */
    public static int parseInt(String number){
        try{
            return Integer.parseInt(number);
        }catch(NumberFormatException e){
            e.printStackTrace();
            return 0;
        }
    }
}
