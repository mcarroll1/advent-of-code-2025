package com.mcarroll;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


record BatteryWithPosition(Integer value, Integer position) {

}

public class App 
{
    public static List<List<Integer>> readInput(String filename) {
        List<List<Integer>> input = new ArrayList<>();
        try {
            Files.lines(Paths.get(filename))
                 .forEach(number -> {input.add(String.valueOf(number)
              .chars()
              .map(c -> c - '0')
              .boxed()
              .toList());});

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return input;
    }

    public static String recurseList(List<Integer> bank, int numBatteries) {
        if (numBatteries == 0) {
            return "";
        }

        List<BatteryWithPosition> bankWithPosition = new ArrayList<>();
        IntStream.range(0, bank.size())
         .forEach(i -> {
             bankWithPosition.add(new BatteryWithPosition(bank.get(i), i));
         });
        Collections.sort(bankWithPosition, (o1, o2) -> o2.value().compareTo(o1.value()) == 0 ? o1.position().compareTo(o2.position()) : o2.value().compareTo(o1.value()));

        for (int i = 0; i < bankWithPosition.size(); i++ ) {
            BatteryWithPosition battery = bankWithPosition.get(i);
            if (bank.size() - battery.position() >= numBatteries) {
                System.out.println(String.format("Recursing with list %s", bank));
                


                return Integer.toString(battery.value()) + recurseList(bank.subList(battery.position()+1, bank.size()), numBatteries - 1);
            }
        }

        return Integer.toString(bankWithPosition.get(0).value());

    }

    public static long determineHighest(List<Integer> bank, int numBatteries) {

        String joltage = recurseList(bank, numBatteries);

        long toReturn = Long.valueOf(joltage);



        System.out.println(String.format("Highest joltage for bank %s is %s", bank, toReturn));
        return toReturn;
    }

    public static void main( String[] args )
    {
        List<Long> invalids = new ArrayList<>();
        List<List<Integer>> input = readInput(args[0]);

        long total = input.stream().map(bank -> determineHighest(bank, 12)).mapToLong(Long::longValue).sum();
        System.out.println(String.format("Total joltage is %s", total));

    }
}
