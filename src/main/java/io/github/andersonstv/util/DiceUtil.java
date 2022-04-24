package io.github.andersonstv.util;


import com.bernardomg.tabletop.dice.Dice;
import com.bernardomg.tabletop.dice.history.RollHistory;
import com.bernardomg.tabletop.dice.history.RollResult;
import com.bernardomg.tabletop.dice.interpreter.DiceRoller;
import com.bernardomg.tabletop.dice.parser.DefaultDiceParser;
import com.bernardomg.tabletop.dice.parser.DiceParser;

import java.util.Iterator;

public class DiceUtil {

    static private Iterable<RollResult> parseAndRoll(String expression){
        DiceParser parser = new DefaultDiceParser();
        DiceRoller roller = new DiceRoller();

        RollHistory history = roller.transform(parser.parse(expression));
        return history.getRollResults();
    }

    static private String printAllRolls(Iterable<RollResult> results){
        StringBuilder allRolls = new StringBuilder();
        for (RollResult result : results) {
            Dice die = result.getDice();
            allRolls.append(die.getQuantity()).append("d").append(die.getSides());
            allRolls.append(" ").append(result.getAllRolls());
            if (results.iterator().hasNext()){
                allRolls.append(" ");
            }
        }
        return allRolls.toString();
    }

    static public String simple(String messageContent){
        String expression = messageContent.replaceAll("\\$roll| ", "");
        return FormatUtil.validateDiscordLimit(simpleRoll(expression));
    }
    static public String simpleRoll(String expression){
        Iterable<RollResult> results = parseAndRoll(expression);

        StringBuilder response = new StringBuilder("**Result:** ");
        response.append(printAllRolls(results));

        if (response.length() >= 2000){
            response = new StringBuilder("Error: Result surpasses Discord character limit");
        }
        return response.toString();
    }

    static public String wod(String messageContent){
        String result;
        int quantity;
        int difficulty;
        String[] inputArray = messageContent.split(" ");

        switch (inputArray.length) {
            case 3 -> {
                quantity = FormatUtil.isInteger(inputArray[1]) ? Integer.parseInt(inputArray[1]) : 1;
                difficulty = FormatUtil.isInteger(inputArray[2]) ? Integer.parseInt(inputArray[2]) : 8;
                result = wodRoll(quantity, difficulty);
            }
            case 2 -> {
                quantity = FormatUtil.isInteger(inputArray[1]) ? Integer.parseInt(inputArray[1]) : 1;
                result = wodRoll(quantity);
            }
            case 1 -> result = wodRoll(1);
            default -> result = "Invalid Input: Try $wod <number of dice> <success difficulty>." +
                    "Ex: $wod 6 8";
        }
        return result;
    }
    static public String wodRoll(int quantity, int difficulty){
        int countSuccess = 0;
        int countFail = 0;
        StringBuilder response = new StringBuilder("**Result:** ");
        Iterable<RollResult> results = parseAndRoll(quantity + "d10");

        for (RollResult result : results) {
            Dice die = result.getDice();
            response.append(die.getQuantity()).append("d").append(die.getSides());
            response.append(" ").append(result.getAllRolls());
            if (results.iterator().hasNext()){
                response.append(" ");
            }
            Iterable<Integer> allRolls = result.getAllRolls();
            for (Integer roll : allRolls) {
                if (roll >= difficulty){
                    countSuccess += 1;
                } else if( roll <= 1){
                    countFail += 1;
                }
            }
        }
        response.append(FormatUtil.sep).append("**Total Successes:** ").append(countSuccess - countFail);
        response.append(FormatUtil.sep).append("**Successes:** ").append(countSuccess);
        response.append(FormatUtil.sep).append("**Failures:** ").append(countFail);
        return response.toString();
    }
    static public String wodRoll(int quantity){
        return wodRoll(quantity, 8);
    }
}
