public class Box<T>{

    //Exercise 1
    public final T content;

    public Box(T content){
        this.content = content;
    }

    //Exercise 2
    public <O> O apply(BoxFunction<T, O> interfaceObject){
        return interfaceObject.apply(content);
    }


}