
####oracle数据库导出表结构
记录一下从oracle数据库导出表结构信息
代码不完整基本功能已经足够。
需要的ojdbc8.jar 在lib目录下
需要自己添加至maven仓库
>> mvn install:install-file -Dfile=ojdbc8.jar -DgroupId=com.oracle -DartifactId=ojdbc8 -Dversion=12.2.0.1 -Dpackaging=jar
