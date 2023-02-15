# AEM安装及实例
AEM全称**Adobe Experience Management**，是一套基于OSGI、Apache Sling和Java Content Repository技术的完整企业解决方案。传统网站是基于HTML、JS、CSS来构建，也可使用目前流行的各种框架Vue3，React等等。在AEM中，是基于模板、组件、内容组成网站的页面。

## 安装
首先需要从AEM软件下载站[AEM as a Cloud Service downloads](https://experience.adobe.com/#/downloads/content/software-distribution/en/aemcloud.html?fulltext=SDK*&orderby=@jcr%3Acontent%2Fjcr%3AlastModified&orderby.sort=desc&layout=list&p.offset=0&p.limit=16)中下载SDK，但由于AEM-SDK是付费使用的，那必须由公司或个人购买SDK后才可以下载使用。

可以按照自己的需求下载最新版本或之前的版本，下载后的文件名为：aem-sdk-2022.8.8316.20220818T100409Z-220700.zip

解压后目录如下，最主要的文件就是目录里的jar包

![在这里插入图片描述](https://img-blog.csdnimg.cn/b1be21be3eef4e3e9df593289f5d92a8.png)
Mac版和Linux版本都可以找到对应的下载链接
## 启动Author实例
在AEM中，分为Author实例和Publish实例，还有一个Dispatcher组件。

Author实例，顾名思义是作者实例，我们可以在Author实例中进行模板、组件的使用，还可以通过代码实现自定义组件、自定义模板等等功能。开发人员的工作内容也大多集中在Author实例上。

Publish实例是发布实例，我们在Author实例中创建页面、在页面中使用组件并编辑内容后，就可以将Author实例中的页面发布到Publish实例中去。

Dispatcher组件是一个集Web服务器、缓存、CDN等功能于一体的组件。

![在这里插入图片描述](https://img-blog.csdnimg.cn/f483a5f8d30e4c17802d7695cafc6695.png)
前面少了一个Author向Publish发送数据的图。上图就是用户访问页面后的一系列底层逻辑。

具体的使用后面都会讲到。

接下来启动Author实例，创建author目录，并把上面解压出来的jar包拷贝过来并改名为aem-author-p4502.jar，license.properties最新版本可以不需要此文件，crx-quickstart目录为author实例启动后自动生成的内容。

jar包名字的命名方式，aem-实例名(author/publish)-p端口号.jar

![在这里插入图片描述](https://img-blog.csdnimg.cn/c7ff365e87e34cc38fa652031acaebe6.png)
在当前目录使用CMD，启动author实例

```shell
java -jar aem-author-p4502.jar -&
```
输入命令后会让用户输入登录author实例的账号和密码

![在这里插入图片描述](https://img-blog.csdnimg.cn/9db5ed3148e447a98f93259783a724a7.png)

启动后会生成crx-quickstart目录，目录结构如下

![在这里插入图片描述](https://img-blog.csdnimg.cn/f69846e02dd7457b89e16379c6fce9d0.png)

需要等1-3分钟，第一次启动会稍微慢一点，浏览器会自动打开http://localhost:4502

![在这里插入图片描述](https://img-blog.csdnimg.cn/a14a25ab6f134d14b1803af680b85e45.png)

如果你的操作系统是英文，那么这个界面就是英文界面。

输入刚才在命令框中输入的账号密码

![在这里插入图片描述](https://img-blog.csdnimg.cn/048fac8e13dc448885f3e717255a67b0.png)
进入AEM的author实例首页，我们就可以在这里进行开发工作。

author实例的安装就到此结束了，下一章会讲解如何使用AEM官方提供的WKND示例进行自定义模板和组件的开发。