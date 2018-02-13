import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MinesweeperGame {
	public static int readMap(String fileName, char[][] map) {
		int i = 0;
		int mines = 0;
		try {
			Scanner read = new Scanner(new File(fileName));
			do {
				String line = read.nextLine();
				for (int j = 0; j < 9; j++) {
					map[i][j] = line.charAt(j);
					if (line.charAt(j) == 'o')
						mines++;
				}
				i++;
			} while (read.hasNext());
			read.close();
		} catch (FileNotFoundException fnf) {
			System.out.println("File was not found");
		}
		return mines;
	}

	public static void printMap(char[][] map) {
		System.out.println("  123456789");
		int Letter = 65;
		for (int i = 0; i < map.length; i++) {
			System.out.print((char) Letter + " ");
			for (int j = 0; j < map[0].length; j++) {
				System.out.print(map[i][j]);
			}
			System.out.println();
			Letter++;
		}
	}

	public static void resetMap(char[][] map) {
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++)
				map[i][j] = 'x';
	}

	public static void menu(char[][]map, char[][]game, int mines) {
		Boolean boucle = true;
		int[] coord;
		while (boucle) {
			System.out.println(mines + " mines left.");
			printMap(game);
			System.out.println("Minesweeper:\n" + "1. Reveal\n" + "2. Drop Flag\n" + "3. Quit");
			int option = -1;
			while (option < 1 || option > 3) {
				System.out.println("Input must be [1-3]");
				option = getNumber();
			}
			switch (option) {
			case 1:
				System.out.println("Enter row letter and column number:");
				coord = getInput();
				if (map[coord[0]][coord[1]] == 'o')
					playAgain(0);
				else
					nearbyMines(map, game, coord[0], coord[1]);
				break;
			case 2:
				System.out.println("Enter row letter and column number:");
				coord = getInput();
				flag(game, coord);
				if (compareGrids(map, game) == true)
					playAgain(1);
				break;
			default:
				boucle = false;
			}
		}
	}

	public static Boolean playAgain(int Result) {
		Scanner in = new Scanner(System.in);
		String input = "";
		boolean isInvalid = true;
		if (Result == 0)
			System.out.println("You Lose!");
		else
			System.out.println("You won!");
		while (isInvalid) {
			System.out.println("Play again? (Y/N)");
			if (in.hasNext()) {
				input = in.nextLine();
				if (input.matches("[YN]{1}"))
					isInvalid = false;
				else {
					System.out.println("Invalid Input");
				}
			} else {
				in.nextLine(); // clear out invalid input
				System.out.println("Invalid Input");
			}
		}
		if (input.charAt(0) == 'Y')
			return true;
		return false;
	}

	public static int getNumber() {
		Scanner in = new Scanner(System.in);
		int input = 0;
		boolean isInvalid = true;
		while (isInvalid) {
			System.out.println("Enter a number: ");
			if (in.hasNextInt()) {
				input = in.nextInt();
				isInvalid = false;
			} else {
				in.nextLine(); // clear out invalid input
				System.out.println("Invalid Input");
			}
		}
		return input;
	}

	public static char getLetter() {
		Scanner in = new Scanner(System.in);
		String input = "";
		boolean isInvalid = true;
		while (isInvalid) {
			System.out.println("Enter a Letter: ");
			if (in.hasNext()) {
				input = in.nextLine();
				if (input.matches("[A-Za-z]{1}"))
					isInvalid = false;
			} else {
				in.nextLine(); // clear out invalid input
				System.out.println("Invalid Input");
			}
		}
		return input.charAt(0);
	}

	public static int[] getInput() {
		int[] coord = new int[2];
		int x = -1;
		int y = -1;

		while (x < 'A' - 65 || x > 'I' - 65) {
			System.out.println("Input must be [A-I]");
			x = getLetter() - 65;
		}

		while (y < 1 || y > 9) {
			System.out.println("Input must be [1-9]");
			y = getNumber();
		}
		coord[0] = x;
		coord[1] = y - 1;
		return coord;
	}

	public static void printArray(int array[]) {
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i]);
		}
	}

	public static Boolean checkMine(char[][] map, int[] coord) {
		if (map[coord[0]][coord[1]] == 'o')
			return true;
		return false;
	}

	public static char[][] nearbyMines(char[][] map, char[][] game, int x, int y) {
		int mines = 0;
		char[][] grid = new char[11][11];

		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 11; j++) {
				if (i == 0 || i == 10)
					grid[i][j] = '*';
				else if (j == 0 || j == 10)
					grid[i][j] = '*';
				else
					grid[i][j] = map[i - 1][j - 1];
			}
		}

		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = y - 1; j <= y + 1; j++) {
				if (grid[i + 1][j + 1] == 'o')
					mines++;
			}
		}
		if (mines > 0)
			game[x][y] = (char) (mines + 47);
		else {
			game[x][y] = ' ';
			// nearbyMines(map, game, x, y + 1);
			// nearbyMines(map, game, x, y - 1);
			// nearbyMines(map, game, x + 1, y + 1);
			// nearbyMines(map, game, x + 1, y - 1);
			// nearbyMines(map, game, x - 1, y + 1);
			// nearbyMines(map, game, x - 1, y - 1);
			// nearbyMines(map, game, x - 1, y);
			// nearbyMines(map, game, x + 1, y);
		}
		return game;
	}

	public static void flag(char[][] game, int[] coord) {
		if (game[coord[0]][coord[1]] == 'x')
			game[coord[0]][coord[1]] = 'F';
		else if (game[coord[0]][coord[1]] == 'F')
			game[coord[0]][coord[1]] = 'x';
	}

	public static Boolean compareGrids(char[][] map, char[][] game) {
		int mines = 10;
		int flags = 0;
		
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (map[i][j] == 'o' && game[i][j] == 'F')
					mines--;
				if (game[i][j] == 'F')
					flags++;
			}
		}
		if (mines == 0 && flags == 10)
			return true;
		return false;
	}

	public static void main(String[] args) {
		char[][] map = new char[9][9];
		char[][] game = new char[9][9];
		int mines = readMap("Mines.txt", map);

		resetMap(game);
		menu(map, game, mines);
	}
}
