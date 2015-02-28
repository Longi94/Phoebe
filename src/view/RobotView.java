package view;

import model.Robot;

/**
 * Egy robot objektum kinézete
 *
 * A példányosodó robot objektum létrehozza a saját nézetét, amelynek mintája ez az osztály
 *
 * @author Reményi Gergely
 * @since 2015-02-28
 */
public class RobotView {

    /**
     * Robot modell referencia
     */
    private Robot robot;

    /**
     * RobotView konstruktor
     *
     * Beállítja a robot modell referenciát
     *
     * @param robot Robot modell referencia
     */
    public RobotView(Robot robot){
        this.robot = robot;
    }

}
