import java.io.*;
import java.util.Random;

public class LevelGenerator {
	public static char[][] gridGenerator(char[][] map) {
		int x;
		int y;
		Random rand = new Random();
		int mines = 0;

		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++)
				map[i][j] = 'x';

		while (mines < 10) {
			x = rand.nextInt(9) + 0;
			y = rand.nextInt(9) + 0;
			if (map[x][y] != 'o') {
				map[x][y] = 'o';
				mines++;
			}
		}
		return map;
	}

	public static void printGrid(char[][] map) {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				System.out.print(map[i][j]);
			}
			System.out.println();
		}
	}

	public static void write(String fileName, char[][] map) {
		try {
			PrintWriter writer = new PrintWriter(fileName);
			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[0].length; j++) {
					writer.print(map[i][j]);
				}
				writer.println();
			}
			writer.close();
		} catch (FileNotFoundException fnf) {
			System.out.println("File was not found");
		}
	}

	public static void main(String[] args) {
		char[][] map = new char[9][9];
		gridGenerator(map);
		printGrid(map);
		write("Mines.txt", map);
	}
}
