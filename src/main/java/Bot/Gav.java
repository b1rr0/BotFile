package Bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Gav {
   public static   Bot bot = Bot.getInstance();

    public static void main(String[] args) throws TelegramApiException {
        SendMessage sendMessage=new SendMessage().setText("sad");
        bot.execute(sendMessage);
    }
}
