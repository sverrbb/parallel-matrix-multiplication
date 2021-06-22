
/**
 * Sequential matrix multiplication A X B which contanins three different algorithms
 * Method no_transpose using a classic algorithm for multiplication
 * Method a_transpose where A is transposed
 * Method b_transpose where B Is transposed
 */
public class SeqMatrixMulti {


    // Classic algorithm - multiply each row in matrix a with each columnt in matrix b
    public static void no_transpose(double[][] a, double[][] b, double[][] c, int start, int end) {
        for (int i = start; i < end; i++) {
            for (int j = 0; j < a.length; j++) {
                for (int k = 0; k < a.length; k++) {
                    c[i][j] += a[i][k] * b[k][j];
                }
            }
        }
    }


    // A transposed - multiply each column in transposed matrix a with each columnt in matrix b
    public static void a_transpose(double[][] a, double[][] b, double[][] c, int start, int end) {
        double[][] trans_a = transpose(a);
        for (int i = start; i < end; i++) {
            for (int j = 0; j < a.length; j++) {
                for (int k = 0; k < a.length; k++) {
                    c[i][j] += trans_a[k][i] * b[k][j];
                }
            }
        }
    }


    // B transposed - multiply each row in matrix a with each row in transposed matrix b
    public static void b_transpose(double[][] a, double[][] b, double[][] c, int start, int end) {
        double[][] trans_b = transpose(b);
        for (int i = start; i < end; i++) {
            for (int j = 0; j < a.length; j++) {
                for (int k = 0; k < a.length; k++) {
                    c[i][j] += a[i][k] * trans_b[j][k];
                }
            }
        }
    }


    // Transpose matrix - used for transpose in a and b
    public static double[][] transpose(double[][] mtx) {
        double[][] transpMatrix = new double[mtx.length][mtx.length];
        for (int i = 0; i < mtx.length; i++) {
            for (int j = 0; j < mtx[0].length; j++) {
                transpMatrix[j][i] = mtx[i][j];
            }
        }
        return transpMatrix;
    }
}
