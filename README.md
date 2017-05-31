＃monitor resource
#in linux os, the command line is the following in order to run this sampler applciation.
#java_tomcat is the monitored process name
cd RS17_Sampler
javac -d ./bin -cp ./lib/log4j.jar:./lib/sigar.jar:.  ./src/jwxsampler/*.java
cd bin
java -cp ../lib/log4j.jar:../lib/sigar.jar:. jwxsampler.OutSampler  java_jwxtomcat
