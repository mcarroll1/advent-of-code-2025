package com.mcarroll;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Hello world!
 *
 */
public class App 
{
    public static List<String> readInput(String filename) {
        List<String> input = new ArrayList<>();
        try {
            Files.lines(Paths.get(filename))
                 .forEach(input::add);

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return input;
    }

    public static void main( String[] args )
    {
        List<String> input = readInput(args[0]);
        List<Integer> instructions;
        instructions = input.stream().map(line -> {int insruction = Integer.parseInt(line.substring(1)); return line.startsWith("L") ? -insruction : insruction;}).collect(Collectors.toList());

        Dial dial = new Dial(50);


        int timesStoppedAtZero = 0;
        int timesMovedPastZero = 0;
        for (Integer instruction : instructions) {

            // Account for all the extra spins, this is my writing not AI lol
            int extraRotations = Math.abs(instruction) / 100;
            timesMovedPastZero += extraRotations;


            int effectiveRotation = instruction % 100;
            int effectivePosition = dial.position() + effectiveRotation;


            // Account for dial starting at 0
            if (effectivePosition > 100 || (effectivePosition < 0 && dial.position() != 0)) {
                System.out.println(String.format("Dial moved from position %s past 0 %s times for instruction %d", dial.position(), (extraRotations + 1), instruction));
                timesMovedPastZero++;
            }
            dial = new Dial((effectivePosition + 100) % 100);
            if (dial.position() == 0) {
                timesStoppedAtZero++;
                System.out.println("Dial at 0!");
            }
        }

        System.out.println("Final dial position: " + dial.position());
        System.out.println("Times stopped at 0: " + timesStoppedAtZero);
        System.out.println("Times moved past 0: " + timesMovedPastZero);
        System.out.println("Total times at 0: " + (timesStoppedAtZero + timesMovedPastZero));
    }
}
