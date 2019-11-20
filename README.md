# springboot_hadoop
quiztok hadoop 연동


# pom.xml 변동사항
	<dependency>
		<groupId>jdk.tools</groupId>
		<artifactId>jdk.tools</artifactId>
		<version>1.8.0_202</version>
		<scope>system</scope>
		<systemPath>/usr/local/java/lib/tools.jar</systemPath>
		<!-- <systemPath>C:\Java\jdk1.8.0_202\lib\tools.jar</systemPath> -->
	</dependency>
    
    *배포시 java_home으로 맞춰 줘야함
    
# (오류)Unable to load native-hadoop library for your platform... using builtin-java classes where applicable

* profile 수정
* HADOOP_HOME 추가
* export HADOOP_OPTS="$HADOOP_OPTS -Djava.library.path=$HADOOP_HOME/lib/native" 추가
