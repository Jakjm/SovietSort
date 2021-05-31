import java.util.ArrayList;
import java.util.List;

public class SovietSort {

	public SovietSort() {
		String x = "haha";
		String y = "br";
		//A class/object that implements the comparable interface must support the compareTo method which returns and integer
		//the compareTo method should return + if x > y 0 if x = y - if x < y
		x.compareTo(y);
	}
	//1 2 3 4 5 6 9 7 20
	//[1 2 3 4] [5 6 9 7 20]
	//... keep splitting
	//O(n log n) performance since we have n items and we have to perform log n split + merge operations which are O(n)
	
	//citizens: [1 2 3 4 5 6 7 20] gulag: [9]
	//recursively call soviet sort on the gulag items
	//Then you can do a O(n) merge on the citizens and gulag and get a sorted list
	//if only one item out of order/the items that are out of order are relatively ordered
	//[1 3 2 5 4 7 6 9
	//[1 3 5 7 9] [2 4 6] if gulag is already in relative order, then this alg performs in linear O(n) time, which beats merge sort.
	//in some cases though, gulag might be really big...
	
	//[9 8 7 6 5 4 3 2 1]
	//[9] [8 7 6 5 4 3 2 1] 
	//[9] [8] [7 6 5 4 3 2 1]
	
	//We end up doing O(n) splits which each cost worst case O(N) so it's not the O(n) performance we were hoping for, and ends up being kinda 
	//lame
	/**
	 * Soviet sort is going to be a sorting algorithm with 2 parts
	 * a split stage similar to stalin sort (where we remove all elements that are out of order - BUT we will recursively call sovietSort)
	 * a merge stage just like merge sort
	 * @param <T>
	 * @param list - list of items that we are sorting
	 */
	public static <T extends Comparable<T>> void sovietSort(List<T> list) {
		//To ensure not completely terrible recursion performance
		if(list == null || list.size() <= 1)return;
		
		//List of soviet citizens that haven't made me angry yet
		List<T> citizens = new ArrayList<T>();
		
		//Soviet jail + prison camp
		//Any persons found to disrespect our great and important leader, Jordan, are sent to gulag
		//(in reality, we're sending the items that are out of order to gulag)
		List<T> gulag = new ArrayList<T>();
		
		//Our algorithm to separate the citizens from the western spy slime
		//1) add an item to citizens
		//2) keep adding items < back item of citizens to citizens
		//3) all other doomed to GULAG
		//yee
		citizens.add(list.get(0));
		
		for(int index = 1;index < list.size();++index) {
			T item = list.get(index);
			//Compare the item at the back of citizens to the current item.
			//If this item greater/eq, it is a citizen
			//if it is lesser, send tO GUL@G 
			if(item.compareTo(citizens.get(citizens.size() - 1)) >= 0) {
				citizens.add(item);
			}
			else {
				gulag.add(item);
			}
		}
		
		//Now we have some items that are citizens, and some that are in gulag.
		//So now we can recursively call soviet sort to send the gulag items back for Jordanville citizenship.
		sovietSort(gulag);
		
		//Gulag and citizens are now separate sorted lists.
		//Clear the original list, and do a sorted merge of the two into list.
		list.clear();
		
		while(citizens.size() > 0 && gulag.size() > 0) {
			//If the first citizen is <= the first gulag resident, it can rejoin the soviet union
			if(citizens.get(0).compareTo(gulag.get(0)) <= 0) {
				list.add(citizens.remove(0));
			}
			else {
				list.add(gulag.remove(0));
			}
		}
		
		while(!citizens.isEmpty()) {
			list.add(citizens.remove(0));
		}
		
		while(!gulag.isEmpty()) {
			list.add(gulag.remove(0));
		}
		
		//List is sorted :)
	}
	
	public static void main(String [] args) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(0);
		list.add(7);
		list.add(5);
		list.add(3);
		System.out.println(list);
		sovietSort(list);
		System.out.println(list);
	}
}
