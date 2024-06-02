import iocModule2L1.CommandMove;
import iocModule2L1.Init;
import iocModule2L1.IoC;


public class Main {
    public static void main(String[] args) {

        Init init = new Init();

        init.init();

        CommandMove move = IoC.resolve("CommandMove");

        move.execute();

    }


}
