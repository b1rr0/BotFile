package Bot;

import Another.UserInBot;
import DataBase.Category;
import DataBase.DB;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;


import java.util.ArrayList;

public class Main {
    public static ArrayList<UserInBot> users = new ArrayList<>();
    public static ArrayList<Category> categories = new ArrayList<>();

    public static void main(String args[]) throws TelegramApiRequestException {
        categories= DB.initCategories();
        FileProcessing.init();
        ApiContextInitializer.init();
        TelegramBotsApi telegram = new TelegramBotsApi();
        telegram.registerBot(new Bot().getInstance());
    }
}