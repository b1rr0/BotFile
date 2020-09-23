package Another;

import java.util.ArrayList;
import java.util.Objects;

public  class UserInBot {
    private long id;
    private String name;
    private String secondName;
    private ArrayList<Long>idOfMessages=new ArrayList<>();
    private  boolean isCom=false;
    private  String hashtag="#";
    public boolean isCom() {
        return isCom;
    }

    public void setCom(boolean com) {
        isCom = com;
    }

    public UserInBot(long id, String name, String secondName) {
        this.id = id;
        this.name = name;
        this.secondName = secondName;
    }

    public void addDataToHashtag(String s) {
        hashtag +=s;
    }

    public long getId() {
        return id;
    }

    public ArrayList<Long> getIdOfMessages() {
        return idOfMessages;
    }
    public void dellAllIdMessages() {
        idOfMessages=new ArrayList<>();
    }

    public void addId(long idOfMessage) {
        idOfMessages.add(idOfMessage);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInBot userInBot = (UserInBot) o;
        return id == userInBot.id;
    }

    @Override
    public String toString() {
        return  hashtag+System.lineSeparator()+
                "#" + name  +
                secondName +System.lineSeparator()
                //+"id=" + id
                ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
