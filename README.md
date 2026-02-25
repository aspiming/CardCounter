# CardCounter - 扑克记牌器

一个简洁易用的安卓记牌器应用，支持**普通扑克**和**掼蛋**两种模式的记牌需求。你可以自由选择显示的牌面、调整扑克副数，所有设置都会自动保存。

## ✨ 功能特点

- 🃏 **普通扑克记牌**
  - 支持 3 到 A、小王、大王共 15 种牌面
  - 可调整牌副数（一副为 4 张，大小王各 1 张）
  - 可编辑要显示的牌型（例如隐藏 3-10）
  - 实时显示每张牌剩余数量，点击“+”/“-”快速调整

- 🎲 **掼蛋记牌**
  - 包含 2 到 A、级牌、小王、大王、红心共 17 种牌面
  - 初始数量自动计算（2-A 各 8 张，级牌 6 张，大小王各 2 张，红心 2 张）
  - **固定表头**设计：牌名、剩余、上家、自己、下家、对家
  - 点击蓝色数字按钮，自动增加对应玩家出牌计数，同时减少该牌剩余
  - 可编辑要显示的牌型，并**自动保存**下次启动生效

- 💾 **数据持久化**
  - 所有设置（副数、显示牌型、掼蛋牌型）均使用 SharedPreferences 本地保存

## 📱 截图

![e173abe2b507574c9def84f3fdfc889d](https://github.com/user-attachments/assets/79221ccb-02f1-4ef0-91a6-5484d324c4d6)
![2d0277ce0588d66564130e66e66a15b8](https://github.com/user-attachments/assets/c6300505-173a-4e8d-ad27-69ce5a251a4d)

## 🚀 如何安装

1. 在 [Releases](https://github.com/aspiming/CardCounter/releases) 页面下载最新的 APK 文件
2. 将 APK 传输到安卓手机，点击安装（若提示“禁止安装未知应用”，请在设置中允许）
3. 打开应用即可开始记牌

## 🛠️ 开发说明

本项目使用 **Kotlin** 编写，基于 Android 官方架构（ViewBinding + LiveData + ViewModel）。UI 采用 Material Design 风格，布局适配横竖屏。

### 主要技术栈

- 语言：Kotlin
- 架构：MVVM (ViewModel + LiveData)
- 数据持久化：SharedPreferences
- UI：RecyclerView + 自定义 Adapter

## 📄 许可证

本项目采用 MIT 许可证，详情请查看 [LICENSE](LICENSE) 文件。

## 🤖 致谢

本项目的部分代码由 AI 助手（DeepSeek）辅助生成。

---

**如有任何问题或建议，欢迎提交 Issue 或 Pull Request！**
