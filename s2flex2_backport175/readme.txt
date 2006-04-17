2006/04/15
mvn install:install-file -Dfile=C:\workspace\s2flex2\target\s2flex2-B5.jar -DgroupId=org.seasar.flex2 -DartifactId=s2flex2 -Dversion=B5 -Dpackaging=jar

lib以下のは、ant buildに必要なものなのでmavenでannotationcができるようになったら削除します。

2006/04/17
s2flex2に対して
mvn clean install
をおこなって、ローカルにインストールしてもいいかと思います。

