name := "teacher_on_demand"
 
version := "1.0" 
      
lazy val `teacher_on_demand` = (project in file(".")).enablePlugins(PlayJava)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
scalaVersion := "2.11.11"

libraryDependencies ++= Seq( javaJdbc , cache , javaWs )

libraryDependencies += "javax.xml.bind" % "jaxb-api" % "2.1"

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

      