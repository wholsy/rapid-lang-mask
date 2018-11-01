BASE
================
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
<a href="">
  <img alt="Coverity Scan Build Status" src="https://img.shields.io/coverity/scan/8244.svg"/>
</a>


所有项目和基础组件的依赖包。
包含
* **base-core**
   * 管理maven、仓库等信息， 独立依赖.

   [![base-core](https://file-vague.codealy.com/code/yueny/base/base-core.jpg)]()

* **yueny-parent**
   * parent 为  base-core;
   * 管理第三方插件的版本信息;

   [![yueny-parent](https://file-vague.codealy.com/code/yueny/base/yueny-parent.jpg)]()


* **boot-parent**
   * 继承了 spring-boot-starter-parent 1.5.3.RELEASE
   * 管理第三方插件的版本信息;

   [![boot-parent](https://file-vague.codealy.com/code/yueny/base/boot-parent.jpg)]()


版本发布历史
================
1.1.5-RELEASE
最新稳定版
