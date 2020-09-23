package Bot;

import Another.UserInBot;
import DataBase.Category;
import DataBase.DB;
import com.sun.deploy.xml.BadTokenException;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import org.telegram.telegrambots.meta.api.methods.ForwardMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

import static Bot.Main.categories;
import static Bot.Main.users;


public class Bot extends TelegramLongPollingBot {
    private long idOfChatWith = -1001349577594L;
    private String token = "1095782783:AAGAtFngwr_gAkarQ_mveBmdqImuwFjHEW8";
    public static final String botName = "FileSanderBot";
    public static UserInBot user;
    public static String buttonString = "В начало";
    public static String startMessage = "Вас привествует Бот для выдачи резюме";
    private static Bot bot;


    public static Bot getInstance() {
        if (bot == null) {
            bot = new Bot();
        }
        return bot;
    }

    public void onUpdateReceived(Update update) {

        if (update.hasCallbackQuery()) {
            logicWithCallBack(update);
        } else if (update.getMessage().isChannelMessage()) {
            logicWithChanel(update);
        } else
            logicWithUser(update);
    }


    public void logicWithUser(Update update) {
        user = user4Message(update.getMessage());
        if (DB.isBanned(user)) {
            return;
        }
        if (update.getMessage().hasDocument() && users.get(users.indexOf(user)).isCom()) {
            if (reSendFile(update)) {
                users.remove(user);
                sendText(user.getId(), "ваше");
            }

            return;
        }
        if (update.getMessage().getText().equals(buttonString)) {
            deleteMessage();
            users.remove(user);
        }
        if (accountCheck4new()) {
            sendCategory();
        }


    }

    public void logicWithChanel(Update update) {
        System.out.println(update);
    }

    public void logicWithCallBack(Update update) {
        String s = update.getCallbackQuery().getData();
        if (s.contains("бан")) {
            DB.ban(s);
        } else if (s.contains("Категория")) {
            deleteMessage();
            sendItemsFromCategory(DB.cut(s));
        } else if (s.contains("Подкатегория")) {
            deleteMessage();
            sendText(user.getId(), "загрузите сюда свое резюме");
            users.get(users.indexOf(user)).setCom(true);
            users.get(users.indexOf(user)).addDataToHashtag(DB.cut(s));


        }
    }


    public boolean accountCheck4new() {
        if (users.contains(user)) {
            user = users.get(users.indexOf(user));
            return false;
        }
        users.add(user);
        return true;
    }

    public UserInBot user4Message(Message ms) {
        return new UserInBot(ms.getChatId(),
                ms.getFrom().getFirstName(),
                ms.getFrom().getLastName());
    }

    public void sendText(long id, String txt) {
        SendMessage sendMessage = new SendMessage().setChatId(id);
        sendMessage.setText(txt);
        setButtons(sendMessage);
        try {
            execute(sendMessage);
            System.out.println(sendMessage.toString());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendForward(Update update) {
        ForwardMessage forwardMessage = new ForwardMessage();
        long formChatId = update.getMessage().getChatId();
        int messageId = update.getMessage().getMessageId();
        forwardMessage.setChatId(idOfChatWith).setFromChatId(formChatId).setMessageId(messageId);
        try {
            execute(forwardMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendWithKeyboard(Update update, String s) {
        SendMessage sendMessage = new SendMessage().setChatId(idOfChatWith).setText(s).
                setReplyMarkup(KeyBoard.getInlineKeyBoardMessage("Забанить", "бан " + update.getMessage().getChatId()));
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public boolean reSendFile(Update update) {
        Document document = FileProcessing.takeFile(update);

        if (document == null) {
            sendText(user.getId(), FileProcessing.wrongData);
            return false;
        } else {
            sendWithKeyboard(update, users.get(users.indexOf(user)).toString());
            sendForward(update);
            return true;
        }
    }


    public void sendCategory() {
        SendMessage sendMessage = new SendMessage().setChatId(user.getId());
        for (Category c : categories) {
            sendMessage.setText(c.getNameOF()).
                    setReplyMarkup(KeyBoard.
                            getInlineKeyBoardMessage("Выбрать данную категорию", "Категория " + c.getNameOF()));
            try {
                Message m = execute(sendMessage);
                users.get(users.indexOf(user)).addId(m.getMessageId());
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        sendMessage = new SendMessage().setChatId(user.getId()).setText("Выберите из предложенных");
        setButtons(sendMessage);
        try {
            Message m = execute(sendMessage);
            users.get(users.indexOf(user)).addId(m.getMessageId());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendItemsFromCategory(String s) {
        SendMessage sendMessage = new SendMessage().setChatId(user.getId());
        Category cat = categories.get(categories.indexOf(new Category(s)));
        users.get(users.indexOf(user)).addDataToHashtag(cat.getNameOF());
        for (String c : cat.getDataInside()) {
            sendMessage.setText(c).
                    setReplyMarkup(KeyBoard.
                            getInlineKeyBoardMessage("Выбрать данную подкатегорию <!>", "Подкатегория " + c));
            try {
                Message m = execute(sendMessage);
                users.get(users.indexOf(user)).addId(m.getMessageId());
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        sendMessage = new SendMessage().setChatId(user.getId()).setText("Выберите из предложенных");
        setButtons(sendMessage);
        try {
            Message m = execute(sendMessage);
            users.get(users.indexOf(user)).addId(m.getMessageId());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }


    public void deleteMessage() {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(user.getId());

        for (long id : users.get(users.indexOf(user)).getIdOfMessages()
        ) {
            //System.out.println();
            deleteMessage.setMessageId((int) id);
            try {
                execute(deleteMessage);
            } catch (TelegramApiException e) {
                //  e.printStackTrace();
                System.out.println("Удаление сообщения =" + id);
            }
        }
        user.dellAllIdMessages();
    }

    public void check() {

    }

    public String getBotUsername() {
        return botName;
    }

    public String getBotToken() {
        return token;
    }

    public synchronized void setButtons(SendMessage sendMessage) {
        // Создаем клавиуатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();

        // Первая строчка клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        // Добавляем кнопки в первую строчку клавиатуры
        keyboardFirstRow.add(new KeyboardButton(buttonString));


        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardFirstRow);

        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

}
