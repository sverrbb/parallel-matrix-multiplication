import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;


class Main {

    static int runs = 7;
    static int seed = 100;
    static int n;
    static Oblig2Precode.Mode runOperation;

    //Font colors used in testing feedback
    public static final String GREEN = "\u001B[32m";
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";

    public static void main(String[] args) {

        //Checks that number of arguments is right
        if(args.length != 1){
            System.out.println("The program requires 1 argument");
            System.out.println(" - e.g: java Main <n>");
            return;
        }

        //Try to take input from command line and assign to variable n
        try {
            n = Integer.parseInt(args[0]);
        } catch(Exception e) {
            System.out.println("Error! Could not read input arguments");
        }


        // Present menu and choose option
        Scanner input = new Scanner(System.in);
        showMenu();
        System.out.print("\nOption: ");
        int mode = input.nextInt();
        System.out.println();

        // Run chosen option
        switch (mode) {
          case 1:
            showCommands();
            System.out.print("\nCommand: ");
            int op = input.nextInt();
            runMatrixOperation(op);
            break;
          case 2:
            timeMeasurementsForAllN();
            break;
          case 3:
            checkAllResults(seed, n);
            break;
          default:
            System.out.println("Mode does not exist");
            break;
        }
    }



    /**
     * Run a spesific operation for Matrix multiplication
     * @param operation - operation for doing the Matrix multiplication
     */
    public static void runMatrixOperation(int operation) {
        //Store operations in hashmap with input command as key and operation as value
        HashMap<Integer, Oblig2Precode.Mode> operations = new HashMap<Integer, Oblig2Precode.Mode>();
        operations.put(1, Oblig2Precode.Mode.SEQ_NOT_TRANSPOSED);
        operations.put(2, Oblig2Precode.Mode.SEQ_A_TRANSPOSED);
        operations.put(3, Oblig2Precode.Mode.SEQ_B_TRANSPOSED);
        operations.put(4, Oblig2Precode.Mode.PARA_NOT_TRANSPOSED);
        operations.put(5, Oblig2Precode.Mode.PARA_A_TRANSPOSED);
        operations.put(6, Oblig2Precode.Mode.PARA_B_TRANSPOSED);

        //Checks if spesific operation is chosen
        if(operation > 0 && operation <= 6) {
            runOperation = operations.get(operation);
            timeMode(seed, n, runOperation);
            return;
        }
        else {
            System.out.println("Operation does not exist!");
            return;
        }
    }



    /**
     * Menu presented when user run the program
     */
    public static void showMenu(){
        System.out.println("\n**** MENU ****");
        System.out.println("‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾");
        System.out.println("1. Choose operation");
        System.out.println("2. Time all operations with all values of N");
        System.out.println("3. Test program");
    }



    /**
     * Show available operations for doing the Matrix multiplication
     */
    public static void showCommands(){
        System.out.println("\n**** COMMANDS ****");
        System.out.println("1. SNT = SEQ_NOT_TRANSPOSED");
        System.out.println("2. SAT = SEQ_A_TRANSPOSED");
        System.out.println("3. SBT = SEQ_B_TRANSPOSED");
        System.out.println("4. PNT = PARA_NOT_TRANSPOSED");
        System.out.println("5. PAT = PARA_A_TRANSPOSED");
        System.out.println("6. PBT = PARA_B_TRANSPOSED");
    }



    /**
     * Get median runtime for all operation for all values of N
     */
    public static void timeMeasurementsForAllN() {
        int allN[] = {100, 200, 500, 1000};
        for(int i = 0; i < allN.length; i++) {
            timeAllOperations(seed, allN[i]);
            System.gc();
        }
    }



    /**
     * Get median runtime for all operation
     * @param seed - seeds used for generating matrix using Oblig2Precode
     * @param n - number used for determining size of matrix using Oblig2Precode
     */
    public static void timeAllOperations(int seed, int n){
        timeMode(seed, n, Oblig2Precode.Mode.SEQ_NOT_TRANSPOSED);
        timeMode(seed, n, Oblig2Precode.Mode.SEQ_A_TRANSPOSED);
        timeMode(seed, n, Oblig2Precode.Mode.SEQ_B_TRANSPOSED);
        timeMode(seed, n, Oblig2Precode.Mode.PARA_NOT_TRANSPOSED);
        timeMode(seed, n, Oblig2Precode.Mode.PARA_A_TRANSPOSED);
        timeMode(seed, n, Oblig2Precode.Mode.PARA_B_TRANSPOSED);
    }



    /**
     * Get median runtime for chosen operation
     * @param seed - seeds used for generating matrix using Oblig2Precode
     * @param n - number used for determining size of matrix using Oblig2Precode
     * @param runOperation - which version of Matrix Multiplication to use
     */
    public static void timeMode(int seed, int n, Oblig2Precode.Mode runOperation){
        System.out.println("Starting: " + runOperation + " - " + n + " X " + n);
        time(seed, n, runOperation);
        System.out.println();
    }



    /**
     * Get median runtime for chosen operation
     * @param seed - seeds used for generating matrix using Oblig2Precode
     * @param n - number used for determining size of matrix using Oblig2Precode
     * @param runOperation - which version of Matrix Multiplication to use
     */
    public static void time(int seed, int n, Oblig2Precode.Mode runOperation){
        // Get the matrices
        double[][] a = Oblig2Precode.generateMatrixA(seed, n);
        double[][] b = Oblig2Precode.generateMatrixB(seed, n);

        double[] runtimes = new double[runs];
        for (int i = 0; i < runs; i++) {
            long start = System.nanoTime();
            double[][] c = getResultMatrix(a, b, runOperation);
            long end = System.nanoTime();
            double runtime = (end - start) / 1000000.0;
            runtimes[i] = runtime;
            //Oblig2Precode.saveResult(seed, runOperation, c); //Saves the result
        }

        //Sorting runtimes and printing out median runtime
        Arrays.sort(runtimes);
        double medianTime = runtimes[runs / 2];
        System.out.println("Time: " + medianTime);
    }



    /**
     * Executes multiplication and gets result matrix
     * @param a - First matrix used for multiplication
     * @param b - Second matrix used for multiplication
     * @param operation - Name of operation for doing the Matrix multiplication
     */
    public static double[][] getResultMatrix(double[][] a, double[][] b, Oblig2Precode.Mode operation) {
        double[][] c = new double[a.length][a.length];

        if(operation == operation.SEQ_NOT_TRANSPOSED) {
            SeqMatrixMulti.no_transpose(a, b, c, 0, a.length);
        }
        else if(operation == operation.SEQ_A_TRANSPOSED) {
            SeqMatrixMulti.a_transpose(a, b, c, 0, a.length);
        }
        else if(operation == operation.SEQ_B_TRANSPOSED) {
            SeqMatrixMulti.b_transpose(a, b, c, 0, a.length);
        }
        else {
            ParaMatrixMulti PMM = new ParaMatrixMulti(a, b, c, operation);
            PMM.multiplyMatrix();
        }
        return c;
    }



    /**
     * Checks results for all operations comparing results for sequential and parallel versions
     * @param seed - seeds used for generating matrix using Oblig2Precode
     * @param n - number used for determining size of matrix using Oblig2Precode
     */
    public static void checkAllResults(int seed, int n) {
        double[][] a = Oblig2Precode.generateMatrixA(seed, n);
        double[][] b = Oblig2Precode.generateMatrixB(seed, n);

        double[][] SNT = getResultMatrix(a, b, Oblig2Precode.Mode.SEQ_NOT_TRANSPOSED);
        double[][] PNT = getResultMatrix(a, b, Oblig2Precode.Mode.PARA_NOT_TRANSPOSED);
        double[][] SAT = getResultMatrix(a, b, Oblig2Precode.Mode.SEQ_A_TRANSPOSED);
        double[][] PAT = getResultMatrix(a, b, Oblig2Precode.Mode.PARA_A_TRANSPOSED);
        double[][] SBT = getResultMatrix(a, b, Oblig2Precode.Mode.SEQ_B_TRANSPOSED);
        double[][] PBT = getResultMatrix(a, b, Oblig2Precode.Mode.PARA_B_TRANSPOSED);

        System.out.println("Test results:");
        checkResult(SNT, PNT, "NOT_TRANSPOSED");
        checkResult(SAT, PAT, "A_TRANSPOSED");
        checkResult(SBT, PBT, "B_TRANSPOSED");
    }



    /**
     * Checks that sequential and paralell results are simular
     * @param x - First matrix used for comparison
     * @param y - Second matrix used for comparison
     * @param name - Name of operation for doing the Matrix multiplication
     */
    public static boolean checkResult(double[][] x, double[][]y, String name) {
        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < x.length; j++) {
                if (x[i][j] != y[i][j]){
                  System.out.println(" - Status compare test for " + name + ": [" + RED +  "failed" + RESET + "]");
                  return false;
                }
            }
        }
        System.out.println(" - Status compare test for " + name + ": [" + GREEN +  "passed" + RESET + "]");
        return true;
    }

}
