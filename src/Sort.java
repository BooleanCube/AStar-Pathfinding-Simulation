import java.util.ArrayList;

/* Sort functions used in path finding to
 * determine lowest F cost Node. Bubble sort is mainly
 * used, quick sort needs work. Currently not working
 * by Devon Crawford
 */
public class Sort {

	//Default is: lowToHigh
	private static boolean lowToHigh = true;
	private static boolean highToLow = false;

	// TODO: USE A BETTER SORTER ALGORITHM AND UNDERSTAND WHAT LOW TO HIGH IS

	public static void bubbleSort(int[] data) {
		int swtch = -1;
		int temp;
		while (swtch != 0) {
			swtch = 0;
			if (lowToHigh) {
				for (int i = 0; i < data.length - 1; i++) {
					if (data[i] > data[i + 1]) {
						temp = data[i];
						data[i] = data[i + 1];
						data[i + 1] = temp;
						swtch = 1;
					}
				}
			} else if (highToLow) {
				for (int i = 0; i < data.length - 1; i++) {
					if (data[i] < data[i + 1]) {
						temp = data[i];
						data[i] = data[i + 1];
						data[i + 1] = temp;
						swtch = 1;
					}
				}
			}
		}
	}

	public static void bubbleSort(ArrayList<Node> list) {
		int swtch = -1;
		Node temp;
		while (swtch != 0) {
			swtch = 0;
			if (lowToHigh) {
				for (int i = 0; i < list.size() - 1; i++) {
					if (list.get(i).f > list.get(i + 1).f) {
						temp = list.get(i);
						list.remove(i);
						list.add(i + 1, temp);
						swtch = 1;
					}
				}
			} else if (highToLow) {
				for (int i = 0; i < list.size() - 1; i++) {
					if (list.get(i).f < list.get(i + 1).f) {
						temp = list.get(i);
						list.remove(i);
						list.add(i + 1, temp);
						swtch = 1;
					}
				}
			}
		}
	}

	// low is 0, high is numbers.length - 1
	// TODO: FIX HIGH TO LOW QUICKSORT
	public static void quickSort(int[] numbers, int low, int high) {
		int i = low, j = high;

		int pivot = numbers[low + (high - low) / 2];

		while (i <= j) {
			if (lowToHigh) {
				while (numbers[i] < pivot) i++;
				while (numbers[j] > pivot) j--;
			} else if (highToLow) {
				while (numbers[i] > pivot) i++;
				while (numbers[j] < pivot) j--;
			}
			if (i <= j) {
				int temp = numbers[i];
				numbers[i] = numbers[j];
				numbers[j] = temp;
				i++;
				j--;
			}
		}
		if (low < j) quickSort(numbers, low, j);
		if (i < high) quickSort(numbers, i, high);
	}

	public void setLowToHigh() {
		lowToHigh = true;
		highToLow = false;
	}

	public void setHighToLow() {
		lowToHigh = false;
		highToLow = true;
	}
}
