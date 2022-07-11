package machine;

import java.util.Scanner;

enum State {
    READY,
    SHUTDOWN,
    WATER_INPUT,
    MILK_INPUT,
    BEANS_INPUT,
    CUPS_INPUT,
    BUY_CHOICE
}

public class CoffeeMachine {
     int water;
     int milk;
     int beans;
     int cups;
     int money;
     String input;
     State state = State.READY;

    CoffeeMachine(int water, int milk, int beans, int cups, int money) {
        this.water = water;
        this.milk = milk;
        this.beans = beans;
        this.cups = cups;
        this.money = money;
    }

    State getState() {
        return this.state;
    }

    void start() {
        ready();
    }

    void stop() {
        this.state = State.SHUTDOWN;
    }

    private void ready() {
        this.state = State.READY;
        System.out.println();
        System.out.print("Write action (buy, fill, take, remaining, exit): ");
    }

    void processInput(String input) {
        this.input = input;

        switch (this.state) {
            case READY:
                processReadyCommand();
                break;
            case WATER_INPUT:
            case MILK_INPUT:
            case BEANS_INPUT:
            case CUPS_INPUT:
                fill();
                break;
            case BUY_CHOICE:
                buy();
                break;
            default:
                System.out.println("Unknown input state");
                ready();
                break;
        }
    }

    private void processReadyCommand() {
        System.out.println();
        switch (input) {
            case "buy":
                buy();
                break;
            case "fill":
                fill();
                break;
            case "take":
                take();
                break;
            case "remaining":
                printRemaining();
                break;
            case "exit":
                stop();
                break;
            default:
                System.out.println("Unknown command");
                break;
        }
    }

    private void buy() {
        switch (this.state) {
            case READY:
                System.out.print("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, " +
                        "back - to main menu: ");
                this.state = State.BUY_CHOICE;
                break;
            case BUY_CHOICE:
                boolean enough = isEnough(this.input);

                switch (this.input) {
                    case "1": // espresso
                        if (enough) {
                            this.water -= 250;
                            this.beans -= 16;
                            this.cups -= 1;
                            this.money += 4;
                        }
                        break;
                    case "2": // latte
                        if (enough) {
                            this.water -= 350;
                            this.milk -= 75;
                            this.beans -= 20;
                            this.cups -= 1;
                            this.money += 7;
                        }
                        break;
                    case "3": // cappuccino
                        if (enough) {
                            this.water -= 200;
                            this.milk -= 100;
                            this.beans -= 12;
                            this.cups -= 1;
                            this.money += 6;
                        }
                        break;
                    case "back":
                        break;
                    default:
                        System.out.println("Unknown buy command");
                        break;
                }
                ready();
                break;
            default:
                System.out.println("Unknown buy state");
                ready();
                break;
        }
    }

    private void fill() {
        switch (this.state) {
            case READY:
                System.out.print("Write how many ml of water do you want to add: ");
                this.state = State.WATER_INPUT;
                break;
            case WATER_INPUT:
                this.water += Integer.parseInt(this.input);
                System.out.print("Write how many ml of milk do you want to add: ");
                this.state = State.MILK_INPUT;
                break;
            case MILK_INPUT:
                this.milk += Integer.parseInt(this.input);
                System.out.print("Write how many grams of coffee beans do you want to add: ");
                this.state = State.BEANS_INPUT;
                break;
            case BEANS_INPUT:
                this.beans += Integer.parseInt(this.input);
                System.out.print("Write how many disposable cups of coffee do you want to add: ");
                this.state = State.CUPS_INPUT;
                break;
            case CUPS_INPUT:
                this.cups += Integer.parseInt(this.input);
                ready();
                break;
            default:
                System.out.println("Unknown fill state");
                ready();
                break;
        }
    }

    private void take() {
        System.out.println("I gave you $" + this.money);
        this.money = 0;
        ready();
    }

    private void printRemaining() {
        System.out.println("The coffee machine has:");
        System.out.println(this.water + " of water");
        System.out.println(this.milk + " of milk");
        System.out.println(this.beans + " of coffee beans");
        System.out.println(this.cups + " of disposable cups");
        System.out.println("$" + this.money + " of money");
        ready();
    }

    private boolean isEnough(String type) {
        boolean enough = false;

        int waterLimit;
        int milkLimit;
        int beansLimit;

        switch (type) {
            case "1": // espresso
                waterLimit = 250;
                milkLimit = 0;
                beansLimit = 16;
                break;
            case "2": // latte
                waterLimit = 350;
                milkLimit = 75;
                beansLimit = 20;
                break;
            case "3": // cappuccino
                waterLimit = 200;
                milkLimit = 100;
                beansLimit = 12;
                break;
            default:
                return false;
        }
        if (this.water < waterLimit) {
            System.out.println("Sorry, not enough water!");
        } else if (this.milk < milkLimit) {
            System.out.println("Sorry, not enough milk!");
        } else if (this.beans < beansLimit) {
            System.out.println("Sorry, not enough coffee beans!");
        } else if (this.cups < 1) {
            System.out.println("Sorry, not enough disposable cups!");
        } else {
            enough = true;
            System.out.println("I have enough resources, making you a coffee!");
        }

        return enough;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CoffeeMachine coffeeMachine = new CoffeeMachine(400, 540, 120, 9, 550);
        coffeeMachine.start();

        while (coffeeMachine.getState() != State.SHUTDOWN) {
            coffeeMachine.processInput(scanner.next());
        }
    }
}



////public class CoffeeMachine {
////    public static void main(String[] args) {
////        Scanner scan = new Scanner(System.in);
////        // System.out.println("Starting to make a coffee");
////        // System.out.println("Grinding coffee beans");
////        // System.out.println("Boiling water");
////        // System.out.println("Mixing boiled water with crushed coffee beans");
////        // System.out.println("Pouring coffee into the cup");
////        // System.out.println("Pouring some milk into the cup");
////        // System.out.println("Coffee is ready!");
////        // System.out.println("Write how many cups of coffee you will need:");
////        // int cups = scan.nextInt();
////        // long water = cups * 200;
////        // long milk = cups * 50;
////        // long beans = cups * 15;
////        // System.out.println("For" + cups + "cups of coffee you will need:");
////        // System.out.println(water + " ml of water");
////        // System.out.println(milk + " ml of milk");
////        // System.out.println(beans + " g of coffee beans");
//////        System.out.println("Write how many ml of water the coffee machine has:");
//////        int water_machine = scan.nextInt();
//////        System.out.println("Write how many ml of milk the coffee machine has: ");
//////        int milk_machine = scan.nextInt();
//////        System.out.println("Write how many grams of coffee beans the coffee machine has:");
//////        int bean_machine = scan.nextInt();
//////        System.out.println("Write how many cups of coffee you will need:");
//////        int cups_machine = scan.nextInt();
//////        int w = water_machine / 200;
//////        int m = milk_machine / 50;
//////        int b = bean_machine / 15;
//////        if (w == 0 || m == 0 || b == 0) {
//////            System.out.println("No, I can make only 0 cup(s) of coffee");
//////            if (cups_machine == 0) {
//////                System.out.println("Yes, I can make that amount of coffee");
//////            }
//////        } else if (w > 0 && m > 0 && b > 0 && cups_machine > 0) {
//////             int c;
//////             if(w <= m && w <= b) {
//////                  c = w;
//////             }  else if (m < w && m < b) {
//////                  c = m;
//////             }  else {
//////                  c = b;
//////             }
//////
//////                if (c == cups_machine) {
//////                    System.out.println("Yes, I can make that amount of coffee");
//////                } else if (c > cups_machine) {
//////                    System.out.println("Yes, I can make that amount of coffee (and even " + (cups_machine - c) + " more than that)");
//////                } else if (c < cups_machine) {
//////                    System.out.println("No, I can make only "+ c +" cup(s) of coffee");
//////                }
//////        }
////        int money = 550;
////        int water = 400;
////        int milk = 540;
////        int beans = 120;
////        int cups = 9;
////        System.out.println("The coffee machine has:");
////        System.out.println(water + " of water");
////        System.out.println(milk + " of milk");
////        System.out.println(beans + " of coffee beans");
////        System.out.println(cups + " of disposable cups");
////        System.out.println(money + " of money");
////        System.out.println("Write action (buy, fill, take, remaining, exit):");
////        String action = scan.nextLine();
////        switch (action) {
////            case "buy":
////                System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
////                String buy = scan.nextLine();
////                switch (buy) {
////                    case "1":
////                        if (water >= 250 && beans >= 16 && cups >= 1) {
////                            System.out.println("I have enough resources, making you a coffee!");
////                            water -= 250;
////                            beans -= 16;
////                            cups -= 1;
////                            money += 4;
////                        } else if (water < 250) {
////                            System.out.println("Sorry, not enough water!");
////                        } else if (beans < 16) {
////                            System.out.println("Sorry, not enough coffee beans!");
////                        } else if (cups < 1) {
////                            System.out.println("Sorry, not enough disposable cups!");
////                        }
////                        break;
////                    case "2":
////                        if (water >= 350 && beans >= 20 && cups >= 1 && milk >= 75) {
////                            System.out.println("I have enough resources, making you a coffee!");
////                            water -= 350;
////                            beans -= 20;
////                            cups -= 1;
////                            milk -= 75;
////                            money += 7;
////                        } else if (water < 350) {
////                            System.out.println("Sorry, not enough water!");
////                        } else if (beans < 20) {
////                            System.out.println("Sorry, not enough coffee beans!");
////                        } else if (cups < 1) {
////                            System.out.println("Sorry, not enough disposable cups!");
////                        } else if (milk < 75) {
////                            System.out.println("Sorry, not enough milk!");
////                        }
////                        break;
////                    case "3":
////                        if (water >= 200 && beans >= 12 && cups >= 1 && milk >= 100) {
////                            System.out.println("I have enough resources, making you a coffee!");
////                            water -= 200;
////                            beans -= 12;
////                            cups -= 1;
////                            milk -= 100;
////
////                        }
////                        break;
////                    case "back":
////                        break;
////                    default:
////                        System.out.println("Invalid input!");
////                        break;
////                }
////                System.out.println("The coffee machine has:");
////                System.out.println(water + " of water");
////                System.out.println(milk + " of milk");
////                System.out.println(beans + " of coffee beans");
////                System.out.println(cups + " of disposable cups");
////                System.out.println(money + " of money");
////                break;
////            case "fill":
////                    System.out.println("Write how many ml of water do you want to add:");
////                    int water_add = scan.nextInt();
////                    System.out.println("Write how many ml of milk do you want to add:");
////                    int milk_add = scan.nextInt();
////                    System.out.println("Write how many grams of coffee beans do you want to add:");
////                    int beans_add = scan.nextInt();
////                    System.out.println("Write how many disposable cups of coffee do you want to add:");
////                    int cups_add = scan.nextInt();
////                    water += water_add;
////                    milk += milk_add;
////                    beans += beans_add;
////                    cups += cups_add;
////                    System.out.println("The coffee machine has:");
////                    System.out.println(water + " of water");
////                    System.out.println(milk + " of milk");
////                    System.out.println(beans + " of coffee beans");
////                    System.out.println(cups + " of disposable cups");
////                    System.out.println(money + " of money");
////                    break;
////            case "take":
////                System.out.println("I gave you $" + money);
////                money = 0;
////                break;
////            default:
////                throw new IllegalStateException("Unexpected value: " + action);
////        }
////
////
////    }
////}
////
//
//import java.util.Scanner;
//
//public class CoffeeMachine {
//
//    //printing the menu
//    public static void print(int water, int milk, int beans, int cups, int money) {
//        System.out.println("The coffee machine has:");
//        System.out.println(water + " of water");
//        System.out.println(milk + " of milk");
//        System.out.println(beans + " of coffee beans");
//        System.out.println(cups + " of disposable cups");
//        System.out.println("$" + money + " of money");
//    }
//
//
////      Check if there is enough resources to make a given coffee type
//    public static boolean isEnough(String type, int water, int milk, int beans, int cups) {
//        boolean enough = false;
//
//        int waterLimit;
//        int milkLimit;
//        int beansLimit;
//
//        switch (type) {
//            case "1": // espresso
//                waterLimit = 250;
//                milkLimit = 0;
//                beansLimit = 16;
//                break;
//            case "2": // latte
//                waterLimit = 350;
//                milkLimit = 75;
//                beansLimit = 20;
//                break;
//            case "3": // cappuccino
//                waterLimit = 200;
//                milkLimit = 100;
//                beansLimit = 12;
//                break;
//            default:
//                return false;
//        }
//        if (water < waterLimit) {
//            System.out.println("Sorry, not enough water!");
//        } else if (milk < milkLimit) {
//            System.out.println("Sorry, not enough milk!");
//        } else if (beans < beansLimit) {
//            System.out.println("Sorry, not enough coffee beans!");
//        } else if (cups < 1) {
//            System.out.println("Sorry, not enough disposable cups!");
//        } else {
//            enough = true;
//            System.out.println("I have enough resources, making you a coffee!");
//        }
//
//        return enough;
//    }
//
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//
//        int water = 400;
//        int milk = 540;
//        int beans = 120;
//        int cups = 9;
//        int money = 550;
//
//        while (true) {
//            System.out.print("Write action (buy, fill, take, remaining, exit): ");
//            String command = scanner.next();
//            System.out.println();
//
//            switch (command) {
//                case "buy":
//                    System.out.print("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, " +
//                            "back - to main menu: ");
//                    String type = scanner.next();
//                    boolean enough = isEnough(type, water, milk, beans, cups);
//
//                    switch (type) {
//                        case "1": // espresso
//                            if (enough) {
//                                water -= 250;
//                                beans -= 16;
//                                cups -= 1;
//                                money += 4;
//                            }
//                            break;
//                        case "2": // latte
//                            if (enough) {
//                                water -= 350;
//                                milk -= 75;
//                                beans -= 20;
//                                cups -= 1;
//                                money += 7;
//                            }
//                            break;
//                        case "3": // cappuccino
//                            if (enough) {
//                                water -= 200;
//                                milk -= 100;
//                                beans -= 12;
//                                cups -= 1;
//                                money += 6;
//                            }
//                            break;
//                        case "back":
//                            break;
//                        default:
//                            System.out.println("Unknown coffee type");
//                            break;
//                    }
//                    break;
//                case "fill":
//                    System.out.print("Write how many ml of water do you want to add: ");
//                    water += scanner.nextInt();
//                    System.out.print("Write how many ml of milk do you want to add: ");
//                    milk += scanner.nextInt();
//                    System.out.print("Write how many grams of coffee beans do you want to add: ");
//                    beans += scanner.nextInt();
//                    System.out.print("Write how many disposable cups of coffee do you want to add: ");
//                    cups += scanner.nextInt();
//                    break;
//                case "take":
//                    System.out.printf("I gave you $%s\n", money);
//                    money = 0;
//                    break;
//                case "remaining":
//                    print(water, milk, beans, cups, money);
//                    break;
//                case "exit":
//                    return;
//                default:
//                    System.out.println("Unknown command");
//                    break;
//            }
//            System.out.println();
//        }
//    }
//}