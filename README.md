# Эконом
Pet-project, направленный в первую очередь на изучение новых подходов и новых инструментов.

#### Disclaimer
В проекте в немалом количестве присутствуют огрехи и компромисные решения. Некоторые помечены TODO-комментариями, а некоторые нет.
Просьба не расценивать код как эталон, а просто по необходимости примечать для себя некоторые решения.
Также будет большая благодарность, если подкинете идей, как можно улучшить код =) 

# Jetpack Compose
В проекте используется JetpackCompose + SingleActivity  
Отсутствуют полностью фрагменты  

### Навигация
Навигация используется стандартная для моих проектов - самописное решение с Destination и инжектом routers во Presentation-слой  
Для подхода с использованием Compose навигация была адаптирована.
Используется самописный примитивный стек навигации.

###### Реализация самописной навигации под стандартные Fragmet
[![Repository](https://github.com/RasM24/Rasm24/blob/main/icon/RepositorySamples.svg)](https://github.com/RasM24/Samples-Sketchpad/tree/master/screen-navigation)

###### Реализация самописной навигации, адаптированной под Compose
[![Repository](https://github.com/RasM24/Rasm24/blob/main/icon/RepositorySamples.svg)](https://github.com/RasM24/Samples-Sketchpad/tree/master/screen-compose-navigation)

### Custom View
Jetpack Compose на момент написания проекта предлагает в большинстве своем примитивные Composable-View  
Для реализации уже стандартных компонентов пришлось писать код с 0, например различные варианты InputTextField в `TextFieldComposable.kt`  

Для других компонентов, для более удобного использования стилизованных текстов были реализованы вариации Text в `TextComposable.kt`  

Для соответствия Slot Api были примеры с переопределением LocalProvider. Используется для того, чтобы локально, в рамках одной или нескольких composable можно было переопределить styles/colors/shape/alpha/appearance.

### Compose Effect / ComposeState
Для запуска SnackBar использовался стандартный инструмент LaunchEffect, который по сути является оберткой над Coroutine, адаптированной под использование в compose.  
  
Для запуска BottomSheet пришлось использовать экспериментальный код, аналог Scaffold, только с модальным нижним диалогом.
Имхо такой вариант очень некрасив и неудобен. Соответственно и реализовать его адекватно (с точки зрения стройности кода) его не получилось, несмотря на то, что функционал полностью работает

### Gradle
В данном проекте в рамках эксперимента был совершен переход на Kotlin Gradle Scripts.
Новый функционал достаточно интересен. При этом скорость сборки как минимум такая же, а в некоторых сценариях даже выше, чем при использовании gradle groovy.

Однако в процессе реализации возникла проблема с таской clean - которая вроде и выполняется успешно, но при этом выдает ошибку на новый build-модуль
Именно по этой причине код не залит пока что в develop, а ждет своего часа в ветке [`improvement/gradle-refactor`](https://github.com/RasM24/App-Econom/tree/improvement/gradle-refactor)

# Скрины
![econom_1](https://user-images.githubusercontent.com/42086955/120317339-b3e1b380-c308-11eb-98f0-5664f19f1393.png)
![econom_2](https://user-images.githubusercontent.com/42086955/120317342-b512e080-c308-11eb-98ba-8abf7b53f764.png)  
  
![econom_3](https://user-images.githubusercontent.com/42086955/120317344-b512e080-c308-11eb-8953-c4d347f11482.png)
![econom_4](https://user-images.githubusercontent.com/42086955/120317345-b5ab7700-c308-11eb-8b37-01a674eefb3f.png)  
  
![econom_5](https://user-images.githubusercontent.com/42086955/120317350-b5ab7700-c308-11eb-94a4-0269ae837b2b.png)
![econom_6](https://user-images.githubusercontent.com/42086955/120317351-b6440d80-c308-11eb-9fee-af077d8dfabf.png)  
