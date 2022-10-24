import java.util.Scanner;

public class DentalRecords {
    private static final Scanner keyboard = new Scanner (System.in);
    private static final int UPPER_TEETH_LEVEL = 0;
    private static final int LOWER_TEETH_LEVEL = 1;
    private static final int MAX_NUMBER_OF_TEETH_LAYERS = 2;
    private static final int MAX_NUMBER_OF_PEOPLE = 6;
    private static final int MIN_NUMBER_OF_PEOPLE = 1;
    private static final int MAX_NUMBER_OF_TEETH = 8;

    public static void main (String [] args){
//Captures names of people and how many there are:
        System.out.print("Welcome to the Floridian Tooth Records\n" + "--------------------------------------\n" + "Please enter number of people in the family : ");
        int numberOfPeopleInFamily;
        numberOfPeopleInFamily = keyboard.nextInt();
//Checks that number of people in family is less than 6 and possible
        while (numberOfPeopleInFamily > MAX_NUMBER_OF_PEOPLE || numberOfPeopleInFamily < MIN_NUMBER_OF_PEOPLE){
            System.out.print("Invalid number of people, try again         : ");
            numberOfPeopleInFamily = keyboard.nextInt();
        }
//Creates array of all teeth and validates that entries are valid:
        String [] familyNames = new String [numberOfPeopleInFamily];
        int currentPerson;
        int currentToothLevel;
        int currentTooth = 0;
        String [][][] familyDentalRecords = new String [numberOfPeopleInFamily][MAX_NUMBER_OF_TEETH_LAYERS][];
        for(currentPerson = 0; currentPerson < familyNames.length; currentPerson++){
            System.out.print("Please enter the name for family member " + (currentPerson + 1) + "   : ");
            familyNames[currentPerson] = keyboard.next();
            for(currentToothLevel = 0; currentToothLevel < MAX_NUMBER_OF_TEETH_LAYERS; currentToothLevel++){
                if (currentToothLevel == UPPER_TEETH_LEVEL) {
                    System.out.printf("%-28s%-16s%-2s","Please enter the uppers for ",familyNames[currentPerson], ": ");
                    populateTeethArrayForGivenLayerOfTeeth(familyDentalRecords, currentToothLevel, currentPerson, currentTooth);
                } else {
                    System.out.printf("%-28s%-16s%-2s","Please enter the lowers for ",familyNames[currentPerson], ": ");
                    populateTeethArrayForGivenLayerOfTeeth(familyDentalRecords, currentToothLevel, currentPerson, currentTooth);
                }
            }
        }
//This is the menu and calls to other methods:
        char choice;
        System.out.print('\n' + "(P)rint, (E)xtract, (R)oot, e(X)it          : ");
        choice = keyboard.next().charAt(0);
        while(choice != 'X' && choice != 'x') {
            if (choice == 'P' || choice == 'p') {
                printOutDentalRecordsMethod(familyDentalRecords, familyNames, numberOfPeopleInFamily);
                System.out.print('\n' + "(P)rint, (E)xtract, (R)oot, e(X)it          : ");
                choice = keyboard.next().charAt(0);
            } else if (choice == 'E' || choice == 'e') {
                extractMethod(familyDentalRecords, familyNames);
                System.out.print('\n' + "(P)rint, (E)xtract, (R)oot, e(X)it          : ");
                choice = keyboard.next().charAt(0);
            } else if (choice == 'R' || choice == 'r') {
                rootCanalMethod(familyDentalRecords);
                System.out.print('\n' + "(P)rint, (E)xtract, (R)oot, e(X)it          : ");
                choice = keyboard.next().charAt(0);
            } else{
                System.out.print("Invalid menu option, try again              : ");
                choice = keyboard.next().charAt(0);
            }
        }
        System.out.println('\n' + "Exiting the Floridian Tooth Records :-)");
    } //end of main method

    //This following method prints out all current data for family:
    public static void printOutDentalRecordsMethod(String [][][] familyDentalRecords, String [] familyNames, int numberOfPeopleInFamily) {
        for (int currentPerson = 0; currentPerson < numberOfPeopleInFamily; currentPerson++) {
            System.out.println(familyNames[currentPerson]);
            for (int currentToothLayer = 0; currentToothLayer < familyDentalRecords[currentPerson].length; currentToothLayer++) {
                if (currentToothLayer == UPPER_TEETH_LEVEL) {
                    System.out.print("  Uppers:  ");
                    for (int currentTooth = 0; currentTooth < familyDentalRecords[currentPerson][currentToothLayer].length; currentTooth++) {
                        System.out.print((currentTooth + 1) + ":" + familyDentalRecords[currentPerson][currentToothLayer][currentTooth] + " ");
                    }
                    System.out.println("");
                } else {
                    System.out.print("  Lowers:  ");
                    for (int k = 0; k < familyDentalRecords[currentPerson][currentToothLayer].length; k++) {
                        System.out.print((k + 1) + ":" + familyDentalRecords[currentPerson][currentToothLayer][k] + " ");
                    }
                    System.out.println("");
                }
            }
        }
    }

    //The following method does the extraction of teeth:
    public static String[][][] extractMethod(String [][][] familyDentalRecords, String [] familyNames){
        String desiredPerson;
        int targetPerson = 0;
        char desiredToothLayer;
        int desiredToothNumber;
        int match = 0;
//This verifies if family member is a valid family member:
        System.out.print("Which family member                         : ");
        desiredPerson = keyboard.next();
        while (match < 1){
            match = 0;
            for (int currentPerson = 0; currentPerson < familyNames.length; currentPerson++) {
                String temporaryFamilyNames;
                desiredPerson = desiredPerson.toUpperCase();
                temporaryFamilyNames = familyNames[currentPerson].toUpperCase();
                if (desiredPerson.equals(temporaryFamilyNames)) {
                    match++;
                    targetPerson = currentPerson;
                }
            }
            if (match == 0){
                System.out.print("Invalid family member, try again            : ");
                desiredPerson = keyboard.next();
            }
        }
//Extracts teeth specifically, includes validation for upper/lower and if teeth are already missing:
        System.out.print("Which tooth layer (U)pper or (L)ower        : ");
        desiredToothLayer = keyboard.next().charAt(0);
        int invalid = 2;
        int targetToothLayer;
        while (invalid > 1) {
            if (desiredToothLayer == 'U' || desiredToothLayer == 'u') {
                targetToothLayer = UPPER_TEETH_LEVEL;
                validateAndReplaceTeeth(familyDentalRecords, targetPerson,targetToothLayer);
                invalid--;
            } else if (desiredToothLayer == 'L' || desiredToothLayer == 'l') {
                targetToothLayer = LOWER_TEETH_LEVEL;
                validateAndReplaceTeeth(familyDentalRecords, targetPerson, targetToothLayer);
                invalid--;
            } else {
                System.out.print("Invalid layer, try again                    : ");
                desiredToothLayer = keyboard.next().charAt(0);
                invalid = 2;
            }
        }
        return(familyDentalRecords);
    }

    //Calculates root canal indices:
    public static void rootCanalMethod(String[][][]familyDentalRecords){
        double amountOfBicuspids = 0.0;
        double amountOfIncisors = 0.0;
        double amountOfMissing = 0.0;
        for(int currentPerson = 0; currentPerson < familyDentalRecords.length; currentPerson++){
            for(int currentToothLayer = 0; currentToothLayer < familyDentalRecords[currentPerson].length; currentToothLayer++){
                for(int currentTooth = 0; currentTooth < familyDentalRecords[currentPerson][currentToothLayer].length; currentTooth++){
                    if (familyDentalRecords[currentPerson][currentToothLayer][currentTooth].equals("B")){
                        amountOfBicuspids++;
                    }else if (familyDentalRecords[currentPerson][currentToothLayer][currentTooth].equals("I")){
                        amountOfIncisors++;
                    }else {
                        amountOfMissing++;
                    }
                }
            }
        }
        double discriminant = (amountOfBicuspids * amountOfBicuspids - (4 * amountOfIncisors * -amountOfMissing));
        double rootOne = (-amountOfBicuspids + Math.sqrt(discriminant)) / (2 * amountOfIncisors);
        double rootTwo = (-amountOfBicuspids - Math.sqrt(discriminant)) / (2 * amountOfIncisors);
        if (discriminant > 0){
            System.out.println("One root canal at     " + String.format("%.2f",rootOne));
            System.out.println("Another root canal at " + String.format("%.2f",rootTwo));
        } else if (discriminant < 0){
            System.out.println("Two imaginary roots");
        } else{
            rootOne = -amountOfBicuspids/(2 * amountOfIncisors);
            System.out.println("One root canal at     " + String.format("%.2f",rootOne));
        }
    }

    //Sub method for validating and populating array with teeth
    public static String[][][] populateTeethArrayForGivenLayerOfTeeth(String [][][] familyDentalRecords, int currentToothLevel, int currentPerson, int currentTooth){
        String userTeethInput;
        userTeethInput = keyboard.next();
        userTeethInput = userTeethInput.toUpperCase();
        int invalid = 2;
        while (invalid > 1) {
            familyDentalRecords[currentPerson][currentToothLevel] = new String[userTeethInput.length()];
            if (userTeethInput.length() > MAX_NUMBER_OF_TEETH){
                System.out.print("Too many teeth, try again                   : ");
                userTeethInput = keyboard.next();
                userTeethInput = userTeethInput.toUpperCase();
                invalid = 2;
            } else {
                for (currentTooth = 0; currentTooth < userTeethInput.length(); currentTooth++) {
                    familyDentalRecords[currentPerson][currentToothLevel][currentTooth] = String.valueOf(userTeethInput.charAt(currentTooth));
                    if (userTeethInput.charAt(currentTooth) != 'I' && userTeethInput.charAt(currentTooth) != 'B' && userTeethInput.charAt(currentTooth) != 'M') {
                        invalid++;
                    }
                }
                if (invalid == 2) {
                    invalid = 0;
                } else {
                    invalid = 2;
                    System.out.print("Invalid teeth types, try again              : ");
                    userTeethInput = keyboard.next();
                    userTeethInput = userTeethInput.toUpperCase();
                }
            }
        }
        return (familyDentalRecords);
    }

    //Validates input and checks for already missing teeth. Replaces teeth:
    public static String[][][] validateAndReplaceTeeth(String [][][] familyDentalRecords, int targetPerson, int targetToothLayer) {
        System.out.print("Which tooth number                          : ");
        int desiredToothNumber = keyboard.nextInt();
        int toothNumberValidation = 2;
        while (toothNumberValidation > 1){
            if (desiredToothNumber > familyDentalRecords[targetPerson][targetToothLayer].length ) {
                System.out.print("Invalid tooth number, try again             : ");
                toothNumberValidation = 2;
                desiredToothNumber = keyboard.nextInt();
            } else {
                if (!familyDentalRecords[targetPerson][targetToothLayer][desiredToothNumber - 1].equals("M")) {
                    familyDentalRecords[targetPerson][targetToothLayer][desiredToothNumber - 1] = "M";
                    toothNumberValidation = 0;
                } else {
                    System.out.print("Missing tooth, try again                    : ");
                    desiredToothNumber = keyboard.nextInt();
                    toothNumberValidation = 2;
                }
            }
        }
        return(familyDentalRecords);
    }

} //end of class

