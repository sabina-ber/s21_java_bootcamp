import java.util.Scanner;

public class Program {

    public static void printErrorAndExit(String msg, Scanner scanner) {
        System.err.println(msg);
        scanner.close();
        System.exit(-1);
    }

    private static void getStudents(Scanner scanner, String[] students) {
        int i = 0;
        System.out.print("Enter student name: ");
        while (i < LIMIT) {
            String name = scanner.nextLine();
            if (name.equals(".")) {
                break;
            }
            if (name.length() > MAX_NAME_LENGHT) {
                printErrorAndExit("Name is too long", scanner);
            }
            students[i] = name;
            i++;
        }
        return;
    }

    private static void getSchedule(Scanner scanner, int[][] schedule) {
        int i = 0;
        System.out.print("Set schedule: ");
        while (i < LIMIT) {
            String studies = scanner.nextLine();
            if (studies.equals(".")) {
                break;
            }
            String[] parts = studies.split(" ");
            if (parts.length != 2) {
                printErrorAndExit("Wrong format", scanner);
            }
            int time = Integer.parseInt(parts[0]);
            if (time < 0 || time > 5) {
                printErrorAndExit("Wrong format", scanner);
            }
            int day = getDayIndex(parts[1]);
            if (day < 0 || day > 6) {
                printErrorAndExit("Wrong format", scanner);
            }
            schedule[time][day] = 1;
        }
    }

    private static int getDayIndex(String dayOfWeek) {
        switch (dayOfWeek) {
            case "MO":
                return 0;
            case "TU":
                return 1;
            case "WE":
                return 2;
            case "TH":
                return 3;
            case "FR":
                return 4;
            case "ST":
                return 5;
            case "SU":
                return 6;
            default:
                return -1;
        }
    }

    private static void setMonthSchedule(boolean[][][] MonthSchedule, int[][] schedule) {
        int dayOfWeek = getDayIndex("TU");
        for (int i = 0; i < daysSeptember; i++) {
            for (int k = 0; k < 5; k++) {
                if (schedule[k][dayOfWeek] == 1) {
                    MonthSchedule[i][dayOfWeek][k] = true;
                } else {
                    for (int j = 0; j < 7; j++) {
                        MonthSchedule[i][j][k] = false;
                    }
                }
            }
            dayOfWeek = (dayOfWeek + 1) % 7;
        }
    }


    private static boolean checkNameExists(String name, String[] students) {
        for (String student : students) {
            if (student != null && student.equals(name)) {
                return true;
            }
        }
        return false;
    }

    public static int getStudentIndex(String[] students, String student) {
        for (int i = 0; i < students.length; i++) {
            if (student.equals(students[i])) {
                return i;
            }
        }
        return -1;
    }

    private static void setAttendance(Scanner scanner, String[] students, boolean[][][] MonthSchedule, int[][][][] attendance) {
        int studentIndex = 0;
        int hasAttended = 0;
        System.out.print("Set attendance: ");
        while (true) {
            String input = scanner.nextLine();
            if (input.equals(".")) {
                break;
            }
            String[] tokens = input.split(" ");
            if (tokens.length != 4) {
                printErrorAndExit("Wrong format", scanner);
            }
            String name = tokens[0];
            studentIndex = getStudentIndex(students, name);
            if (studentIndex == -1) {
                printErrorAndExit("Name does not exist", scanner);
            }
            int time = Integer.parseInt(tokens[1]);
            if (time < 0 || time > 5) {
                printErrorAndExit("Wrong format", scanner);
            }

            int day = Integer.parseInt(tokens[2]);
            if (day < 0 || day > 30) {
                printErrorAndExit("Wrong format", scanner);
            }

            String isHere = tokens[3];
            if (isHere.equals("HERE")) {
                attendance[studentIndex][time][day - 1][hasAttended] = 1;
            } else if (isHere.equals("NOT_HERE")) {
                attendance[studentIndex][time][day - 1][hasAttended] = -1;
            } else {
                printErrorAndExit("Wrong format", scanner);
            }
        }
    }

    private static void printMonthSchedule(boolean[][][] MonthSchedule) {
        String[] weekDays = {"MO", "TU", "WE", "TH", "FR", "ST", "SU"};
        String[] times = {"1:00", "2:00", "3:00", "4:00", "5:00"};
        System.out.format("%10s", "");
        for (int i = 0; i < MonthSchedule.length; i++) {
            for (int j = 0; j < 7; j++) {
                for (int k = 0; k < 5; k++) {
                    if (MonthSchedule[i][j][k]) {
                        if (i < 10) {
                            System.out.format("%-5s %2s %02d|", times[k - 1], weekDays[j], (i + 1));
                        } else {
                            System.out.format("%-5s %2s %02d|", times[k - 1], weekDays[j], (i + 1));

                        }
                    }
                }
            }
        }
        System.out.println();
    }

    private static void printAttendance1(String[] students, int[][][][] attendance, boolean[][][] MonthSchedule) {

        for (int studentIndex = 0; studentIndex < students.length; studentIndex++) {
            if (students[studentIndex] != null) {
                System.out.format("%-10s", students[studentIndex]);
                for (int i = 0; i < MonthSchedule.length; i++) {
                    for (int j = 0; j < 7; j++) {
                        for (int k = 0; k < 5; k++) {
                            if (MonthSchedule[i][j][k]) {
                                if (attendance[studentIndex][k][i][0] == 1) {
                                    System.out.format("%12s", "1|");
                                } else if (attendance[studentIndex][k][i][0] == -1) {
                                    System.out.format("%12s", "-1|");
                                } else {
                                    System.out.format("%12s", "|");
                                }
                            }

                        }
                    }
                }
            }
            System.out.println();
        }
    }

    private static final int MAX_NAME_LENGHT = 10;
    private static final int LIMIT = 10;
    private static final int firstDay = 1;
    private static final int daysSeptember = 30;

    public static void main(String[] argv) {
        Scanner scanner = new Scanner(System.in);
        String[] students = new String[LIMIT];
        int[][] schedule = new int[5][7];
        boolean[][][] MonthSchedule = new boolean[30][7][5];
        int[][][][] attendance = new int[students.length][5][30][1];
        getStudents(scanner, students);
        getSchedule(scanner, schedule);
        setMonthSchedule(MonthSchedule, schedule);
        setAttendance(scanner, students, MonthSchedule, attendance);
        printMonthSchedule(MonthSchedule);
        printAttendance1(students, attendance, MonthSchedule);
    }

}
