package model;

import java.util.ArrayList;
import java.util.List;

public class Model {

    private static final ThreadLocal<List<User>> users = ThreadLocal.withInitial(ArrayList::new);
    public static void empty(){
        users.set(new ArrayList<>());
    }
    private static List<User> getUsers() { return users.get(); }
    public static void setUsers(List<User> users) { Model.users.set(users); }

    public static User getUser() {
        try{
            return getUsers().get(getUsers().size() - 1);
        }catch(ArrayIndexOutOfBoundsException e){
            return null;
        }
    }

    public static void setUser(User user) { getUsers().add(user); }

}
