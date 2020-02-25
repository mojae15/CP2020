import java.util.*;

public class Box<T> {

	//Exercise 1
	private final T content;

	public Box(T content) {
		//Addition from exercise 2
		if (content == null) {
			throw new IllegalArgumentException();
		}
		this.content = content;
	}

	public T content() {
		return content;
	}

	//Exercise 2
	public <O> O apply(BoxFunction<T, O> function) {
		return function.apply(content);
		
	}
	
	// Exercise 5
	public static <I, O> List<O> applyToAll(List<Box<I>> list, BoxFunction<I, O> function) {
		final List<O> returnList = new ArrayList<>();
		for (Box<I> box : list) {
			returnList.add(box.apply(function));
		}
		return returnList;
	}

	//Exercise 5 - With streams
	// public static <I, O> List<O> applyToAll(List<Box<I>> list, BoxFunction<I, O> function) {



	// 	final List<O> returnList = new ArrayList<>();

	// 	list.stream().forEach(b -> returnList.add(b.apply(function)));
		
	// 	return returnList;
	// }

}
