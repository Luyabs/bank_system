# 项目文档
## 一. 开发规范
> 1. mapper包内只写SQL语句，每一个xxxMapper类都写对应实体类的SQL语句。含select语句的方法请返回实体类 或 实体类列表，update insert delete请返回boolean表示是否更新成功。
> 2. TCP传输过程需要把实体类通过toString()方法转换成String后，才能通过out.writeUTF()进行发送。同时接收方通过in.readUTF()接收数据后，请对接收到的字符串形式实体类用utils.ToEntity()等工具类的方法转换为实体类。
> 3. client发给server的数据 需要有操作码和数据两部分 操作码是String前三位 数据是实体类 或 实体类列表通过toString()方法得到的。同时server在接收数据时也应分离操作码和数据。
> 4. server层需要实现所有ServerResponse接口的方法 （接口会随着进度增加方法）。
> 5. client层需要实现所有ClientResponse接口内的方法 （接口会随着进度增加方法）。
> 6. 开发前端界面的在监听器中 请调用ClientRequest接口的方法操作数据。

## 二. 项目结构
> 1. mapper: SQL层，通过操作数据库来为上层(server)提供boolean/Entity/List数据。
> 2. server: 服务端层，在与客户端建立连接后，持续监听客户端发来的请求，根据请求去处理数据，并返回处理结果或数据(boolean/Entity.toString()/List.toString())。
> 3. client: 客户端层，客户端层为界面层提供方法，当用户点击按钮时，就调用本层方法，向服务端发送请求，并接收回复，传数据给界面层处理。
> 4. ui: 界面层。
> 5. utils: 工具包，基于java反射，提供了从String转化为Entity/List<Entity>的方法。

## 三. 项目运行
> 1. 运行src/main/resources/create_db.sql建表。
> 2. 修改src/main/resources/SqlConfig.properties中数据库配置
> 3. 同时运行StartServer.java和Login.java。