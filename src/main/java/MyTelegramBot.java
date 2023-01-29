import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;

public class MyTelegramBot extends TelegramLongPollingBot {

    public static final String BOT_TOKEN = "5849914601:AAE4LN3tIRQh649YPq8WzHY5sxxUVh2Mr1M";
    public static final String BOT_USERNAME = "TaraparotBot";
    public static final String URI = "https://api.nasa.gov/planetary/apod?api_key=Z0W2wa5OSFmv4dOTo0E1pFSvBZtCsoAtRGH8KWqO";

    public static long chat_id;

    public MyTelegramBot() throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(this);
    }
    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage()) {
            chat_id = update.getMessage().getChatId();

            switch (update.getMessage().getText()) {
                case "/help":
                    sendMessage("Hey! I'm NasaBot");
                    break;

                case "/give":
                    try {
                        sendMessage(Utils.getUrl(URI));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case "/privet":
                    sendMessage("И тебе привет, Юный подаван!");
                    break;

                default: sendMessage("What's going on??!!");
            }
        }
    }

    private void sendMessage(String messageText) {
        SendMessage message = new SendMessage();
        message.setChatId(chat_id);
        message.setText(messageText);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
