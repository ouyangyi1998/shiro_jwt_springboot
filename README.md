# shiro_jwt_springboot
通过jwt对于token进行重写，实现登录权限校验
- shiro对于权限进行控制
- 两个表user和role 
- user表 id username password role permission ban默认为0 
- role表 id role permission
- 先通过@RestContollerAdvice,再通过@ExceptionHandler来处理异常，httpstatus可以获得错误的枚举类型
- admin可以实现获得user和ban的特性
- 利用postman进行操作， 先登录后获得token，放置token，再进行相关操作
- 在filter中设置 /** jwt 实现所有请求通过jwt 
- 通过DefaultSubjectDAO 对于shiro自带session进行关闭
- 
