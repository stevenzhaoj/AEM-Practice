[TOC]

# 32、HTL Sightly（10）Sly tag & comments

这一节中简介如何使用Sly标签和注释标签实现代码的注释。

## Sly tag

新增组件代码

```html
<h1>==== sly tag & comment ====</h1>
<h2>==== sly tag ====</h2>
<div data-sly-use.byline="com.adobe.aem.guides.wknd.core.models.Byline"></div>
<sly data-sly-use.byline="com.adobe.aem.guides.wknd.core.models.Byline"></sly>
<sly data-sly-use.byline="com.adobe.aem.guides.wknd.core.models.Byline" data-sly-unwrap="${false}"></sly>
<sly data-sly-use.byline="com.adobe.aem.guides.wknd.core.models.Byline" data-sly-unwrap="${!wcmmode.edit}"></sly>
```

查看效果

- Edit模式

  ![image-20230504130020140](./11_sly-tag%20&%20comment.assets/image-20230504130020140.png)

  - 第一行：用div标签时，如果没有内容，在页面中显示一个空白div标签
  - 第二行：用sly标签时，如果没有内容，在页面不显示
  - 第三行：使用data-sly-unwrap为false时，在页面中显示空白sly标签
  - 第四行：当前为编辑模式，!wcmmode.edit为false，则显示空白sly标签

- View as publish模式

  ![image-20230504130746784](./11_sly-tag%20&%20comment.assets/image-20230504130746784.png)

  - 第四行：当前为publish模式，!wcmmode.edit为true，则不显示空白sly标签

## Comments

新增组件页面代码

```html
<h2>==== comment ====</h2>
<!--/*<div data-sly-use.byline="com.adobe.aem.guides.wknd.core.models.Byline">123</div>*/-->
    <!--<div data-sly-use.byline="com.adobe.aem.guides.wknd.core.models.Byline"></div>-->
```

查看效果

![image-20230504131248522](./11_sly-tag%20&%20comment.assets/image-20230504131248522.png)

- 第一行：使用了`<!--/* ·`标签对代码进行注释，在页面中不渲染div元素
- 第二行：使用`<!-- -->`标签对代码进行注释，在页面中渲染div元素

**NOTE：需要注意`<!--/*`和`*/-->`中间不能有空格**
