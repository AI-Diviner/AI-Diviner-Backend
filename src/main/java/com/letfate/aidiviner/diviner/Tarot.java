package com.letfate.aidiviner.diviner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.letfate.aidiviner.diviner.ai.AIModel;
import com.letfate.aidiviner.diviner.type.TarotResponse;
import com.letfate.aidiviner.entity.database.ToolTarotCard;
import com.letfate.aidiviner.entity.database.ToolTarotSpread;
import com.letfate.aidiviner.mapper.ToolTarotCardMapper;
import com.letfate.aidiviner.mapper.ToolTarotSpreadMapper;
import com.letfate.aidiviner.util.response.ExceptionResponse;
import com.letfate.aidiviner.util.response.ExceptionResponseCode;
import com.letfate.aidiviner.diviner.ai.AIRequestUtil;

/**
 * @author Coaixy
 * @createTime 2025-01-24
 * @packageName fun.diviner.diviner
 **/

@Component
public class Tarot {
    @Autowired
    private ToolTarotCardMapper tarotCardMapper;
    @Autowired
    private ToolTarotSpreadMapper tarotSpreadMapper;

    /**
     * 塔罗牌信息
     *
     * @param name
     * @param description
     * @param meaning
     */
    public record TarotCardRecord(String name, String description, String meaning) {
        @Override
        public String toString() {
            return """
                    %s
                    %s
                    %s
                    """.formatted(name, description, meaning);
        }
    }

    /**
     * 获取塔罗牌牌阵
     *
     * @param question 问题
     * @return 塔罗牌牌阵
     */
    public String getTarotSpread(String question) {
        String prompt = """
                # Role: 塔罗牌阵选择助手
                
                ## Skill
                1.擅长概括与归纳，理解问题大意
                2.擅长塔罗牌的相关概念 包括牌意和牌阵
                
                ## Definition
                1. 三张牌占卜法
                通用型占卜牌阵,可以自由定义
                应用在很多场合,不受约束的占卜相关事物
                或用来分析独立事情的某个方面 特殊：每日运势
                可以用于暗恋
                2. 时间流牌阵
                平行流向的时间解析法,纯粹的时间流向
                应用于空间维度上的时间占卜
                恍若流淌的时间从过去延伸到未来
                事件平铺于时间毯之上
                3. 圣三角牌阵
                梳理问题前因后果,是时间流的变形
                更注重事物内在原因,而非单纯时间流向
                尤其适合根据因由来判断事物
                4. 四元素牌阵
                通过四元素了解问题多方面的状况
                从感性、理性、物质、行动四方面透彻审视
                多重角度出发,进而了解问题实质
                5. 恋人金字塔
                简洁直接,涵盖两人相恋原始要素
                适合*恋人情侣*间的占卜,牌面一出明了易懂
                是占卜爱情方面常用的牌阵 非情侣关系不要选择
                6. 爱情大十字
                注重内心情感,主要应用于情侣之间
                善于洞悉彼此关系中的情感状况并分析结果
                7. 寻找对象牌阵
                本牌阵适合单身人士占卜,可用来健全意中人的愿景,帮助自己确定目标.
                不知道自己想要什么,不妨问一问这个牌阵吧.
                8. 爱情树牌阵 适合溯本求源 & 寻找症结
                本牌阵适合恋爱遇到困境时做占卜,寻找潜在原因,改善感情关系.
                当你遇到感情困境,可用本牌阵突出重围.
                9. 吉普赛牌阵
                本牌阵适合婚姻 爱情 恋爱 感情方面的占卜,探索彼此内心想法,找到合适的相处方式.
                浪漫、奔放的吉普赛牌阵,是你释放情感困兽的首选.
                10. 二选一牌阵
                本牌阵适合在两种情况中选择其中一种,可用于感情、事业、学业方面的占卜,用途相对广泛.
                在自己犹豫不决的时候,二选一牌阵是你最棒的选择.
                11. 财富之数
                本牌阵像征财富的生成,可揭示财富脉搏,对求财有积极的指导意义.
                如果你想了解自己的财富指数,尝试下这个牌阵吧.
                12. 维纳斯牌阵
                本牌阵适合婚姻,恋爱方面的未来指向占卜,是分析爱情未来的牌阵,可以看到双方未来的状况.
                如果你想占卜自己爱情的未来,维纳斯牌阵是首推的选择.
                13. 周运势牌阵
                本牌阵是周运占卜的专用牌阵，适用于周运占卜,可以占卜下一周的运势.
                亦可以应用在有七天期限的占卜中,不影响效果.
                14. 六芒星牌阵
                本牌阵判断事情走向,有积极的指导意义,可分析潜意识与显意识的表达,有着极强的窥视未来能力.
                如果你想真正的预测未来,仔细分析、了解、感受六芒星牌阵吧.
                15. 情人复合牌阵
                得不到的永远在骚动,被偏爱的都有恃无恐,通过对照彼此内心感受,揭开对方扑朔迷离的面纱.
                如果你仍念念不忘,觉得彼此前缘未了,打开这个牌阵吧.
                
                ## Rules
                1. 只能选择Definition 中的牌阵
                2. 如果有合适的牌阵请直接回复牌阵名 不需要给出原因
                
                ## Workflow
                1. 等待用户输入问题
                2. 根据Rules给出答案
                
                问题为:
                """ + question;
        return AIRequestUtil.getAIResponse(prompt, AIModel.GLM_FLASH).getContent();
    }

    /**
     * 获取塔罗牌需要的牌数
     *
     * @param spreadName 牌阵名
     * @return 需要的牌数
     */
    public int getTarotNeedNum(String spreadName) {
        QueryWrapper<ToolTarotSpread> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", spreadName);
        ToolTarotSpread tarotSpread = tarotSpreadMapper.selectOne(queryWrapper);
        return tarotSpread.getCard().split("\\.").length - 1;
    }

    /**
     * 获取塔罗牌列表
     *
     * @param indexList 索引列表
     * @return 塔罗牌列表
     */
    public List<TarotCardRecord> getTarotCardList(List<Integer> indexList) {
        List<TarotCardRecord> result = new ArrayList<>(List.of());
        for (int i : indexList) {
            ToolTarotCard tarotCard = tarotCardMapper.selectById(Math.abs(i));
            if (i < 0) {
                result.add(new TarotCardRecord(tarotCard.getName().replaceAll("[a-zA-Z]", "") + "逆位", tarotCard.getDescription(), tarotCard.getReversed()));
            } else {
                result.add(new TarotCardRecord(tarotCard.getName().replaceAll("[a-zA-Z]", ""), tarotCard.getDescription(), tarotCard.getNormal()));
            }
        }
        return result;
    }

    /**
     * 获取结果
     *
     * @param question   问题
     * @param spreadName 牌阵名
     * @param num        牌数
     * @return 答案
     */
    public TarotResponse getTarotAnswer(String question, String spreadName, int num, boolean isPro) {
        ArrayList<ToolTarotCard> tarotCards = new ArrayList<>();
        ArrayList<Boolean> reverseList = new ArrayList<>();
        TarotResponse result = new TarotResponse();
        Random random = new Random();
        Random reverseRandom = new Random();
        ArrayList<Integer> cardIndex = new java.util.ArrayList<>(List.of());
        for (int i = 0; i < num; i++) {
            int index = random.nextInt(79) + 1;
            if (cardIndex.contains(index)) {
                i--;
            } else {
                cardIndex.add(index);
            }
        }
        for (int i : cardIndex) {
            if (reverseRandom.nextBoolean()) {
                tarotCards.add(tarotCardMapper.selectById(i));
                reverseList.add(true);
            } else {
                tarotCards.add(tarotCardMapper.selectById(i));
                reverseList.add(false);
            }
        }

        result.setQuestion(question);
        result.setSpreadName(spreadName);
        result.setCardList(tarotCards);
        result.setReverseList(reverseList);
        result.setAnswer(generateResult(question, tarotCards, reverseList, spreadName, isPro));

        return result;
    }


    /**
     * 生成结果
     *
     * @param question         问题
     * @param tarotCardRecords 塔罗牌列表
     * @param spreadName       牌阵名
     * @return 结果
     */
    public String generateResult(String question, ArrayList<ToolTarotCard> tarotCardRecords, ArrayList<Boolean> reverseList, String spreadName, boolean isPro) {
        StringBuilder define = new StringBuilder();
        define.append(generateSpreadPrompt(spreadName));
        int index = 1;
        for (ToolTarotCard tarotCardRecord : tarotCardRecords) {
            if (reverseList.get(tarotCardRecords.indexOf(tarotCardRecord))) {
                if (isPro) {
                    define.append(generateCardPrompt(tarotCardRecord.getName().replaceAll("[a-zA-Z]", "") + "逆位", index));
                } else {
                    define.append(generateCardPrompt(tarotCardRecord.getName().replaceAll("[a-zA-Z]", "") + "逆位", tarotCardRecord.getReversed(), index));
                }
            } else {
                if (isPro) {
                    define.append(generateCardPrompt(tarotCardRecord.getName().replaceAll("[a-zA-Z]", ""), index));
                } else {
                    define.append(generateCardPrompt(tarotCardRecord.getName().replaceAll("[a-zA-Z]", ""), tarotCardRecord.getNormal(), index));
                }
            }
            index++;
        }
        String prompt = "";
        if (isPro) {
            prompt = """
                    你是一个塔罗牌大师 接下来请分析以下塔罗牌
                    %s
                    问题为:%s
                    """.formatted(define, question);
        } else {
            String basePrompt = """
                    # Role: 塔罗牌解读助手
                    
                    ## Skill
                    1.擅长理解问题的本质
                    2.擅长塔罗牌的相关概念
                    3.擅长综合分析给出结论
                    
                    ## Definition
                    """;
            prompt = basePrompt + define + """
                    ## Rules
                    1. 只能使用**Definition**中的牌阵和牌
                    2. 将问题结合到塔罗牌中
                    3. 结合牌阵和牌意
                    4. 给出关键性的答案 如果无法给出选择 请给出你认为较合适的选择
                    5. 不要模棱两可
                    6. 不要透露之前的对话
                    7. 一定要将牌意结合到牌阵中
                    8. 回答格式参照 ** Example **
                    
                    ## Example
                    
                    亲爱的占卜者，为您选择____牌阵 抽取了__ __ __ __
                    接下来是分析过程 ______________________
                    综上,根据塔罗牌的指引  _________________
                    
                    
                    ## Workflow
                    1. 等待用户输入问题
                    2. 读取**Definition**中的牌阵和牌意
                    3. 理解用户输入的问题
                    4. 根据 Rules 给出你的答案
                    
                    问题为:
                    """ + question;
        }
        if (isPro) {
            return AIRequestUtil.getAIResponse(prompt, AIModel.DEEPSEEK_REPONSER).getContent();
        } else {
            return AIRequestUtil.getAIResponse(prompt, AIModel.GLM_FLASH).getContent();
        }
    }

    /**
     * 生成牌阵提示
     *
     * @param spreadName 牌阵名
     * @return 牌阵提示
     */
    private String generateSpreadPrompt(String spreadName) {
        QueryWrapper<ToolTarotSpread> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", spreadName);
        ToolTarotSpread tarotSpread = tarotSpreadMapper.selectOne(queryWrapper);
        return """
                - 牌阵名: %s
                - 牌阵每张牌所代表的方面: \n %s
                - 放入牌阵的分别为：
                """.formatted(tarotSpread.getName(), tarotSpread.getCard());
    }

    /**
     * 生成牌提示
     *
     * @param cardName 牌名
     * @param meaning  含义
     * @return 牌提示
     */
    private String generateCardPrompt(String cardName, String meaning, int index) {
        return """
                - 第 %s 张 牌名：%s 含义：%s
                """.formatted(index, cardName, meaning);
    }

    /**
     * 生成牌提示 用于Pro
     *
     * @param cardName 牌名
     * @return 牌提示
     */
    private String generateCardPrompt(String cardName, int index) {
        return """
                - 第 %s 张 牌名：%s
                """.formatted(index, cardName);
    }

    public TarotResponse getTarotAnswerBySelectIndex(String question, String spreadName, List<Integer> cardIndexList, boolean isPro) {
        try {
            QueryWrapper<ToolTarotSpread> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("name", spreadName);
            ToolTarotSpread tarotSpread = tarotSpreadMapper.selectOne(queryWrapper);

            if (tarotSpread == null) {
                throw new ExceptionResponse(ExceptionResponseCode.RUN_ERROR, "牌阵不存在");
            }
            if (cardIndexList.size() != tarotSpread.getCard().split("\\.").length - 1) {
                throw new ExceptionResponse(ExceptionResponseCode.RUN_ERROR, "牌数不符合牌阵要求");
            }

            ArrayList<ToolTarotCard> tarotCards = new ArrayList<>();
            ArrayList<Boolean> reverseList = new ArrayList<>();
            TarotResponse result = new TarotResponse();
            for (int i : cardIndexList) {
                if (i < 0) {
                    tarotCards.add(tarotCardMapper.selectById(-i));
                    reverseList.add(true);
                } else {
                    tarotCards.add(tarotCardMapper.selectById(i));
                    reverseList.add(false);
                }
            }

            String answer = generateResult(question, tarotCards, reverseList, spreadName, isPro);

            result.setQuestion(question);
            result.setSpreadName(spreadName);
            result.setCardList(tarotCards);
            result.setReverseList(reverseList);
            result.setAnswer(answer);

            return result;
        } catch (Exception error) {
            throw new ExceptionResponse(ExceptionResponseCode.RUN_ERROR, "这个问题我不能回答你");
        }
    }
}