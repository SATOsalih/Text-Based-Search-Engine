

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;
import java.util.Scanner;

public class Test {

	public static long getMin(long arr[], int n) {
		long result = arr[0];
		for (int i = 1; i < n; i++)
			result = Math.min(result, arr[i]);
		return result;
	}

	public static long getMax(long arr[], int n) {
		long result = arr[0];
		for (int i = 1; i < n; i++)
			result = Math.max(result, arr[i]);
		return result;
	}

	public static long getAvg(long arr[], int n) {

		long result = 0;
		for (int i = 0; i < n; i++) {
			result = result + arr[i];
		}
		result = result / n;
		return result;
	}

	public static int[][] dictToArray(DictionaryInterface<Integer, Integer> dict) {
		int[][] keyValue = new int[100][2];

		Iterator<Integer> keyIterator = dict.getKeyIterator();
		Iterator<Integer> valueIterator = dict.getValueIterator();
		int i = 0;
		while (keyIterator.hasNext()) {
			keyValue[i][0] = keyIterator.next();
			keyValue[i][1] = valueIterator.next();
			i++;
		}
		return keyValue;
	}

	public static void main(String[] args) throws IOException {

		HashedDictionary<String, Dictionary<Integer, Integer>> dataBase = new HashedDictionary<String, Dictionary<Integer, Integer>>();

		String[] words = null;

		boolean flag = false;
		while (!flag) {
			Scanner sc = new Scanner(System.in);
			System.out.print("Please enter just 3 words: ");
			String threeWords = sc.nextLine();
			threeWords = threeWords.toLowerCase(Locale.ENGLISH);
			words = threeWords.split(" +");

			if (words.length != 3) {
				flag = false;
			} else {
				flag = true;
			}

		}

		String DELIMITERS = "[-+=" + " " + "\r\n " + "1234567890" + "’'\"" + "(){}<>\\[\\]" + ":" + "," + "‒–—―" + "…"
				+ "!" + "." + "«»" + "-‐" + "?" + "‘’“”" + ";" + "/" + "⁄" + "␠" + "·" + "&" + "@" + "*" + "\\" + "•"
				+ "^" + "¤¢$€£¥₩₪" + "†‡" + "°" + "¡" + "¿" + "¬" + "#" + "№" + "%‰‱" + "¶" + "′" + "§" + "~" + "¨"
				+ "_" + "|¦" + "⁂" + "☞" + "∴" + "‽" + "※" + " +" + "\\s+" + "]";

		// long indexing_time1=System.nanoTime();
		int i = 1;

		File dir = new File("sport");
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isFile()) {
				BufferedReader inputStream = null;
				String line;
				try {
					inputStream = new BufferedReader(new FileReader(file));
					while ((line = inputStream.readLine()) != null && i <= 100) {
						line = line.toLowerCase(Locale.ENGLISH);
						String[] splitted = line.split(DELIMITERS);
						for (int j = 0; j < splitted.length; j++) {
							if (splitted[j] != " ") {
								if (!dataBase.contains(splitted[j])) {
									Dictionary<Integer, Integer> dict = new Dictionary<Integer, Integer>();
									dict.add(i, 1);
									dataBase.add(splitted[j], dict);
								} else if (dataBase.contains(splitted[j])) {
									Dictionary<Integer, Integer> dict = dataBase.getValue(splitted[j]);
									if (dict.getValue(i) != null) {
										int newValue = dict.getValue(i) + 1;
										dict.add(i, newValue);
										dataBase.add(splitted[j], dict);
									} else {
										dict.add(i, 1);
										dataBase.add(splitted[j], dict);
									}

								}
							}
						}

					}
				} catch (IOException e) {
					System.out.println(e);
				} finally {
					if (inputStream != null) {
						inputStream.close();
					}
				}
			}
			i++;
		}

		// long indexing_time2=System.nanoTime();
		// System.out.println(indexing_time2-indexing_time1);

		File searchFile = new File("search.txt");
		Scanner scanner1 = new Scanner(searchFile);

		long[] search = new long[1000];
		int a = 0;
		while (scanner1.hasNextLine()) {
			String searchWord = scanner1.nextLine();
			searchWord = searchWord.toLowerCase(Locale.ENGLISH);
			long indexing_time3 = System.nanoTime();
			dataBase.getValue(searchWord);
			long indexing_time4 = System.nanoTime();
			search[a] = indexing_time4 - indexing_time3;
			a++;
		}
		// System.out.println();
		// System.out.println(getAvg(search,1000)+" "+getMin(search, 1000)+" "+getMax(search, 1000));

		// System.out.println(dataBase.collisionCount);

		// System.out.println(collisionCount);

		Dictionary<Integer, Integer> dict1 = dataBase.getValue(words[0]);
		Dictionary<Integer, Integer> dict2 = dataBase.getValue(words[1]);
		Dictionary<Integer, Integer> dict3 = dataBase.getValue(words[2]);
		int[][] dict1Array = null;
		int[][] dict2Array = null;
		int[][] dict3Array = null;
		boolean nullCheck = false;
		if (dict1 != null && dict2 != null && dict3 != null) {
			dict1Array = dictToArray(dict1);
			dict2Array = dictToArray(dict2);
			dict3Array = dictToArray(dict3);
			nullCheck = true;
		}

		int value = 0;
		int key = 0;

		boolean check1 = false;
		boolean check2 = false;

		if (nullCheck == true) {

			for (int j = 0; j < 100; j++) {
				for (int k = 0; k < 100; k++) {
					for (int z = 0; z < 100; z++) {
						if (dict1Array[j][1] != 0 && dict1Array[j][0] == dict2Array[k][0]
								&& dict1Array[j][0] == dict3Array[z][0]) {

							if (dict1Array[j][1] < dict2Array[k][1] && dict1Array[j][1] < dict3Array[z][1]) {
								value = dict1Array[j][1];
								key = dict1Array[j][0];
							} else if (dict2Array[k][1] < dict1Array[j][1] && dict2Array[k][1] < dict3Array[z][1]) {
								value = dict2Array[k][1];
								key = dict2Array[k][0];
							} else if (dict3Array[z][1] < dict1Array[j][1] && dict3Array[z][1] < dict2Array[k][1]) {
								value = dict3Array[z][1];
								key = dict3Array[z][0];
							}

							check1 = true;
						}
					}
				}
			}

			if (check1 == false) {

				for (int j = 0; j < 100; j++) {
					for (int k = 0; k < 100; k++) {
						if (dict1Array[j][1] != 0 && dict1Array[j][0] == dict2Array[k][0]) {

							if (dict1Array[j][1] < dict2Array[k][1]) {
								value = dict1Array[j][1];
								key = dict1Array[j][0];
							} else {
								value = dict2Array[k][1];
								key = dict2Array[k][0];
							}
							check2 = true;
						}
					}
				}

				for (int j = 0; j < 100; j++) {
					for (int k = 0; k < 100; k++) {
						if (dict1Array[j][1] != 0 && dict1Array[j][0] == dict3Array[k][0]) {

							if (dict1Array[j][1] < dict3Array[k][1]) {
								if (value < dict1Array[j][1]) {
									value = dict1Array[j][1];
									key = dict1Array[j][0];
								}
							} else {
								if (value < dict3Array[k][1]) {
									value = dict3Array[k][1];
									key = dict3Array[k][0];
								}
							}
							check2 = true;
						}
					}
				}

				for (int j = 0; j < 100; j++) {
					for (int k = 0; k < 100; k++) {
						if (dict2Array[j][1] != 0 && dict2Array[j][0] == dict3Array[k][0]) {

							if (dict2Array[j][1] < dict3Array[k][1]) {
								if (value < dict2Array[j][1]) {
									value = dict2Array[j][1];
									key = dict2Array[j][0];
								}
							} else {
								if (value < dict3Array[k][1]) {
									value = dict3Array[k][1];
									key = dict3Array[k][0];
								}
							}
							check2 = true;
						}
					}
				}

			}

			if (check1 == false && check2 == false) {

				for (int j = 0; j < 100; j++) {
					if (dict1Array[j][1] != 0 && dict1Array[j][1] > value) {
						value = dict1Array[j][1];
						key = dict1Array[j][0];
					}
					if (dict2Array[j][1] != 0 && dict2Array[j][1] > value) {
						value = dict2Array[j][1];
						key = dict2Array[j][0];
					}
					if (dict3Array[j][1] != 0 && dict3Array[j][1] > value) {
						value = dict3Array[j][1];
						key = dict3Array[j][0];
					}

				}

			}

		}

		if (nullCheck == false) {
			System.out.println("You entered at least one word that is not in the files, please re-enter the words.");
		} else {
			System.out.println(key + ".txt");
		}
	}

}
