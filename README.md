领域模型的生命周期；
http封装及各微服务之间调用；
简单auth与currentUser context封装；

各模块之间依赖： 
  包括DTO依赖，interface和abstract设计，复杂功能封装依赖。
  现采用”提取为common“的方式进行依赖，但实际上最优解应为”各个微服务都应提出自己的lib作为jar包供其他上游微服务依赖使用“，
  相较于上述方法可以做到解耦及更细微的版本控制。
