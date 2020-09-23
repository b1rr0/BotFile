package DataBase;

import java.util.ArrayList;
import java.util.Objects;

public class Category {
    private  String nameOF;
    private ArrayList<String> dataInside=new ArrayList<String>();

    public Category(String nameOF) {
        this.nameOF = nameOF;
    }

    public String getNameOF() {
        return nameOF;
    }

    public void setNameOF(String nameOF) {
        this.nameOF = nameOF;
    }

    public ArrayList<String> getDataInside() {
        return dataInside;
    }

    public void addSomeData(String data) {
        dataInside.add(data);
    }
    public  void  dellData(){
        dataInside=new ArrayList<>();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(nameOF, category.nameOF);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameOF);
    }
}
