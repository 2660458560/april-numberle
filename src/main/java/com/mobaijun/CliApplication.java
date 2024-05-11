package com.mobaijun;

import com.mobaijun.service.impl.GameInterfaceImpl;
import com.mobaijun.util.TextUtil;
import java.util.Scanner;

/**
 * Description: [命令行客户端]
 * Author: [mobaijun]
 * Date: [2024/5/7 14:04]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public class CliApplication {

    public static void main(String[] args) {
        GameInterfaceImpl model = new GameInterfaceImpl(TextUtil.readTextFromFile());
        System.out.println("Play the Numberle game:");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String enter = scanner.nextLine();
            if (enter.equals("end")) {
                System.exit(0);
            }
            char[] enterChars = enter.toCharArray();
            if (enterChars.length != 7) {
                System.out.println("Too short");
            } else {
                for (char enterChar : enterChars) {
                    model.processInput(String.valueOf(enterChar));
                }
                int[] verify = model.checkEquation();
                if (verify[0] == 1) {
                    System.out.println("No equals sign");
                    model.clear();
                    System.out.println("Enter again");
                } else if (verify[0] == 2) {
                    System.out.println("No operator");
                    model.clear();
                    System.out.println("Enter again");
                } else if (verify[0] == 3) {
                    System.out.println("left and right of the expression are not equal");
                    model.clear();
                    System.out.println("Enter again");
                } else if (verify[0] == 5) {
                    System.out.println("You lose!");
                    System.out.println("Restart or end: ");
                    String sc = scanner.next();
                    if (sc.equals("r")) {
                        model.startNewGame();
                        System.out.println("Start new game:");
                        scanner.nextLine();
                    } else if (sc.equals("end")) {
                        System.exit(0);
                    }
                } else if (verify[0] == 6) {
                    System.out.println("You win!");
                    System.out.println("Restart or end: ");
                    String sc = scanner.next();
                    if (sc.equals("r")) {
                        model.startNewGame();
                    } else if (sc.equals("end")) {
                        System.exit(0);
                    }
                } else {
                    if (verify.length == 7) {
                        for (int i = 0; i < verify.length; i++) {
                            if (verify[i] == 2) {
                                System.out.println(model.getEnteredStrings()[i] + " (Exclude it)");
                            } else if (verify[i] == 1) {
                                System.out.println(model.getEnteredStrings()[i] + " (Include it but location error)");
                            } else if (verify[i] == 0) {
                                System.out.println(model.getEnteredStrings()[i] + " (Correct character)");
                            }
                        }
                    }
                    model.clear();
                    model.setLineNum(model.getLineNum() + 1);
                    System.out.println("You can try " + (6 - model.getLineNum()) + " times");
                }
            }
        }
    }
}
