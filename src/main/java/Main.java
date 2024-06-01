import iocModule2L9.CommandMove;
import iocModule2L9.Init;
import iocModule2L9.IoC;


public class Main {
    public static void main(String[] args) {

        Init init = new Init();

        init.init();

        CommandMove move = IoC.resolve("CommandMove");

        move.execute();

    }


}
