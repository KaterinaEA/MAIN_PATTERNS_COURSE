import exceptionModule1L3.Command1;
import exceptionModule1L3.CommandProcessor;
import exceptionModule1L3.ICommand;
import exceptionModule1L3.LogCommand;
import iocModule2L9.IoC;
import lspIspModule1L2.move.IMovable;
import lspIspModule1L2.move.Move;
import lspIspModule1L2.move.Vector;
import lspIspModule1L2.rotate.Rotate;

public class Main {
    public static void main(String[] args) {


        IoC.resolve("IoC.Register"
                , "Command1"
                , () -> {
            return new Command1();
        }).execute();

    }


}
