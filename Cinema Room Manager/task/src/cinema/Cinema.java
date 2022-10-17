package cinema;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Seat {
    int row;
    int col;

    Seat(int row, int col){
        this.row = row;
        this.col = col;
    }
}

public class Cinema {

    private static int row;
    private static int col;
    private static String[][] seats;

    private static int count;

    private static int currentIncome;


    private Cinema(int row, int col) {
        Cinema.row = row;
        Cinema.col = col;
        seats = createEmptyMatrix(row, col);
    }

    private static int getTotalIncome(int row, int col) {
        int totalSeats = row * col;
        if (totalSeats <= 60) {
            return totalSeats * 10;
        } else {
            int frontRow = row / 2;
            int backRow = row - frontRow;
            return frontRow * col * 10 + backRow * col * 8;
        }
    }

    private static String[][] createEmptyMatrix(int row, int col) {
        String[][] seats = new String[row][];
        for (int i = 0; i < row; i++){
            String[] rows = new String[col];
            Arrays.fill(rows, "S");
            seats[i] = rows;
        }
        return seats;
    }

    private static Cinema readCinema() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of rows: ");
        System.out.print("");
        int rows = sc.nextInt();
        System.out.println("Enter the number of seats in each row: ");
        System.out.print("");
        int cols = sc.nextInt();
        return new Cinema(rows, cols);
    }

    private String getTopRows() {
        String[] topRows = new String[col + 1];
        topRows[0] = " ";
        for (int i = 1; i < topRows.length; i++){
            topRows[i] = String.valueOf(i);
        }
        return String.join(" ", topRows);
    }

    private void print() {
        System.out.println();
        System.out.println("Cinema: ");
        String topRow = getTopRows();
        System.out.println(topRow);
        for (int i = 1; i <= seats.length; i++) {
            System.out.printf("%d ", i);
            String row = String.join(" ", seats[i-1]);
            System.out.println(row);
        }
    }

    private void takeSeat(Seat seat) {
        seats[seat.row - 1][seat.col-1] = "B";
    }

    private int totalSeats() {
        return col * row;
    }

    private boolean isFrontHalf(Seat seat) {
        return seat.row <= row /2;
    }

    private static Seat readSeat() {
        Scanner sc = new Scanner(System.in);
        System.out.println();
        System.out.println("Enter a row number: ");
        System.out.print("> ");
        int row = sc.nextInt();
        System.out.println("Enter a seat number in that row: ");
        System.out.print("> ");
        int col = sc.nextInt();
        if (row > Cinema.row || col > Cinema.col) {
            System.out.println("Wrong input!");
            return readSeat();
        }
        else if (seats[row-1][col-1].equals("B")) {
            System.out.println("That ticket has already been purchased!");
            return readSeat();
        }
        else {
            return new Seat(row, col);
        }
    }

    private void printPrice(Seat seat) {
        System.out.println();
        int price;

        if (totalSeats() <= 60 || isFrontHalf(seat)){
            price = 10;
        }
        else {
            price = 8;
        }
        currentIncome += price;
        System.out.printf("Ticket price: $%s", price);
        System.out.println();
    }

    private static void statistics() {
        float result = (float) count / (row * col) * 100;
        System.out.printf("Number of purchased tickets: %d%n", count);
        System.out.printf("Percentage: %.2f%%", result);
        System.out.println();
        System.out.println("Current income: $" + currentIncome);
        System.out.println("Total income: $" + getTotalIncome(row, col));
    }

    public static void main(String[] args) {
        // Write your code here
        Cinema cinema = readCinema();

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println();
            System.out.println("1. Show the seats");
            System.out.println("2. Buy a ticket");
            System.out.println("3. Statistics");
            System.out.println("0. Exit");
            System.out.println();
            int option = sc.nextInt();
            switch (option) {
                case 1 -> {
                    cinema.print();
                }
                case 2 -> {
                    Seat seat = readSeat();
                    cinema.takeSeat(seat);
                    cinema.printPrice(seat);
                    count++;
                }
                case 3 -> {
                    statistics();
                }
                case 0 -> {
                    return;
                }
                default -> {
                    throw new RuntimeException(String.format("unknown menu command %d", option));
                }
            }
        }


    }
}