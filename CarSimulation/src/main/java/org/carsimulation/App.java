package org.carsimulation;

import java.util.Scanner;

public class App
{
    public static void main( String[] args )
    {
        Scanner scanner = new Scanner(System.in);
        UserInterface userInterface = new UserInterface(scanner);
        SimulationRunner runner = new SimulationRunner(userInterface);
        runner.start();
    }
}
