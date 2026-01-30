public class Instrumentinator {
    public static String armonizator(String brano)
    {
        //Prende un brano in semiminime e mette un accordo di 4/4 partendo da ogni inizio battuta
        String armonia = "";
        String [] branoDecostruito = brano.split(" ");

        for(int i = 0; i< branoDecostruito.length; i++)
        {
            if(i % 4 == 0)
            {
                armonia += branoDecostruito[i]+"majw ";
            }
        }

        return armonia;
    }
}
