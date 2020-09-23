package DataBase;

import Another.UserInBot;

import java.util.ArrayList;

public class DB {
    public static boolean isBanned(UserInBot user) {
        return false;
    }

    public static ArrayList<Category> initCategories() {
        ArrayList<Category> categories = new ArrayList<>();
        Category category = new Category("QA");
        category.addSomeData("QA1");
        category.addSomeData("QA2");
        category.addSomeData("QA3");
        category.addSomeData("QA4");
        categories.add(category);
        category = new Category("BA");

        category.addSomeData("BA1");
        category.addSomeData("BA2");
        category.addSomeData("BA3");
        category.addSomeData("BA4");
        categories.add(category);

        category = new Category("ZBA");

        category.addSomeData("ZBA1");
        category.addSomeData("ZBA2");
        category.addSomeData("ZBA3");
        category.addSomeData("zBA4");
        categories.add(category);

        return categories;
    }
 public  static void  ban(String s){
  String[] strings=  s.split(" ");
   addBanToDB(strings[1]);
 }
    public static String   cut(String s){
        String[] strings=  s.split(" ");
       return strings[1];
    }
 public  static  void addBanToDB(String s){

 }

}

