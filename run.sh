cd spring-petclinic/

mvn tomcat7:run >/dev/null 2>&1 &

cd ../

cd graphwalker-petclinic/java-petclinic/

mvn graphwalker:test --log-file log.txt

cd ../

cd ../

cd spring-petclinic/

killall -w java

java -jar ../jars/jacoco/lib/jacococli.jar report jacoco.exec --classfiles ./target/classes/cache --classfiles ./target/classes/dandelion --classfiles ./target/classes/db --classfiles ./target/classes/messages --classfiles ./target/classes/org --classfiles ./target/classes/spring --html coverage

cd ../

cd coverage-display/

mvn clean package

java -jar ./target/coverage-display-1.0-SNAPSHOT.jar


