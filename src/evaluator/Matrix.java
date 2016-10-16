/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evaluator;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Hatem
 */
public class Matrix implements Serializable {

    //ArrayList<ArrayList<Double>> data;
    double[][] matrix;
    int rowsNumber;
    int columnsNumber;
    String[][] stringMatrix;

    /*public Matrix(ArrayList<ArrayList<Double>> data, int rowsNumber, int columnsNumber) {
     this.data = (ArrayList<ArrayList<Double>>) data.clone();
     this.rowsNumber = rowsNumber;
     this.columnsNumber = columnsNumber;
     }*/
    public Matrix(double[][] matrix, int rowsNumber, int columnsNumber) {
        this.matrix = matrix;
        this.rowsNumber = rowsNumber;
        this.columnsNumber = columnsNumber;
        this.stringMatrix = new String[this.rowsNumber][this.columnsNumber];
    }

    public Matrix(double x) {
        this.matrix = new double[1][1];
        this.matrix[0][0] = x;
        this.rowsNumber = 1;
        this.columnsNumber = 1;
        this.stringMatrix = new String[1][1];
        this.stringMatrix[0][0] = String.valueOf(x);
    }

    public Matrix(Matrix matrix) {
        this.rowsNumber = matrix.rowsNumber;
        this.columnsNumber = matrix.columnsNumber;
        this.matrix = new double[rowsNumber][columnsNumber];
        this.stringMatrix = new String[this.rowsNumber][this.columnsNumber];
        for (int i = 0; i < rowsNumber; i++) {
            for (int j = 0; j < columnsNumber; j++) {
                this.matrix[i][j] = matrix.matrix[i][j];
                this.stringMatrix[i][j] = matrix.stringMatrix[i][j];
            }
        }

    }

    public Matrix(int rowsNumber, int columnsNumber) {

        this.rowsNumber = rowsNumber;
        this.columnsNumber = columnsNumber;
        this.matrix = new double[rowsNumber][columnsNumber];
        this.stringMatrix = new String[this.rowsNumber][this.columnsNumber];

    }

    public Matrix(int rowsNumber, int columnsNumber, double init) {
        this.rowsNumber = rowsNumber;
        this.columnsNumber = columnsNumber;
        this.matrix = new double[rowsNumber][columnsNumber];
        this.stringMatrix = new String[this.rowsNumber][this.columnsNumber];
        for (int i = 0; i < rowsNumber; i++) {
            for (int j = 0; j < rowsNumber; j++) {
                this.matrix[i][j] = init;
                this.stringMatrix[i][j] = String.valueOf(init);
            }
        }
    }

    /*public void set(int i, int j, double val) {
     this.data.get(i).set(j, val);
     }*/

    /*public double get(int i, int j) {
     return this.data.get(i).get(j);
     }*/

    /*ArrayList<ArrayList<Double>> invert2() {
     ArrayList<ArrayList<Double>> ret = new ArrayList<ArrayList<Double>>();
     double x[][] = new double[this.rowsNumber][this.rowsNumber];
     double y[][] = new double[this.rowsNumber][this.rowsNumber];
     for (int i = 0; i < this.rowsNumber; i++) {
     for (int j = 0; j < this.rowsNumber; j++) {
     x[i][j] = this.get(i, j);
     }
     }
     y = invertGauss(x);
     for (int i = 0; i < this.rowsNumber; i++) {
     for (int j = 0; j < this.rowsNumber; j++) {
     this.data.get(i).set(j, y[i][j]);
     }
     }
     return data;
     }*/
    public Matrix identity() {
        Matrix ret = new Matrix(new double[this.rowsNumber][this.columnsNumber], this.rowsNumber, this.columnsNumber);
        int n = 0;
        if (rowsNumber <= columnsNumber) {
            n = rowsNumber;
        } else {
            n = columnsNumber;
        }
        for (int i = 0; i < n; i++) {
            ret.set(i, i, 1.);
        }
        return ret;
    }

    public Matrix addToMatrix(Matrix arg) throws EvaluatorException {
        Matrix ret = new Matrix(new double[this.rowsNumber][this.columnsNumber], this.rowsNumber, this.columnsNumber);
        if (this.columnsNumber == arg.columnsNumber && this.rowsNumber == arg.rowsNumber) {
            for (int i = 0; i < this.rowsNumber; i++) {
                for (int j = 0; j < this.columnsNumber; j++) {
                    ret.matrix[i][j] = this.matrix[i][j] + arg.matrix[i][j];
                    ret.stringMatrix[i][j] = String.valueOf(ret.matrix[i][j]);
                    //System.out.println(i+","+j+":"+getElem(i, j));
                }
            }
        } else {
            throw new NotAllowedOperationException("Matrix have not same dimensions");
        }
        return ret;
    }

    public Matrix negateMatrix() throws EvaluatorException {
        Matrix ret = new Matrix(new double[this.rowsNumber][this.columnsNumber], this.rowsNumber, this.columnsNumber);
        for (int i = 0; i < this.rowsNumber; i++) {
            for (int j = 0; j < this.columnsNumber; j++) {
                ret.matrix[i][j] = -this.matrix[i][j];
                ret.stringMatrix[i][j] = String.valueOf(ret.matrix[i][j]);
                //System.out.println(i+","+j+":"+getElem(i, j));
            }
        }

        return ret;
    }

    public Matrix addToFactor(double factor) {
        Matrix ret = new Matrix(new double[this.rowsNumber][this.columnsNumber], this.rowsNumber, this.columnsNumber);

        for (int i = 0; i < this.rowsNumber; i++) {
            for (int j = 0; j < this.columnsNumber; j++) {
                ret.matrix[i][j] = this.matrix[i][j] + factor;
                ret.stringMatrix[i][j] = String.valueOf(ret.matrix[i][j]);
                //System.out.println(i+","+j+":"+getElem(i, j));
            }
        }

        return ret;
    }

    public Matrix subByMatrix(Matrix arg) throws EvaluatorException {
        Matrix ret = new Matrix(new double[this.rowsNumber][this.columnsNumber], this.rowsNumber, this.columnsNumber);
        if (this.columnsNumber == arg.columnsNumber && this.rowsNumber == arg.rowsNumber) {
            for (int i = 0; i < this.rowsNumber; i++) {
                for (int j = 0; j < this.columnsNumber; j++) {
                    ret.matrix[i][j] = this.matrix[i][j] - arg.matrix[i][j];
                    ret.stringMatrix[i][j] = String.valueOf(ret.matrix[i][j]);
                    //System.out.println(i+","+j+":"+getElem(i, j));
                }
            }
        } else {
            throw new NotAllowedOperationException("Matrix have not same dimensions");
        }
        return ret;
    }

    public Matrix subByFactor(double factor) { //matrix - factor
        Matrix ret = new Matrix(new double[this.rowsNumber][this.columnsNumber], this.rowsNumber, this.columnsNumber);

        for (int i = 0; i < this.rowsNumber; i++) {
            for (int j = 0; j < this.columnsNumber; j++) {
                ret.matrix[i][j] = this.matrix[i][j] - factor;
                ret.stringMatrix[i][j] = String.valueOf(ret.matrix[i][j]);
                //System.out.println(i+","+j+":"+getElem(i, j));
            }
        }

        return ret;
    }

    public Matrix subFromFactor(double factor) { //factor - matrix
        Matrix ret = new Matrix(new double[this.rowsNumber][this.columnsNumber], this.rowsNumber, this.columnsNumber);

        for (int i = 0; i < this.rowsNumber; i++) {
            for (int j = 0; j < this.columnsNumber; j++) {
                ret.matrix[i][j] = factor - this.matrix[i][j];
                ret.stringMatrix[i][j] = String.valueOf(ret.matrix[i][j]);
                //System.out.println(i+","+j+":"+getElem(i, j));
            }
        }

        return ret;
    }

    public Matrix multByMatrix(Matrix arg) throws EvaluatorException {
        Matrix ret = new Matrix(new double[this.rowsNumber][arg.columnsNumber], this.rowsNumber, arg.columnsNumber);

        if (this.columnsNumber == arg.rowsNumber) {
            for (int i = 0; i < this.rowsNumber; i++) {
                for (int j = 0; j < arg.columnsNumber; j++) {
                    ret.matrix[i][j] = 0.;
                    for (int k = 0; k < this.columnsNumber; k++) {
                        ret.matrix[i][j] = ret.matrix[i][j] + this.matrix[i][k] * arg.matrix[k][j];
                        ret.stringMatrix[i][j] = String.valueOf(ret.matrix[i][j]);
                    }
                    //System.out.println("ret:"+ret);
                    //System.out.println(i+","+j+":"+getElem(i, j));

                }
            }
        } else {
            throw new NotAllowedOperationException("Matrix don't respect dimensions");
        }

        return ret;
    }

    public Matrix multByFactor(double factor) {
        Matrix ret = new Matrix(new double[this.rowsNumber][this.columnsNumber], this.rowsNumber, this.columnsNumber);
        for (int i = 0; i < this.rowsNumber; i++) {
            for (int j = 0; j < this.columnsNumber; j++) {
                ret.matrix[i][j] = factor * this.matrix[i][j];
                ret.stringMatrix[i][j] = String.valueOf(ret.matrix[i][j]);
                //System.out.println(i+","+j+":"+getElem(i, j));
            }
        }

        return ret;
    }

    public Matrix invert() throws EvaluatorException {
        Matrix ret = new Matrix(new double[this.rowsNumber][this.columnsNumber], this.rowsNumber, this.columnsNumber);
        if (this.rowsNumber == this.columnsNumber) {
            ret.matrix = invertGauss(this.matrix);
            for (int i = 0; i < this.rowsNumber; i++) {
                for (int j = 0; j < this.columnsNumber; j++) {
                    ret.stringMatrix[i][j] = String.valueOf(ret.matrix[i][j]);
                    if (Double.isInfinite(ret.matrix[i][j]) || Double.isNaN(ret.matrix[i][j])) {
                        throw new NotAllowedOperationException("Matrix is not inversible !");
                    }
                }
            }
        } else {
            throw new NotAllowedOperationException("Matrix is not square");
        }

        return ret;
    }

    public Matrix divideByMatrix(Matrix arg) throws EvaluatorException {
        Matrix ret = new Matrix(new double[this.rowsNumber][this.columnsNumber], this.rowsNumber, this.columnsNumber);
        Matrix inverted = arg.invert();
        if (this.rowsNumber == arg.rowsNumber && this.columnsNumber == arg.columnsNumber) {
            ret = this.multByMatrix(inverted);

        } else {
            //System.out.println("Operation not allowed ! Matrix have not same dimensions !");
            throw new NotAllowedOperationException("Matrix have not same dimensions");
        }

        return ret;
    }

    public Matrix divideByFactor(double factor) {
        Matrix ret = new Matrix(new double[this.rowsNumber][this.columnsNumber], this.rowsNumber, this.columnsNumber);
        for (int i = 0; i < this.rowsNumber; i++) {
            for (int j = 0; j < this.columnsNumber; j++) {
                ret.matrix[i][j] = this.matrix[i][j] / factor;
                ret.stringMatrix[i][j] = String.valueOf(ret.matrix[i][j]);
                //System.out.println(i+","+j+":"+getElem(i, j));
            }
        }

        return ret;
    }

    public Matrix powByFactor(double factor) throws EvaluatorException {
        long factlong = 0;
        try {
            factlong = (long) factor;
        } catch (NumberFormatException e) {
            throw new NotAllowedOperationException("Exponent must be an integer !");
        }

        Matrix ret = this.identity();
        if (this.rowsNumber == this.columnsNumber) {
            if (factlong > 0) {
                while (factlong > 0) {
                    ret = ret.multByMatrix(this);
                    factlong--;
                }
            } else if (factlong < 0) {
                Matrix inv = this.invert();
                while (factlong < 0) {
                    ret = ret.multByMatrix(inv);
                    factlong++;
                }
            }
        } else {
            throw new NotAllowedOperationException("Matrix is not square");
        }

        return ret;
    }

    public Matrix divideFromFactor(double factor) throws EvaluatorException {
        Matrix ret = new Matrix(new double[this.rowsNumber][this.columnsNumber], this.rowsNumber, this.columnsNumber);
        Matrix inverted = this.invert();
        for (int i = 0; i < this.rowsNumber; i++) {
            for (int j = 0; j < this.columnsNumber; j++) {
                ret.matrix[i][j] = factor * inverted.matrix[i][j];
                ret.stringMatrix[i][j] = String.valueOf(ret.matrix[i][j]);
                //System.out.println(i+","+j+":"+getElem(i, j));
            }
        }

        return ret;
    }

    private double[][] invertGauss(double a[][]) {
        int n = a.length;
        double x[][] = new double[n][n];
        double b[][] = new double[n][n];
        int index[] = new int[n];
        for (int i = 0; i < n; ++i) {
            b[i][i] = 1;
        }

        // Transform the matrix into an upper triangle
        gaussian(a, index);

        // Update the matrix b[i][j] with the ratios stored
        for (int i = 0; i < n - 1; ++i) {
            for (int j = i + 1; j < n; ++j) {
                for (int k = 0; k < n; ++k) {
                    b[index[j]][k]
                            -= a[index[j]][i] * b[index[i]][k];
                }
            }
        }

        // Perform backward substitutions
        for (int i = 0; i < n; ++i) {
            x[n - 1][i] = b[index[n - 1]][i] / a[index[n - 1]][n - 1];
            for (int j = n - 2; j >= 0; --j) {
                x[j][i] = b[index[j]][i];
                for (int k = j + 1; k < n; ++k) {
                    x[j][i] -= a[index[j]][k] * x[k][i];
                }
                x[j][i] /= a[index[j]][j];
            }
        }
        return x;
    }

// Method to carry out the partial-pivoting Gaussian
// elimination.  Here index[] stores pivoting order.
    private void gaussian(double a[][], int index[]) {
        int n = index.length;
        double c[] = new double[n];

        // Initialize the index
        for (int i = 0; i < n; ++i) {
            index[i] = i;
        }

        // Find the rescaling factors, one from each row
        for (int i = 0; i < n; ++i) {
            double c1 = 0;
            for (int j = 0; j < n; ++j) {
                double c0 = Math.abs(a[i][j]);
                if (c0 > c1) {
                    c1 = c0;
                }
            }
            c[i] = c1;
        }

        // Search the pivoting element from each column
        int k = 0;
        for (int j = 0; j < n - 1; ++j) {
            double pi1 = 0;
            for (int i = j; i < n; ++i) {
                double pi0 = Math.abs(a[index[i]][j]);
                pi0 /= c[index[i]];
                if (pi0 > pi1) {
                    pi1 = pi0;
                    k = i;
                }
            }

            // Interchange rows according to the pivoting order
            int itmp = index[j];
            index[j] = index[k];
            index[k] = itmp;
            for (int i = j + 1; i < n; ++i) {
                double pj = a[index[i]][j] / a[index[j]][j];

                // Record pivoting ratios below the diagonal
                a[index[i]][j] = pj;

                // Modify other elements accordingly
                for (int l = j + 1; l < n; ++l) {
                    a[index[i]][l] -= pj * a[index[j]][l];
                }
            }
        }
    }

    public int getRowsNumber() {
        return rowsNumber;
    }

    public int getColumnsNumber() {
        return columnsNumber;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public boolean isSquare() {
        return (this.columnsNumber == this.rowsNumber);
    }

    public boolean isMatrix() {
        return ((this.columnsNumber > 1) || (this.rowsNumber > 1));
    }

    public double get(int i, int j) {
        return matrix[i][j];
    }
    
    public String getString(int i, int j) {
        return stringMatrix[i][j];
    }

    public void set(int i, int j, double val) {
        matrix[i][j] = val;
        stringMatrix[i][j] = String.valueOf(val);
    }

    public void set(int i, int j, double val, String val2) {
        matrix[i][j] = val;
        stringMatrix[i][j] = val2;
    }

    public void setFirstValue(double value) {
        matrix[0][0] = value;
        stringMatrix[0][0] = String.valueOf(value);
    }

    public double getFirstValue() {
        return matrix[0][0];
    }

    @Override
    public String toString() {
        ArrayList<ArrayList<Double>> arrayList = new ArrayList<>();
        for (int i = 0; i < rowsNumber; i++) {
            arrayList.add(new ArrayList<>());
            for (int j = 0; j < columnsNumber; j++) {
                arrayList.get(i).add(this.get(i, j));
            }

        }
        return arrayList.toString(); //To change body of generated methods, choose Tools | Templates.
    }

}
