package vexybot.helper;

import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.objects.Message;
import vexybot.Bot;
import vexybot.Status;
import vexybot.manager.ChatsManager;
import vexybot.manager.MessageManager;
import vexybot.services.Geocoder;

import java.io.IOException;

import static vexybot.helper.MessageHelper.start;
import static vexybot.manager.MessageManager.RBText;

public class LocationHelper implements Helper {
    public static void startSelectStandartLocation(Message message) throws TelegramApiException {
        Bot.bot.sendMessage(MessageManager.getSendMessage(message,
                RBText(message, "select.standart.location")));
        ChatsManager.setStatus(message, Status.ON_SELECT_STANDART_LOCATION.toString());
    }

    public static void onSelectStandartLocation(Message message) throws IOException, TelegramApiException {
        if (message.hasText()) {
            String text = message.getText();
            if (text.equalsIgnoreCase(RBText(message, "no.need")))
                MessageHelper.onCancel(message);
            else
                ChatsManager.setLocation(message, text);
        } else if (message.hasLocation()) {
            ChatsManager.setLocation(message, Geocoder.getTextByCoordinates(message));
        } else {
            Bot.bot.sendMessage(MessageManager.getSendMessage(message,
                    RBText(message, "select.standart.location")));
            return;
        }
        ChatsManager.removeStatus(message);
        start(message);
    }

    public static void selectLocation(Message message) throws TelegramApiException {
        Bot.bot.sendMessage(MessageManager.getSendMessage(message,
                RBText(message, "give.me.the.location")));
        ChatsManager.setStatus(message, Status.GET_WEATHER.toString());
    }
}
