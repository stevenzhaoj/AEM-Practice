[TOC]

# 36、Adaptive Forms（4）Create Adapt Form

DataModel创建好后，就可以进行数据的获取和存储。

## Data Model服务配置

要让Data Model生效，还需要进行Services的配置。打开Data Model页面，将model和service绑定，选中Model后点及Edit Properties按钮，在右侧编辑栏中对ReadService和WriteService进行编辑，在ReadService中将Binding To选项更改为Literal，Binding Value中输入id

![image-20230515194012321](./04_CreateAdaptForm.assets/image-20230515194012321.png)

## 创建Adapt Form Template

进入Template管理页面

![image-20230515194447879](./04_CreateAdaptForm.assets/image-20230515194447879.png)

点击Create按钮，选中Adaptive Form标准模板

![image-20230515194526222](./04_CreateAdaptForm.assets/image-20230515194526222.png)

输入模板名称后，进行编辑

![image-20230515194608053](./04_CreateAdaptForm.assets/image-20230515194608053.png)

将左侧的组件拖入到右侧编辑栏中，生成如下编辑信息

![image-20230515194705060](./04_CreateAdaptForm.assets/image-20230515194705060.png)

选中Adapt Form组件，点击右侧的设置按钮

![image-20230515195344983](./04_CreateAdaptForm.assets/image-20230515195344983.png)

在Data Model中选中Form Data Model和之前创建的Data Model

![image-20230515195503802](./04_CreateAdaptForm.assets/image-20230515195503802.png)

在Submission选项中，配置Submit Action和Data Model To submit，点击对勾保存

![image-20230515195559621](./04_CreateAdaptForm.assets/image-20230515195559621.png)

再对所有的属性进行配置（id、name、email、gender）

![image-20230515195655439](./04_CreateAdaptForm.assets/image-20230515195655439.png)

## 创建Adapt Form

进入Forms配置页面

![image-20230515194252487](./04_CreateAdaptForm.assets/image-20230515194252487.png)

进入Forms选项卡，点击Create按钮，创建Adapt Form

![image-20230515194320209](./04_CreateAdaptForm.assets/image-20230515194320209.png)

进入模板选择页面，选择刚创建好的模板

![image-20230515194857976](./04_CreateAdaptForm.assets/image-20230515194857976.png)

就可以看到生成的Form内容

![image-20230515194925071](./04_CreateAdaptForm.assets/image-20230515194925071.png)

输入内容进行测试

![image-20230515194956995](./04_CreateAdaptForm.assets/image-20230515194956995.png)

点击submit按钮后会跳转到信息提示页面

![image-20230515195115350](./04_CreateAdaptForm.assets/image-20230515195115350.png)

查看数据库数据，可以看到已经插入成功

![image-20230515195226640](./04_CreateAdaptForm.assets/image-20230515195226640.png)

