SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for card
-- ----------------------------
DROP TABLE IF EXISTS `card`;
CREATE TABLE `card`  (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                         `point` int UNSIGNED NOT NULL,
                         `number` int UNSIGNED NOT NULL DEFAULT 1,
                         `count` int UNSIGNED NOT NULL DEFAULT 0,
                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of card
-- ----------------------------

-- ----------------------------
-- Table structure for card_record
-- ----------------------------
DROP TABLE IF EXISTS `card_record`;
CREATE TABLE `card_record`  (
                                `card_id` int NOT NULL,
                                `user_id` int NOT NULL,
                                PRIMARY KEY (`card_id`, `user_id`) USING BTREE,
                                INDEX `f_share_card_record_user_id`(`user_id` ASC) USING BTREE,
                                CONSTRAINT `card_id` FOREIGN KEY (`card_id`) REFERENCES `card` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
                                CONSTRAINT `share_card_record_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of card_record
-- ----------------------------

-- ----------------------------
-- Table structure for core
-- ----------------------------
DROP TABLE IF EXISTS `core`;
CREATE TABLE `core`  (
                         `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                         `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                         PRIMARY KEY (`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of core
-- ----------------------------
INSERT INTO `core` VALUES ('openTool', '1');
INSERT INTO `core` VALUES ('yiPayId', '');
INSERT INTO `core` VALUES ('yiPayMerchantPrivateKey', '');
INSERT INTO `core` VALUES ('yiPayNoticeUrlPrefix', '');
INSERT INTO `core` VALUES ('yiPayPlatformPublicKey', '');
INSERT INTO `core` VALUES ('yiPayReturnUrl', '');

-- ----------------------------
-- Table structure for recharge
-- ----------------------------
DROP TABLE IF EXISTS `recharge`;
CREATE TABLE `recharge`  (
                             `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                             `point` int UNSIGNED NOT NULL,
                             `money` decimal(10, 2) UNSIGNED NOT NULL,
                             `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                             `state` tinyint(1) NOT NULL DEFAULT 0,
                             `creation_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                             `payment_time` timestamp NULL DEFAULT NULL,
                             `user_id` int NOT NULL,
                             PRIMARY KEY (`id`) USING BTREE,
                             INDEX `f_recharge_user_id`(`user_id` ASC) USING BTREE,
                             CONSTRAINT `recharge_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of recharge
-- ----------------------------

-- ----------------------------
-- Table structure for record
-- ----------------------------
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record`  (
                           `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                           `tool_id` int NOT NULL,
                           `request` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                           `response` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                           `time_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           `user_id` int NOT NULL,
                           PRIMARY KEY (`id`) USING BTREE,
                           INDEX `f_record_user_id`(`user_id` ASC) USING BTREE,
                           INDEX `f_tool_id`(`tool_id` ASC) USING BTREE,
                           CONSTRAINT `record_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
                           CONSTRAINT `tool_id` FOREIGN KEY (`tool_id`) REFERENCES `tool` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of record
-- ----------------------------

-- ----------------------------
-- Table structure for tool
-- ----------------------------
DROP TABLE IF EXISTS `tool`;
CREATE TABLE `tool`  (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                         `alias` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                         `price` int UNSIGNED NOT NULL,
                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tool
-- ----------------------------
INSERT INTO `tool` VALUES (1, '塔罗牌普通', 'tarot_common', 20);
INSERT INTO `tool` VALUES (2, '塔罗牌自选', 'tarot_select', 40);
INSERT INTO `tool` VALUES (3, '八字排盘', 'bazi_arrange', 0);
INSERT INTO `tool` VALUES (4, '八字解盘', 'bazi_solve', 50);
INSERT INTO `tool` VALUES (6, '梅花易数起卦', 'meihua_arrange', 0);
INSERT INTO `tool` VALUES (7, '梅花易数解卦', 'meihua_solve', 60);
INSERT INTO `tool` VALUES (12, '塔罗牌普通 Pro', 'tarot_common_pro', 60);
INSERT INTO `tool` VALUES (14, '塔罗牌自选 Pro', 'tarot_select_pro', 60);

-- ----------------------------
-- Table structure for tool_tarot_card
-- ----------------------------
DROP TABLE IF EXISTS `tool_tarot_card`;
CREATE TABLE `tool_tarot_card`  (
                                    `id` int NOT NULL AUTO_INCREMENT,
                                    `name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
                                    `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
                                    `normal` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
                                    `reversed` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
                                    `detail` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
                                    `link` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
                                    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 80 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tool_tarot_card
-- ----------------------------
INSERT INTO `tool_tarot_card` VALUES (1, 'The Fool 愚人', '愚人牌描绘了一个快乐地走向世界的青年。他正在迈出他的第一步，他充满活力，快乐，兴奋。除了一个小袋子，他什么都没带，对路上可能出现的危险毫不在意。事实上，他很快就会遇到这些可能的危险中的第一个，因为如果他再多走一步，他就会从他前方的悬崖上跌落。但这似乎与他无关——我们不确定他是天真还是幼稚。跟在他身后的狗吠叫着警告他，如果他不尽快意识到周围的环境，他可能永远经历他梦想遇到的所有冒险。', '新的开始，新的旅程，自由，纯真，无限潜力，冒险，理想主义，自发性', '鲁莽，粗心，心烦意乱，幼稚，愚蠢，容易上当受骗，古板，沉闷', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/the-fool-meaning-major-arcana-tarot-card-meanings', 'Fool.jpg');
INSERT INTO `tool_tarot_card` VALUES (2, 'The Magician 魔术师', '魔术师是一张充满象征意义的塔罗牌。中央人物描绘了一个人，一只手指向天空，另一只手指向地面，仿佛在说“天上如是，地上亦然”（\"as above, so below.\"）。这是一个比较复杂的说法，但概括来说就是地反映天，外部反映内在，微观反映宏观。在这里也可以解释为，魔术师象征着在天上世界和人类世界之间充当中间人的能力。在他的桌子上，魔术师也摆放着所有的塔罗花色。这象征着由这位魔术师连接的四种元素——风，火，水和土。他头上的无穷大标志，预示着用意志可以创造无限可能。', '意志力，欲望，足智多谋，强大的技巧与能力，注意力，积极行动，愿望实现', '操纵，狡猾，诡计，幻觉，欺骗，才能被浪费或用到错误的方向', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/the-magician-meaning-major-arcana-tarot-card-meanings', 'Magician.jpg');
INSERT INTO `tool_tarot_card` VALUES (3, 'The High Priestess 女祭司', '女祭司坐在所罗门圣殿，雅斤和波阿斯两根柱子之间的立方石之上。雅斤（右）通常被称为建立之柱，波阿斯（左）是力量之柱。柱子描绘了自然的二元性：男性与女性，善与恶，消极与积极。女祭司位于两者之间，这表明她有责任充当现实深处的调解人。她是第三根支柱——两者之间的路径。她认为这两个支柱是平等的，并且在这两个世界中都有知识需要学习。你还会注意到她戴着伊希斯的王冠，这可能意味着她是魔法的信徒。佩戴太阳十字架的女祭司表明她与地球的季节和地球本身有关。她脚下的新月也出现在许多对圣母玛利亚的描绘中，这意味着她对自己的情绪有着完全的把握。背后的石榴则代表了女祭司的抱负。', '直觉，神秘，灵性，更高的力量，更高的智慧，内心的声音，平衡，调和', '被压抑的直觉，隐藏的动机，肤浅，混乱，认知失调', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/the-high-priestess-meaning-major-arcana-tarot-card-meanings', 'High_Priestess.jpg');
INSERT INTO `tool_tarot_card` VALUES (4, 'The Empress 女皇', '女皇牌描绘了一个坐在宝座上的女人。从她周围丰富的大自然中，我们可以假设这个女人代表了地球母亲的原型，即生育女神。她的世界由金星统治，这意味着在这位女神的恩典下，有完整的爱，和谐，生育和奢华。这位女士本人有一头金色的头发，上面饰有星星，表明她与神秘领域的神圣联系。她穿着代表生育能力的石榴图案长袍，坐在绣有金星标志的垫子上。她被一片迷人的绿色森林所包围，一条河流穿过这片森林，这象征着蓬勃的生命力。女皇牌为抽中她的人带来繁荣和祝福。', '神圣的女性，充足的活力，感官的享受，生育能力，孕育，创造力，美，丰富，自然', '不安全感，霸道，过强的控制欲，疏忽，缺乏成长，缺乏感受能力', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/the-empress-meaning-major-arcana-tarot-card-meanings', 'Empress.jpg');
INSERT INTO `tool_tarot_card` VALUES (5, 'The Emperor 皇帝', '在皇帝塔罗牌中，可以看到一个坚忍的统治者形象。宝座上上面装饰着四只公羊的头，代表他的星座——白羊座。一方面，他手持权杖，代表他的统治；另一方面，他拿着一个球体，象征着他所守护的王国。皇帝的长胡子代表着他丰富的阅历。他身后的荒山，彰显着他的决心，更上一层楼的雄心和领导能力。皇帝用勇气，力量和权威来统治。', '世俗力量，自律，稳定，实用，权威，控制，纪律', '暴君，霸道，死板，固执，缺乏纪律，缺乏自律，缺乏掌控，鲁莽', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/the-emperor-meaning-major-arcana-tarot-card-meanings', 'Emperor.jpg');
INSERT INTO `tool_tarot_card` VALUES (6, 'The Hierophant 教皇', '这张卡片描绘了一个宗教人物，他端坐在教堂的信众前。他穿着三件精心设计的法衣，分别代表三个不同的世界。他的右手正确地举起，表示祝福的手势——这与魔术师举起的手是同一只手。他头戴三重皇冠，象征着身心灵三个层次。另一方面，他带着三重十字架，十字架上的三根横杠被认为代表了圣父，圣子和圣灵。在他的下方，坐着两名侍僧，代表着机构内神圣知识的转移。通过这些追随者，这张牌也代表了通往知识和教育的道路。', '传统，社会规范，教育，知识，信仰', '叛逆，打破常规，不循规蹈矩，新方法，无知', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/the-hierophant-meaning-major-arcana-tarot-card-meanings', 'Hierophant.jpg');
INSERT INTO `tool_tarot_card` VALUES (7, 'The Lovers 恋人', '在恋人牌中，图上的男人和女人正受到上方天使的保护和祝福。这对恋人似乎在他们的家中安全和快乐，这里似乎是伊甸园。女人身后有蛇的果树是对圣经故事的指称，它讲述了人类陷入诱惑，陷入肉体和欲望的境界。这里描绘的天使是拉斐尔，风之天使——他与这张牌所关联的星座相同：双子座。风与心理活动有关，尤其是沟通，这是健康关系的基础。他的祝福似乎给这张牌带来了一种平衡与和谐的感觉，象征着两种对立力量之间在宏大和普遍意义上的联合。', '被祝福的爱，平衡、调和的关系，浪漫，暗示可以开展新的关系或关系的新阶段。', '不和谐、不平衡的关系，关系的停滞，冲突，分离，错误选择，优柔寡断', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/the-lovers-meaning-major-arcana-tarot-card-meanings', 'Lovers.jpg');
INSERT INTO `tool_tarot_card` VALUES (8, 'The Chariot 战车', '战车牌描绘了一个人坐在一辆由两个黑白狮身人面像驾驶的车辆内。整张牌有点天籁之气；这个人物坐在一个由白色星星装饰的蓝色天篷下。在他的肩膀上，他带着新月的标志，代表引导着他的精神因素。他的头上戴着一顶王冠，这意味着他是开悟的，他的意志是纯洁的。在他的胸前印有一个正方形，表示地球的元素，物质世界的元素，这是他和他的行动的基础。狮身人面像以黑色和白色着色，象征着战车夫必须学会控制相互对抗的力量。战车的身后是城堡，这代表着他的初心——他要守护的家园。', '训练有素的心智，前进的力量，守护初心，控制相互对抗的力量，协调冲突，成功', '未解决的冲突，矛盾，情绪的失衡，受到阻碍，忘记初心，忘记要守护的东西。', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/the-chariot-meaning-major-arcana-tarot-card-meanings', 'Chariot.jpg');
INSERT INTO `tool_tarot_card` VALUES (9, 'Strength 力量', '在这张塔罗牌中，你会看到一个女人从容地握着成年狮子的下颚。尽管狮子看起来凶猛而强壮，但女人似乎对它有统治力。令人着迷的是她如何优雅地控制狮子。她镇定自若，这代表着控制和纪律，尤其是在逆境中。她握着狮子的下巴，这也表明她有勇气。她对狮子的控制并不粗暴，而是表现出爱和同情心。群山之上的蓝色背景显示了稳定和稳定带来的那种平静。', '内心平静而柔和的力量，理智的力量，爱的力量，勇气，信心，同情心，自信', '软弱，恐惧，无力感，缺乏自信', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/strength-meaning-major-arcana-tarot-card-meanings', 'Strength.jpg');
INSERT INTO `tool_tarot_card` VALUES (10, 'The Hermit 隐士', '隐士牌描绘了一位老人独自站在山顶，一手拿着灯笼，一手拿着杖。山代表成就、发展和成功。隐士已经获得了很高的精神知识水平，他准备下山，将这种知识传授给每个人。他对自己的目标有着坚定的决心，并且对自己所走的道路有着坚定的认识。在灯笼内，你会看到一颗六角星，它也被称为所罗门的印章。这个符号代表智慧。他持有的权杖描绘了权威和权力。隐士牌中没有明亮的色彩，这反而能让他看见生命中幽微的事物。', '独处，内省，沉思，寻找自我，寻找智慧', '缺乏省思，迷失方向，孤独，回归社会', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/the-hermit-meaning-major-arcana-tarot-card-meanings', 'Hermit.jpg');
INSERT INTO `tool_tarot_card` VALUES (11, 'The Wheel of Fortune 命运之轮', '命运之轮是塔罗牌中最具象征意义的牌之一，充满了意义不同的符号。在牌的中心，有一个巨大的轮子，上面覆盖着深奥的符号。轮子周围四种有不同的生物：天使、鹰、公牛和狮子。它们与十二生肖中的四个固定星座有关——狮子座、金牛座、天蝎座和水瓶座。这四只动物也是基督教传统中四位福音传道者的代表，这也许是它们都长有翅膀的原因。每个生物拿着的书籍代表了传达智慧和自我理解的《托拉》。蛇表示下降到物质世界的行为。在轮子上面骑着一个狮身人面像，轮子下面的生物看起来要么是魔鬼，要么是阿努比斯本人。这两个埃及形象既代表了神和国王的智慧（狮身人面像），也代表了冥界（阿努比斯）。它们永远在循环中旋转，当一个上升时，另一个下降。', '改变，新的开始，命运，时来运转，意外事件', '时机未到，坏运气，当前的课题还未完成，重复', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/the-wheel-of-fortune-meaning-major-arcana-tarot-card-meanings', 'Wheel_of_Fortune.jpg');
INSERT INTO `tool_tarot_card` VALUES (12, 'Justice 正义', '正义牌是真理、公平和法律的象征。当法官坐在椅子上时，她用左手的天平代表直觉应该如何平衡逻辑。她用右手的双刃剑象征着公正。她所戴的王冠上的正方形象征着主持正义所需的清晰思路。在她的身后，有一件紫色的斗篷和直立的灰色柱子。在红色斗篷下面，她露出了一只白鞋的鞋尖。这是在提醒台下众人，她所传达的是他们行动的结果。', '正义，公正，诚实，平衡，法律，真理，承担责任，付出将有公正的回报', '不公正，不诚实，腐败，不平衡，逃避责任', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/justice-meaning-major-arcana-tarot-card-meanings', 'Justice.jpg');
INSERT INTO `tool_tarot_card` VALUES (13, 'The Hanged Man 倒吊人', '这张牌描绘了一个被倒吊着的人，他的脚被吊在世界树上。这棵树扎根于冥界深处，众所周知，它能支撑起天体。人们相信，被吊着的人实际上是遵从他自己的自由意志而安置在那里的。我们相信这一点是因为他脸上的表情很平静。他的右脚被绑在树枝上，但他的左脚仍然完全自由。同时，他的双手在背后握着，形成一个倒三角形。他穿的红色裤子代表着肉体和人类的激情，而他衬衫上的蓝色则代表着平静的情绪，这种颜色组合在圣人身上很常见。他的鞋子、头发和光环的黄色象征着他的智力。', '换个角度看问题，静心省思，等待，牺牲，顺从命运安排，平静从容', '停滞不前，不感兴趣，视角受限，钻牛角尖', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/the-hanged-man-meaning-major-arcana-tarot-card-meanings', 'Hanged_Man.jpg');
INSERT INTO `tool_tarot_card` VALUES (14, 'Death 死亡', '在图上，我们看到死神骑着一匹漂亮的白马，手举着一面绘有白色玫瑰的黑旗。他穿着盔甲，这赋予了他无敌的能力——这表明没有人可以摧毁死亡。他所骑的白马代表着纯洁，因为死亡净化了所有人。在他的身下，所有阶级的人类都躺在泥土中——一个国王和一个贫民，旨在提醒我们死亡不区分阶级、种族和性别。 然而，我们还是可以看到远处冉冉升起的太阳，这代表着重生，带来新的开始和新的希望。', '结局，新生，放手，顺从于改变', '畏惧改变，抵制变化，不肯放手，停滞不前，衰败', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/death-meaning-major-arcana-tarot-card-meanings', 'Death.jpg');
INSERT INTO `tool_tarot_card` VALUES (15, 'Temperance 节制', '在节制牌上，有一个长着翅膀的天使，其性别并不明显，这表明两性之间存在着一种平衡。天使的一只脚在水中，代表潜意识，而另一只脚在地上，代表现实世界。在她的袍子上，有一个正方形，里面刻有一个三角形，是对有形的地球与神圣的三位一体的一种呼应。她拿着两个杯子，在被子里她可以将水混合，这代表了超意识和潜意识的融合。水在它们之间流动，暗示着联合和无限。这张牌的一切都代表着平衡，完美的和谐来自于二元性的结合。她带来的建议是，在跳入深渊之前，先探测任何新的水域。', '平衡，和平，耐心，节制，平静，安宁，和谐，调和，沟通', '偏执，极端，不节制，不和谐，鲁莽，草率', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/temperance-meaning-major-arcana-tarot-card-meanings', 'Temperance.jpg');
INSERT INTO `tool_tarot_card` VALUES (16, 'The Devil 魔鬼', '这张牌中的魔鬼以他最著名的萨梯尔（Satyr）形式现身，也就是所谓的巴弗灭（Baphomet）。除了半羊半人的身躯外，这魔鬼还有蝙蝠的翅膀和额头上的倒五角星。他站在一个基座上，上面拴着裸体的一男一女，这似乎要表明魔鬼对他们有统治权。男人和女人都有角，似乎表明他们与魔鬼相处的时间越长，他们就越不像人。锁链使他们看起来好像被魔鬼俘虏了。男人的尾巴上有一团火焰，而女人的尾巴上有一碗葡萄，这分别象征着他们对权力和生活中美好事物的沉迷。 仔细看，男人和女人看起来都不高兴。他们的个人权力已被夺走，这让他们暴露在赤裸之中并感到羞耻。', '欲望的枷锁，不纯粹、被束缚的关系，窒息或无力感，成瘾，痴迷', '独立，自由，释放，夺回掌控权，理性战胜欲望', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/the-devil-meaning-major-arcana-tarot-card-meanings', 'Devil.jpg');
INSERT INTO `tool_tarot_card` VALUES (17, 'The Tower 高塔', '高塔牌描绘了一个依偎在山顶上的高尖塔。一道闪电击中了塔楼，使其燃烧起来。火焰在窗户上爆开，人们从窗户跳出来，这是一种绝望的行为。他们想逃离内部的动荡和破坏。塔是一个象征，象征着建立在错误前提下的野心。塔的毁灭必须发生，以便清除旧的方式，迎接新的东西。', '突然的、无可避免的改变，破坏性、爆发性的事件，灾难，混乱', '可能表示抗拒改变，拒绝放开你所压抑的东西，但这可能会带来更大的爆发。改变迟早要发生', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/the-tower-meaning-major-arcana-tarot-card-meanings', 'Tower.jpg');
INSERT INTO `tool_tarot_card` VALUES (18, 'The Star 星星', '这张星星牌描绘一个女人跪在一个小池塘的边缘。她拿着两个装水的容器。一个容器将水倒向干涸的土地，似乎是为了滋养它，确保它的肥力。她周围郁郁葱葱的土地似乎在说，它正在发挥作用。一只脚在水里，这显示了这个女人的精神能力和内在力量。另一只脚踩在地上，显示了她的实际能力和力量。在她身后，有一颗中央大星，周围有七颗小星，代表脉轮。有一只鸟站在树枝上，是代表思想和智慧的圣朱鹮。这张牌的星座对应的是水瓶座。', '希望，灵感，宁静，和平，疗愈，创造力，生命力', '失去潜意识的能量，失去保持内在平和和信心的能力，失去创造力和灵感，失去希望', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/the-star-meaning-major-arcana-tarot-card-meanings', 'Star.jpg');
INSERT INTO `tool_tarot_card` VALUES (19, 'The Moon 月亮', '当我们抽到月亮时，我们看到一条通向远方的道路。小路的两边站着一只狼和一只狗，代表我们的兽性——驯化的和未驯化的。有一只小龙虾正从池塘里爬出来，若隐若现的龙虾象征着比恐惧更深层次的面对未知和诡异的不安情绪。在远处，我们可以看到中央小路两侧的两座塔，对立两端的塔楼代表着善与恶的力量，而塔楼相似的外观可能暗示我们在区分善恶时面临的困难。这张牌中的一切似乎都在相互呼应，似乎在暗指两种可能性。当我们走在这条路上时，我们走在有意识和无意识之间，走在狗的文明驯服和狼的自然野性之间的窄道上。', '恐惧，不安，疯狂，错觉，不确定性，混乱。指导你通过梦或是想象探索你的潜意识，正视你的恐惧和不安。', '拒绝对潜意识的探索，对事件的认识停留在表面。隐藏的事物逐渐浮现，因而恐惧、不安的情绪有所减弱。', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/the-moon-meaning-major-arcana-tarot-card-meanings', 'Moon.jpg');
INSERT INTO `tool_tarot_card` VALUES (20, 'The Sun 太阳', '太阳牌呈现出一种乐观和满足的感觉。这张牌代表着在最黑暗的夜晚之后出现的黎明。太阳是我们星球上所有生命的源泉，它代表生命能量本身。牌中有一个孩子，在前景中快乐地玩耍。它是我们纯真的象征，代表着当你与真正的自我保持一致时的快乐。孩子是赤裸的，意味着他完全没有什么可隐藏的。这张牌还描绘了童年的纯真和绝对的纯洁。这一点通过孩子所骑的白马得到了特别强调。这里的马也是力量和高贵的象征。', '光明，幸福，成功，乐观，活力，欢乐，纯真，满足', '过度热情，悲观，不切实际的期望，自负，较小的成功，或成功了仍不满足。可能是乌云暂时遮住了阳光，仍有一些恐惧、怀疑和困难尚未克服。', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/the-sun-meaning-major-arcana-tarot-card-meanings', 'Sun.jpg');
INSERT INTO `tool_tarot_card` VALUES (21, 'Judgement 审判', '这张牌描绘了人们想象中的最终审判在许多神话中采取的各种形式。审判牌中的图像显示，妇女、男子和儿童正从坟墓中站起来，响应加百列的号角声。他们伸出的手臂象征着他们已经准备好接受宇宙的审判。他们即将见到他们的造物主，他们的行为会被评估，并发现他们将在哪里度过余下的永恒：在天堂或在地狱。背景中的巨大潮汐标志着审判是不可避免的，而且这种审判将是最终的。审判是由冥王掌管的，他是冥界的统治者。这张牌与死亡牌交相呼应，因为它们都提醒我们，一切都会结束，新的开始即将到来。', '自我评判，觉醒，更新，清算，清晰的判断力。审判指示你将过去经验做出结论，以此作为超越它的步骤。', '抗拒人生的转变，抗拒命运的召唤。可能做出错误的决定或错过重要的讯息，需多加防范。', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/judgement-meaning-major-arcana-tarot-card-meanings', 'Judgement.jpg');
INSERT INTO `tool_tarot_card` VALUES (22, 'The World 世界', '世界牌中心有一个跳舞的人物。牌上的舞蹈人物一条腿交叉在另一条腿上，两手拿着魔杖。她象征着运动中的平衡和进化。她所代表的圆满和统一不是静态的，而是不断变化的，动态的和永恒的。围绕中心人物的绿色花环是成功的象征，而环绕花环的红色丝带则让人联想到无穷。牌的每个角上都有四个数字——它们与命运之轮中的数字相同。这四个数字代表天蝎座、狮子座、水瓶座和金牛座——代表宇宙的四个角落、四个元素和四个福音传道者。他们一起象征着所有能量的大和谐。', '完成，完满，和谐，发自内心的快乐满足和持久的成功。可能暗示旅行。', '不完满，空虚，不持久的成功，旅行', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/the-world-meaning-major-arcana-tarot-card-meanings', 'World.jpg');
INSERT INTO `tool_tarot_card` VALUES (23, 'The World 世界', '世界牌中心有一个跳舞的人物。牌上的舞蹈人物一条腿交叉在另一条腿上，两手拿着魔杖。她象征着运动中的平衡和进化。她所代表的圆满和统一不是静态的，而是不断变化的，动态的和永恒的。围绕中心人物的绿色花环是成功的象征，而环绕花环的红色丝带则让人联想到无穷。牌的每个角上都有四个数字——它们与命运之轮中的数字相同。这四个数字代表天蝎座、狮子座、水瓶座和金牛座——代表宇宙的四个角落、四个元素和四个福音传道者。他们一起象征着所有能量的大和谐。——它们与命运之轮中的数字相同。这四个数字代表天蝎座、狮子座、水瓶座和金牛座——代表宇宙的四个角落、四个元素和四个福音传道者。他们一起象征着所有能量的大和谐。', '完成，完满，和谐，发自内心的快乐满足和持久的成功。可能暗示旅行。', '不完满，空虚，不持久的成功，旅行', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/the-world-meaning-major-arcana-tarot-card-meanings', 'World.jpg');
INSERT INTO `tool_tarot_card` VALUES (24, 'Ace of Wands 权杖一', '权杖与火的能量有关，而权杖一是权杖牌组中火的核心代表。这张牌描绘的是一只手从云中伸出来，手中拿着一根权杖。当我们仔细看这张牌时，我们可以看到这只手中权杖还在生长。权杖上的一些叶子已经发芽，这意味着代表精神和物质的平衡和进步。远处是一座城堡，象征着未来的机会。', '灵感，创意，新计划，新激情，热情，活力', '延缓，阻碍，缺乏激情，缺乏活力，犹豫不决，灵感枯竭', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/ace-of-wands-meaning-tarot-card-meanings', 'Wands01.jpg');
INSERT INTO `tool_tarot_card` VALUES (25, 'Two of Wands 权杖二', '权杖二的图景是一个男人站在一座城堡的顶部，右手拿着一个微型地球仪。从他站立的方式来看，这个人俯视着广阔的地形，左侧是海洋，右侧是陆地。男子身穿橙色束腰外衣，头戴红色帽子，分别象征着他对生活本身的热情和对冒险的渴望。他手中的微型地球仪代表着他去探索更广阔的生活的可能性。', '制定计划，迈出第一步，做出决定，离开舒适区，承担风险', '糟糕的计划，过度分析，不采取行动，谨慎行事，规避风险', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/two-of-wands-meaning-tarot-card-meanings', 'Wands02.jpg');
INSERT INTO `tool_tarot_card` VALUES (26, 'Three of Wands 权杖三', '权杖三描绘了一个站在悬崖边上的人，俯瞰大海和山脉。从悬崖边上，他看清了他前方的一切。权杖被种植于他身旁的土地，他手握着其中一根。他似乎在展望未来，并思考他对自己计划的承诺，以及执行的方法，以便把它们变成现实。', '前进的势头，信心，扩张，增长，远见，向前看，新的阶段，将计划付诸实践', '限制，局限，缺乏进展，障碍，延误，沮丧，反思', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/three-of-wands-meaning-tarot-card-meanings', 'Wands03.jpg');
INSERT INTO `tool_tarot_card` VALUES (27, 'Four of Wands 权杖四', '权杖四描绘了一对夫妇在欢迎花环下跳舞，花环被绑在在四根权杖之间。似乎有一个正在进行的聚会，或者为这对夫妇举行的某种欢迎仪式。你可以从图上看到，这张牌代表了一个充满了成就感的时刻，一个因为目标真正实现而带来满足的时刻。', '社群，家庭，庆祝活动，团聚，聚会，稳定，归属感', '缺乏支持，不稳定，不受欢迎，短暂性，缺乏根基，家庭冲突', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/four-of-wands-meaning-tarot-card-meanings', 'Wands04.jpg');
INSERT INTO `tool_tarot_card` VALUES (28, 'Five of Wands 权杖五', '权杖五描绘了五个人都拿着自己的权杖，在空中挥舞。就像塔罗牌中的大多数五一样，它是关于问卜者所面临的某种分歧。这种分歧可以被认为是这些人之间冲突的征兆，也可以认为他们所有人都只是在享受这种冲突带来的肾上腺素刺激。他们之间的竞争是一种善意的竞争，而不是愤怒。他们拿着权杖的轻松方式让人感觉这场争论可能只是一场表演，而不是一场激烈的斗争。', '冲突，竞争，争论，侵略性，紧张，对手，自我冲突', '结束冲突，合作，协议，休战，避免冲突；也可能是不公平的竞争（欺诈、作弊等）', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/five-of-wands-meaning-tarot-card-meanings', 'Wands05.jpg');
INSERT INTO `tool_tarot_card` VALUES (29, 'Six of Wands 权杖六', '权杖六描绘了一个男人，他头戴胜利的花环，骑马穿过一群正在欢呼的人。这匹马是白色的，这是众所周知的力量、纯洁以及成功的象征。欢呼的群众表示大家对骑马人成就的认可。该男子携带的权杖上还系着一个花环，进一步强调他的成功。该男子对所有这些关注并不害怕，也不害羞，而是为自己的成就感到自豪。对此，他周围的人群做出了欢快和热情的反应。', '自信，成功，胜利，凯旋，好消息，好的进展，认可，赞美', '失败，缺乏认可，缺乏成就感，过度骄傲自满', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/six-of-wands-meaning-tarot-card-meanings', 'Wands06.jpg');
INSERT INTO `tool_tarot_card` VALUES (30, 'Seven of Wands 权杖七', '权杖七描绘了一个人的形象，他站在高高的山上，受到下面对手的挑战。他似乎在捍卫这个位置，并进行报复性的攻击。有趣的是，在权杖七的描绘中，这个人穿的两只鞋不是同一双。这与牌面上的其它细节互相呼应，例如不平整的地面，以及主角没有一个稳定的立足点。', '面临挑战，遭到反对，冲突对立的局面，自卫反击', '陷入焦虑，优柔寡断，没用勇气面对挑战，不利的局势，放弃，承认失败', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/seven-of-wands-meaning-tarot-card-meanings', 'Wands07.jpg');
INSERT INTO `tool_tarot_card` VALUES (31, 'Eight of Wands 权杖八', '权杖八描绘了八根飞行的权杖，看起来好像它们悬浮在空中。他们飞行的背景是晴朗的天空，这代表着他们到达目的地的路上可能没有任何障碍。这张牌展现了壮丽的景色，河水在流淌，水给画面带来了生命力。权杖看起来似乎即将降落，这标志着漫长旅程的结束。', '迅速，自由，旅行，自由流动、不受约束的能量', '进展过于迅速而失控，准备不充分；欲速则不达，进展受阻，延误，等待', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/eight-of-wands-meaning-tarot-card-meanings', 'Wands08.jpg');
INSERT INTO `tool_tarot_card` VALUES (32, 'Nine of Wands 权杖九', '权杖九显示一个看起来很虚弱的人抓着一根魔杖，后面竖着另外8根魔杖。这个人看起来受伤了，但他似乎仍然准备好了再战，并且强烈地渴望胜利。他的脸上有一种希望和决心，应该可以帮助他完成最后一场战斗。这是一张显示挑战、希望和胜利的混合牌。', '最后一搏，坚持，勇气，韧性，毅力，接近成功，疲劳', '固执，死板，防御，拒绝妥协，放弃', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/nine-of-wands-meaning-tarot-card-meanings', 'Wands09.jpg');
INSERT INTO `tool_tarot_card` VALUES (33, 'Ten of Wands 权杖十', '画面中，一名男子背着一捆重重的木头，十根捆成一捆的权杖，向不远处的房子走去。权杖十上的这个形象，表明一个人已经在生活中奋斗并取得了收获，他现在正带着收成到达他的最终目的地。此时他承受着巨大的压力，因此显得疲累万分。但不远处的房子表示终点已并不遥远，一步一个脚印向前就能成功。', '负担，责任，职责，压力，义务，精疲力竭，挣扎，奋斗', '承担过多的责任，压力过大，崩溃', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/ten-of-wands-meaning-tarot-card-meanings', 'Wands10.jpg');
INSERT INTO `tool_tarot_card` VALUES (34, 'Page of Wands 权杖侍卫', '一个衣着光鲜的年轻人大胆地拿着一根权杖站在一片荒芜的土地上。他的衣衫上有一个像蝾螈一样的图案，这是一个从坏到好的转变的象征。这个年轻人是一个热情的倡导者，他不断地传播精神和社会进步的理念，提升他的同伴。他身后背景中的沙漠既表示他的元素——火，也代表他生活在一个还没有结出果实的世界。因此，他的想法完全是假设性的。然而，如果他选择实践这些想法，并开始他的旅程，他纯洁的心可以使他找到更好的财富。', '冒险，兴奋，新鲜的想法，开朗，热情，活泼，天真，坦率，精力充沛，无所畏惧，性格外向', '三分钟热度，顽皮，缺乏想法，懒惰，无聊', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/page-of-wands-meaning-tarot-card-meanings', 'Wands11.jpg');
INSERT INTO `tool_tarot_card` VALUES (35, 'Knight of Wands 权杖骑士', '权杖骑士骑在他的马上，马匹扬起头颅，准备好了行动。从骑士的衣服和盔甲，以及他的马来看，他已经为未来做好了准备。他在金属盔甲上穿着一件黄色图案的衬衫。他还戴着一个头盔，上面插着红色的羽翼。他似乎已经做好了战斗的准备，只是他拿着一根大权杖而不是一把剑。他的马是橙色的，鬃毛看起来像火焰。当你看着权杖骑士的脸时，你会看到想要在他的努力中取得成功的决心。火是权杖骑士象征意义的主要因素。挂在他手臂和背上的装饰性流苏，还有马鬃，都是火焰的颜色。他还穿着一件印有火蝾螈符号的黄色衬衫。', '勇敢的，迷人的，英雄气概的，自由的精神，性格火热、充满活力的行动派', '傲慢的，鲁莽的，没有耐心的，缺乏自我控制，消极，易怒，霸道', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/knight-of-wands-meaning-tarot-card-meanings', 'Wands12.jpg');
INSERT INTO `tool_tarot_card` VALUES (36, 'Queen of Wands 权杖王后', '权杖王后所描绘的图像显示，一位王后骄傲地坐在宝座上，同时面向前方，这显然是力量和火的象征。她的左手拿着一朵向日葵，王座上也雕刻着向日葵的形象——这意味着幸福、满足和肥沃。她的右手拿着发芽的权杖，象征着生命。在她的积极示人的一面，权杖王后可以与忠诚、寄托和温暖联系在一起。她脚下的黑猫暗示了她隐秘的一面。黑猫在传统上是巫术和神秘主义的象征，但也可以指出她运用深刻直觉来把握的能力。', '自信，热情，坚定，有魅力，阳光开朗，乐观，富有灵感，直觉灵敏，强大的内心，丰收，财运，', '专横，善妒，欺软怕硬，不够自信，控制欲，内心力量的匮乏', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/queen-of-wands-meaning-tarot-card-meanings', 'Wands13.jpg');
INSERT INTO `tool_tarot_card` VALUES (37, 'King of Wands 权杖国王', '权杖国王描绘了一个人，他的手中握着一根抽芽的权杖，这代表着创造力和生活的激情。他的宝座以及他的斗篷上都装饰着火蝾螈和狮子，它们是火和力量的象征。咬着他尾巴的蝾螈代表着无限以及穿越所有障碍的动力。他的袍子是明亮的橙色，类似于火焰，而皇冠的形状像火舌。这些都表明这位国王富有能量和威权。', '领导力，远见，大局观，控制，大胆的决定，野心，干劲，创造力', '一个可能是暴虐也可能萎靡的人，但终究是因为自己内心能量不足，无法掌控身边的事物。', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/king-of-wands-meaning-tarot-card-meanings', 'Wands14.jpg');
INSERT INTO `tool_tarot_card` VALUES (38, 'Ace of Cups 圣杯一', '在圣杯一中，有一只手从云中伸出来，拿着一个杯子，似乎是在向问卜者提议喝水。这只杯子里溢出了闪闪发光的水，象征着纯洁和质朴的情感。如果你接受这个提议，喝下这杯水肯定会给你带来情感和精神上的满足。杯子中有五条水流从杯口溢出。这五条水流代表了直觉的力量，表明倾听你内心声音的重要性。为了获得圣杯王牌所描绘的那种情感或精神上的满足，问卜者需要遵循这种内在的声音，并且无论在什么情况下，都要忠实于它。这需要情绪上的约束，但是这张牌本身就暗示了它所能带来的回报：洋溢的快乐和幸福。', '情感上的满足，新情感的开端，创造力，灵性，直觉，收获快乐、爱和喜悦', '冷漠，空虚，情感挫折，创造力受阻，感觉不到被爱，心情阴郁', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/ace-of-cups-meaning-tarot-card-meanings', 'Cups01.jpg');
INSERT INTO `tool_tarot_card` VALUES (39, 'Two of Cups 圣杯二', '这张牌显示了一个男人和一个女人的形象，他们正在进行某种交换杯子的仪式。中间有赫尔墨斯的节杖标志，这通常与谈判、贸易、宇宙能量、保护、适当的行为和双重性有关。圣杯二想要揭示的是相当积极的事情——它是塔罗牌中最吉利的牌之一，适合恋爱、商业或其他关系。它暗示着一种新的合作关系正在酝酿之中，它将建立在平衡、尊重和荣誉的前提下。在节杖上方是一只奇美拉，象征着火和激情也影响着这种伙伴关系。', '团结，伙伴关系，恋爱，相互吸引，联系，联合，相互尊重', '关系中不和谐的因素，不平等的关系，沟通不畅，分离、分手', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/two-of-cups-meaning-tarot-card-meanings', 'Cups02.jpg');
INSERT INTO `tool_tarot_card` VALUES (40, 'Three of Cups 圣杯三', '当你抽到圣杯三，你会看到三位女士将她们的杯子举向天空，进行某种庆祝。可以看出空气中洋溢着美好的氛围，因为这三位女士的头发上戴着花环。花环通常与成功和胜利有关，所以她们的幸福是显而易见的。这三位女士还站在一圈鲜花和水果的之中，进一步增加了这张牌的欢快气氛。可以想见，女士之间正互相分享着美好、欢快的感受。', '友谊，团聚，聚会，庆祝活动', '流言蜚语，丑闻，孤立，孤独，不和谐、不稳定的团队，合作失败，友谊破裂', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/three-of-cups-meaning-tarot-card-meanings', 'Cups03.jpg');
INSERT INTO `tool_tarot_card` VALUES (41, 'Four of Cups 圣杯四', '圣杯四显示一个年轻人坐在山顶的一棵树下，远离他人。他似乎在沉思和冥想。在他面前有三只圣杯躺在草地上，而空中的一只手给他拿来了剩下的一只圣杯。这个人手脚交叉，低头看着这三只圣杯，不为所动，以至于他看不到那只伸过来的手递给他的圣杯。圣杯四代表我们倾向于认为我们所拥有的东西是理所当然的，使我们很难看到宇宙呈现给我们的宝藏。我们倾向于在我们身边找到解决烦恼的答案，但过度关注我们所缺少的东西。', '冷漠，沉思，失去连结，忧郁，无聊，拒绝，漠不关心，失落', '清醒，觉察，接受，重燃热情', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/four-of-cups-meaning-tarot-card-meanings', 'Cups04.jpg');
INSERT INTO `tool_tarot_card` VALUES (42, 'Five of Cups 圣杯五', '有些塔罗牌会让人立刻联想到负面情绪，而圣杯五就有这样的效果。这张牌象征着损失，以及由这种特定损失带来的痛苦挑战。这张牌描绘了一个穿着黑色斗篷的人物。这个人把自己的脸藏起来，似乎很绝望的样子。地上有五个杯子，其中三个已经倒下，另外两个仍然站着。然而，这个人似乎没有注意到有两个站着的杯子，因为他正忙着为那些倒下的杯子感到哀伤。有一条汹涌的河流在他和远处的城堡之间流淌，表明情感的洪流已经把他和家分开。', '悲伤、失落、失望。但提醒我们意识到身后仍有退路（支持、希望）。', '当事人意识到自己仍保有的部分，从悲伤中走出来投入新的生活；重拾信心，看见积极的方面；知足常乐', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/five-of-cups-meaning-tarot-card-meanings', 'Cups05.jpg');
INSERT INTO `tool_tarot_card` VALUES (43, 'Six of Cups 圣杯六', '圣杯六象征着怀旧的乐趣，家庭的舒适和童年的纯真。牌中有六个杯子，里面都装着白花。前景中描绘了两个孩子，其中一个正把杯子递给另一个。男孩把花递给女孩，表明记忆的传递和快乐的团聚。孩子们似乎在一个类似于城堡的地方，可以想见这会给他们一种安全感和舒适感。', '童年，怀旧，回忆，思乡，照顾、安全和保障', '沉溺于过去，不安，被抛弃、被忽视，向前看，离家，独立', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/six-of-cups-meaning-tarot-card-meanings', 'Cups06.jpg');
INSERT INTO `tool_tarot_card` VALUES (44, 'Seven of Cups 圣杯七', '圣杯七象征着想象力、选择、一厢情愿的想法、幻觉和幻想。这张牌显示人背对着我们，沉思着从杯子里浮现出来的7种图景，这些图景都漂浮在云端。云是梦想、幻觉和想象力的代表。有许多幻想正从杯子里出现，这代表了人们在做梦时看到的许多幻象。圣杯七可能意味着你有很多选择可以选择。', '幻象，梦境，不切实际的幻想，许多选择，迷茫', '缺乏目标，混乱，分心；云开雾散，看清内心，做出选择，梦想成真', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/seven-of-cups-meaning-tarot-card-meanings', 'Cups07.jpg');
INSERT INTO `tool_tarot_card` VALUES (45, 'Eight of Cups 圣杯八', '在圣杯八中，我们面临着过渡的时刻。我们看到一个披着斗篷的人走到一片荒芜的土地上，留下了八个金杯。他已经厌倦了那些他花了很多时间收集的杯子，现在正着手寻求一个更高的目标。这可能是由于意识到他在生活中一直在寻找的东西并不像他曾经想象的那样令人愉快或满意而感到无聊或不快。进行这次旅行的人也可能象征着在未知的地方寻求刺激。他所前往的荒山野岭，也可能象征着要面临的新的挑战。它们的空旷似乎表明，它们在等待一个人的到来和塑造。他可能是为了尝试新事物而进行旅行，这些新事物有可能帮助一个人在精神和心灵上成长。这张牌还表明愿意将自己与他人分离开来，以便自己能够致力于自我完善、自我理解和成长。', '抛弃，放手，跳脱出旧有的功绩（或束缚），追寻更高的境界', '停滞不前，单调乏味，拒绝突破，拒绝离开现有的环境', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/eight-of-cups-meaning-tarot-card-meanings', 'Cups08.jpg');
INSERT INTO `tool_tarot_card` VALUES (46, 'Nine of Cups 圣杯九', '在这张牌中，一个中年男子坐在木椅上，双臂交叉，脸上流露出相当程度的满足感。他穿着优雅，有一种真正的满富足。他头上的头饰是红色的，是他活跃思维的写照。在他的背后，你会发现有九个排列整齐有序的金杯。圣杯九象征着精神和物质上的满足和成功。这个人在实现他内心深处的愿望后，显出了成功的表情。', '荣耀，成功，梦想成真，成就感，满足，享乐，幸福圆满', '不快乐，缺乏成就感，失望；可能期望通过世俗的享乐或成功来弥补内心的空虚或是爱的缺失，然而并不会成功。', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/nine-of-cups-meaning-tarot-card-meanings', 'Cups09.jpg');
INSERT INTO `tool_tarot_card` VALUES (47, 'Ten of Cups 圣杯十', '在圣杯十的画面上，有一对夫妇搂着彼此，面前是美丽的房屋和绿色的原野。在他们旁边，有两个孩子在欢乐地玩耍。这对夫妇以一种充满爱和浪漫的方式拥抱着彼此，而孩子们在自由地玩耍。这对夫妇似乎关系稳定，同时也得到了舒适的家和美丽的孩子。绿色的土地象征着肥沃，而河流显示了这对夫妇的感情是多么自由地流淌，这意味着他们的关系是相当和谐的。在天上，有十个杯子摆成弧形，意为来自天堂的祝福。他们身后的彩虹显示了艰苦岁月和悲伤的结束，以及新的幸福生活的到来，每个家庭成员都将享受其中。圣杯十的牌描绘了“拥有一切”。', '幸福，归家，团圆，家庭和睦', '家庭不和，关系不和；或是个人脱离团体、离群索居、拒绝他人的爱', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/ten-of-cups-meaning-tarot-card-meanings', 'Cups10.jpg');
INSERT INTO `tool_tarot_card` VALUES (48, 'Page of Cups 圣杯侍卫', '圣杯侍卫代表的是来自无意识的意外灵感，也许是通过我们可能还无法真正理解的方式。在这张牌中，一个在海边的青年穿着一件蓝色的外衣，上面装饰着花纹，拿着一个金杯。她还戴着一顶看起来很波西米亚的贝雷帽，以及一条飘逸的长围巾。她被一条从杯子里跳出来的鱼吓了一跳，这条鱼仿佛是从童话故事中跳出来迎接她。她既不能喝酒，也不能用杯子敬酒，因为那条鱼盯着她，似乎想和她打交道。', '理想主义，情感细腻，好奇，想象力丰富，天真、纯真、童真', '情感脆弱，不成熟，天马行空而不切实际；因缺乏安全感而过度提防，拒绝他人善意', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/page-of-cups-meaning-tarot-card-meanings', 'Cups11.jpg');
INSERT INTO `tool_tarot_card` VALUES (49, 'Knight of Cups 圣杯骑士', '圣杯骑士这张牌描绘了一个年轻的骑士，他光荣地骑着一匹白马，同时拿着一个杯子，好像他是某种信使。与权杖骑士或宝剑骑士不同的是，这位骑士并没有骑着马冲锋陷阵。相反，他正缓慢地向前移动，这为整个环境提供了一个平和的整体印象。众所周知，马代表着一个人的动力、能量和权力。马的颜色是白色，这是一个精神、光明和纯洁的象征。', '理想主义者，迷人，艺术，优雅，梦想家，浪漫', '失望，发脾气，情绪化，虚荣，花心，不切实际', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/knight-of-cups-meaning-tarot-card-meanings', 'Cups12.jpg');
INSERT INTO `tool_tarot_card` VALUES (50, 'Queen of Cups 圣杯王后', '圣杯王后掌管情感领域。她的宝座就在海的边缘，而水通常指向无意识和感觉的领域。她在岸边的位置表明她位于陆地和海洋之间，是感觉和思想交融的地方。她拿着一个杯子，杯子的把手形状像一个天使。这个杯子是封闭的，这就是为什么圣杯皇后象征着我们无意识的思想。王后独自坐着，这使她可以思考。水和天空的平静象征着王后宁静的心灵。她的脚没有接触到水，这代表她从外面（也即客观的角度）审视着自己的想法和感受。', '怜悯，温暖，仁慈，敏感细腻，直觉，疗愈', '不安全感，付出太多，过度敏感，情绪化，脆弱，依赖，殉道者', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/queen-of-cups-meaning-tarot-card-meanings', 'Cups13.jpg');
INSERT INTO `tool_tarot_card` VALUES (51, 'King of Cups 圣杯国王', '圣杯国王是塔罗牌中代表慷慨、控制和情绪平衡的一张牌。这张牌本身描绘了一个坐在宝座上的国王，他有一个鱼状的护身符，代表他的创造力和精神在他周围平静的水中茁壮成长。我们可以从背景中看到，在意识和无意识之间存在着稳定的平衡。在国王身后，右手边是一条跳出海面的鱼，左边是一艘船，分别代表情感和物质世界。圣杯国王表明你没有压抑你的冲动，而是学会了以平衡调和的方式处理它们。', '慈爱，温和体贴，宽容，慷慨，奉献，情感成熟，智慧，富有耐心', '不知所措，焦虑，冷漠，压抑，退缩，控制欲强，自私', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/king-of-cups-meaning-tarot-card-meanings', 'Cups14.jpg');
INSERT INTO `tool_tarot_card` VALUES (52, 'Ace of Swords 宝剑一', '宝剑一显示一只手从云中伸出，挥舞着一把双刃剑。这把双刃剑带着一顶金皇冠，同时被花环所环绕。长期以来，花环一直与胜利、成功和伟大的成就有关。皇冠被用作皇室和皇室所带来的统治权的象征。宝剑的背后是山和海洋，它们被用来象征剑可以用来征服的广阔范围和遥远土地，以及雄心。', '广阔的视野，力量，专注，决心，真理，突破', '敌意，争吵，伤害，破坏，野蛮，混乱', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/ace-of-swords-meaning-tarot-card-meanings', 'Swords01.jpg');
INSERT INTO `tool_tarot_card` VALUES (53, 'Two of Swords 宝剑二', '宝剑二象征着我们在被迫做出艰难选择时面临的困惑。在宝剑二牌中，有一个坐着的女人，她被蒙着眼睛，双手各拿着一把剑。在背景中，有一个大海，周围有峭壁和岩石，作为船只和船舶的障碍，阻碍了前进和行动。牌中被蒙住眼睛的女人代表了一种情况，这种情况使她无法清晰地看到问题和解决方案。她两手握着的剑表明有两种选择，分别通向不同的方向，而且是相互排斥的。这也可能描绘了一个僵局，这意味着应该用逻辑和理性的思维来解决这个问题。月亮位于牌的右边，表示可能象征着幻想和欺骗为这位女士做出选择所带来的困难。', '僵局，困难的选择，逃避，抗拒，被蒙蔽', '犹豫不决，焦虑不安，信息太多，没有正确的选择；打破僵局，真相大白', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/two-of-swords-meaning-tarot-card-meanings', 'Swords02.jpg');
INSERT INTO `tool_tarot_card` VALUES (54, 'Three of Swords 宝剑三', '作为塔罗牌中最有代表性的图像之一，宝剑三展示了一颗漂浮在空中的心被三把剑刺穿。背景中阴云密布，下着倾盆大雨。牌的象征意义是不明确的，但它的情感效果是相当直接的。心脏是温暖的，是情感和精神的所在；而三把剑表示伤害的力量，它们给这颗心脏造成痛苦。这是一个悲痛、损伤和心碎的形象。云和雨描绘了周围严峻的形势。所有这些符号都表明宝剑三显示了一个人生命中的低谷。', '心碎，分离，悲伤，孤立，损失，创伤，泪水', '愈合，宽恕，恢复，和解；压抑的情绪，封闭自己', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/three-of-swords-meaning-tarot-card-meanings', 'Swords03.jpg');
INSERT INTO `tool_tarot_card` VALUES (55, 'Four of Swords 宝剑四', '宝剑四中有一种安静的样态，一种与宝剑三中的痛苦形成强烈对比的平静的和平感。在一个教堂里，有一位骑士的雕塑，他躺在坟墓上，上面挂着三把剑，而第四把剑放在他的下方。这三把剑是提醒他在宝剑三中所承受的痛苦。第四把剑的位置似乎是一个信号，表明战斗已经结束。雕像后面的彩色玻璃处描绘了一个孩子和一个女人，让人感觉到撤退后的温暖和欢迎的场景。骑士的双手合十，仿佛在祈祷。', '休养，放松，和平，避难所，自我保护，恢复活力', '恢复，觉醒，重新投入生活，积极入世；倦怠，不安', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/four-of-swords-meaning-tarot-card-meanings', 'Swords04.jpg');
INSERT INTO `tool_tarot_card` VALUES (56, 'Five of Swords 宝剑五', '宝剑五这张牌描绘了一个年轻人，他的脸上有一种明显的轻蔑。他正看着自己成功征服的敌人。他拥有五把剑——这是他从牌上的其他人那里夺来的。另外两个人物正以缓慢的方式从他身边走过，带着一种失落和悲伤的感觉。在他们上方，天空波澜不惊，阴云密布，这表明尽管战斗已经结束，但并非一切顺利。', '争论，纠纷，攻击，欺凌，恐吓，冲突，敌意，自私', '和解，妥协，报复，失败，遗憾，悔恨，及时止损', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/five-of-swords-meaning-tarot-card-meanings', 'Swords05.jpg');
INSERT INTO `tool_tarot_card` VALUES (57, 'Six of Swords 宝剑六', '在宝剑六的牌面上，船夫将载有女人和孩子的船划向彼岸。我们可以从图中猜测，这个女人和孩子离去时一定将某些东西抛在了身后，因为他们的背对着我们。女人的头被斗篷遮住了——也许她在逃离什么，而且必须在别人不知道她真实身份的情况下离开。正因如此，她正经历着巨大的损失或悲伤。宝剑六的象征意义是指损失或变化，以及走向比我们离开的那个未来更有希望的未来。船边的六把剑象征着理性思维的强大力量，而不是直觉和心灵。', '继续向前，离去，离开舒适圈，接受教训', '沉溺于过去，不愿改变，逃避问题，被困住', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/six-of-swords-meaning-tarot-card-meanings', 'Swords06.jpg');
INSERT INTO `tool_tarot_card` VALUES (58, 'Seven of Swords 宝剑七', '在宝剑七牌上，有一个人从某个营地偷偷跑出来，手里拿着五把剑。在他身后，还有两把剑立在地上。从这张牌中，我们可以推断出，宝剑七是关于隐秘的，逃脱的，欺骗或背叛的。这个人脸上的表情显示出相当大的自信，他似乎确信自己偷剑的任务一定会成功。但不幸的是，左边似乎有一群士兵发现了这起盗窃案，其中一名士兵实际上正在举起旗帜或武器，似乎在向他发出追击的信号。', '谎言，诡计，阴谋，战略，可能失败的计划，阴险，狡猾', '忏悔，良心发现，合作商讨', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/seven-of-swords-meaning-tarot-card-meanings', 'Swords07.jpg');
INSERT INTO `tool_tarot_card` VALUES (59, 'Eight of Swords 宝剑八', '在宝剑八上有一个被捆绑和蒙上眼睛的女人。有八把剑战略性地放在她周围，限制了她的行动，好像她在某种陷阱或监狱里。然而，似乎建造陷阱的人有点懒惰或草率，留下了一些她可以逃脱的空隙。但是，由于蒙住了眼睛，使这个女人看不见，她没有办法找到走出这个陷阱的方法。她周围的贫瘠土地可能意味着缺乏某种创造力，而背景中的灰色天空可能意味着绝望，因为在那里她觉得她没有希望挣脱她被束缚的那种处境。只要她能摘掉眼罩，她就会发现她可以轻松走出这种状况。', '被困，被限制，被陷害，无助，被蒙蔽，看不到可能的出路', '自由，释放，觉醒，掌控局面，幸存', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/eight-of-swords-meaning-tarot-card-meanings', 'Swords08.jpg');
INSERT INTO `tool_tarot_card` VALUES (60, 'Nine of Swords 宝剑九', '宝剑九描绘了一个双手捂着脸坐在床上的女人。这个女人似乎刚刚从一个非常糟糕的噩梦中醒来。她似乎很不安、焦虑和恐惧。她的墙上挂着九把剑，在她的床上可以看到一个雕刻，描绘了一个被另一个人打败的人。被子上装饰着各种占星术符号和玫瑰花。在这里，我们看到的是先前在宝剑八中看到的那个女人——也许，她终于被释放了，但却被她被抛弃和遗弃时所承受的痛苦的噩梦所困扰。', '恐惧，焦虑，消极，崩溃，绝望，噩梦', '噩梦结束，恢复精神，重拾信心，学会应对恐惧', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/nine-of-swords-meaning-tarot-card-meanings', 'Swords09.jpg');
INSERT INTO `tool_tarot_card` VALUES (61, 'Ten of Swords 宝剑十', '一张最醒目和不祥的牌。宝剑十显示一个人平躺在地上，脸朝向泥土。他从腰部到腿部都被一块红布所覆盖。十把长剑刺入他的背部，他可能没有看到这个结局。空气中弥漫着可怕的寂静：他头顶上的天空是黑色的，阴云密布，表明与死亡有关的恐惧和消极情绪。他面前的水是静止的，没有任何涟漪——为这张牌增添了阴森的静止和终局气质。向东方的地平线看去，太阳正在升起，尽管黑暗，但天气似乎非常平静。宝剑十似乎暗示这是一个人生命中的最低谷，不可能比这更糟。但至少，即使在这种状态下，太阳依然缓缓升起。', '毁灭，失败，苦涩，崩溃，受害，背叛', '幸存，改善，痊愈，吸取经验教训', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/ten-of-swords-meaning-tarot-card-meanings', 'Swords10.jpg');
INSERT INTO `tool_tarot_card` VALUES (62, 'Page of Swords 宝剑侍卫', '宝剑侍卫牌的出现代表一个人在生活中表现出丰富的能量。在风和云朵的簇拥中，一个青年骄傲地站在岩石的悬崖上。这个精力充沛的青年手中拿着一把剑。他的表情是坚定的，也许还有一点挑衅的意味——他似乎准备好了一言不合就扑上去。', '好奇，机智，思维敏捷，健谈，善于沟通，灵感，警觉', '散漫，愤世嫉俗，讽刺，流言蜚语，侮辱，粗鲁，缺乏规划', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/page-of-swords-meaning-tarot-card-meanings', 'Swords11.jpg');
INSERT INTO `tool_tarot_card` VALUES (63, 'Knight of Swords 宝剑骑士', '我们看到的这位骑士是多么的匆忙啊！宝剑骑士描绘了一个身穿盔甲的年轻人，骑着一匹强壮的白马进入战斗之中。他的白马指向整体的纯洁和智慧的能量，激励着这位年轻的骑手。他身后的天空有暴风雨般的云彩，以及被强风吹得歪倒的树木。马的马具上也装饰着鸟和蝴蝶的图像。骑士的披风上也有这些鸟的装饰。这都突出了他的风属性。', '自信，直接，急性子，聪明，大胆，专注，完美主义，雄心勃勃', '粗鲁，蛮横，欺凌，侵略性，牙尖嘴利，恶毒，无情，傲慢', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/knight-of-swords-meaning-tarot-card-meanings', 'Swords12.jpg');
INSERT INTO `tool_tarot_card` VALUES (64, 'Queen of Swords 宝剑王后', '宝剑王后坐在高高的宝座上，带着严厉的表情向远方眺望。作为宝剑所代表的风元素的王后，她在云中的位置表明没有人可以欺骗或愚弄她。她右手中的剑指向天空，而左手则伸出来，像是在施予什么。宝剑王后给了我们判断日常决策的天赋，并具有吸取他人经验的灵活性。', '诚实，独立，有原则，公平，冷静理智，批评建议，客观，敏锐，洞察力，决断力', '悲观，苛刻，痛苦，铁石心肠，讥讽，残忍，孤独，分离', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/queen-of-swords-meaning-tarot-card-meanings', 'Swords13.jpg');
INSERT INTO `tool_tarot_card` VALUES (65, 'King of Swords 宝剑国王', '宝剑国王显示一个坐在宝座上的国王，而他的右手拿着一把向上的双刃剑。宝剑国王散发出智慧的力量、清晰的思维和真理和权威。他明白权力意味着巨大的责任。国王穿的蓝色外衣是他精神理解的象征。他的宝座后面的蝴蝶是表示转变。', '理智，权威，纪律，律法，诚信，道德，严肃，严格', '不理智，独裁，压迫，不人道，控制，冷酷，不诚实', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/king-of-swords-meaning-tarot-card-meanings', 'Swords14.jpg');
INSERT INTO `tool_tarot_card` VALUES (66, 'Ace of Pentacles 星币一', '在这张牌中，有一只神秘的手从云中伸出来，拿着一个看起来像金币的东西，表面刻着一个五角星。这个五角星与土元素有关，被视为财富和所有物质、世俗事物的标志。手的下方是一个花园，里面似乎正盛开着鲜花和其他种类的植物——散发出肥沃、成长和繁荣的气息。远处的山代表了推动人们寻找星币的雄心壮志。', '新机会，资源，财富，丰富，繁荣，安全，稳定', '错失机会，稀缺，匮乏，不稳定，吝啬，投资失败，亏空，支出', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/ace-of-pentacles-meaning-tarot-card-meanings', 'Pents01.jpg');
INSERT INTO `tool_tarot_card` VALUES (67, 'Two of Pentacles 星币二', '星币二牌描绘了一个正在跳舞的人，他手中玩弄着两个大金币。星币的周围有一个无限的标志，表示这个人可以处理所有问题，他可以优雅地掌控自己的生活。在背景中，你会注意到有两艘船正在乘着巨浪，它们强调了这个人维持平衡的难度——他在波涛汹涌的水域中，但必须设法保持平衡。星币二描绘了一个人生活中通常发生的起伏。尽管周围一片混乱，这个人却过着非常无忧无虑的生活，因为他在跳舞，并快乐地处理所有来到他身边的事情。', '平衡，适应，足智多谋，灵活，娱乐', '失衡，无组织，不知所措，混乱，古板僵化，不懂变通', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/two-of-pentacles-meaning-tarot-card-meanings', 'Pents02.jpg');
INSERT INTO `tool_tarot_card` VALUES (68, 'Three of Pentacles 星币三', '星币三描绘一个在大教堂工作的年轻学徒。在他面前有两个人，一个是牧师，一个是某位贵族，他们正拿着羊皮纸上的大教堂建筑图稿。我们可以从学徒抬头的方式看出，他正在介绍他建造大教堂的进展。另外两个人正仔细地听他说话，以便更清楚地了解需要他们做什么，以及他们如何提供指导。尽管学徒的经验较少，但其他两个人都听他的，因为他们知道学徒的想法和经验是成功完成整个建筑的根本。因此，星币三代表了不同种类的知识聚集在一起，以便共同造就一些东西。', '团队合作，共同目标，协作，学徒制，计划，知识和经验', '缺乏凝聚力，缺乏团队精神，团队不和，经验或能力不足，计划不周', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/three-of-pentacles-meaning-tarot-card-meanings', 'Pents03.jpg');
INSERT INTO `tool_tarot_card` VALUES (69, 'Four of Pentacles 星币四', '这张牌描绘了一个坐在凳子上的人，他正以一种非常紧张和防御性的姿态守护着他的星币，似乎很害怕失去它们。一个星币在他的头上岌岌可危，另一个星币被他自己的双手紧紧攥住，还有两个星币被稳稳地踩在他脚下。他要确保绝对没有人能碰他的星币。显然，他无法移动，因为他要紧紧守护这些星币。换句话说，他被限制了行动，因为他对自己的财产抓得太紧。对某些人来说，金钱并不总是带来自由。', '占有欲，不安全感，警戒，囤积，吝啬，稳定，安全，储蓄，物欲，财富，节俭，守成', '慷慨，给予，支出，开放，财务不安全，乱花钱，亏损', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/four-of-pentacles-meaning-tarot-card-meanings', 'Pents04.jpg');
INSERT INTO `tool_tarot_card` VALUES (70, 'Five of Pentacles 星币五', '和其它牌组的五一样，星币五象征着逆境。它描绘了两个在大雪中行走的人。他们不仅寒冷，而且还生病、贫穷、疲惫和饥饿。看起来他们似乎缺乏生活的基本必需品。牌上一个人拄着拐杖，而另一个人则用披肩遮住头部，赤脚走在雪地上。背景是一面黑色的墙，上面有一扇彩色玻璃窗，窗上有五个五角星的图案，说明这是一座教堂。通常情况下，星币五是一个坏兆头。然而，不用着急，因为它的含义可以根据它在牌阵中的位置而发生改变。', '困难，损失，被抛弃感，逆境，挣扎，失业，疏远，耻辱，相互扶持', '混乱，毁灭，绝望；化险为夷，克服逆境', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/five-of-pentacles-meaning-tarot-card-meanings', 'Pents05.jpg');
INSERT INTO `tool_tarot_card` VALUES (71, 'Six of Pentacles 星币六', '在星币六的图像中，有一个身穿红色长袍的人。红色象征的是这个人的地位和财富。他的一只手拿着一个天平，代表着平等和公正。这让我们想起了正义塔罗牌，它暗示着因果报应、因果关系，以及种瓜得瓜，种豆得豆的道理。他的另一只手正在给两个跪在他脚下的乞丐发钱。星币六暗示你有足够的安全感，可以在不影响自己稳定的情况下给予他人。', '慷慨，慈善，帮助，支持，分享，感恩', '吝啬，过于慷慨，有条件的好处，不平等，勒索', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/six-of-pentacles-meaning-tarot-card-meanings', 'Pents06.jpg');
INSERT INTO `tool_tarot_card` VALUES (72, 'Seven of Pentacles 星币七', '星币七描绘了一个年轻人，他似乎暂时停下了艰苦的劳动，欣赏起花园里的果实和花朵。他撑在他的铲子上，欣赏着挂在郁郁葱葱的植物上的七个星币。他把头伏在手上的样子似乎暗示了一种疲劳——他努力工作以确保今年的庄稼是个好收成。因为他专注于长期目标，所以他现在不能收获他的成果，只能为自己保留一个星币，而继续培育其他的。通过他的努力，他希望他的作物能够持续结果。', '收获，回报，结果，成长，进步，毅力，耐心，计划，耕耘', '未完成的工作，拖延，缺少努力，浪费，缺乏成长，挫折，急躁，回报不令人满意，还未到收获的时候', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/seven-of-pentacles-meaning-tarot-card-meanings', 'Pents07.jpg');
INSERT INTO `tool_tarot_card` VALUES (73, 'Eight of Pentacles 星币八', '星币八描绘了一个年轻人在八枚金币上刻下五角星的形状。这张牌象征着一个完全专注于自己所做事情的人。背景中远处的小镇意味着他成功地将自己与使人分心的事物隔离开来，这样他就能完全专注于自己的任务。星币八指的是你生活中专心致志地完成某项任务的时刻。你完全沉浸其中，并致力于表现出最好的水平。', '技巧，才能，勤奋，专注，高标准，精益求精，勤学苦练，付出', '低质量，工作仓促，偷工减料，声誉不佳，缺乏动力，平庸，懒惰，水平低下，无前途的工作，过于劳累', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/eight-of-pentacles-meaning-tarot-card-meanings', 'Pents08.jpg');
INSERT INTO `tool_tarot_card` VALUES (74, 'Nine of Pentacles 星币九', '星币九描绘了一个在葡萄园中的女人。这个女人穿着一件豪华的长裙，上面装饰着向日葵。她似乎是一个富人家的一员，因为在远处的背景中，我们看到了一座城堡。一只猎鹰安详地坐在她的左手上玩耍。她身后的葡萄树上挂满了葡萄和金币，表明她的事业非常成功，取得了丰收。', '成功，成就，休闲，物质保障，独立，物质或精神富足，生活充实，付出收到回报，自信', '戒备心强，生活无度，经济困难，生活来源不稳定，乱花钱，肤浅', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/nine-of-pentacles-meaning-tarot-card-meanings', 'Pents09.jpg');
INSERT INTO `tool_tarot_card` VALUES (75, 'Ten of Pentacles 星币十', '星币十中，有一位老人坐在通往一个大庄园的拱门里。他似乎是一家之主，因为他周围都是年轻的亲人。他穿着一件长袍，上面有月牙和葡萄藤的装饰——表示精神和物质的结合。他带着他的家人和狗。在他前面有一对幸福的夫妇，估计是他的后辈。在这对夫妇后面有一个俏皮的孩子，是他的孙子。孩子俏皮地伸出手去抚摸狗。', '遗产，根基稳固，家庭，祖先，继承，富裕，稳定，持久的事业', '家庭纠纷，破产，债务，转瞬即逝的成功，财产纠纷，不稳定，打破传统', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/ten-of-pentacles-meaning-tarot-card-meanings', 'Pents10.jpg');
INSERT INTO `tool_tarot_card` VALUES (76, 'Page of Pentacles 星币侍卫', '星币侍卫描绘了一个青年独自站在一片宽阔的田野上，周围长满了鲜花。在背景中，你还会注意到几棵郁郁葱葱的树和一块开垦过的田地。这个年轻人似乎走得很慢，并且忽视了周围的环境，因为他的注意力完全被他手中的星币所吸引。他沉迷于星币所代表的一切：野心、安全、财富、自然和感性。星币侍卫象征着一个脚踏实地、忠诚和勤奋的人。', '雄心勃勃，勤奋，目标导向，计划性强，始终如一，勤奋好学，脚踏实地，忠诚，可靠，亲近自然', '愚蠢，不成熟，不负责任，懒惰，心浮气躁，成绩不佳，拖延，错过机会，前景灰暗', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/page-of-pentacles-meaning-tarot-card-meanings', 'Pents11.jpg');
INSERT INTO `tool_tarot_card` VALUES (77, 'Knight of Pentacles 星币骑士', '与星币牌阵的其他宫廷牌一样，星币骑士的主题围绕着工作、努力和责任。星币骑士坐在田野里的一匹黑马上。不像其他骑士总是去参加这样那样的冒险，这位骑士决定留在家里，专注于耕种田地。他在为下一次收获做准备——他相信这片土地是他发挥的最佳场所。在他的手中，有一枚金币，他凝视着它。在他的眼中，我们看到了一种审慎的考虑。也许他正梦想着它能给他带来什么。其他骑士可能认为他有点无聊，但这位骑士更关心他的王国的长远未来。', '实事求是，可靠，高效，坚忍不拔，慢条斯理，勤奋，投入，耐心，保守', '工作狂，懒惰，沉闷，无聊，被动，小气，不负责任，赌徒，风险投资', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/knight-of-pentacles-meaning-tarot-card-meanings', 'Pents12.jpg');
INSERT INTO `tool_tarot_card` VALUES (78, 'Queen of Pentacles 星币王后', '星币女王描绘了一个美丽的女人坐在一个花纹繁复的宝座上，手里拿着一枚金币。她周身围绕着美丽的花木和绿色的花圃。她的宝座上装饰着各种大地的野兽，将她与自然和丰收紧密联系在一起。在牌的底部右侧，一只兔子好像要跳出牌框，这象征着她充盈的能量和生育能力。因此，星币女王描绘了某种程度的成功和繁荣。', '慷慨、有爱心，养育，恋家，良好的商业意识，理财，实用主义，温柔宽厚，安慰，热情，繁荣', '自私，嫉妒，缺乏安全感，贪婪，物质(adj.)，严苛，自以为是', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/queen-of-pentacles-meaning-tarot-card-meanings', 'Pents13.jpg');
INSERT INTO `tool_tarot_card` VALUES (79, 'King of Pentacles 星币国王', '星币国王指的是一个野心勃勃、物质上满足和世俗上成功的人。星币国王坐在一个装饰着葡萄藤和牛头的宝座上，他身上的长袍上绣着许多葡萄藤的图案。他看起来很成熟，很有威严。他的周围还有不同类型的植物、藤蔓和花朵，描绘了这位国王在物质方面取得的成功。他的右手拿着权杖，左手拿着刻有五角星的硬币。在他身后可以看到城堡，这象征着他的决心和努力。', '富足，繁荣，安稳，雄心勃勃，持之以恒，勤奋刻苦，事业有成，慷慨，稳重，可靠', '贪婪，自私，物质(adj.)，大男子主义，理财失败，赌徒，占有欲强', 'https://labyrinthos.co/blogs/tarot-card-meanings-list/king-of-pentacles-meaning-tarot-card-meanings', 'Pents14.jpg');

-- ----------------------------
-- Table structure for tool_tarot_spread
-- ----------------------------
DROP TABLE IF EXISTS `tool_tarot_spread`;
CREATE TABLE `tool_tarot_spread`  (
                                      `id` int NOT NULL AUTO_INCREMENT,
                                      `name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
                                      `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
                                      `card` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
                                      PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 45 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tool_tarot_spread
-- ----------------------------
INSERT INTO `tool_tarot_spread` VALUES (27, '三张牌占卜法', '通用型占卜牌阵,可以自由定义。应用在很多场合,不受约束的占卜相关事物,或用来分析独立事情的某个方面。', '1.2.3.此牌阵需要综合三张牌的牌意共同理解');
INSERT INTO `tool_tarot_spread` VALUES (28, '时间流牌阵', '平行流向的时间解析法,纯粹的时间流向。应用于空间维度上的时间占卜,恍若流淌的时间从过去延伸到未来,事件平铺于时间毯之上。', '1.过去 2.现在 3.未来');
INSERT INTO `tool_tarot_spread` VALUES (29, '圣三角牌阵', '梳理问题前因后果,是时间流的变形,更注重事物内在原因,而非单纯时间流向,尤其适合根据因由来判断事物。', '1.过去的原因 2.问题的现状 3.将来的结果');
INSERT INTO `tool_tarot_spread` VALUES (30, '四元素牌阵', '通过四元素了解问题多方面的状况。从感性、理性、物质、行动四方面透彻审视,多重角度出发,进而了解问题实质。', '1. 行动与信心的暗示 2.现实与金钱的暗示 3.理性与决策的暗示 4.感情与感性的暗示');
INSERT INTO `tool_tarot_spread` VALUES (31, '恋人金字塔', '简洁直接,涵盖两人相恋原始要素。适合恋人情侣间的占卜,牌面一出明了易懂,是占卜爱情方面常用的牌阵。', '1.你自己 2.你的恋人 3.彼此的关系 4.未来的发展');
INSERT INTO `tool_tarot_spread` VALUES (32, '爱情大十字', '注重内心情感,主要应用于情侣之间,善于洞悉彼此关系中的情感状况并分析结果。', '1.自己目前的心情和想法 2.对方目前的心理和态度 3.彼此现在的状况 4.目前周遭的环境情况 5.彼此关系最后的结果');
INSERT INTO `tool_tarot_spread` VALUES (33, '寻找对象牌阵', '适合单身人士占卜,可用来健全意中人的愿景,帮助自己确定目标。不知道自己想要什么,不妨问一问这个牌阵吧。', '1.你的心情和处境 2.你希望追求的对象 3.你不喜欢的对象 4.你采取的行动 5.未来的发展');
INSERT INTO `tool_tarot_spread` VALUES (34, '爱情树牌阵', '适合溯本求源 & 寻找症结。适合恋爱遇到困境时做占卜,寻找潜在原因,改善感情关系。当你遇到感情困境,可用本牌阵突出重围。', '1.自己的想法 2.过去的原因 3.现在的建议 4.未来的志向 5.潜在的影响');
INSERT INTO `tool_tarot_spread` VALUES (35, '吉普赛牌阵', '适合婚姻、爱情、恋爱、感情方面的占卜,探索彼此内心想法,找到合适的相处方式。浪漫、奔放的吉普赛牌阵,是你释放情感困兽的首选。', '1.对方目前的想法 2.自己目前的状况 3.与对方相处才去的方式 4.目前的周遭情况 5.关系最后的结果');
INSERT INTO `tool_tarot_spread` VALUES (36, '二选一牌阵', '适合在两种情况中选择其中一种,可用于感情、事业、学业方面的占卜,用途相对广泛。在自己犹豫不决的时候,二选一牌阵是你最棒的选择。', '1.问题的现状 2.选择A的发展 3.选择B的发展 4.选择A的结果 5.选择B的结果');
INSERT INTO `tool_tarot_spread` VALUES (37, '财富之数', '象征财富的生成,可揭示财富脉搏,对求财有积极的指导意义。如果你想了解自己的财富指数,尝试下这个牌阵吧。', '1.生长的根基 2.依赖的能量 3.遇到的阻碍 4.潜在的危险 5.最终的高度');
INSERT INTO `tool_tarot_spread` VALUES (38, '维纳斯牌阵', '适合婚姻、恋爱方面的未来指向占卜,是分析爱情未来的牌阵,可以看到双方未来的状况。如果你想占卜自己爱情的未来,维纳斯牌阵是首推的选择。', '1.自己的真实想法 2.对方的真实想法 3，彼此关系对自己的影响 4.彼此关系对对方的印象 5.你们将会遇到的障碍 6.最后的结果 7.将来自己的心情 8.将来对方的心情');
INSERT INTO `tool_tarot_spread` VALUES (39, '周运势牌阵', '是周运占卜的专用牌阵,适用于周运占卜,可以占卜下一周的运势。亦可以应用在有七天期限的占卜中,不影响效果。', '运势：1.第一天 2.第二天 3.第三天 4.第四天 5.第五天 6.第六天 7.第七天');
INSERT INTO `tool_tarot_spread` VALUES (40, '六芒星牌阵', '判断事情走向,有积极的指导意义,可分析潜意识与显意识的表达,有着极强的窥视未来能力。如果你想真正的预测未来,仔细分析、了解、感受六芒星牌阵吧。', '1.问题过去的状况 2.问题现在的状况 3，问题未来的状况 4.解决问题的对应策略 5.周遭的环境情况 6.本人的心理态度。7.事物的最终结果');
INSERT INTO `tool_tarot_spread` VALUES (41, '情人复合牌阵', '通过对照彼此内心感受,揭开对方扑朔迷离的面纱。如果你仍念念不忘,觉得彼此前缘未了,打开这个牌阵吧。', '1.你和对方的过去 2.你现在的状况 3.对方现在的状况 4.你对复合的感受 5.对方对复合的感受 6.阻碍你的 7.帮助你的 8.你不知道的重要事情 9.整体的结果');
INSERT INTO `tool_tarot_spread` VALUES (43, '别人的爱', NULL, '第1.张和第2.张综合解释 在这段关系中 他所喜欢的部分\n第3.和第4.张综合解释在这段关系中 她所喜欢的部分\n第5.和第6.张综合解释在这段关系中 他不喜欢的部分\n第7.和第8.张综合解释在这段关系中 她不喜欢 的部分\n第9.和第10.张综合解释在这段关系中 他想要看见什么样的事情发生\n第11.和第12.张综合解释在这段关系中 她想要看见什么样的事情发生\n第13.和第14.张综合解释 结果：这段关系当前的处境');
INSERT INTO `tool_tarot_spread` VALUES (44, '单卡占卜', NULL, '1.单卡进行解析');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `phone_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                         `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                         `reward_balance` int UNSIGNED NOT NULL DEFAULT 0,
                         `recharge_balance` int UNSIGNED NOT NULL DEFAULT 0,
                         `check_in` tinyint(1) NOT NULL DEFAULT 0,
                         `group_id` int NOT NULL DEFAULT 1,
                         `group_expiration_time` timestamp NULL DEFAULT NULL,
                         PRIMARY KEY (`id`) USING BTREE,
                         UNIQUE INDEX `u_phone_number`(`phone_number` ASC) USING BTREE,
                         INDEX `f_group_id`(`group_id` ASC) USING BTREE,
                         CONSTRAINT `group_id` FOREIGN KEY (`group_id`) REFERENCES `user_group` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 5039 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------

-- ----------------------------
-- Table structure for user_group
-- ----------------------------
DROP TABLE IF EXISTS `user_group`;
CREATE TABLE `user_group`  (
                               `id` int NOT NULL AUTO_INCREMENT,
                               `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                               `check_in_point` int UNSIGNED NOT NULL,
                               `price` int UNSIGNED NULL DEFAULT NULL,
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 778 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_group
-- ----------------------------
INSERT INTO `user_group` VALUES (1, '青铜', 20, NULL);
INSERT INTO `user_group` VALUES (2, '白银', 40, 400);
INSERT INTO `user_group` VALUES (3, '黄金', 80, 800);
INSERT INTO `user_group` VALUES (4, '铂金', 120, 1200);
INSERT INTO `user_group` VALUES (5, '钻石', 200, 2000);

SET FOREIGN_KEY_CHECKS = 1;