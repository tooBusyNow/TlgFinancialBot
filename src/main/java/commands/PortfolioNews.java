package commands;

import architecture.BasicCommand;
import architecture.CommandContainer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import utils.JedisHandler;
import utils.KeyboardSetUp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PortfolioNews extends BasicCommand {

    public static SendMessage news(CommandContainer comCont, SendMessage message) {

        message.setText("News for your portfolio");
        message.setChatId(comCont.getChatID());
        
        Map<String, Integer> portfolio = JedisHandler.getUserData(comCont.getChatID(), comCont.getDataBase());
        if (portfolio == null) {
            message.setText("Something went wrong with database, please try later");
            return message;
        }
        List<String> tickers = new ArrayList<>(portfolio.keySet());

        InlineKeyboardMarkup keyboard = KeyboardSetUp.setInlineKeyboard(new HashMap<String, String>() {{
            for (String ticker: tickers) 
                put(ticker, "/news " + ticker);
        }});
        message.setReplyMarkup(keyboard);
        return message;
    }

    @Override
    public int getNumberOfArgs() {
        return 0;
    }

    @Override
    public void validateArgs(CommandContainer comCont) {
        Map<String, Integer> userPortfolio = JedisHandler.getUserData(comCont.getChatID(), comCont.getDataBase());
        if (userPortfolio == null || userPortfolio.isEmpty()){
            comCont.setError("Your portfolio is empty");
        }
    }
}
