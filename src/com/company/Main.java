package com.company;

import mpi.MPI;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        MPI.Init(args);
        int rank = MPI.COMM_WORLD.Rank();
        Matrix a = new Matrix(20, 15);
        Matrix b = new Matrix(15, 30);
        Matrix c = new Matrix(20, 30);
        if (rank == 0) {
            a.init();
            b.init();
        }
        MPI.COMM_WORLD.Barrier();
        MPI.COMM_WORLD.Bcast(a.getValues(), 0, a.getValues().length, MPI.DOUBLE, 0);
        MPI.COMM_WORLD.Bcast(b.getValues(), 0, b.getValues().length, MPI.DOUBLE, 0);
        //MPI.COMM_WORLD.Bcast(c.getValues(), 0, c.getValues().length, MPI.DOUBLE, 0);
        MatrixMult.parallelMatrixMultiply(a, b, c);
        MPI.COMM_WORLD.Barrier();
        if (rank == 0) {
            a.show();
            b.show();
            c.show();
        }
        MPI.Finalize();
    }
}
