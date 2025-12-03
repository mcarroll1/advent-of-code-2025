package com.mcarroll;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

record Range(Long begin, Long end) {
}

public class App 
{
    public static List<Range> readInput(String filename) {
        List<Range> input = new ArrayList<>();
        try {
            String line = Files.readAllLines(Paths.get(filename)).get(0);
            List<String> ranges = Arrays.asList(line.split(","));
            System.err.println(ranges);
            ranges.forEach(range -> {String[] splitRange = range.split("-"); input.add(new Range(Long.valueOf(splitRange[0]), Long.valueOf(splitRange[1])));});

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return input;
    }

    public static Long findRepeat(Long code, boolean onlyTwice) {
        String stringCode = code.toString();
        int codeLength = stringCode.length();

        if (!onlyTwice && (codeLength % 2 == 1 || codeLength == 2) && codeLength > 1) {
            char firstDigit = stringCode.charAt(0);
            boolean uniform = true;
            for (int i = 1; i < codeLength; i++){
                if (firstDigit != stringCode.charAt(i)) {
                    uniform = false;
                }
            }
            if (uniform)
                return code;
        }

        int maxRepeatSize = codeLength / 2;
        if (onlyTwice) {
            maxRepeatSize = 2;
        }
        for(int i = 2; i <= maxRepeatSize; i++) {
            if (codeLength % i != 0) {
                continue;
            }
            int chunkSize = codeLength / i;

            boolean isInvalid = true;
            for (int j = 1; j < i; j++) {
                if (!stringCode.substring(0, chunkSize).equals(stringCode.substring(j*chunkSize, (j+1)*chunkSize))) {
                    isInvalid = false;
                }
            }
            if (isInvalid)
                return code;
        }

        return null;
    }

    public static void main( String[] args )
    {
        List<Long> invalids = new ArrayList<>();
        List<Range> input = readInput(args[0]);
        for (Range range : input) {
            for (long i = range.begin(); i <= range.end(); i++) {
                Long invalid = findRepeat(i, false);
                if (invalid != null) {
                    System.out.println(String.format("Found invalid id %s for range %s-%s", i, range.begin(), range.end()));
                    invalids.add(i);
                }
            }
        }
        long total = invalids.stream().mapToLong(Long::longValue).sum();
        System.out.println(String.format("Sum of all invalid ids: %s", total));
    }
}
