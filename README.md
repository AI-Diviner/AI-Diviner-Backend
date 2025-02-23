## 介绍
一个AI聊天助手,可以辅助解卦,塔罗占卜,八字命理
## 前端
[AI-Diviner-Web](https://github.com/AI-Diviner/AI-Diviner-Web)
## 部署
1. 请先安装Java21,MySQL8以及Redis
2. 修改`src/main/resources/application.yml`文件将数据库和Redis配置修改为自己的配置
3. 修改`src/main/java/fun/diviner/ai/entity/Special.java`将里面的authSecret修改为自己的
4. 修改`src/main/java/fun/diviner/ai/diviner/ai/AIModel.java`填入自己的模型密钥
5. 将根目录下的data.sql导入到数据库中
6. 修改数据库中的支付配置，往下看[支付配置](#支付配置)
7. 运行`mvn clean package`打包项目
8. 运行`java -jar target/AI-Diviner-1.0.0.jar`启动项目
### 支付配置
首先进入core表中,修改以下字段:
1. `yiPayId`(易支付ID)
2. `yiPayMerchantPrivateKey`(易支付商户私钥)
3. `yiPayPlatformPublicKey`(易支付平台公钥)
4. `yiPayNoticeUrlPrefix`(易支付后端回调前缀)，比如说你的后端API域名是`https://api.diviner.fun`,那么这个字段就是`https://api.diviner.fun`，不要带最后的`/`
5. `yiPayReturnUrl`(易支付前端回调地址)，这个输入你的前端网址即可
> 我这里使用的是[易支付](https://yi-pay.com/)这家的平台，你也可以使用其他的同类型的平台，通常情况下应该只需要修改`src/main/java/fun/diviner/ai/util/yi_pay/YiPay.java`这个中的支付接口网址即可
