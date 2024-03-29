[TOC]

# 33、Adaptive Forms（1）Introduction

从这一节开始，介绍AEM的自适应表单。在AEM中，本身包含JCR这样的内容数据存储方式，也支持关系型数据Mysql的集成。在这一部分的内容中，主要介绍如何使用Adaptive Forms集成MySQL来进行数据的访问。

## Adaptive Forms介绍

Adaptive Forms功能提供了强大的表单构建解决方案。

1. drag and drop操作简单快速构建表单:可以拖放多种表单组件构建出复杂的响应式表单,无需手工编码。
2. 移动优先响应式设计:表单可以根据屏幕大小自动调整布局,适配移动端和桌面端,提供良好的用户体验。
3. 数据模型集成:表单可以绑定到AEM的数据模型,提交表单会自动更新数据模型,实现表单驱动的内容管理。
4.  Workflow集成:表单提交可以直接触发AEM Workflow, setting多种业务流程自动化。
5. 验证与条件表达式:提供多种验证方式校验表单数据,支持设置条件判断逻辑,动态显示/隐藏组件或字段。 
6. Theme编辑器:提供主题编辑器,可以通过拖放组件构建出自定义的表单主题。
7. 元数据框架:支持元数据框架,可以在表单中捕获内容的元数据信息。
8. 嵌入组件:可以在页面中以组件形式嵌入Adaptive Form,并设置各种属性控制其行为。
9. 跨渠道输出:一份表单定义可以生成网页表单、PDF表单、移动 APP中的表单等多渠道输出。 
10. Reporting和分析:通过报表可以实时跟踪 Adaptive Form的使用情况,包括提交量、完成率、放弃率等。

## Adaptive Form三部分组成

1. 表单容器:包括表单属性配置、主题选择、提交动作设置等。
2. 表单组件:包括文本框、按钮、选择框、图片等20+种常用组件。
3. 组件属性:每个组件都有对应的属性窗口,用于设置单个组件的各项属性。

## Adaptive Form集成支持

Adaptive Form不只是支持Mysql，也支持其他的关系型数据库，如Access、Oracle、SQL Server，也支持文档类型，如XML等
