package MacroCommandModule1L8;

import lombok.Getter;
import lombok.Setter;

@Getter
public class MoveObject {

    @Setter
    // текущее кол-во топлива
    public double fuelLevel;

    public double minFuelLevel;

    // скорость расхода топлива в литрах в секунду
    public double fuelConsumptionPerSecond;

    //время в движении
    public double timeMillis;

    public static MoveObject burnFuel (MoveObject moveObject){

        double currentFuelLevel = moveObject.getFuelLevel(); // метод для получения текущего уровня топлива

        double fuelConsumptionPerSecond = moveObject.getFuelConsumptionPerSecond();// скорость расхода топлива в литрах в секунду

        System.out.printf("Burn Fuel before currentFuelLevel = %f\n", currentFuelLevel);

        currentFuelLevel -= (fuelConsumptionPerSecond * moveObject.getTimeMillis()) / 1000; // вычисляем новый уровень топлива с учётом скорости расхода

        moveObject.setFuelLevel(currentFuelLevel);

        System.out.printf("Burn Fuel after currentFuelLevel = %f\n", currentFuelLevel);

        return moveObject;
    }

}
