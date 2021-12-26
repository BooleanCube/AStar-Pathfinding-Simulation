import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Sort {

	public static void bubbleSort(ArrayList<Node> list) {
		int swtch = -1;
		Node temp;
		while(swtch != 0) {
			swtch = 0;
			for(int i = 0; i < list.size() - 1; i++) {
				if(list.get(i).f > list.get(i + 1).f) {
					temp = list.get(i);
					list.remove(i);
					list.add(i + 1, temp);
					swtch = 1;
				}
			}
		}
	}

	public static void quickSort(ArrayList<Node> list, int low, int high) {
		//int low = 0, high = list.size()-1;
		if(low < high) {
			int pivot = list.get(high).f;
			int i = low-1;
			for(int j=low; j<=high-1; j++) {
				if(list.get(j).f < pivot) {
					i++;
					Collections.swap(list, i, j);
				}
			}
			Collections.swap(list, i+1, high);
			int pi = i+1;
			quickSort(list, low, pi - 1);
			quickSort(list, pi + 1, high);
		}
	}

	public static void fastSort(ArrayList<Node> list) {
		list.sort(Comparator.comparingInt(n -> n.f));
	}

}
