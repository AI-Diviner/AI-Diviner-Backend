package fun.diviner.ai.diviner.type;

import java.util.List;

import lombok.Data;

import fun.diviner.ai.entity.database.ToolTarotCard;

@Data
public class TarotResponse {
    public String question;
    public String spreadName;
    public List<ToolTarotCard> cardList;
    public List<Boolean> reverseList;
    public String answer;
}